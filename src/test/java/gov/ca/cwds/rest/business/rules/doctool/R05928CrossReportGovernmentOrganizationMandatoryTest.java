package gov.ca.cwds.rest.business.rules.doctool;


import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
public class R05928CrossReportGovernmentOrganizationMandatoryTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Validator validator;

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }


  @Test
  public void testCrossReportMissingGovtAgencyIdFails() throws Exception {
    ScreeningToReferral toValidate = MAPPER.readValue(
        fixture(
            "fixtures/domain/ScreeningToReferral/invalid/withCrossReportMissingGovtAgencyId.json"),
        ScreeningToReferral.class);
    Set<ConstraintViolation<ScreeningToReferral>> constraintViolations =
        validator.validate(toValidate);
    assertEquals(1, constraintViolations.size());
    ConstraintViolation<ScreeningToReferral> constraintViolation =
        constraintViolations.iterator().next();

    assertEquals("may not be empty", constraintViolation.getMessage());
    assertEquals("crossReports[].agencies[].id", constraintViolation.getPropertyPath().toString());
  }

}
