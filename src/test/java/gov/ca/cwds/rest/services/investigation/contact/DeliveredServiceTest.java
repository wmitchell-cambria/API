package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

public class DeliveredServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  DeliveredServiceDao deliveredServiceDao;
  StaffPersonDao staffPersonDao;
  LongTextHelper longTextHelper;
  Date timestamp;

  DeliveredService target;


  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {

    new TestingRequestExecutionContext("0X5");
    deliveredServiceDao = mock(DeliveredServiceDao.class);
    staffPersonDao = mock(StaffPersonDao.class);
    longTextHelper = mock(LongTextHelper.class);


    target = new DeliveredService(deliveredServiceDao, staffPersonDao, longTextHelper);
    timestamp = new Date();
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
  public void getTheLastUpdatedByStaffPersonCallsStaffPersonDaoFind() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    target.getTheLastUpdatedByStaffPerson(toTest);
    verify(staffPersonDao, atLeastOnce()).find(any());
  }

  @Test
  public void getTheLastUpdatedByStaffPersonAddressesNullStaffPersonId() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, null, new Date());
    LastUpdatedBy actual = target.getTheLastUpdatedByStaffPerson(toTest);
    LastUpdatedBy expected = new LastUpdatedBy();
    assertEquals(actual, expected);
  }

  @Test
  public void combineDetailTextAndContinuationCallsLongTextHelperTwice() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    target.combineDetailTextAndContinuation(toTest);
    verify(longTextHelper, times(2)).getLongText(any());
  }

  @Test
  public void updateCallsDeliveredServiceDaoUpdate() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity("id",
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Set<PostedIndividualDeliveredService> people = new HashSet<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao.update(any())).thenReturn(toTest);
    when(longTextHelper.updateLongText(any(), any(), any())).thenReturn("ABCD");
    target.update("123", request, "99");
    verify(deliveredServiceDao, atLeastOnce()).update(any());
  }

  @Test
  public void createCallsDeliveredServiceDaoCreate() throws Exception {
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity toTest =
        new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(DEFAULT_KEY,
            deliveredServiceDomain, "ABC", new Date());
    Set<Integer> services = new HashSet<>();
    final Set<PostedIndividualDeliveredService> people = new HashSet<>();
    ContactRequest contactRequest = new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408",
        "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    ContactReferralRequest request = new ContactReferralRequest("referralid", contactRequest);
    when(deliveredServiceDao.find(any())).thenReturn(toTest);
    when(deliveredServiceDao
        .create(any(gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class)))
            .thenReturn(toTest);
    when(longTextHelper.updateLongText(any(), any(), any())).thenReturn("ABCD");
    target.create(request, "99");
    verify(deliveredServiceDao, atLeastOnce()).create(any());
  }



}
