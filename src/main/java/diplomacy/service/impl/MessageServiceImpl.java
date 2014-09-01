package diplomacy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletContext;

import diplomacy.vo.PagerBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import diplomacy.dao.MessageDao;
import diplomacy.dao.UserDao;
import diplomacy.entity.Attachment;
import diplomacy.entity.Message;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.User;
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
		} catch (IllegalStateException | IOException e) {
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
	
}
