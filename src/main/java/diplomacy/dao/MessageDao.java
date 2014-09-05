package diplomacy.dao;

import diplomacy.entity.Attachment;
import diplomacy.entity.Message;
import diplomacy.entity.MessageBox;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.User;
import diplomacy.vo.PagerBean;

public interface MessageDao {
	void save(Message message);
	void save(MessageBox messageBox);
	void delete(Message message);
	void delete(MessageBox messageBox);
	void refresh(Message message);
	void setMessageMeta(Message message, MessageMeta meta);
	MessageBox putMessageBox(Message message);
	MessageBox putMessageBox(Message message, User receiver);
	Message getValidCodeMessage(String target);
	Attachment putAttachment(Attachment attachment);
    PagerBean<Message> listMessageBySender(User user, int offset, int limit);
    PagerBean<MessageBox> listMessageBoxByReceiver(User user, int offset, int limit);
    MessageBox getMessageBoxById(long msgId);
    Message getMessageById(long id);
}
