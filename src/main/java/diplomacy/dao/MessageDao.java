package diplomacy.dao;

import diplomacy.entity.Message;
import diplomacy.entity.MessageBox;
import diplomacy.entity.MessageMeta;

public interface MessageDao {
	void save(Message message);
	void delete(Message message);
	void refresh(Message message);
	void setMessageMeta(Message message, MessageMeta meta);
	MessageBox putMessageBox(Message message);
	Message getValidCodeMessage(String target);
}
