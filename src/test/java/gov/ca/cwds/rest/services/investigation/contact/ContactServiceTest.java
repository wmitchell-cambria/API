package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.cms.LongTextService;

public class ContactServiceTest {

  private static final String DEFAULT_KEY = "abc1234567";

  DeliveredService deliveredService;
  StaffPersonDao staffPersonDao;
  LongTextService longTextService;
  IndividualDeliveredServiceDao individualDeliveredServiceDao;
  ClientDao clientDao;
  AttorneyDao attorneyDao;
  CollateralIndividualDao collateralIndividualDao;
  ServiceProviderDao serviceProviderDao;
  SubstituteCareProviderDao substituteCareProviderDao;
  ReporterDao reporterDao;
  ReferralClientDeliveredService referralClientDeliveredService;
  ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  ReferralDao referralDao;
  ReferralClientDao referralClientDao;
  ChildClientDao childClientDao;
  DeliveredToIndividualService deliveredToIndividualService;
  ContactService target;

  @BeforeClass
  public static void setupSuite() {
    new TestSystemCodeCache();
    new TestingRequestExecutionContext("abc1234567");
  }

  @Before
  public void setup() throws Exception {
    attorneyDao = mock(AttorneyDao.class);
    clientDao = mock(ClientDao.class);
    collateralIndividualDao = mock(CollateralIndividualDao.class);
    reporterDao = mock(ReporterDao.class);
    serviceProviderDao = mock(ServiceProviderDao.class);
    substituteCareProviderDao = mock(SubstituteCareProviderDao.class);

    individualDeliveredServiceDao = mock(IndividualDeliveredServiceDao.class);
    deliveredService = mock(DeliveredService.class);
    staffPersonDao = mock(StaffPersonDao.class);
    longTextService = mock(LongTextService.class);
    referralClientDeliveredService = mock(ReferralClientDeliveredService.class);
    contactPartyDeliveredServiceDao = mock(ContactPartyDeliveredServiceDao.class);
    referralDao = mock(ReferralDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    childClientDao = mock(ChildClientDao.class);

    deliveredToIndividualService = new DeliveredToIndividualService(clientDao, attorneyDao,
        collateralIndividualDao, serviceProviderDao, substituteCareProviderDao, reporterDao,
        individualDeliveredServiceDao);
    target = new ContactService(deliveredService, referralClientDeliveredService,
        deliveredToIndividualService, contactPartyDeliveredServiceDao, referralDao);
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
  public void find_Args__String() throws Exception {
    final String primaryKey = DEFAULT_KEY;
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }

  @Test(expected = NotImplementedException.class)
  public void delete_Args__String() throws Exception {
    String primaryKey = null;
    target.delete(primaryKey);
  }

}
