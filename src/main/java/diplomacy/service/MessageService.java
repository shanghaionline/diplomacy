package diplomacy.service;

import diplomacy.entity.MessageBox;
import diplomacy.vo.PagerBean;
import org.springframework.web.multipart.MultipartFile;

import diplomacy.entity.Message;
import diplomacy.entity.User;

public interface MessageService {
	Message sendPhoneValidCode(String target);
	boolean checkValidCode(String target, String code);
	boolean checkValidCode(String target, String code, boolean clean);
	Message sendSingleMessage(User sender, String receiver, 
			String title, String content, MultipartFile attachment);
	Message sendMultipleMessage(User sender, String perm, 
			String title, String content, MultipartFile attachment);
    PagerBean<Message> listOutboxByPage(User user, int page, int size);
    PagerBean<MessageBox> listInboxByPage(User user, int page, int size);
    Message readMessage(User user, long msgId);
    Message receiveMessage(User user, long msgId);
}
