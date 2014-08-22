package diplomacy.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diplomacy.dao.MessageDao;
import diplomacy.dao.UserDao;
import diplomacy.entity.Message;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.User;
import diplomacy.entity.status.MessageStatus;
import diplomacy.entity.status.MessageType;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
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
		messageDao.getValidCodeMessage(target);
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
			String title, String content, Long attachId) {
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
		messageDao.putMessageBox(msg);
		return null;
	}

	private String makeValidCode() {
		Random r = new Random();
		return String.format("%04d", r.nextInt(10000));
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
