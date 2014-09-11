package diplomacy.action;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import diplomacy.entity.User;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.UserService;
import diplomacy.validator.ApplyInviteFormValidator;
import diplomacy.validator.FixValidator;
import diplomacy.validator.ModifyUserFormValidator;
import diplomacy.vo.ApplyInviteFormVO;
import diplomacy.vo.ModifyUserFormVO;
import diplomacy.vo.PagerBean;

@Controller
@RequestMapping("/admin")
@SessionAttributes("SessionUserId")
public class MainAdminAct {
	
	private UserService userService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        Validator fix = new FixValidator(new Validator[]{
        		new ApplyInviteFormValidator(), new ModifyUserFormValidator()
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
	
	
	@RequestMapping(value = "/apply-invite/{userId}", method = RequestMethod.GET)
	public String applyInvite(ModelMap model, @PathVariable Long userId){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        User data = userService.get(userId);
        if(data == null || !data.getStatus().equals(UserStatus.UNVALITED))return "common/error";
        model.addAttribute("data", data);
        if(model.get("applyInviteFormVO") == null){
        	model.addAttribute("applyInviteFormVO", new ApplyInviteFormVO());
        }
		return "admin/apply-invite";
	}
	
	@RequestMapping(value = "/apply-invite/{userId}", method = RequestMethod.POST)
	public String applyInvite(@Valid ApplyInviteFormVO applyInviteFormVO, BindingResult result, ModelMap model, @PathVariable Long userId){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
		if (result.hasErrors()) {
            return applyInvite(model, userId);
        }
		User data = userService.get(userId);
		if(data == null) return "common/error";
		data.setLogin(applyInviteFormVO.getLogin());
		data.setPasswd(applyInviteFormVO.getPassword());
		data.setNicename(applyInviteFormVO.getNicename());
		data.setGroup(applyInviteFormVO.getGroup());
		data.setEmail(applyInviteFormVO.getEmail());
		data.setPhone(applyInviteFormVO.getPhone());
		data = userService.passInvited(user, data);
		if(data == null){
			result.rejectValue("login", null, "用户名已重复");
			return applyInvite(model, userId);
		}
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

	@RequestMapping(value = "/show-listuser/{userId}", method = RequestMethod.GET)
    public String showListUser(ModelMap model, @PathVariable long userId){
    	User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        User listUser = userService.get(userId);
        if(listUser == null) return "common/error";
        model.addAttribute("listUser", listUser);
        return "admin/show-listuser";
}

	@RequestMapping(value = "/modify-user/{userId}", method = RequestMethod.GET)
	public String modifyUser(ModelMap model, @PathVariable Long userId){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        User modifyUser = userService.get(userId);
        if(modifyUser == null || !modifyUser.getStatus().equals(UserStatus.ENABLED))return "common/error";
        model.addAttribute("modifyUser", modifyUser);
        if(model.get("modifyUserFormVO") == null){
        	model.addAttribute("modifyUserFormVO", new ModifyUserFormVO());
        }
		return "admin/modify-user";
	}
	
	@RequestMapping(value = "/modify-user/{userId}", method = RequestMethod.POST)
	public String modifyUser(@Valid ModifyUserFormVO modifyUserFormVO, BindingResult result, ModelMap model, @PathVariable Long userId){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
		if (result.hasErrors()) {
            return modifyUser(model, userId);
        }
		User modifyUser = userService.get(userId);
		if(modifyUser == null) return "common/error";
		modifyUser.setNicename(modifyUserFormVO.getNicename());
		modifyUser.setGroup(modifyUserFormVO.getGroup());
		modifyUser.setEmail(modifyUserFormVO.getEmail());
		modifyUser.setPhone(modifyUserFormVO.getPhone());
		modifyUser = userService.modifyUser(modifyUser);
		return "redirect:/admin/list-user/1";
	}
	
	@RequestMapping(value = "/list-user/delete/{userId}")
	public String deleteUser(ModelMap model, @PathVariable Long userId){
		User user = userService.perm((Long) model.get("SessionUserId"), "PERM_OPTER_ADMIN");
        if (user == null) return "common/error";
        userService.Delete(userId);;
		return "redirect:/admin/list-user/1";
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

}
