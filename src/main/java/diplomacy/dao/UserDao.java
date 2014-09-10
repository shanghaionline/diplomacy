package diplomacy.dao;

import java.util.List;

import diplomacy.entity.User;
import diplomacy.entity.UserMeta;
import diplomacy.entity.status.UserStatus;
import diplomacy.vo.PagerBean;

public interface UserDao {
    void save(User user);

    void refresh(User user);

    void delete(User user);

    User getUserByLogin(String login);

    User getUserById(Long id);

    void setUserMeta(User user, UserMeta meta);

    List<User> listUserByMeta(String key, String value);

    List<User> listUserByMeta(UserStatus status, String key, String value);

    PagerBean<User> queryUser(String query, int offset, int limit);
    
    PagerBean<User> queryUser(UserStatus status, String query, int offset, int limit);
    
    PagerBean<User> listUserByStatus(UserStatus status, int offset, int limit);
    
    
}
