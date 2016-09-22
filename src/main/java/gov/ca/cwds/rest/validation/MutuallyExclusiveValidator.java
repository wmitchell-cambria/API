package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;
import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator which confirms only one of the given properties on a bean have been set. 
 *
 * @author CWDS API Team
 */
public class MutuallyExclusiveValidator implements ConstraintValidator<MutuallyExclusive, Object> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MutuallyExclusiveValidator.class);

	private String[] properties;
	private boolean required;

	@Override
	public void initialize(MutuallyExclusive constraintAnnotation) {
		this.properties = constraintAnnotation.properties();
		this.required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(final Object bean, ConstraintValidatorContext context) {
		boolean valid = true;
		int countNotEmpty = 0;
		for( String property : properties ) {
			String value = readBeanValue(bean, property);
			if( StringUtils.isNotBlank(value)) {
				countNotEmpty++;
			}
		}
		
		if( required && countNotEmpty == 0 ) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(MessageFormat.format("{0} must have one of their values set", Arrays.toString(properties)))
					.addPropertyNode("Properties").addConstraintViolation();
			valid = false;
		}
		if( countNotEmpty > 1 ) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(MessageFormat.format("{0} are mutually exclusive but multiple values are set", Arrays.toString(properties)))
					.addPropertyNode("Properties").addConstraintViolation();	
			valid = false;
		}
		return valid;
	}
	
	private String readBeanValue(Object bean, String property) {
		try {
			return BeanUtils.getProperty(bean, property);
		} catch (Throwable e) {
			throw new ValidationException(MessageFormat.format("Unable to read '{0}' from bean:{1}", property, bean), e);
		}
	}
}
