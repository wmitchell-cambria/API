package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.AfterDateValid;

/**
 * @author CWDS API Team
 *
 */
public class R10664LimitedAccessDateGreaterTest {

  private Validator validator;

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test when referral limited access date is below the referral received date, using the
   * AfterDateValidator
   * 
   * @see AfterDateValid
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenLimitedAccessIsLower() throws Exception {
    Referral toValidate = new ReferralResourceBuilder().setReceivedDate("1996-08-23")
        .setLimitedAccessDate("1990-08-23").build();
    Set<ConstraintViolation<Referral>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("should be greater than or equal to receivedDate",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void successWhenLimitedAccessIsAbove() throws Exception {
    Referral toValidate = new ReferralResourceBuilder().setReceivedDate("1996-08-23")
        .setLimitedAccessDate("1996-08-24").build();
    Set<ConstraintViolation<Referral>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

}
