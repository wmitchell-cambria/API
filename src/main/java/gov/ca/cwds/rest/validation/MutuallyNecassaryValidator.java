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
 * Validator which confirms all the given properties on a bean have been set.
 *
 * @author CWDS API Team
 */
public class MutuallyNecassaryValidator extends AbstractBeanValidator implements ConstraintValidator<MutuallyNecassary, Object> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MutuallyNecassaryValidator.class);

	private String[] properties;
	private boolean required;

	@Override
	public void initialize(MutuallyNecassary constraintAnnotation) {
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
		
		if( required && countNotEmpty < properties.length ) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(MessageFormat.format("{0} all must have their values set", Arrays.toString(properties)))
					.addPropertyNode("Properties").addConstraintViolation();
			valid = false;
		}
		if( countNotEmpty > 0 && countNotEmpty < properties.length ) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(MessageFormat.format("{0} are mutually necassary but not all values are set", Arrays.toString(properties)))
					.addPropertyNode("Properties").addConstraintViolation();	
			valid = false;
		}
		return valid;
	}
}
