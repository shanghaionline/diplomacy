package diplomacy.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import diplomacy.vo.ApplyInviteFormVO;
import diplomacy.vo.LoginFormVO;

public class ApplyInviteFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return ApplyInviteFormVO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		
	}

}
