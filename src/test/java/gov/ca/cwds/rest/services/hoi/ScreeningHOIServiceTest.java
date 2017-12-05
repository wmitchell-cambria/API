package gov.ca.cwds.rest.services.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.CombinedHOI;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ScreeningHOIServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private ScreeningHOIService screeningHOIService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    screeningHOIService = new ScreeningHOIService();
  }

  // find test
  @Test
  public void findReturnsExpectedHistoryOfInvolvement() throws Exception {

    CombinedHOI serialized = MAPPER.readValue(
        fixture("gov/ca/cwds/rest/services/hoi/combinedhoi/valid/valid.json"), CombinedHOI.class);
    Response returned = screeningHOIService.find("999999");
    assertThat(returned, is(serialized));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningHOIService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningHOIService.delete("string");
  }


  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    screeningHOIService.delete("string");
  }


}
