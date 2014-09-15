package diplomacy.action;

import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import diplomacy.entity.Content;
import diplomacy.entity.User;
import diplomacy.service.ContentService;
import diplomacy.service.UserService;
import diplomacy.validator.ApplyInviteFormValidator;
import diplomacy.validator.ContentFormValidator;
import diplomacy.validator.FixValidator;
import diplomacy.validator.ModifyUserFormValidator;
import diplomacy.vo.PagerBean;

@Controller
@RequestMapping("/content")
@SessionAttributes("SessionUserId")

public class ContentAct {
	
	private UserService userService;
	private ContentService contentService;
	

	@RequestMapping(value = "/show-content/{contentId}", method = RequestMethod.GET)
	public String showContent(ModelMap model, @PathVariable long contentId){
		User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        Content content = contentService.get(contentId);
        if(content == null )return "common/error";
        model.addAttribute("content", content);
        return "content/show-content";
	}
	
	@RequestMapping(value = "/list/q{query}/{page}", method = RequestMethod.GET)
	public String content(ModelMap model, @PathVariable String query, @PathVariable Integer page){
		User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        PagerBean<Content> content = contentService.query(query, page, 5);
        model.addAttribute("user", user);
        model.addAttribute("content", content);
        model.addAttribute("query", query == null ? "" : query);
		return "content/select-content";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String content(String query) throws Exception{
		return "redirect:/content/list/q" + URLEncoder.encode(query, "UTF-8") + "/1";
	}
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
	
}
