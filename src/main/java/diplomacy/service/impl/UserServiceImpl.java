package diplomacy.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diplomacy.dao.UserDao;
import diplomacy.entity.User;
import diplomacy.entity.UserMeta;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.UserService;
import diplomacy.util.PasswordUtil;
import diplomacy.vo.PagerBean;

@Service("userService")
public class UserServiceImpl implements UserService {
    static final String[] PERMISSIONS = new String[]{
            "PERM_HANDLE_INVITATION", "PERM_SEND_SINGLE", "PERM_SEND_MULTIPLE", "PERM_MESSAGE_MEMBER",
            "PERM_MESSAGE_DIRECTOR", "PERM_MESSAGE_CHAIRMAN", "PERM_OPTER_ADMIN"};
    static final Map<String, String[]> GROUP_PERMS = new HashMap<String, String[]>();

    static {
        GROUP_PERMS.put("ADMIN", PERMISSIONS);
        GROUP_PERMS.put("MEMBER", new String[] {"PERM_SEND_SINGLE", "PERM_MESSAGE_MEMBER"});
        GROUP_PERMS.put("DIRECTOR", new String[] {
                "PERM_SEND_MULTIPLE", "PERM_SEND_SINGLE", "PERM_MESSAGE_MEMBER", "PERM_MESSAGE_DIRECTOR"
        });
        GROUP_PERMS.put("CHAIRMAN", new String[] {
                "PERM_SEND_MULTIPLE", "PERM_SEND_SINGLE", "PERM_MESSAGE_MEMBER", "PERM_MESSAGE_DIRECTOR",
                "PERM_MESSAGE_CHAIRMAN"
        });
    }

    private UserDao userDao;

    @Override
    @Transactional(readOnly = false)
    public User create(String login, String password, UserStatus status) {
        if (userDao.getUserByLogin(login) != null) return null;
        User user = new User();
        user.setLogin(login);
        user.setNicename(login);
        user.setPasswd(PasswordUtil.password(password));
        user.setRegistered(new Date());
        user.setStatus(status);
        user.setPhone("123456789");
        user.setEmail("admin@admin.com");
        user.setGroup("ADMIN");
        userDao.save(user);
        userDao.refresh(user);
        refreshGroup(user);
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public User createSystem(String login, String perm) {
        User user = new User();
        user.setLogin(login);
        user.setNicename(login);
        user.setPasswd(PasswordUtil.password());
        user.setRegistered(new Date());
        user.setStatus(UserStatus.SYSTEM);
        user.setPhone("");
        user.setEmail("");
        user.setGroup("");
        userDao.save(user);
        userDao.setUserMeta(user, new UserMeta("MULTIPLE_RECEIVER_GROUP", "PERM_MESSAGE_MEMBER"));
        userDao.refresh(user);
        return user;
    }

    @Override
    public List<User> listUserByPerm(String perm) {
        return userDao.listUserByMeta(perm, "PERM_ENABLED");
    }

    @Override
    public PagerBean<User> queryUser(String query, int page, int size) {
        page = page <= 0 ? 1 : page;
        return userDao.queryUser(query, (page - 1) * size, size);
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
        user.setPhone(phone);
        user.setEmail(email);
        userDao.save(user);
        userDao.refresh(user);
        return user;
    }

    
    @Override
	public PagerBean<User> listUnvalited(int page, int size) {
    	page = page <= 0 ? 1 : page;
        return userDao.listUserByStatus(UserStatus.UNVALITED, (page - 1) * size, size);
	}

    
	@Override
	public PagerBean<User> listUser(int page, int size) {
		page = page <= 0 ? 1 : page;
        return userDao.listUserByStatus(UserStatus.ENABLED, (page - 1) * size, size);
	}

	@Override
    public String postInvited(User user) {
        String checksum = PasswordUtil.invateHash(user.getId());
        return String.format("%d/%s", user.getId(), checksum);
    }

    @Override
    @Transactional(readOnly = false)
    public User passInvited(User admin, User user) {
        if (user.getStatus() == UserStatus.UNVALITED &&
                userDao.getUserByLogin(user.getLogin()) == null &&
                perm(admin, "PERM_HANDLE_INVITATION") != null) {
            user.setStatus(UserStatus.ENABLED);
            userDao.save(user);
            refreshGroup(user);
            return user;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void rejectInvited(User admin, Long userId) {
        User user = userDao.getUserById(userId);
        if (user != null && user.getStatus() == UserStatus.UNVALITED &&
                perm(admin, "PERM_HANDLE_INVITATION") != null) {
            user.setStatus(UserStatus.REJECTED);
            userDao.save(user);
        }
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
        User user = perm(userDao.getUserById(id), perms);
        return user;
    }

    @Override
    public User perm(User user, String... perms) {
        if (user == null) return null;
        int permsCnt = 0;
        for (String perm : perms) {
            UserMeta meta = user.getMetas().get(perm);
            if (meta != null && meta.getValue().equalsIgnoreCase("PERM_ENABLED"))
                permsCnt += 1;
        }
        if (permsCnt != perms.length) return null;
        return user;
    }


    @Override
    public Set<String> perms(String... perms) {
        Set<String> ret = new HashSet<String>(Arrays.asList(perms));
        ret.retainAll(Arrays.asList(PERMISSIONS));
        return ret;
    }

    @Override
    @Transactional(readOnly = false)
    public User refreshGroup(User user) {
        String group = user.getGroup();
        if (group == null || group.isEmpty()) return user;
        Set<String> perms = perms(GROUP_PERMS.get(group));
        Set<String> unperms = perms(PERMISSIONS);
        unperms.removeAll(perms);
        putPerms(user, true, perms);
        putPerms(user, false, unperms);
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public User putPerms(User user, boolean enabled, Set<String> perms) {
        Map<String, UserMeta> metas = user.getMetas();
        String v = enabled ? "PERM_ENABLED" : "PERM_DISABLED";
        for (String perm : perms) {
            UserMeta meta = metas.get(perm);
            if (meta == null) {
                meta = new UserMeta(perm, v);
            } else {
                meta.setValue(v);
            }
            userDao.setUserMeta(user, meta);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public User changePassword(User user, String oldpassword, String password) {
        if (!PasswordUtil.check(user.getPasswd(), oldpassword)) {
            return null;
        }
        user.setPasswd(PasswordUtil.password(password));
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public User saveUserInfo(User user, String nicename, String email) {
        user.setNicename(nicename);
        user.setEmail(email);
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public User changePhone(User user, String phone) {
        user.setPhone(phone);
        userDao.save(user);
        return user;
    }


}
