package diplomacy.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import diplomacy.entity.User;
import diplomacy.service.UserService;
import diplomacy.validator.ApplyInviteFormValidator;
import diplomacy.validator.FixValidator;
import diplomacy.validator.InviteFormValidator;
import diplomacy.validator.LoginFormValidator;
import diplomacy.validator.ModifyPasswordFormValidator;
import diplomacy.validator.ModifyPhoneFormValidator;
import diplomacy.validator.UserModifyFormValidator;
import diplomacy.vo.PagerBean;

@Controller
@RequestMapping("/admin")
@SessionAttributes("SessionUserId")
public class MainAdminAct {
	
	private UserService userService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        Validator fix = new FixValidator(new Validator[]{
        		new ApplyInviteFormValidator()
        });
        binder.setValidator(fix);
    }
	
	@RequestMapping("/")
    public String index(ModelMap model) {
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        return "admin/main";
    }
	
	@RequestMapping(value = "/handle-invite/{page}")
    public String handleInvite(ModelMap model, @PathVariable Integer page) {
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
		if (page == null) page = 1;
		PagerBean<User> list= userService.listUnvalited(page, 20);
		model.addAttribute("userList", list);
		return "admin/handle-invite";
    }
	
	@RequestMapping(value = "/handle-invite/delete/{userId}")
	public String deleteInvite(ModelMap model, @PathVariable Long userId){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        userService.rejectInvited(user, userId);
		return "redirect:/admin/handle-invite/1";
	}
	
	@RequestMapping(value = "/list-user/{page}")
	public String listUser(ModelMap model, @PathVariable Integer page){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        if (page == null) page = 1;
		PagerBean<User> list= userService.listUser(page, 20);
		model.addAttribute("userList", list);
        return "admin/list-user";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

}
