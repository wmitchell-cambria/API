package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ParticipantResourceBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.business.rules.R00786VictimAgeRestriction;

/**
 * Unit tests for R00786VictimAgeRestriction rule.
 * 
 * @author CWDS API Team
 */
public class R00786VictimAgeRestrictionTest {

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  private Validator validator;

  @Before
  public void setup() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Test that victim age is under max allowed.
   */
  @Test
  public void testVictimAgeNotOver() {
    // check with age = 10 years
    int age = 10;
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations = validate(age);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * Test with victim age at maximum allowed
   */
  @Test
  public void testVictimAgeMax() {
    // check with age = 18 years
    int age = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS;
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations = validate(age);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * Test with victim age below maximum allowed
   */
  @Test
  public void testVictimAgeBelowMax() {
    // check with age = 18 years - 1 year
    int age = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS - 1;
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations = validate(age);
    assertEquals(0, constraintViolations.size());
  }

  /**
   * Test with victim age over maximum allowed
   */
  @Test
  public void testVictimAgeOver() {
    // check with age = 18 years + 1 year
    int age = R00786VictimAgeRestriction.MAX_VICTIM_AGE_YEARS + 1;
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations = validate(age);
    assertEquals(1, constraintViolations.size());
  }

  /**
   * Create ScreeningToReferral for test and validate it for give victim age.
   * 
   * @param victimAgeDays Victim age in days
   * @return Validation result.
   */
  private Set<ConstraintViolation<ScreeningToReferral>> validate(int victimAgeYears) {
    String screeningStartedAt = "2017-01-01T00:00:00.000-08:00";
    DateTime screeningStartedAtDateTime =
        new DateTime(DomainChef.uncookDateString(screeningStartedAt));

    ScreeningToReferralResourceBuilder builder = new ScreeningToReferralResourceBuilder();
    builder.setStartedAt(screeningStartedAt);

    String victimDob =
        DomainChef.cookDate(screeningStartedAtDateTime.minusYears(victimAgeYears).toDate());
    Participant victim = new ParticipantResourceBuilder().setGender("M").setDateOfBirth(victimDob)
        .createVictimParticipant();

    Participant perp = new ParticipantResourceBuilder().setGender("F").createPerpParticipant();
    Participant reporter =
        new ParticipantResourceBuilder().setGender("M").createReporterParticipant();

    Set<Participant> participants = new HashSet<>(Arrays.asList(victim, perp, reporter));
    builder.setParticipants(participants);

    ScreeningToReferral screeingToReferral = builder.createScreeningToReferral();

    return validator.validate(screeingToReferral);
  }
}
