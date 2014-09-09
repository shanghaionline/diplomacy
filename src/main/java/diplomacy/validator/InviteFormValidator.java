package diplomacy.validator;

import diplomacy.service.MessageService;
import diplomacy.vo.InviteFormVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class InviteFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return InviteFormVO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "nicename", null, "名字不能为空");
        ValidationUtils.rejectIfEmpty(errors, "email", null, "邮箱地址不能为空");
        ValidationUtils.rejectIfEmpty(errors, "phone", null, "手机不能为空");
        ValidationUtils.rejectIfEmpty(errors, "code", null, "验证码不能为空");
    }
}
