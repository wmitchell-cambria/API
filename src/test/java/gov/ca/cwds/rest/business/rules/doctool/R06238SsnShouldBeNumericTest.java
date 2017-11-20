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

/**
 * @author CWDS API Team
 * 
 */
public class R06238SsnShouldBeNumericTest {

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
   * @throws Exception - Exception
   */
  @Test
  public void testParticipantSsnTooLongFail() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setSsn("1234567890").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must match \"^(|[0-9]{9})$\"",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testParticipantSsnNonNumericFail() throws Exception {
    Participant toValidate =
        new ParticipantResourceBuilder().setSsn("1234567kk").createParticipant();
    Set<ConstraintViolation<Participant>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("must match \"^(|[0-9]{9})$\"",
        constraintViolations.iterator().next().getMessage());
  }

}
