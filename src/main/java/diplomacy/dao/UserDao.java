package diplomacy.dao;

import diplomacy.entity.User;
import diplomacy.entity.UserMeta;

public interface UserDao {
	void save(User user);
	void refresh(User user);
	void delete(User user);
	User getUserByLogin(String login);
	User getUserById(Long id);
	void setUserMeta(User user, UserMeta meta);
}
