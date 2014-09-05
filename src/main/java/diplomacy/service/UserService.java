package diplomacy.service;

import java.util.List;
import java.util.Set;

import diplomacy.entity.User;
import diplomacy.entity.status.UserStatus;
import diplomacy.vo.PagerBean;

public interface UserService {
    User create(String login, String password, UserStatus status);

    User createSystem(String login, String perm);

    List<User> listUserByPerm(String perm);

    PagerBean<User> queryUser(String query, int page, int size);

    User handleInvited(Long inviterId, String nicename, String phone, String email);

    String postInvited(User user);

    User passInvited(User admin, User user);

    void rejectInvited(User admin, Long userId);

    User login(String login, String password);

    User get(Long id);

    User perm(Long id, String... perms);

    User perm(User user, String... perms);

    User refreshGroup(User user);

    Set<String> perms(String... perms);

    User putPerms(User user, boolean enabled, Set<String> perms);

    User changePassword(User user, String oldpassword, String password);

    User saveUserInfo(User user, String nicename, String email);

    User changePhone(User user, String phone);
}
