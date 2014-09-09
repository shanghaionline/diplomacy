package diplomacy.validator;

import diplomacy.vo.SendMsgFormVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SendMsgFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return SendMsgFormVO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        System.out.println("============================================================");
        ValidationUtils.rejectIfEmpty(errors, "title", null, "标题不能为空");
        ValidationUtils.rejectIfEmpty(errors, "content", null, "内容不能为空");
        ValidationUtils.rejectIfEmpty(errors, "receiver", null, "收件人不能为空");
    }
}
