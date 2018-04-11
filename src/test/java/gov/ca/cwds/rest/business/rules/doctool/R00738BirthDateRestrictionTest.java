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
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.validation.AfterDateValid;

/**
 * @author CWDS API Team
 * 
 * @see Client
 *
 */
public class R00738BirthDateRestrictionTest {
  private Validator validator;

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * 
   */
  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test when client creationdate date is below the client birth date, using the AfterDateValidator
   * 
   * @see AfterDateValid
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenCreationDateIsLesserThanBirthDate() throws Exception {
    Client toValidate = new ClientResourceBuilder().setBirthDate("2017-08-02")
        .setCreationDate("2010-08-02").build();
    Set<ConstraintViolation<Client>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("should be greater than or equal to birthDate",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void successWhenCreationDateIsGreaterThanBirthDate() throws Exception {
    Client toValidate = new ClientResourceBuilder().setBirthDate("2010-08-02")
        .setCreationDate("2017-08-02").build();
    Set<ConstraintViolation<Client>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }

}
