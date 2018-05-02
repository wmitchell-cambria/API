package gov.ca.cwds.rest.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.util.GetValidParticipantUtils;

/**
 * @author CWDS API team
 *
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
    Collection<Participant> victims = GetValidParticipantUtils.getVictims(screening.getParticipants());
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
    if (StringUtils.isBlank(victim.getDateOfBirth()) && victim.getApproximateAge().contains("0")) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
          "Victim's should have either of the value DOB or AgeNumber").addConstraintViolation();
      return false;
    }
    if (StringUtils.isNotBlank(victim.getApproximateAge())
        && StringUtils.isBlank(victim.getApproximateAgeUnits())) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate("Victim's AgeUnit must be set if AgeNumber is set")
          .addConstraintViolation();
      return false;
    }
    return true;
  }

}
