package diplomacy.validator;

import diplomacy.vo.ModifyPasswordFormVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ModifyPasswordFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ModifyPasswordFormVO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldpassword", null, "旧密码不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", null, "密码不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2", null, "请再次输入密码");

        ModifyPasswordFormVO obj = (ModifyPasswordFormVO)o;
        if (!errors.hasErrors() && !obj.getPassword().equals(obj.getPassword2())) {
            errors.rejectValue("password2", null, "两次密码不一样");
        }
    }
}
