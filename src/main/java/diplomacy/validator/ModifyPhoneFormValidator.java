package diplomacy.validator;

import diplomacy.vo.ModifyPasswordFormVO;
import diplomacy.vo.ModifyPhoneFormVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ModifyPhoneFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ModifyPhoneFormVO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "phone", null, "手机不能为空");
        ValidationUtils.rejectIfEmpty(errors, "code", null, "验证码不能为空");
    }
}
