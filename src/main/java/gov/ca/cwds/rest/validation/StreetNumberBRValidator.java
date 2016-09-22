package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreetNumberBRValidator implements ConstraintValidator<LawEnforcementBR, Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(StreetNumberBRValidator.class);

	private String fieldName;
	private String dependentFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final LawEnforcementBR constraintAnnotation) {
		this.fieldName = constraintAnnotation.fieldName();
		this.dependentFieldName = constraintAnnotation.dependentFieldName();
		this.secondFieldName = constraintAnnotation.SecondFieldName();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		try {
			final String fieldNameValue = BeanUtils.getProperty(value, fieldName);
			final String dependentFieldNameValue = BeanUtils.getProperty(value, dependentFieldName);
			final String secondFieldNameValue = BeanUtils.getProperty(value, secondFieldName);

			if (StringUtils.isNotBlank(dependentFieldNameValue) && StringUtils.isNotBlank(secondFieldNameValue)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("is entreted and cityName can not be Empty")
						.addPropertyNode(secondFieldName).addConstraintViolation();
				return false;
			}

			if (StringUtils.isNotBlank(fieldNameValue) && StringUtils.isNotBlank(dependentFieldNameValue)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("is entreted and streetName can not be Empty")
						.addPropertyNode(dependentFieldName).addConstraintViolation();
				return false;
			}

		} catch (Exception e) {
			LOGGER.info("Exception in BusinessRule Validator {} ", e.toString());
			return false;
		}
		return true;
	}
}