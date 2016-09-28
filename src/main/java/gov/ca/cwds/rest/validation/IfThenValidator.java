package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

/**
 * Validates that if the {@code IfThen.ifProperty} of a given bean is not empty then the {@code IfThen.thenProperty} is also not empty.
 *  
 * @author CWDS API Team
 */
public class IfThenValidator extends AbstractBeanValidator implements ConstraintValidator<IfThen, Object> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(IfThenValidator.class);

	private boolean required;

	private String ifProperty;
	private String thenProperty;
	
	@Override
	public void initialize(IfThen constraintAnnotation) {
		this.required = constraintAnnotation.required();
		this.ifProperty = constraintAnnotation.ifProperty();
		this.thenProperty = constraintAnnotation.thenProperty();
	}

	@Override
	public boolean isValid(final Object bean, ConstraintValidatorContext context) {
		boolean valid = true;
		ImmutableList.Builder<String> messages = new ImmutableList.Builder<String>();
		
		String ifValue = readBeanValue(bean, ifProperty);
		boolean ifValueNotBlank = StringUtils.isNotBlank(ifValue);
		if( required ) {
			if( !ifValueNotBlank ) { 
				valid = false;
				messages.add(MessageFormat.format("{0} is required but not set", ifProperty));
			}
		} 
		
		if( ifValueNotBlank ) {
			String thenValue = readBeanValue(bean, thenProperty);
			if( StringUtils.isBlank(thenValue)) {
				valid = false;
				messages.add(MessageFormat.format("{0} is required since {1} is set", thenProperty, ifProperty));
			}
		}
		
		return valid;
	}
}