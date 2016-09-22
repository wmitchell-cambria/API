package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator which confirms only one of a beans given properties have been set. 
 *
 * @author CWDS API Team
 */
public class MutuallyExclusiveValidator implements ConstraintValidator<MutuallyExclusive, Object> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MutuallyExclusiveValidator.class);

	private String[] properties;
	private String message;

	@Override
	public void initialize(MutuallyExclusive constraintAnnotation) {
		this.properties = constraintAnnotation.properties();
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object bean, ConstraintValidatorContext context) {
		int countNotEmpty = 0;
		for( String property : properties ) {
			String value = readBeanValue(bean, property);
			if( StringUtils.isNotBlank(value)) {
				countNotEmpty++;
			}
		}
		if( countNotEmpty > 1 ) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode("Properties").addConstraintViolation();	
			return false;
		}
		return true;
	}
	
	private String readBeanValue(Object bean, String property) {
		try {
			return BeanUtils.getProperty(bean, property);
		} catch (Throwable e) {
			throw new ValidationException(MessageFormat.format("Unable to read '{0}' from bean:{1}", property, bean), e);
		}
	}
}
