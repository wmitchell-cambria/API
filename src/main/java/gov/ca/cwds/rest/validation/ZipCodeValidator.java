package gov.ca.cwds.rest.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks to see that the value is in a valid Zip Code format, five digits 
 *  
 * @author CWDS API Team
 */
public class ZipCodeValidator implements ConstraintValidator<ZipCode, String> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ZipCodeValidator.class);
	private static final Pattern pattern = Pattern.compile("[0-9]{5}");

	private boolean required;

	@Override
	public void initialize(ZipCode constraintAnnotation) {
		this.required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean success = true;
		if (required || StringUtils.isNotBlank(value)) {
			Matcher matcher = pattern.matcher(value);
			success = matcher.matches();
		}
		return success;
	}
}
