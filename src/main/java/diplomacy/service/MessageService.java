package diplomacy.service;

import diplomacy.entity.Message;

public interface MessageService {
	Message sendPhoneValidCode(String target);
	boolean checkValidCode(String target, String code);
	boolean checkValidCode(String target, String code, boolean clean);
}
