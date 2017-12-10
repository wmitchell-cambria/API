package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.validation.NotEqual;

/**
 * @author CWDS API Team
 * 
 * @see Participant
 *
 */
public class R00741PrimaryAndSecondaryLanguageCantBeSameTest {
  private Validator validator;
  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test when client primary language and secondaty language are same, using the NotEqualValidator
   * 
   * @see NotEqual
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenPrimaryAndSeondayLanguageAreSame() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setPrimaryLanguage((short) 1253)
        .setSecondaryLanguage((short) 1253).createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("can not be same as primaryLanguage",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void SuccessWhenPrimaryAndSeondayLanguageAreDifferent() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setPrimaryLanguage((short) 1253)
        .setSecondaryLanguage((short) 1271).createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

}
