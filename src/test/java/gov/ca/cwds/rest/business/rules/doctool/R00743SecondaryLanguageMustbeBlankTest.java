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
import gov.ca.cwds.rest.validation.OnlyIf;

/**
 * @author CWDS API Team
 *
 */
public class R00743SecondaryLanguageMustbeBlankTest {

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();
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
   * Test when client seconday language is set without setting the client primaty language, using
   * the OnlyIfValidator
   * 
   * @see OnlyIf
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenSecondayLanguageSetWithoutPrimaryLanguage() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setPrimaryLanguage(null)
        .setSecondaryLanguage((short) 1271).createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("can only be set if primaryLanguage is set",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void SuccessWhenSecondayAndPrimaryLanguageSet() throws Exception {
    Participant toValidate = new ParticipantResourceBuilder().setPrimaryLanguage((short) 1271)
        .setSecondaryLanguage((short) 1253).createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

}
