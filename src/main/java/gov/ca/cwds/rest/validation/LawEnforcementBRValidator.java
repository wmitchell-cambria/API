package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LawEnforcementBRValidator implements ConstraintValidator<LawEnforcementBR, Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(LawEnforcementBRValidator.class);

	private String lawEnforcementId;
	private String badgeNumber;

	@Override
	public void initialize(final LawEnforcementBR constraintAnnotation) {
		this.lawEnforcementId = constraintAnnotation.fieldName();
		this.badgeNumber = constraintAnnotation.dependentFieldName();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		try {
			final String lawEnforcementIdValue = BeanUtils.getProperty(value, lawEnforcementId);
			final String badgeNumberValue = BeanUtils.getProperty(value, badgeNumber);

			if (StringUtils.isNotBlank(lawEnforcementIdValue) && badgeNumberValue == null) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("can not be null when lawEnfrocementId is entered")
						.addNode(badgeNumber).addConstraintViolation();
				return false;
			}

			if (StringUtils.isNotBlank(badgeNumberValue) && lawEnforcementIdValue == null) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("is null then badgeNumber should not be entered")
						.addNode(lawEnforcementId).addConstraintViolation();
				return false;
			}
		} catch (Exception e) {
			LOGGER.info("Exception in BusinessRule Validator {} ", e.toString());
			return false;
		}

		return true;
	}
}
