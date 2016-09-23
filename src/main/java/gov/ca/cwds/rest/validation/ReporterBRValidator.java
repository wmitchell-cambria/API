package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

public class ReporterBRValidator implements ConstraintValidator<ReporterBusinessRule, Object> {

	private String fieldName;
	private String dependentFieldName;

	@Override
	public void initialize(final ReporterBusinessRule constraintAnnotation) {
		this.fieldName = constraintAnnotation.fieldName();
		this.dependentFieldName = constraintAnnotation.dependentFieldName();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		try {
			final String fieldNameValue = BeanUtils.getProperty(value, fieldName);
			final String dependentFieldNameValue = BeanUtils.getProperty(value, dependentFieldName);
			if (StringUtils.isNotBlank(fieldNameValue) && StringUtils.isBlank(dependentFieldNameValue)) {
				return false;
			}

		} catch (Exception e) {
			throw new ValidationException("Unable to validate the bean", e);
		}
		return true;
	}
}
