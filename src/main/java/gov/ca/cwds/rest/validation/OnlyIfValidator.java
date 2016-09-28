package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

/**
 * Validates that the {@code OnlyIf.property} of a given bean is not set unless the {@code Onlyif.ifProperty} is also not empty.
 *  
 * @author CWDS API Team
 */
public class OnlyIfValidator extends AbstractBeanValidator implements ConstraintValidator<OnlyIf, Object> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(OnlyIfValidator.class);

	private String ifProperty;
	private String property;
	
	@Override
	public void initialize(OnlyIf constraintAnnotation) {
		this.ifProperty = constraintAnnotation.ifProperty();
		this.property = constraintAnnotation.property();
	}

	@Override
	public boolean isValid(final Object bean, ConstraintValidatorContext context) {
		boolean valid = true;
		
		String ifValue = readBeanValue(bean, ifProperty);
		boolean ifValueBlank = StringUtils.isBlank(ifValue);
		
		if( ifValueBlank ) {
			String value = readBeanValue(bean, property);
			if( StringUtils.isNotBlank(value)) {
				valid = false;
			}
		}
		
		return valid;
	}
}
