package diplomacy.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import diplomacy.entity.User;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;
import diplomacy.util.PasswordUtil;
import diplomacy.vo.PagerBean;

@Controller
@RequestMapping("/user")
@SessionAttributes("SessionUserId")
public class UserAct {
	
	private UserService userService;
	private MessageService messageService;
	
	@RequestMapping("/init")
	public String init() {
		userService.create("admin", "admin", UserStatus.ENABLED);
        userService.createSystem("会员", "PERM_MESSAGE_MEMBER");
		return "index";
	}
	@RequestMapping("/groupfresh")
	public String freshGroup(ModelMap model) {
		User user = userService.perm((Long)model.get("SessionUserId"));
		if (user != null) userService.refreshGroup(user);
		return "index";
	}
	
	@RequestMapping("/test")
	public String test(String name, Integer age) {
		System.out.println("=====" + PasswordUtil.invateHash(1L));
		return "index";
	}
	
	@RequestMapping("/get-invite")
	public String getInvite(ModelMap model){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if (user == null) return "common/error";
		String checksum = PasswordUtil.invateHash(user.getId());
		model.addAttribute("user", user);
		model.addAttribute("checksum", checksum);
		return "user/get-invite";
	}
	
	
	@RequestMapping(value = "/invite/{userId}/{checksum}", method = RequestMethod.GET)
	public String invite(@PathVariable Long userId, @PathVariable String checksum, ModelMap model) {
		if (!PasswordUtil.invateHash(userId).equalsIgnoreCase(checksum)) return "common/error";
		model.addAttribute("userId", userId);
		model.addAttribute("checksum", checksum);
		return "user/invite";
	}
	
	@RequestMapping(value = "/invite/{userId}/{checksum}", method = RequestMethod.POST)
	public String handleInvite(ModelMap model, 
			@PathVariable Long userId, @PathVariable String checksum,
			String nicename, String email, String phone, String code) {
		if (!PasswordUtil.invateHash(userId).equalsIgnoreCase(checksum)) return "common/error";
		if (!messageService.checkValidCode(phone, code)) {
			model.addAttribute("errCodeMsg", "验证码错误");
			model.addAttribute("nicename", nicename);
			model.addAttribute("phone", phone);
			model.addAttribute("email", email);
			model.addAttribute("checksum", checksum);
			return "user/invite";
		}
		User user = userService.handleInvited(userId, nicename, phone, email);
		if (user == null) return "common/error";
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		User user = userService.perm((Long)model.get("SessionUserId"));
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, ModelMap model) {
		if (username == null || username.trim().isEmpty() ||
			password == null || password.trim().isEmpty()) {
			model.addAttribute("errorMsg", "用户名或密码不能为空");
			return "user/login";
		}
		User user = userService.login(username, password);
		if (user == null) {
			model.addAttribute("errorMsg", "用户名或密码错误");
            model.addAttribute("username", username);
			return "user/login";
		}
		model.addAttribute("SessionUserId", user.getId());
		return "redirect:/message/inbox/1";
	}
	
	@RequestMapping("apply-invitation")
	public String applyInvitation(ModelMap model, Long userId,
			String username, String password, String group,
			String nicename, String phone, String email) {
		User admin = userService.perm((Long)model.get("SessionUserId"), 
				"PERM_HANDLE_INVITATION");
		User user = userService.get(userId);
		if (admin == null || user == null) {
			return "";
		}
		user.setLogin(username); user.setPasswd(password);
		user.setNicename(nicename); user.setPhone(phone);
		user.setEmail(email);
		user.setGroup(group);
		user = userService.passInvited(admin, user);
		if (user == null) {
			return "";
		}
		return "index";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(ModelMap model){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if (user == null) return "common/error";
		model.addAttribute("user", user);
		String checksum = PasswordUtil.invateHash(user.getId());
		model.addAttribute("checksum", checksum);
		return "user/modify";
	}
	
	@RequestMapping(value="/modifypwd", method = RequestMethod.GET)
	String modifypwdpage(ModelMap model){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if(user == null) return "common/error";
		model.addAttribute("user", user);
		return "user/modifypwd";
	}
	
	@RequestMapping(value = "/modifypwd", method = RequestMethod.POST)	
	public String modifypwd(String oldpassword, String password, ModelMap model) {
		User user = userService.perm((Long)model.get("SessionUserId"));
		if(user == null) return "redirect:/user/login";
		model.addAttribute("user", user);
		if (oldpassword == null || oldpassword.trim().isEmpty() ||
			password == null || password.trim().isEmpty()) {
			model.addAttribute("errorMsg", "密码不能为空");
			return "user/modifypwd";
		}
		if(userService.changePassword(user, oldpassword, password) == null){
			model.addAttribute("errorMsg", "密码错误");
			return "user/modifypwd";
		}
		return "redirect:/user/modify";
	}
	
	@RequestMapping(value="/modifyphone", method = RequestMethod.GET)
	public String modifyphonepage(ModelMap model){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if(user == null) return "common/error";
		model.addAttribute("user", user);
		return "user/modifyphone";
	}

	@RequestMapping(value="/select-user/q{query}/{page}")
	public String selectUser(ModelMap model, @PathVariable String query, @PathVariable Integer page){
		if(page == null) page = 1;
		PagerBean<User> list = userService.queryUser(query, page, 20);
		model.addAttribute("userList", list);
		model.addAttribute("pageNum", page);
		model.addAttribute("query", query == null ? "" : query);
		return "user/select-user";
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
}
