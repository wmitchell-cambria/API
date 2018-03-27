package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;

/**
 * @author CWDS API Team
 *
 */
public class HOIUsingClientIdServiceTest {

  private InvolvementHistoryService involvementHistoryService;
  private HoiUsingClientIdService hoiUsingClientIdService;

  private String clientId;
  private String clientId2;

  private List<String> clientIds = Arrays.asList(clientId, clientId2);

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * setup for before test
   */
  @Before
  public void setup() {
    clientId = "1XVZ43D";
    clientId2 = "2ZY52Q";

    clientIds = Arrays.asList(clientId, clientId2);
    involvementHistoryService = mock(InvolvementHistoryService.class);
    hoiUsingClientIdService = new HoiUsingClientIdService();
    hoiUsingClientIdService.involvementHistoryService = involvementHistoryService;
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    HoiUsingClientIdService target = new HoiUsingClientIdService();
    assertThat(target, notNullValue());
  }

  /**
   * Test to find the history of involvement by client id's
   */
  @Test
  public void shouldSearchHOIByClientIds() {
    InvolvementHistory involvementHistory = new InvolvementHistory(null, new ArrayList<HOICase>(),
        new ArrayList<HOIReferral>(), new ArrayList<HOIScreening>());
    when(involvementHistoryService.findInvolvementHistoryByClientIds(any(Set.class)))
        .thenReturn(involvementHistory);
    List<String> clientIds = new ArrayList<>();
    clientIds.add(clientId);
    clientIds.add(clientId2);
    Response returned = hoiUsingClientIdService.findByClientIds(clientIds);
    assertThat(returned, is(notNullValue()));
  }

  // delete test
  /**
   * delete method not implemented
   * 
   * @throws Exception - Exception
   */
  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    hoiUsingClientIdService.delete("string");
  }

  // update test
  /**
   * update method not implemented
   * 
   * @throws Exception - Exception
   */
  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    hoiUsingClientIdService.update("string", null);
  }


  // create test
  /**
   * update method not implemented
   * 
   * @throws Exception - Exception
   */
  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    hoiUsingClientIdService.create(null);
  }

  /**
   * find method not implemented
   * 
   * @throws Exception - Exception
   */
  // find test
  @Test
  public void findThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    hoiUsingClientIdService.find(null);
  }

}
