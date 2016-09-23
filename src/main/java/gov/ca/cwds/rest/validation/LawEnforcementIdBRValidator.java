package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

public class LawEnforcementIdBRValidator implements ConstraintValidator<LawEnforcementIdBR, Object> {

	private String lawEnforcementId;
	private String employerName;

	@Override
	public void initialize(final LawEnforcementIdBR constraintAnnotation) {
		this.lawEnforcementId = constraintAnnotation.fieldName();
		this.employerName = constraintAnnotation.dependentFieldName();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		try {
			final String lawEnforcementIdValue = BeanUtils.getProperty(value, lawEnforcementId);
			final String employerNameValue = BeanUtils.getProperty(value, employerName);

			if (StringUtils.isNotBlank(lawEnforcementIdValue) && StringUtils.isNotBlank(employerNameValue)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("and lawEnforcmentId both can not be entered at a time")
						.addPropertyNode(employerName).addConstraintViolation();
				return false;
			}

		} catch (Exception e) {
			throw new ValidationException("Unable to validate the bean", e);
		}
		return true;
	}
}