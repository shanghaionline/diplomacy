package diplomacy.action;

import java.net.URLEncoder;

import diplomacy.validator.*;
import diplomacy.vo.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import diplomacy.entity.User;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;
import diplomacy.util.PasswordUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@SessionAttributes("SessionUserId")
public class UserAct {

    private UserService userService;
    private MessageService messageService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Validator fix = new FixValidator(new Validator[]{
           new LoginFormValidator(), new InviteFormValidator(), new UserModifyFormValidator(),
                new ModifyPasswordFormValidator(), new ModifyPhoneFormValidator()
        });
        binder.setValidator(fix);
    }
    
    @RequestMapping("/init")
    public String init() {
        userService.create("admin", "admin", UserStatus.ENABLED);
        userService.createSystem("会员", "PERM_MESSAGE_MEMBER");
        userService.createSystem("理事", "PERM_MESSAGE_DIRECTOR");
        userService.createSystem("会长", "PERM_MESSAGE_CHAIRMAN");
        return "index";
    }

    @RequestMapping("/groupfresh")
    public String freshGroup(ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user != null) userService.refreshGroup(user);
        return "index";
    }

    @RequestMapping("/test")
    public String test(String name, Integer age) {
        System.out.println("=====" + PasswordUtil.invateHash(1L));
        return "index";
    }

    @RequestMapping("/get-invite")
    public String getInvite(ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
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
        if (model.get("inviteFormVO") == null) model.addAttribute("inviteFormVO", new InviteFormVO());
        return "user/invite";
    }

    @RequestMapping(value = "/invite/{userId}/{checksum}", method = RequestMethod.POST)
    public String handleInvite(ModelMap model,
                               @PathVariable Long userId, @PathVariable String checksum,
                               @Valid InviteFormVO inviteFormVO, BindingResult result) {
        if (!PasswordUtil.invateHash(userId).equalsIgnoreCase(checksum)) return "common/error";
        if (result.hasErrors()) {
            return invite(userId, checksum, model);
        }
        if (!messageService.checkValidCode(inviteFormVO.getPhone(), inviteFormVO.getCode())) {
            result.rejectValue("code", null, "验证码不正确");
            return invite(userId, checksum, model);
        }
        User user = userService.handleInvited(userId,
                inviteFormVO.getNicename(), inviteFormVO.getPhone(), inviteFormVO.getEmail());
        if (user == null) return "common/error";
        return "user/invite-ok";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (model.get("loginFormVO") == null) {
            model.addAttribute("loginFormVO", new LoginFormVO());
        }
        return "user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid LoginFormVO loginFormVO, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return login(model);
        }
        User user = userService.login(loginFormVO.getUsername(), loginFormVO.getPassword());
        if (user == null) {
            result.rejectValue("username", null, "用户名或密码错误");
            return login(model);
        }
        model.addAttribute("SessionUserId", user.getId());
        return "redirect:/message/inbox/1";
    }

    @RequestMapping(value = "/front_login", method = RequestMethod.GET)
    public String frontLogin(ModelMap model){
    	User user = userService.perm((Long) model.get("SessionUserId"));
    	if (model.get("loginFormVO") == null) {
            model.addAttribute("loginFormVO", new LoginFormVO());
        }
    	if(user != null) model.addAttribute("user", user);
    	return "user/front_login";
    }
    
    @RequestMapping(value = "/front_login", method = RequestMethod.POST)
    public String frontLogin(@Valid LoginFormVO loginFormVO, BindingResult result, ModelMap model){
    	if (result.hasErrors()) {
            return login(model);
        }
        User user = userService.login(loginFormVO.getUsername(), loginFormVO.getPassword());
        if (user == null) {
            result.rejectValue("username", null, "用户名或密码错误");
            return frontLogin(model);
        }
        model.addAttribute("SessionUserId", user.getId());
        return "user/front_login_ok";
    }

    @RequestMapping(value = "/logout")
    public String logout(ModelMap model) {
        model.addAttribute("SessionUserId", -1L);
        return "redirect:/user/login";
    }
    
    
    @RequestMapping("apply-invitation")
    public String applyInvitation(ModelMap model, Long userId,
                                  String username, String password, String group,
                                  String nicename, String phone, String email) {
        User admin = userService.perm((Long) model.get("SessionUserId"),
                "PERM_HANDLE_INVITATION");
        User user = userService.get(userId);
        if (admin == null || user == null) {
            return "";
        }
        user.setLogin(username);
        user.setPasswd(password);
        user.setNicename(nicename);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGroup(group);
        user = userService.passInvited(admin, user);
        if (user == null) {
            return "";
        }
        return "index";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modify(ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        if (model.get("userModifyFormVO") == null) model.addAttribute("userModifyFormVO", new UserModifyFormVO());
        return "user/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyUser(@Valid UserModifyFormVO userModifyFormVO, BindingResult result, ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        if (result.hasErrors()) return modify(model);
        userService.saveUserInfo(user, userModifyFormVO.getNicename(), userModifyFormVO.getEmail());
        return "redirect:/user/modify";
    }


    @RequestMapping(value = "/modifypwd", method = RequestMethod.GET)
    String modifypwdpage(ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        if (model.get("modifyPasswordFormVO") == null)
            model.addAttribute("modifyPasswordFormVO", new ModifyPasswordFormVO());
        return "user/modifypwd";
    }

    @RequestMapping(value = "/modifypwd", method = RequestMethod.POST)
    public String modifypwd(@Valid ModifyPasswordFormVO modifyPasswordFormVO, BindingResult result, ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "redirect:/user/login";
        if (result.hasErrors()) return modifypwdpage(model);
        if (userService.changePassword(user, modifyPasswordFormVO.getOldpassword(),
                modifyPasswordFormVO.getPassword()) == null) {
            result.rejectValue("oldpassword", null, "旧密码错误");
            return modifypwdpage(model);
        }
        return "redirect:/user/modify";
    }

    @RequestMapping(value = "/modifyphone", method = RequestMethod.GET)
    public String modifyphonepage(ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        if (model.get("modifyPhoneFormVO") == null) model.addAttribute("modifyPhoneFormVO", new ModifyPhoneFormVO());
        return "user/modifyphone";
    }

    @RequestMapping(value = "/modifyphone", method = RequestMethod.POST)
    public String changePhone(@Valid ModifyPhoneFormVO modifyPhoneFormVO, BindingResult result, ModelMap model) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        if (result.hasErrors()) return modifyphonepage(model);
        if (!messageService.checkValidCode(modifyPhoneFormVO.getPhone(), modifyPhoneFormVO.getCode())) {
            result.rejectValue("code", null, "验证码错误");
            return modifyphonepage(model);
        }
        userService.changePhone(user, modifyPhoneFormVO.getPhone());
        return "redirect:/user/modifyphone";
    }


    @RequestMapping(value = "/select-user/q{query}/{page}", method = RequestMethod.GET)
    public String selectUser(ModelMap model, @PathVariable String query, @PathVariable Integer page) {
    	User user = userService.perm((Long) model.get("SessionUserId"));
    	if (page == null) page = 1;
        PagerBean<User> list = userService.queryUser(query, page, 20);
        model.addAttribute("userList", list);
        model.addAttribute("pageNum", page);
        model.addAttribute("query", query == null ? "" : query);
        return "user/select-user";
    }
    
    @RequestMapping("/help")
    public String help(){
    	 return "user/help";
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }


}
