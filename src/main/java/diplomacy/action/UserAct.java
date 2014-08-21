package diplomacy.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import diplomacy.entity.User;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;
import diplomacy.util.PasswordUtil;

@Controller
@RequestMapping("/user")
public class UserAct {
	
	private UserService userService;
	private MessageService messageService;
	
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
		if (!messageService.checkValidCode(phone, code)) return "user/invite";
		User user = userService.handleInvited(userId, nicename, phone, email);
		if (user == null) return "user/invite";
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password) {
		return "user/login";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
}
