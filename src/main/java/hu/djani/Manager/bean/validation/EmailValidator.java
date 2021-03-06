package hu.djani.Manager.bean.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public void initialize(final ValidEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(final String username, final ConstraintValidatorContext context) {
		return (this.validateEmail(username));
	}

	private boolean validateEmail(final String email) {
		this.pattern = Pattern.compile(EMAIL_PATTERN);
		this.matcher = this.pattern.matcher(email);
		return this.matcher.matches();
	}
}