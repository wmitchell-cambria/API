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
import org.mockito.Mockito;

import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.fixture.ChildClientEntityBuilder;
import gov.ca.cwds.fixture.contacts.ReferralClientDeliveredServiceEntityBuilder;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

public class ReferralClientDeliveredServiceTest {

  ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  ChildClientDao childClientDao;
  Date timestamp;

  ReferralClientDeliveredService target;
  private String deliveredServiceId;
  private String referralId;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {
    deliveredServiceId = "ABC1234567";
    referralId = "ABX1234560";
    new TestingRequestExecutionContext("0X5");
    referralClientDeliveredServiceDao = mock(ReferralClientDeliveredServiceDao.class);
    childClientDao = mock(ChildClientDao.class);
    target = new ReferralClientDeliveredService(referralClientDeliveredServiceDao, childClientDao);
    timestamp = new Date();

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

  @Test(expected = ServiceException.class)
  public void checkContactIdValidForGivenReferralIdWhenNoReferralClientDeliveredServiceEntities()
      throws Exception {
    ReferralClientDeliveredServiceEntity[] entity = {};
    when(referralClientDeliveredServiceDao.findByReferralId(referralId)).thenReturn(entity);
    target.checkContactIdValidForGivenReferralId(referralId, deliveredServiceId);
  }

  @Test(expected = ServiceException.class)
  public void checkContactIdValidForGivenReferralIdWhenContactIdNotValid() throws Exception {
    target.checkContactIdValidForGivenReferralId(referralId, "DEF1234567");
    verify(referralClientDeliveredServiceDao, atLeastOnce()).findByReferralId(any());
  }

  @Test
  public void findByReferralIdCallsReferralClientDeliveredServiceDao() throws Exception {
    target.findByReferralId(referralId);
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
  public void addOnBehalfOfClientsForGivenReferralIdWhenNoClientsToAdd() throws Exception {
    ChildClient[] childClients = {new ChildClientEntityBuilder().build()};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    ReferralClientDeliveredServiceEntity entity =
        new ReferralClientDeliveredServiceEntityBuilder().setClientId("ABC12345ll")
            .setReferralId(referralId).setDeliveredServiceId(deliveredServiceId).build();
    ReferralClientDeliveredServiceEntity[] entities = {entity};
    when(referralClientDeliveredServiceDao.findByReferralId(referralId)).thenReturn(entities);

    when(referralClientDeliveredServiceDao.find(entity.getPrimaryKey())).thenReturn(entity);

    target.addOnBehalfOfClients(deliveredServiceId, referralId, "99");
    verify(referralClientDeliveredServiceDao, Mockito.times(0)).create(any());
  }

  @Test(expected = ServiceException.class)
  public void addOnBehalfOfClientsForGivenReferralIdThrowsExceptionWhenNoVictimClients()
      throws Exception {
    ChildClient[] childClients = {};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.addOnBehalfOfClients(deliveredServiceId, referralId, "99");
  }

  @Test(expected = ServiceException.class)
  public void addOnBehalfOfClientsForGivenReferralIdThrowsExceptionWhenVictimClientsNull()
      throws Exception {
    ChildClient[] childClients = null;
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.addOnBehalfOfClients(deliveredServiceId, referralId, "99");
  }


  @Test
  public void updateOnBehalfOfClientsForGivenReferralIdCallsReferralClientDeliveredServiceDao()
      throws Exception {
    ChildClient[] childClients = {new ChildClientEntityBuilder().build()};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.updateOnBehalfOfClients(deliveredServiceId, referralId, "99");
    verify(referralClientDeliveredServiceDao, atLeastOnce()).create(any());
  }

  @Test(expected = ServiceException.class)
  public void updateOnBehalfOfClientsForGivenReferralIdWhenNoVictimClients() throws Exception {
    ChildClient[] childClients = {};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.updateOnBehalfOfClients(deliveredServiceId, referralId, "99");
  }


  @Test(expected = ServiceException.class)
  public void updateOnBehalfOfClientsForGivenReferralIdWhenVictimClientsNull() throws Exception {
    ChildClient[] childClients = null;
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    target.updateOnBehalfOfClients(deliveredServiceId, referralId, "99");
  }


  @Test
  public void updateOnBehalfOfClientsForGivenReferralIdWhenNotExistsReferralClientDeliveredServiceEntity()
      throws Exception {
    ChildClient[] childClients =
        {new ChildClientEntityBuilder().setVictimClientId("APc109852u").build()};
    when(childClientDao.findVictimClients(referralId)).thenReturn(childClients);
    ReferralClientDeliveredServiceEntity[] entity =
        {new ReferralClientDeliveredServiceEntityBuilder().build()};
    when(referralClientDeliveredServiceDao.findByReferralId(referralId)).thenReturn(entity);
    target.updateOnBehalfOfClients(deliveredServiceId, referralId, "99");
    verify(referralClientDeliveredServiceDao, Mockito.times(0)).create(any());
  }

}
