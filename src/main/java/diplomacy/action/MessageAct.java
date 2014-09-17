package diplomacy.action;

import java.util.HashMap;
import java.util.Map;

import diplomacy.validator.FixValidator;
import diplomacy.validator.SendMsgFormValidator;
import diplomacy.vo.SendMsgFormVO;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import diplomacy.entity.Message;
import diplomacy.entity.User;
import diplomacy.service.MessageService;
import diplomacy.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/message")
@SessionAttributes("SessionUserId")
public class MessageAct {
    private MessageService messageService;
    private UserService userService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Validator fix = new FixValidator(new Validator[]{
           new SendMsgFormValidator()
        });
        binder.setValidator(fix);
    }

    @RequestMapping(value = "/sendvalidcode")
    public String sendValidCode(ModelMap model, String phone) {
        messageService.sendPhoneValidCode(phone);
        model.addAttribute("status", "success");
        model.addAttribute("data", phone);
        return "common/json_result";
    }

    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
    public String sendMessage(@Valid SendMsgFormVO sendMsgFormVO, BindingResult result,
                              ModelMap model, MultipartFile attachment) {
        User sender = userService.perm((Long) model.get("SessionUserId"));
        if (sender == null) return "common/error";
        if (result.hasErrors()) return sendMessagePage(model, null);
        if (sendMsgFormVO.getPerm() == null || sendMsgFormVO.getPerm().isEmpty()) {
            messageService.sendSingleMessage(sender,
                    sendMsgFormVO.getReceiver(), sendMsgFormVO.getTitle(), sendMsgFormVO.getContent(), attachment);
        } else {
            messageService.sendMultipleMessage(sender,
                    sendMsgFormVO.getPerm(), sendMsgFormVO.getTitle(), sendMsgFormVO.getContent(), attachment);
        }
        return "redirect:/message/inbox/1";
    }

    @RequestMapping(value = "/inbox/{page}")
    public String inbox(ModelMap model, @PathVariable int page) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        model.addAttribute("boxList", messageService.listInboxByPage(user, page, 20));
        return "message/inbox";
    }

    @RequestMapping(value = "/outbox/{page}")
    public String outbox(ModelMap model, @PathVariable int page) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        model.addAttribute("boxList", messageService.listOutboxByPage(user, page, 20));
        return "message/outbox";
    }

    @RequestMapping(value = "/sendmessage", method = RequestMethod.GET)
    public String sendMessagePage(ModelMap model, String receiver) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        if (receiver != null) model.addAttribute("receiver", receiver);
        if (model.get("sendMsgFormVO") == null) model.addAttribute("sendMsgFormVO", new SendMsgFormVO());
        return "message/sendmessage";
    }

    @RequestMapping(value = "/showmsg/{msgId}")
    public String showMsg(ModelMap model, @PathVariable long msgId) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        Message msg = messageService.readMessage(user, msgId);
        if (msg == null) return "common/error";
        model.addAttribute("message", msg);
        return "message/showmsg";
    }

    @RequestMapping(value = "/showreceive/{msgId}")
    public String showReceive(ModelMap model, @PathVariable long msgId) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        model.addAttribute("user", user);
        Message msg = messageService.receiveMessage(user, msgId, true);
        if (msg == null) return "common/error";
        model.addAttribute("message", msg);
        return "message/showreceive";
    }

    @RequestMapping(value = "/inbox/delete")
    public String deleteInbox(ModelMap model, long[] ids) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        if (ids != null) messageService.deleteMessageBox(user, ids);
        return "redirect:/message/inbox/1";
    }


    @RequestMapping(value = "/outbox/delete")
    public String deleteOutbox(ModelMap model, long[] ids) {
        User user = userService.perm((Long) model.get("SessionUserId"));
        if (user == null) return "common/error";
        if (ids != null) messageService.deleteMessage(user, ids);
        return "redirect:/message/outbox/1";
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
