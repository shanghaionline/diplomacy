package diplomacy.service;

import diplomacy.entity.User;
import diplomacy.entity.status.UserStatus;

public interface UserService {
	User create(String login, String password, UserStatus status);
	User handleInvited(Long inviterId, String nicename, String phone, String email);
	User get(Long id);
}
