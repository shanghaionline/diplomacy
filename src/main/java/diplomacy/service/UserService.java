package diplomacy.service;

import diplomacy.entity.User;
import diplomacy.entity.status.UserStatus;

public interface UserService {
	User create(String login, String password, UserStatus status);
	User handleInvited(Long inviterId, String nicename, String phone, String email);
	User login(String login, String password);
	User get(Long id);
	User perm(Long id, String... perms);
}
