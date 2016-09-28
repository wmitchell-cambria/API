package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

/**
 * Validator which confirms exclusivity.
 * 
 * Two types are handled: String and Boolean
 * 
 * If type=String
 *   When not required will allow no values being set but ensure that only one value is set
 *   When required then one and only one value must be set.
 * 
 * If type=Boolean
 *   Validates only one of the given boolean properties is set to True
 *   When not required allows for null values ( assumes these are false ) - all null values will return valid == true
 *   When required at least one of the boolean properties must be set - again there must be one and only one True value
 *
 * @author CWDS API Team
 */
public class MutuallyExclusiveValidator extends AbstractBeanValidator implements ConstraintValidator<MutuallyExclusive, Object> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MutuallyExclusiveValidator.class);

	private String[] properties;
	private boolean required;
	@SuppressWarnings("rawtypes")
	private Class type;

	@Override
	public void initialize(MutuallyExclusive constraintAnnotation) {
		this.properties = constraintAnnotation.properties();
		this.required = constraintAnnotation.required();
		this.type = constraintAnnotation.type();
	}

	@Override
	public boolean isValid(final Object bean, ConstraintValidatorContext context) {
		boolean valid = true;
		
		if(String.class.equals(type)) {
			valid = handleStringType(bean, context);			
		} else if(Boolean.class.equals(type)) {
			valid = handleBooleanType(bean, context);
		} else {
			throw new ValidationException(MessageFormat.format("Unhandled type : {0}", type.getName()));
		}
		return valid;
	}

	private boolean handleBooleanType(final Object bean, ConstraintValidatorContext context) {
		boolean valid = true;
		ImmutableList.Builder<String> messages = new ImmutableList.Builder<String>();
		int countTrue = 0;
		int countSet = 0;
		for( String property : properties ) {
			String value = readBeanValue(bean, property);
			if( value != null ) {
				countSet++;
				if( Boolean.TRUE.equals(Boolean.valueOf(value)) ) {
					countTrue++;
				}
			}
		}
		if( required ) {
			if( countTrue != 1 ) {
				valid = false;
				messages.add(MessageFormat.format("{0} must have one and only one true value", Arrays.toString(properties)));
			} 
		} else {
			if( countSet > 0 && countTrue != 1) {
				valid = false;
				messages.add(MessageFormat.format("{0} must all be null or have one and only one true value", Arrays.toString(properties)));
			}
		}
		
		if(!valid) {
			setupViolationMessages(context, messages.build());
		}

		return valid;	
	}
	
	private void setupViolationMessages(ConstraintValidatorContext context, List<String> messages) {
		context.disableDefaultConstraintViolation();
		for(String msg : messages) {
			context.buildConstraintViolationWithTemplate(msg)
					.addPropertyNode("Properties").addConstraintViolation();
		}
	}
	
	private boolean handleStringType(final Object bean, ConstraintValidatorContext context) {
		boolean valid = true;
		ImmutableList.Builder<String> messages = new ImmutableList.Builder<String>();
		int countNotEmpty = 0;
		for( String property : properties ) {
			String value = readBeanValue(bean, property);
			if( StringUtils.isNotBlank(value)) {
				countNotEmpty++;
			}
		}
		if( required && countNotEmpty == 0 ) {
			messages.add(MessageFormat.format("{0} must have one of their values set", Arrays.toString(properties)));
			valid = false;
		}
		if( countNotEmpty > 1 ) {
			messages.add(MessageFormat.format("{0} are mutually exclusive but multiple values are set", Arrays.toString(properties)));	
			valid = false;
		}
		
		if( !valid ) {
			setupViolationMessages(context, messages.build());
		}
		return valid;
	}
	

}
