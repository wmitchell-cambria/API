package gov.ca.cwds.rest.business.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.validation.AtRiskAllegation;

/**
 * 
 * <p>
 * BUSINESS RULE: "R - 06511"
 * 
 * If the allegation type is 'At risk, sibling abused', then there should exist at least one
 * allegation type of 'physical abuse', 'severe neglect', 'sexual abuse', or 'general neglect'
 * <p>
 * 
 * @author CWDS API Team
 *
 */
public class R06511AtRiskAllegation
    implements ConstraintValidator<AtRiskAllegation, Collection<Allegation>> {

  public static final Short AT_RISK_TYPE = 5001;
  public static final Short GENERAL_NEGLECT = 2178;
  public static final Short PHYSICAL_ABUSE = 2179;
  public static final Short SEVERE_NEGLECT = 2180;
  public static final Short SEXUAL_ABUSE = 2181;

  /*
   * (non-Javadoc)
   *
   * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
   */
  @Override
  public void initialize(AtRiskAllegation constraintAnnotation) {
    //No Initialization currently needed apparently
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
   * javax.validation.ConstraintValidatorContext)
   */
  @Override
  public boolean isValid(Collection<Allegation> allegations, ConstraintValidatorContext context) {
    boolean valid = true;
    List<Short> allegationTypes = new ArrayList<>();

    if (allegations != null) {
      for (Allegation allegation : allegations) {
        allegationTypes.add(allegation.getType());
      }

      if (allegationTypes.contains(AT_RISK_TYPE)) {
        valid = allegationTypes.contains(GENERAL_NEGLECT)
            || allegationTypes.contains(PHYSICAL_ABUSE) || allegationTypes.contains(SEVERE_NEGLECT)
            || allegationTypes.contains(SEXUAL_ABUSE);
      }

      return valid;
    }
    return valid;
  }
}
