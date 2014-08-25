package diplomacy.dao;

import diplomacy.entity.Attachment;
import diplomacy.entity.Message;
import diplomacy.entity.MessageBox;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.User;

public interface MessageDao {
	void save(Message message);
	void delete(Message message);
	void refresh(Message message);
	void setMessageMeta(Message message, MessageMeta meta);
	MessageBox putMessageBox(Message message);
	MessageBox putMessageBox(Message message, User receiver);
	Message getValidCodeMessage(String target);
	Attachment putAttachment(Attachment attachment);
}
