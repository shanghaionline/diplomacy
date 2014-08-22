package diplomacy.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diplomacy.dao.UserDao;
import diplomacy.entity.User;
import diplomacy.entity.UserMeta;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.UserService;
import diplomacy.util.PasswordUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	@Override
	@Transactional(readOnly = false)
	public User create(String login, String password, UserStatus status) {
		if(userDao.getUserByLogin(login) != null) return null;
		User user = new User();
		user.setLogin(login);
		user.setNicename(login);
		user.setPasswd(PasswordUtil.password(password));
		user.setRegistered(new Date());
		user.setStatus(status);
		userDao.save(user);
		return user;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional(readOnly = false)
	public User handleInvited(Long inviterId, String nicename, String phone, String email) {
		User inviter = userDao.getUserById(inviterId);
		if (inviter == null || inviter.getStatus() != UserStatus.ENABLED) return null;
		User user = new User();
		user.setLogin(UUID.randomUUID().toString());
		user.setPasswd(PasswordUtil.password());
		user.setNicename(nicename);
		user.setInviter(inviter);
		user.setRegistered(new Date());
		user.setStatus(UserStatus.UNVALITED);
		userDao.save(user);
		userDao.setUserMeta(user, new UserMeta("info.phone", phone));
		userDao.setUserMeta(user, new UserMeta("info.email", email));
		userDao.refresh(user);
		return user;
	}
	

	@Override
	public User login(String login, String password) {
		User user = userDao.getUserByLogin(login);
		if (user == null || !PasswordUtil.check(user.getPasswd(), password))
			return null;
		return user;
	}

	@Override
	public User get(Long id) {
		User user = userDao.getUserById(id);
		return user;
	}

	@Override
	public User perm(Long id, String... perms) {
		if (id == null) return null;
		User user = userDao.getUserById(id);
		if (user == null) return null;
		int permsCnt = 0;
		for (String perm : perms) {
			if (user.getMetas().get(perm).getValue().equalsIgnoreCase("PERM_ENABLED"))
				permsCnt += 1;
		}
		if (permsCnt != perms.length) return null;
		return user;
	}
	
}
