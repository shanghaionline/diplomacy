package diplomacy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;

import diplomacy.entity.*;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.MobileMessageService;
import diplomacy.vo.PagerBean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import diplomacy.dao.MessageDao;
import diplomacy.dao.UserDao;
import diplomacy.entity.status.MessageStatus;
import diplomacy.entity.status.MessageType;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;

@Service("messageService")
public class MessageServiceImpl implements MessageService, ServletContextAware {
    private ServletContext servletContext;
    private MessageDao messageDao;
    private UserDao userDao;
    private UserService userService;
    private MobileMessageService mobileMessageService;

    @Override
    @Transactional(readOnly = false)
    public Message sendPhoneValidCode(String target) {
        String code = makeValidCode();
        Message msg = new Message();
        msg.setMsgType(MessageType.VALIDCODE);
        msg.setTitle("VALID CODE");
        msg.setContent("验证码: " + code);
        msg.setStatus(MessageStatus.UNSEND);
        messageDao.save(msg);
        messageDao.setMessageMeta(msg, new MessageMeta("validcode_target", target));
        messageDao.setMessageMeta(msg, new MessageMeta("validcode_code", code));
        messageDao.refresh(msg);
        return msg;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean checkValidCode(String target, String code, boolean clean) {
        Message codeMessage = messageDao.getValidCodeMessage(target);
        if (codeMessage != null &&
                codeMessage.getMetas().get("validcode_code").getValue().equals(code)) {
            if (clean) messageDao.delete(codeMessage);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean checkValidCode(String target, String code) {
        return checkValidCode(target, code, true);
    }


    @Override
    @Transactional(readOnly = false)
    public Message sendSingleMessage(User sender, String receiver,
                                     String title, String content, MultipartFile attachment) {
        User rec = userDao.getUserByLogin(receiver);
        if (userService.perm(sender, "PERM_SEND_SINGLE") == null ||
                rec == null) return null;
        Message msg = new Message();
        msg.setMsgType(MessageType.SINGLEMSG);
        msg.setSender(sender);
        msg.setReceiver(rec);
        msg.setTitle(title);
        msg.setContent(content);
        msg.setStatus(MessageStatus.READED);
        messageDao.save(msg);
        if (attachment != null) putAttachment(msg, attachment);
        messageDao.putMessageBox(msg);
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public Message sendMultipleMessage(User sender, String perm, String title,
                                       String content, MultipartFile attachment) {
        if (userService.perm(sender, "PERM_SEND_MULTIPLE", perm) == null) return null;
        Message msg = new Message();
        msg.setMsgType(MessageType.MULTIPLE);
        msg.setSender(sender);
        msg.setReceiver(getMultiMessageReceiver(perm));
        msg.setTitle(title);
        msg.setContent(content);
        msg.setStatus(MessageStatus.READED);
        messageDao.save(msg);
        if (attachment != null) putAttachment(msg, attachment);
        messageDao.setMessageMeta(msg, new MessageMeta("MULTIPLE_MESSAGE_PERM", perm));
        for (User receiver : userService.listUserByPerm(perm)) {
            messageDao.putMessageBox(msg, receiver);
        }
        return msg;
    }

    @Override
    public PagerBean<Message> listOutboxByPage(User user, int page, int size) {
        page = page <= 0 ? 1 : page;
        return messageDao.listMessageBySender(user, (page - 1) * size, size);
    }

    @Override
    public PagerBean<MessageBox> listInboxByPage(User user, int page, int size) {
        page = page <= 0 ? 1 : page;
        return messageDao.listMessageBoxByReceiver(user, (page - 1) * size, size);
    }

    private User getMultiMessageReceiver(String perm) {
        List<User> list = userDao.listUserByMeta(UserStatus.SYSTEM, "MULTIPLE_RECEIVER_GROUP", perm);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    private Attachment putAttachment(Message message, MultipartFile attachment) {
        String uri = String.format("/attachment/%d%s",
                System.currentTimeMillis(), getFileSuffix(attachment.getOriginalFilename()));
        String path = servletContext.getRealPath(uri);
        Attachment ret = null;
        try {
            attachment.transferTo(new File(path));
            ret = new Attachment();
            ret.setMessage(message);
            ret.setName(attachment.getOriginalFilename());
            ret.setStatus("ENABLED");
            ret.setUri(uri);
            messageDao.putAttachment(ret);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String getFileSuffix(String filename) {
        int e = filename.lastIndexOf(".");
        if (e == -1) {
            return "";
        }
        return filename.substring(e);
    }


    @Override
    public Message readMessage(User user, long msgId) {
        Message msg = messageDao.getMessageById(msgId);
        if (msg == null || !msg.getSender().getId().equals(user.getId()))
            return null;
        return msg;
    }


    @Override
    @Transactional(readOnly = false)
    public Message receiveMessage(User user, long msgId, boolean setReaded) {
        MessageBox msgBox = messageDao.getMessageBoxById(msgId);
        if (msgBox == null || !msgBox.getReceiver().getId().equals(user.getId()))
            return null;
        if (setReaded && msgBox.getStatus().equals(MessageStatus.UNREAD)) {
            msgBox.setStatus(MessageStatus.READED);
            messageDao.save(msgBox);
        }
        return msgBox.getMessage();
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteMessage(User user, long[] ids) {
        for (long id : ids) {
            Message msg = messageDao.getMessageById(id);
            if (msg != null && msg.getSender().getId().equals(user.getId()))
                messageDao.delete(msg);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMessageBox(User user, long[] ids) {
        for (long id : ids) {
            MessageBox msgBox = messageDao.getMessageBoxById(id);
            if (msgBox != null && msgBox.getReceiver().getId().equals(user.getId()))
                messageDao.delete(msgBox);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void postValidCode(int limit) {
        List<Message> list = messageDao.listUnSendValidCode(limit);
        for (Message item : list) {
            MessageMeta meta = item.getMetas().get("validcode_target");
            if (meta == null) continue;
            try {
                int ret = mobileMessageService.sendMessage(meta.getValue(), item.getContent());
                if (ret == 0) {
                    item.setStatus(MessageStatus.READED);
                    messageDao.save(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void postMessageNotice(int limit) {
        List<MessageBox> list = messageDao.listUnNoticedMessage(limit);
        for (MessageBox item : list) {
            User user = item.getReceiver();
            Message msg = item.getMessage();
            if (user == null || user.getPhone() == null || msg == null) continue;
            try {
                int ret = mobileMessageService.sendMessage(user.getPhone(), msg.getTitle());
                if (ret == 0) {
                    item.setNoticed(true);
                    messageDao.save(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeValidCode() {
        Random r = new Random();
        return String.format("%04d", r.nextInt(10000));
    }


    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMobileMessageService(MobileMessageService mobileMessageService) {
        this.mobileMessageService = mobileMessageService;
    }
}
