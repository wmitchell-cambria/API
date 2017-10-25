package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.fixture.contacts.IndividualDeliveredServiceEntityBuilder;
import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

public class DeliveredToIndividualServiceTest {


  ClientDao clientDao;
  AttorneyDao attorneyDao;
  CollateralIndividualDao collateralIndividualDao;
  ServiceProviderDao serviceProviderDao;
  SubstituteCareProviderDao substituteCareProviderDao;
  ReporterDao reporterDao;
  DeliveredToIndividualService deliveredToIndividualService;
  IndividualDeliveredServiceDao individualDeliveredServiceDao;

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
    individualDeliveredServiceDao = mock(IndividualDeliveredServiceDao.class);
    substituteCareProviderDao = mock(SubstituteCareProviderDao.class);
    deliveredToIndividualService = new DeliveredToIndividualService(clientDao, attorneyDao,
        collateralIndividualDao, serviceProviderDao, substituteCareProviderDao, reporterDao,
        individualDeliveredServiceDao);
  }

  @Test
  public void type() throws Exception {
    assertThat(DeliveredToIndividualService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(deliveredToIndividualService, notNullValue());
  }

  @Test
  public void findPerson__Collateral() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("O")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(collateralIndividualDao, atLeastOnce()).find(any());
  }

  @Test
  public void findPerson__Client() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("C")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(clientDao, atLeastOnce()).find(any());

  }

  @Test
  public void findPerson__Attorney() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("A")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(attorneyDao, atLeastOnce()).find(any());
  }

  @Test
  public void findPerson__Reporter() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("R")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(reporterDao, atLeastOnce()).find(any());
  }


  @Test
  public void findPerson__ServiceProvider() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("P")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(serviceProviderDao, atLeastOnce()).find(any());
  }


  @Test
  public void findPerson__SubstituteCareProvider() throws Exception {
    IndividualDeliveredServiceEntity individualDeliveredService =
        new IndividualDeliveredServiceEntityBuilder().setDeliveredToIndividualCode("S")
            .buildIndividualDeliveredServiceEntity();
    deliveredToIndividualService.findPerson(individualDeliveredService);
    verify(substituteCareProviderDao, atLeastOnce()).find(any());
  }

  @Test
  public void addPeopleToIndividualDeliveredService() throws Exception {
    deliveredToIndividualService.addPeopleToIndividualDeliveredService("123", validContactRequest(),
        "99");
    verify(individualDeliveredServiceDao, atLeastOnce()).create(any());
  }


  private ContactRequest validContactRequest() {
    final Set<PostedIndividualDeliveredService> people = validPeople();
    return new ContactRequest("2010-04-27T23:30:14.000Z", "", "433", "408", "C",
        new HashSet<Integer>(), "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
  }


  private Set<PostedIndividualDeliveredService> validPeople() {
    final Set<PostedIndividualDeliveredService> ret = new HashSet<>();
    CmsRecordDescriptor person1LegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("3456789ABC").setUiId("2222-2222-3333-4444555")
            .setTableName("CLIENT_T").setTableDescription("Client").build();
    CmsRecordDescriptor person2LegacyDescriptor =
        new CmsRecordDescriptorEntityBuilder().setId("4567890ABC").setUiId("3333-2222-3333-4444555")
            .setTableName("REPTR_T").setTableDescription("Reporter").build();

    ret.add(new PostedIndividualDeliveredService(person1LegacyDescriptor, "John", "Bob", "Smith",
        "Mr.", "Jr.", ""));
    ret.add(new PostedIndividualDeliveredService(person2LegacyDescriptor, "Sam", "Bill", "Jones",
        "Mr.", "III", "Reporter"));
    return ret;
  }

}
