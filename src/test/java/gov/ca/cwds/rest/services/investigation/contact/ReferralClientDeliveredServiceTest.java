package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.fixture.ChildClientEntityBuilder;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.fixture.contacts.ReferralClientDeliveredServiceEntityBuilder;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

public class ReferralClientDeliveredServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  ReferralClientDao referralClientDao;
  ChildClientDao childClientDao;
  Date timestamp;

  ReferralClientDeliveredService target;

  private String deliveredServiceId;

  private String referralId;

  private String clientId;

  private String staffId;


  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {

    deliveredServiceId = "ABC1234567";
    referralId = "ABX1234560";
    clientId = "APc109852u";
    staffId = "0X5";

    new TestingRequestExecutionContext("0X5");
    referralClientDeliveredServiceDao = mock(ReferralClientDeliveredServiceDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    childClientDao = mock(ChildClientDao.class);


    target = new ReferralClientDeliveredService(referralClientDeliveredServiceDao,
        referralClientDao, childClientDao);
    timestamp = new Date();

    gov.ca.cwds.data.persistence.cms.ReferralClient referralClient =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            new ReferralClientResourceBuilder().buildReferralClient(), staffId, timestamp);
    gov.ca.cwds.data.persistence.cms.ReferralClient[] referralClients = {referralClient};

    when(referralClientDao.findByReferralId(referralId)).thenReturn(referralClients);

    ReferralClientDeliveredServiceEntity[] entity =
        {new ReferralClientDeliveredServiceEntityBuilder().build()};
    when(referralClientDeliveredServiceDao.findByReferralId(referralId)).thenReturn(entity);


  }

  @Test
  public void type() throws Exception {
    assertThat(DeliveredService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void checkContactIdValidForGivenReferralIdCallsReferralClientDeliveredServiceDao()
      throws Exception {


    target.checkContactIdValidForGivenReferralId(referralId, deliveredServiceId);
    verify(referralClientDeliveredServiceDao, atLeastOnce()).findByReferralId(any());
  }

  @Test
  public void addOnBehalfOfClientsForGivenReferralIdCallsReferralClientDeliveredServiceDao()
      throws Exception {
    ChildClient[] childClients = {new ChildClientEntityBuilder().build()};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.addOnBehalfOfClients(deliveredServiceId, referralId, "99");
    verify(referralClientDeliveredServiceDao, atLeastOnce()).create(any());
  }

  @Test
  public void updateOnBehalfOfClientsForGivenReferralIdCallsReferralClientDeliveredServiceDao()
      throws Exception {
    ChildClient[] childClients = {new ChildClientEntityBuilder().build()};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.updateOnBehalfOfClients(deliveredServiceId, referralId, "99");
    verify(referralClientDeliveredServiceDao, atLeastOnce()).create(any());
  }

}
