package gov.ca.cwds.rest.resources.relationship;

import static gov.ca.cwds.rest.core.Api.SCREENING_RELATIONSHIPS;
import static io.dropwizard.testing.FixtureHelpers.fixture;

import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import gov.ca.cwds.IntakeBaseTest;

public class ScreeningRelationshipResourceIRT extends IntakeBaseTest {

  @Test
  @Ignore
  public void testGet() throws Exception {
    String actualJson = doGetCall(SCREENING_RELATIONSHIPS + "/1");
    String expectedResponse =
        fixture("fixtures/gov/ca/cwds/rest/resources/relationship_get_response.json");
    JSONAssert.assertEquals(expectedResponse, actualJson, JSONCompareMode.NON_EXTENSIBLE);
  }

}
