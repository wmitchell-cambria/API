package gov.ca.cwds.rest.services.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.hoi.HOICaseResourceBuilder;
import gov.ca.cwds.fixture.hoi.HOIReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import io.dropwizard.jackson.Jackson;

/***
 *
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class InvolvementHistoryServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private HOICaseService hoiCaseService;
  private HOIReferralService hoiReferralService;
  private HOIScreeningService hoiScreeningService;
  private InvolvementHistoryService involvementHistoryService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");

    hoiCaseService = mock(HOICaseService.class);
    HOICase hoicase = new HOICaseResourceBuilder().createHOICase();
    List<HOICase> hoicases = new ArrayList<>();
    hoicases.add(hoicase);
    HOICaseResponse hoiCaseResponse = new HOICaseResponse();
    hoiCaseResponse.setHoiCases(hoicases);
    when(hoiCaseService.find(any(String.class))).thenReturn(hoiCaseResponse);

    hoiReferralService = mock(HOIReferralService.class);
    HOIReferral hoireferral = new HOIReferralResourceBuilder().createHOIReferral();
    List<HOIReferral> hoireferrals = new ArrayList<>();
    hoireferrals.add(hoireferral);
    HOIReferralResponse hoiReferralResponse = new HOIReferralResponse();
    hoiReferralResponse.setHoiReferrals(hoireferrals);
    when(hoiReferralService.handleFind(any(String.class))).thenReturn(hoiReferralResponse);

    hoiScreeningService = mock(HOIScreeningService.class);
    HOIScreeningResponse hoiScreeningResponse = new HOIScreeningResponse(new HashSet<>());
    when(hoiScreeningService.handleFind(any(String.class))).thenReturn(hoiScreeningResponse);

    involvementHistoryService = new InvolvementHistoryService();
    involvementHistoryService.hoiCaseService = hoiCaseService;
    involvementHistoryService.hoiReferralService = hoiReferralService;
    involvementHistoryService.hoiScreeningService = hoiScreeningService;

    ParticipantDao participantDao = mock(ParticipantDao.class);
    when(participantDao.findLegacyIdListByScreeningId(any(String.class))).thenReturn(new HashSet<>());
    involvementHistoryService.participantDao = participantDao;
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

  // find test
  @Test
  public void findReturnsExpectedHistoryOfInvolvementNonStub() throws Exception {
    Response returned = involvementHistoryService.find("1");
    assertThat(returned, is(notNullValue()));
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
    InvolvementHistoryService target = new InvolvementHistoryService();
    assertThat(target, notNullValue());
  }

}
