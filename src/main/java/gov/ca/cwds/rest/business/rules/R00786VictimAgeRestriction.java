package gov.ca.cwds.rest.business.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Role;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.validation.VictimAgeRestriction;

public class R00786VictimAgeRestriction
    implements ConstraintValidator<VictimAgeRestriction, ScreeningToReferral> {

  public static final Short MAX_VICTIM_AGE_YEARS = 18;

  /*
   * (non-Javadoc)
   * 
   * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
   */
  @Override
  public void initialize(VictimAgeRestriction constraintAnnotation) {
    // TODO Auto-generated method stub
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
   * javax.validation.ConstraintValidatorContext)
   */
  @Override
  public boolean isValid(ScreeningToReferral screening, ConstraintValidatorContext context) {
    boolean valid = true;
    Collection<Participant> victims = getVictims(screening.getParticipants());

    if (!victims.isEmpty()) {
      String referralReceiveTimestampStr = screening.getStartedAt();
      DateTime referralReceiveTimestamp =
          new DateTime(DomainChef.uncookDateString(referralReceiveTimestampStr));

      // Subtract 18 years from referral receive time
      DateTime victimOverAgeDate = referralReceiveTimestamp.minusYears(MAX_VICTIM_AGE_YEARS);

      for (Participant victim : victims) {
        boolean overage = isVictimOverAge(victim, victimOverAgeDate);
        if (overage) {
          valid = false;
          break;
        }
      }
    }

    return valid;
  }

  private Collection<Participant> getVictims(Collection<Participant> participants) {
    List<Participant> victims = new ArrayList<>();

    for (Participant participant : participants) {
      Set<String> roles = participant.getRoles();
      if (roles != null && roles.contains(Role.VICTIM_ROLE.getType())) {
        victims.add(participant);
      }
    }
    return victims;
  }

  private boolean isVictimOverAge(Participant victim, DateTime victimOverAgeDate) {
    boolean overage = false;
    String dobStr = victim.getDateOfBirth();

    if (StringUtils.isNotBlank(dobStr)) {
      DateTime dob = new DateTime(DomainChef.uncookDateString(dobStr));

      if (dob.isBefore(victimOverAgeDate)) {
        overage = true;
      }
    } else {
      // check if victim age is provided and it is not over 18.
      // At this time, victim's age is not provided in payload
    }

    return overage;
  }
}
