package gov.ca.cwds.rest.business.rules;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Set;

import javax.validation.Validation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fasterxml.jackson.core.type.TypeReference;

import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryEntityBuilder;
import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;
import gov.ca.cwds.fixture.ChildClientEntityBuilder;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.CmsReporterResourceBuilder;
import gov.ca.cwds.fixture.DrmsDocumentResourceBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.helper.CmsIdGenerator;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.ReferralClient;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ClientParticipants;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * 
 * @author CWDS API Team
 */
public class R00797SensitiveReferralAssignmentTest extends Doofenshmirtz<ClientAddress> {

  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private LongTextService longTextService;
  private CmsDocumentService cmsDocumentService;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentTemplateService drmsDocumentTemplateService;
  private AssignmentService assignmentService;
  private ParticipantService participantService;

  private gov.ca.cwds.data.persistence.cms.Referral referral;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * @throws Exception - Exception
   */
  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    new TestingRequestExecutionContext("02f");
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    clientService = new ClientService(clientDao, staffpersonDao, triggerTablesDao,
        nonLACountyTriggers, ssaName3Dao, upperCaseTables, externalInterfaceTables);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, riReferralClient);

    allegationService = new AllegationService(allegationDao, riAllegation);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    riAllegationPerpetratorHistory = mock(RIAllegationPerpetratorHistory.class);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, riAllegationPerpetratorHistory);

    crossReportService = new CrossReportService(crossReportDao, riCrossReport);

    reporterService = new ReporterService(reporterDao, riReporter);

    clientAddressService =
        new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);

    longTextService = new LongTextService(longTextDao);

    drmsDocumentDao = mock(DrmsDocumentDao.class);
    cmsDocumentService = mock(CmsDocumentService.class);
    drmsDocumentTemplateService = mock(DrmsDocumentTemplateService.class);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao);

    childClientService = new ChildClientService(childClientDao, riChildClient);

    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);

    reminders = mock(Reminders.class);
    riReferral = mock(RIReferral.class);

    participantService = mock(ParticipantService.class);
    ClientParticipants referralParticipants = new ClientParticipants();
    when(participantService.saveParticipants(any(), any(), any(), any()))
        .thenReturn(referralParticipants);

    governmentOrganizationCrossReportService = mock(GovernmentOrganizationCrossReportService.class);

    referralService = new XaCmsReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, assignmentService, validator, cmsDocumentService,
        drmsDocumentService, drmsDocumentTemplateService, addressService, longTextService,
        riReferral);

    screeningToReferralService = new ScreeningToReferralService(referralService, allegationService,
        crossReportService, participantService, validator, referralDao, new MessageBuilder(),
        allegationPerpetratorHistoryService, reminders, governmentOrganizationCrossReportService,
        clientRelationshipDao);
  }

  /**
   * <p>
   * BUSINESS RULE: "R - 00797" Sensitive Referral Assignment
   * </p>
   * 
   * <p>
   * A Sealed or Sensitive CASE or REFERRAL may be assigned to any Staff Person, whether they have
   * sealed or sensitive authority or not.
   * </p>
   * 
   * <p>
   * We are currently not checking for sealed or sensitive authority of a Staff Person when
   * assigning a CASE or REFERRAL. If we start checking for sealed or sensitive authority of a Staff
   * Person, this test should fail. The test should then be redesigned to make sure that the
   * Business Rule R - 00797 is being applied to Referral Creation.
   * </p>
   * 
   * @throws Exception - Exception
   */
  @Test
  public void testForSensitiveReferralAssignment() throws Exception {
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new ReferralEntityBuilder().build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentDomain = new DrmsDocumentResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.DrmsDocument drmsDocumentToCreate =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument("ABD1234568", drmsDocumentDomain, "ABC",
            new Date());
    when(drmsDocumentDao.create(any(gov.ca.cwds.data.persistence.cms.DrmsDocument.class)))
        .thenReturn(drmsDocumentToCreate);

    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new ChildClientEntityBuilder().build();
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Set<Client> clientDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validClient.json"),
            new TypeReference<Set<Client>>() {});
    gov.ca.cwds.data.persistence.cms.Client clientToCreate =
        new gov.ca.cwds.data.persistence.cms.Client("1234567ABC",
            (Client) clientDomain.toArray()[0], "ABC", new Date());
    when(clientDao.create(any(gov.ca.cwds.data.persistence.cms.Client.class)))
        .thenReturn(clientToCreate);

    Set<ReferralClient> referralClientDomain = MAPPER.readValue(
        fixture("fixtures/domain/ScreeningToReferral/valid/validReferralClient.json"),
        new TypeReference<Set<ReferralClient>>() {});
    gov.ca.cwds.data.persistence.cms.ReferralClient referralClientToCreate =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(
            (ReferralClient) referralClientDomain.toArray()[0], "ABC", new Date());
    when(referralClientDao.create(any(gov.ca.cwds.data.persistence.cms.ReferralClient.class)))
        .thenReturn(referralClientToCreate);

    Set<Allegation> allegationDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validAllegation.json"),
            new TypeReference<Set<Allegation>>() {});
    gov.ca.cwds.data.persistence.cms.Allegation allegationToCreate =
        new gov.ca.cwds.data.persistence.cms.Allegation("2345678ABC",
            (Allegation) allegationDomain.toArray()[0], "ABC", new Date());
    when(allegationDao.create(any(gov.ca.cwds.data.persistence.cms.Allegation.class)))
        .thenReturn(allegationToCreate);

    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new AllegationPerpetratorHistoryEntityBuilder().build();
    when(allegationPerpetratorHistoryDao
        .create(any(gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class)))
            .thenReturn(allegationPerpHistoryToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            (CrossReport) crossReportDomain.toArray()[0], "OXA", new Date());
    when(crossReportDao.create(any(gov.ca.cwds.data.persistence.cms.CrossReport.class)))
        .thenReturn(crossReportToCreate);

    Reporter reporterDomain = new CmsReporterResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Reporter reporterToCreate =
        new gov.ca.cwds.data.persistence.cms.Reporter(reporterDomain, "ABC", new Date());
    when(reporterDao.create(any(gov.ca.cwds.data.persistence.cms.Reporter.class)))
        .thenReturn(reporterToCreate);

    gov.ca.cwds.data.persistence.cms.Address addressToCreate = new AddressEntityBuilder().build();
    when(addressDao.create(any(gov.ca.cwds.data.persistence.cms.Address.class)))
        .thenReturn(addressToCreate);

    gov.ca.cwds.data.persistence.cms.ClientAddress clientAddressToCreate =
        new ClientAddressEntityBuilder().buildClientAddress();
    when(clientAddressDao.create(any(gov.ca.cwds.data.persistence.cms.ClientAddress.class)))
        .thenReturn(clientAddressToCreate);

    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new LongTextEntityBuilder().build();
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    gov.ca.cwds.data.persistence.cms.Assignment assignmentToCreate =
        new AssignmentEntityBuilder().build();
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId("ABC1234567").build();
    CaseLoad[] caseLoadList = new CaseLoad[1];
    caseLoadList[0] = caseLoad;
    StaffPerson staffPerson =
        new StaffPersonEntityBuilder().setId("02f").setCountyCode("34").build();
    when(staffPersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("BTr");
    when(assignmentDao.findCaseLoads(any(String.class))).thenReturn(caseLoadList);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setassigneeStaffId("02f").createScreeningToReferral();

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation) {
            referral = (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            return referral;
          }
        });

    ClientParticipants clientParticipants = new ClientParticipants();
    Set<Participant> participants = screeningToReferral.getParticipants();

    CmsIdGenerator generator = new CmsIdGenerator();
    for (Participant participant : participants) {
      participant.setLegacyId(generator.generate());
    }
    clientParticipants.addParticipants(participants);
    when(participantService.saveParticipants(any(), any(), any(), any()))
        .thenReturn(clientParticipants);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("21");

    riReferral = mock(RIReferral.class);
    referralService = new XaCmsReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, assignmentService, validator, cmsDocumentService,
        drmsDocumentService, drmsDocumentTemplateService, addressService, longTextService,
        riReferral);

    screeningToReferralService.create(screeningToReferral);
    assertThat(referral.getLastUpdatedId(), is(equalTo("02f")));
  }

  @Test
  public void R00797SensitiveReferralAssignmentDefaultsToTrue() throws Exception {
    assertEquals(Boolean.TRUE, new R00797SensitiveReferralAssignment().isValid());

  }

}
