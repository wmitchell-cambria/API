package gov.ca.cwds.rest.services.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.fixture.investigation.HistoryOfInvolvementEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HistoryOfInvolvementServiceTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private HistoryOfInvolvementService historyOfInvolvementService;
  private DeliveredServiceDao deliveredServiceDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    historyOfInvolvementService = new HistoryOfInvolvementService(deliveredServiceDao);
  }

  // find test
  @Test
  public void findReturnsExpectedContact() throws Exception {
    HistoryOfInvolvement serialized = new HistoryOfInvolvementEntityBuilder().build();
    serialized = MAPPER.readValue(
        fixture("gov/ca/cwds/rest/services/investigation/historyOfInvolvement/valid/valid.json"),
        HistoryOfInvolvement.class);
    Response returned = historyOfInvolvementService.find("999999");
    assertThat(returned, is(serialized));
  }

  // delete test
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    historyOfInvolvementService.delete("string");
  }

  // update test
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    historyOfInvolvementService.delete("string");
  }


  // create test
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    historyOfInvolvementService.delete("string");
  }


}
