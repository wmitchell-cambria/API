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
import gov.ca.cwds.fixture.CmsAllegationResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.validation.NotEqual;

/**
 * Test cases for R - 00785 Client Restriction.
 * 
 * @author CWDS API Team
 *
 */
public class R00785AllegationClientRestrictionTest {

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
   * Test to throw exception when victim and Perpetrator both are same
   * 
   * @see NotEqual
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenVictimAndPerpetratorAreSame() throws Exception {
    Allegation toValidate = new CmsAllegationResourceBuilder().setVictimClientId("1234567890")
        .setPerpetratorClientId("1234567890").buildCmsAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    assertEquals("can not be same as victimClientId",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * Test to throw exception when victim and Perpetrator both are NOT same
   * 
   * @see NotEqual
   * 
   * @throws Exception - Exception
   */
  @Test
  public void successWhenVictimAndPerpetratorAreNotSame() throws Exception {
    Allegation toValidate = new CmsAllegationResourceBuilder().setVictimClientId("1234567890")
        .setPerpetratorClientId("1234567891").buildCmsAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }


  /**
   * Test to throw exception when victim and Perpetrator both are NOT same
   * 
   * @see NotEqual
   * 
   * @throws Exception - Exception
   */
  @Test
  public void successWhenPerpetratorIsNull() throws Exception {
    Allegation toValidate = new CmsAllegationResourceBuilder().setVictimClientId("1234567890")
        .setPerpetratorClientId(null).buildCmsAllegation();
    Set<ConstraintViolation<Allegation>> constraintViolations = validator.validate(toValidate);
    assertEquals(0, constraintViolations.size());
  }


}
