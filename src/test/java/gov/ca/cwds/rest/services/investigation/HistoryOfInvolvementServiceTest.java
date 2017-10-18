package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.fixture.investigation.HistoryOfInvolvementEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HistoryOfInvolvementServiceTest {

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
    Response returned = historyOfInvolvementService.find("string");
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
