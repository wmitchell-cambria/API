package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.DeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.contacts.ReferralClientDeliveredServiceEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

public class ContactServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  DeliveredService deliveredService;
  ReferralClientDeliveredService referralClientDeliveredService;
  ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  ReferralDao referralDao;
  DeliveredToIndividualService deliveredToIndividualService;
  ContactService target;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {
    deliveredService = mock(DeliveredService.class);
    referralClientDeliveredService = mock(ReferralClientDeliveredService.class);
    contactPartyDeliveredServiceDao = mock(ContactPartyDeliveredServiceDao.class);
    referralDao = mock(ReferralDao.class);
    deliveredToIndividualService = mock(DeliveredToIndividualService.class);

    target = new ContactService(deliveredService, referralClientDeliveredService,
        deliveredToIndividualService, contactPartyDeliveredServiceDao, referralDao);
    when(deliveredService.find(any()))
        .thenReturn(new DeliveredServiceEntityBuilder().buildDeliveredServiceEntity());
    when(deliveredService.getTheLastUpdatedByStaffPerson(any()))
        .thenReturn(new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr."));
    when(deliveredService.combineDetailTextAndContinuation(any())).thenReturn("this is a test");
    when(deliveredToIndividualService.getPeopleInIndividualDeliveredService(any()))
        .thenReturn(new HashSet<PostedIndividualDeliveredService>());

    ReferralClientDeliveredServiceEntity[] entity =
        {new ReferralClientDeliveredServiceEntityBuilder().build()};
    when(referralClientDeliveredService.findByReferralId(any())).thenReturn(entity);

  }

  @Test
  public void type() throws Exception {
    assertThat(ContactService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findSingleContact() throws Exception {
    final String primaryKey = "ABC1234567:" + "ABC1234567";
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }

  @Test
  public void findAllContactsForAReferral() throws Exception {
    final String primaryKey = "ABC1234567";
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }


  @Test(expected = NotImplementedException.class)
  public void delete_Args__String() throws Exception {
    String primaryKey = null;
    target.delete(primaryKey);
  }

}
