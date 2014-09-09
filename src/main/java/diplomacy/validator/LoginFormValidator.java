package diplomacy.validator;

import diplomacy.vo.LoginFormVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class LoginFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return LoginFormVO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "username", null, "用户名不能为空");
        ValidationUtils.rejectIfEmpty(errors, "password", null, "密码不能为空");
    }
}
