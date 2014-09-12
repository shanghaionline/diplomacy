package diplomacy.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import diplomacy.vo.ContentFormVO;

public class ContentFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ContentFormVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "title", null, "标题不能为空");
	}

}
