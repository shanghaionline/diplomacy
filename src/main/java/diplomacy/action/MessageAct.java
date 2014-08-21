package diplomacy.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import diplomacy.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageAct {
	private MessageService messageService;
	@RequestMapping(value = "/sendvalidcode")
	@ResponseBody
	Map<String, String> sendValidCode(String phone) {
		Map<String, String> ret = new HashMap<String, String>();
		messageService.sendPhoneValidCode(phone);
		ret.put("msg", "success");
		ret.put("phone", phone);
		return ret;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}
