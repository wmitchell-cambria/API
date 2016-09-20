package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgencyNameValidator implements ConstraintValidator<AgencyName, Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgencyNameValidator.class);
		
		private String employerName;
		private String lawEnforcementId;
		
		@Override
		public void initialize(final AgencyName constraintAnnotation) {
			this.lawEnforcementId = constraintAnnotation.fieldName();
			this.employerName = constraintAnnotation.dependentFieldName();
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public boolean isValid(final Object value, final ConstraintValidatorContext context) {

			try {
				final String lawEnforcementIdValue = BeanUtils.getProperty(value, lawEnforcementId);
				final String employerNameValue = BeanUtils.getProperty(value, employerName);
				
				if (StringUtils.isNotBlank(lawEnforcementIdValue) && StringUtils.isNotBlank(employerNameValue)) {
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate("and lawEnforcmentId both can not be entered at a time")
							.addNode(employerName).addConstraintViolation();
					return false;
				}
				
			} catch (Exception e) {
				LOGGER.info("Exception in BusinessRule Validator {} ", e.toString());
				return false;
			}

			return true;
		}
}
