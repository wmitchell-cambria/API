package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferralResponse;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralServiceTest {

  private ClientDao clientdao;
  private ReferralClientDao referralClientDao;

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

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
    clientdao = mock(ClientDao.class);
    referralClientDao = mock(ReferralClientDao.class);
  }

  /**
   * Test to find for handle find
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testForHandleFind() throws Exception {

    HOIReferralService hoiService = new HOIReferralService(clientdao, referralClientDao);
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

    when(clientdao.find(any(String.class))).thenReturn(client);
    when(referralClientDao.findByClientId(any(String.class))).thenReturn(referralClients);

    HOIReferralResponse response = hoiService.handleFind("123");
    assertThat(response.getHoiReferrals().size(), is(equalTo(2)));
  }

}
