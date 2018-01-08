package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CmsReporterResourceBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralServiceTest {

  private ClientDao clientDao;
  private ReferralClientDao referralClientDao;
  private HOIReferralService hoiService;
  private HOIRequest request;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test setup
   * 
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    SystemCodeCache.global().getAllSystemCodes();
    clientDao = mock(ClientDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    hoiService = new HOIReferralService(clientDao, referralClientDao);
    request = new HOIRequest();
    request.setClientIds(Stream.of("123").collect(Collectors.toSet()));
  }

  /**
   * Test to find for handle find
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testForHandleFind() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567")
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568")
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testHandleFindWhenReporterMandated() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().setMandatedReporterIndicator(true).build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567")
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568")
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testHandleFindWhenAnonymousReporter() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567")
        .setAnonymousReporterIndicator("Y")
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet())).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568")
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testHandleFindWhenSelfReporter() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId("1234567ABC").setPerpetratorClientId("P975G53fTh").build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567")
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet())).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568")
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain =
        new ReferralClientResourceBuilder().setSelfReportedIndicator(true).buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void testForHandleFindWhenPerpetratorAndVictimClientNull() throws Exception {
    Allegation allegation1 = new AllegationEntityBuilder().setReferralId("ABC1234567")
        .setVictimClientId("1234567ABC").setPerpetratorClientId(null).build();

    Allegation allegation2 = new AllegationEntityBuilder().setReferralId("ABC1234568")
        .setVictimClientId(null).setPerpetratorClientId(null).build();

    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("0X5").build();
    Reporter reporter = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter persistentReporter =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporter, "0X5", new Date());

    Referral referral1 = new ReferralEntityBuilder().setId("ABC1234567")
        .setAllegations(Arrays.asList(allegation1).stream().collect(Collectors.toSet()))
        .setReporter(persistentReporter).build();
    Referral referral2 = new ReferralEntityBuilder().setId("ABC1234568")
        .setAllegations(Arrays.asList(allegation2).stream().collect(Collectors.toSet()))
        .setFirstResponseDeterminedByStaffPersonId("0X5").build();

    referral1.setStaffPerson(staffPerson);
    referral2.setStaffPerson(staffPerson);

    Client client = new ClientEntityBuilder().build();
    ReferralClient referralCliemtDomain = new ReferralClientResourceBuilder().buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent1 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain, "OXA",
            new Date());
    persistent1.setReferral(referral1);

    ReferralClient referralCliemtDomain1 =
        new ReferralClientResourceBuilder().setReferralId("ABC1234568").buildReferralClient();
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent2 =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(referralCliemtDomain1, "OXA",
            new Date());
    persistent2.setReferral(referral2);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {persistent1, persistent2};

    when(clientDao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientIds(any(Collection.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind(request);
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() throws Exception {
    hoiService.handleRequest(new HOIReferral());
  }

}
