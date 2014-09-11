package diplomacy.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import diplomacy.vo.ApplyInviteFormVO;
import diplomacy.vo.ModifyUserFormVO;

public class ModifyUserFormValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> aClass) {
		return ModifyUserFormVO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nicename", null, "姓名不能为空");
		ValidationUtils.rejectIfEmpty(errors, "phone", null, "电话不能为空");
		ValidationUtils.rejectIfEmpty(errors, "email", null, "邮箱不能为空");
		ModifyUserFormVO obj = (ModifyUserFormVO)o;
        if (!errors.hasErrors() && obj.getEmail().indexOf('@') == -1) {
        	errors.rejectValue("email", null, "邮箱格式不正确");
        }
	}
}
