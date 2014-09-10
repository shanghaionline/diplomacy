package diplomacy.validator;

import diplomacy.vo.UserModifyFormVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserModifyFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserModifyFormVO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "nicename", null, "姓名不能为空");
        ValidationUtils.rejectIfEmpty(errors, "email", null, "邮箱地址不能为空");
        UserModifyFormVO obj = (UserModifyFormVO)o;
        if (obj.getEmail().indexOf('@') == -1) {
        	errors.rejectValue("email", null, "邮箱格式不正确");
        }
    }
}
