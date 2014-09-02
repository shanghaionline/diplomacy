package diplomacy.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import diplomacy.entity.User;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;

@Controller
@RequestMapping("/message")
@SessionAttributes("SessionUserId")
public class MessageAct {
	private MessageService messageService;
	private UserService userService;
	@RequestMapping(value = "/sendvalidcode")
	@ResponseBody
	Map<String, String> sendValidCode(String phone) {
		Map<String, String> ret = new HashMap<String, String>();
		messageService.sendPhoneValidCode(phone);
		ret.put("msg", "success");
		ret.put("phone", phone);
		return ret;
	}
	
	@RequestMapping(value= "/sendmessage", method = RequestMethod.POST)
	String sendMessage(ModelMap model, String receiver, String perm, String title, String content, 
			MultipartFile attachment){
		User sender = userService.perm((Long)model.get("SessionUserId"));
		if (sender == null) return "";
		if (perm == null || perm.isEmpty()) {
			messageService.sendSingleMessage(sender, receiver, title, content, attachment);
		} else {
			messageService.sendMultipleMessage(sender, perm, title, content, attachment);
		}
		return "message/inbox";
	}
	
	@RequestMapping(value="/inbox/{page}")
	String inbox(ModelMap model, @PathVariable int page){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if(user == null) return "common/error";
		model.addAttribute("user", user);
        model.addAttribute("boxList", messageService.listInboxByPage(user, page, 20));
		return "message/inbox";
	}
	
	@RequestMapping(value="/outbox/{page}")
	String outbox(ModelMap model, @PathVariable int page){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if(user == null) return "common/error";
		model.addAttribute("user", user);
        model.addAttribute("boxList", messageService.listOutboxByPage(user, page, 20));
		return "message/outbox";
	}
	
	@RequestMapping(value="/sendmessage", method = RequestMethod.GET)
	String sendMessagePage(ModelMap model){
		User user = userService.perm((Long)model.get("SessionUserId"));
		if(user == null) return "common/error";
		model.addAttribute("user", user);
		return "message/sendmessage";
	}
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
