package gov.ca.cwds.rest.services.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.resources.hoi.HOICaseResource;
import gov.ca.cwds.rest.resources.hoi.HOIReferralResource;
import io.dropwizard.jackson.Jackson;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class InvolvementHistoryServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private HOICaseResource hoicaseResource;
  private HOIReferralResource hoireferralResource;
  private InvolvementHistoryService involvementHistoryService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    hoicaseResource = mock(HOICaseResource.class);
    hoireferralResource = mock(HOIReferralResource.class);
    involvementHistoryService = new InvolvementHistoryService(hoicaseResource, hoireferralResource);
  }

  // find test
  @Test
  public void findReturnsExpectedHistoryOfInvolvement() throws Exception {
    InvolvementHistory serialized = MAPPER.readValue(
        fixture("gov/ca/cwds/rest/services/hoi/involvementhistory/valid/valid.json"),
        InvolvementHistory.class);
    Response returned = involvementHistoryService.find("999999");
    assertThat(returned, is(serialized));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    involvementHistoryService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    involvementHistoryService.update("string", null);
  }


  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    involvementHistoryService.create(null);
  }

  @Test
  public void instantiation() throws Exception {
    InvolvementHistoryService target =
        new InvolvementHistoryService(hoicaseResource, hoireferralResource);
    assertThat(target, notNullValue());
  }

}
