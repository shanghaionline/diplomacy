package diplomacy.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diplomacy.dao.MessageDao;
import diplomacy.entity.Message;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.status.MessageStatus;
import diplomacy.entity.status.MessageType;
import diplomacy.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	private MessageDao messageDao;

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

	private String makeValidCode() {
		Random r = new Random();
		return String.format("%04d", r.nextInt(10000));
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	
}
