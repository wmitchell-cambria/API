package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.validation.AtRiskAllegation;

/**
 * 
 * @author CWDS API Team
 * 
 * @see Allegation
 *
 */
public class R06511AtRiskAllegationTest {

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
   * Test when allegation type has 5001 and has one of types 2178, 2179, 2180 or 2181
   * 
   * @see AtRiskAllegation
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenAllegationTypeOther() throws Exception {
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType((short) 5001).createAllegation();
    gov.ca.cwds.rest.api.domain.Allegation allegation2 =
        new AllegationResourceBuilder().setInjuryHarmType((short) 2177).createAllegation();
    Set<gov.ca.cwds.rest.api.domain.Allegation> allegations =
        new HashSet<>(Arrays.asList(allegation, allegation2));
    ScreeningToReferral validScreeningtoreferal = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(validScreeningtoreferal);
    assertEquals(1, constraintViolations.size());
    assertEquals(
        "Allegations must contain one of following types if 'At risk, Sibling abused' (5001) is present: [2178 - General Neglect], [2179 - Physical Abuse], [2180 - Severe Neglect], [2181 - Sexual Abuse]",
        constraintViolations.iterator().next().getMessage());
  }

  /**
   * Test when allegation type has 5001 and has one of types 2178, 2179, 2180 or 2181
   * 
   * @see AtRiskAllegation
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testWhenAllegationType2179ReturnValid() throws Exception {
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType((short) 5001).createAllegation();
    gov.ca.cwds.rest.api.domain.Allegation allegation2 =
        new AllegationResourceBuilder().setInjuryHarmType((short) 2179).createAllegation();
    Set<gov.ca.cwds.rest.api.domain.Allegation> allegations =
        new HashSet<>(Arrays.asList(allegation, allegation2));
    ScreeningToReferral validScreeningtoreferal = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(validScreeningtoreferal);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testWhenAllegationType2178ReturnValid() throws Exception {
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType((short) 5001).createAllegation();
    gov.ca.cwds.rest.api.domain.Allegation allegation2 =
        new AllegationResourceBuilder().setInjuryHarmType((short) 2178).createAllegation();
    Set<gov.ca.cwds.rest.api.domain.Allegation> allegations =
        new HashSet<>(Arrays.asList(allegation, allegation2));
    ScreeningToReferral validScreeningtoreferal = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(validScreeningtoreferal);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testWhenAllegationType2180ReturnValid() throws Exception {
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType((short) 5001).createAllegation();
    gov.ca.cwds.rest.api.domain.Allegation allegation2 =
        new AllegationResourceBuilder().setInjuryHarmType((short) 2180).createAllegation();
    Set<gov.ca.cwds.rest.api.domain.Allegation> allegations =
        new HashSet<>(Arrays.asList(allegation, allegation2));
    ScreeningToReferral validScreeningtoreferal = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(validScreeningtoreferal);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testWhenAllegationType2181ReturnValid() throws Exception {
    gov.ca.cwds.rest.api.domain.Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType((short) 5001).createAllegation();
    gov.ca.cwds.rest.api.domain.Allegation allegation2 =
        new AllegationResourceBuilder().setInjuryHarmType((short) 2181).createAllegation();
    Set<gov.ca.cwds.rest.api.domain.Allegation> allegations =
        new HashSet<>(Arrays.asList(allegation, allegation2));
    ScreeningToReferral validScreeningtoreferal = new ScreeningToReferralResourceBuilder()
        .setAllegations(allegations).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(validScreeningtoreferal);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * Test fails when Allegation is empty or null.
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testFailAllegationisEmpty() throws Exception {
    ScreeningToReferral validScreeningtoreferal =
        new ScreeningToReferralResourceBuilder().setAllegations(null).createScreeningToReferral();
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(validScreeningtoreferal);
    assertEquals(1, constraintViolations.size());
    assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
  }

}
