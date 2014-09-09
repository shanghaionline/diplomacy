package diplomacy.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FixValidator implements Validator {

    private Validator[] validators;
    public FixValidator(Validator[] validators) {
        this.validators = validators;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {
        for (Validator v : validators) {
            if (v.supports(o.getClass())) v.validate(o, errors);
        }
    }
}
