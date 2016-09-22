package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.legacy.Referral;
import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

public class AnonymousReporterIndValidator implements ConstraintValidator<AnonymousReporterInd, Object> {

	@Override
	public void initialize(final AnonymousReporterInd constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		Referral referral = null;
		ReferralClient referrelClient = null;
		try {
			final Object obj1 = PropertyUtils.getProperty(value, "referral");
			if (obj1 instanceof Referral) {
				referral = (Referral) obj1;
			} else {
				throw new ValidationException("Unable to find Referral object");
			}

			final Object obj2 = PropertyUtils.getProperty(value, "referralClient");
			if (obj2 instanceof ReferralClient) {
				referrelClient = (ReferralClient) obj2;
			} else {
				throw new ValidationException("Unable to find ReferralClient object");
			}

			if (referral.getAnonymousReporterIndicator() == true && referrelClient.getSelfReportedIndicator() == true) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"true then selfReportedIndicator from ReferralClient should be false")
						.addPropertyNode("anonymousReporterIndicator").addConstraintViolation();
				return false;
			}

			if (referral.getAnonymousReporterIndicator() == false
					&& referrelClient.getSelfReportedIndicator() == false) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"false then selfReportedIndicator from ReferralClient should be true")
						.addPropertyNode("anonymousReporterIndicator").addConstraintViolation();
				return false;
			}
		} catch (Exception e) {
			throw new ValidationException("Unable to find Referral or ReferralClient", e);
		}

		return true;
	}
}