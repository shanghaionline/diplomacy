package diplomacy.service;

import diplomacy.entity.Message;
import diplomacy.entity.User;

public interface MessageService {
	Message sendPhoneValidCode(String target);
	boolean checkValidCode(String target, String code);
	boolean checkValidCode(String target, String code, boolean clean);
	Message sendSingleMessage(User sender, String receiver, 
			String title, String content, Long attachId);
	Message sendMultipleMessage(User sender, String perm, 
			String title, String content, Long attachId);
}
