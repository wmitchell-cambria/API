package gov.ca.cwds.rest.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.util.ParticipantUtils;

/**
 * Victim Birth Validator is implemented to validate the Dob, or AgeUnit, AgeUnitCode is required
 * for the victim.
 * 
 * @author CWDS API team
 */
public class VictimBirthValidator
    implements ConstraintValidator<ValidVictimBirth, ScreeningToReferral> {

  @Override
  public void initialize(ValidVictimBirth constraintAnnotation) {
    // nothing to initialize in this class
  }

  @Override
  public boolean isValid(ScreeningToReferral screening, ConstraintValidatorContext context) {
    boolean valid = true;
    Collection<Participant> victims = ParticipantUtils.getVictims(screening.getParticipants());
    if (!victims.isEmpty()) {
      for (Participant victim : victims) {
        if (!hasValidBirthDateOrAge(victim, context)) {
          valid = false;
          break;
        }
      }
    }
    return valid;
  }

  private boolean hasValidBirthDateOrAge(Participant victim, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(victim.getDateOfBirth())
        && StringUtils.isBlank(victim.getApproximateAge())) {
      String message = "Victim's should have either of the value DOB or approximateAge";
      buildMessage(context, message);
      return false;
    }
    if (StringUtils.isNotBlank(victim.getApproximateAge())
        && !victim.getApproximateAge().contains("0")
        && StringUtils.isBlank(victim.getApproximateAgeUnits())) {
      String message = "Victim's approximateAgeUnits must be set if approximateAge is set";
      buildMessage(context, message);
      return false;
    }
    return true;
  }

  private void buildMessage(ConstraintValidatorContext context, String message) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }

}
