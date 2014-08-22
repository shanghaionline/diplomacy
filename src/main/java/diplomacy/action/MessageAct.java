package diplomacy.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import diplomacy.entity.Message;
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
	
	@RequestMapping(value= "/sendmessage")
	String sendMessage(ModelMap model, String receiver, String perm, String title, String content, Long attachmentId){
		User sender = userService.perm((Long)model.get("SessionUserId"));
		if (sender == null) return "";
		if (perm == null || perm.isEmpty()) {
			messageService.sendSingleMessage(sender, receiver, title, content, attachmentId);
		}
		return "index";
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
