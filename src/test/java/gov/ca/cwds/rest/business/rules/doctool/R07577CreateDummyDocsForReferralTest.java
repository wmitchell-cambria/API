package gov.ca.cwds.rest.business.rules.doctool;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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

import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.AllegationEntityBuilder;
import gov.ca.cwds.fixture.AllegationPerpetratorHistoryEntityBuilder;
import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;
import gov.ca.cwds.fixture.ChildClientEntityBuilder;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.CmsReporterResourceBuilder;
import gov.ca.cwds.fixture.DrmsDocumentEntityBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ReferralClientEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.helper.CmsIdGenerator;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ClientParticipants;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.ScreeningToReferralService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsReferralService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * 
 * @author CWDS API Team
 */
public class R07577CreateDummyDocsForReferralTest extends Doofenshmirtz<ClientAddress> {

  private gov.ca.cwds.data.persistence.cms.Referral referral;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    new TestingRequestExecutionContext("0X5");
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, riReferralClient);

    staffPerson = new StaffPersonEntityBuilder().setId("0X5").setCountyCode("34").build();
    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("52");

    participantService = mock(ParticipantService.class);
    governmentOrganizationCrossReportService = mock(GovernmentOrganizationCrossReportService.class);

    referralService = new XaCmsReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffpersonDao, assignmentService, validator, cmsDocumentService,
        drmsDocumentService, drmsDocumentTemplateService, addressService, longTextService,
        riReferral);

    screeningToReferralService =
        new ScreeningToReferralService(referralService, allegationService, crossReportService,
            participantService, Validation.buildDefaultValidatorFactory().getValidator(),
            referralDao, new MessageBuilder(), allegationPerpetratorHistoryService, reminders,
            governmentOrganizationCrossReportService, clientRelationshipDao);
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 07577" - Create Dummy Docs for Referral
   * 
   * When Referral is Posted, it creates three dummy document values in the drmsDocument and 
   * assigned the identifer in the referrals(drmsAllegationDescriptionDoc, drmsErReferralDoc, 
   * drmsInvestigationDoc).
   *
   * </pre>
   * 
   * </blockquote>
   * 
   * @throws Exception on general error
   */

  /**
   * test for drmsAllegationDescriptionDoc Id
   * 
   * @throws Exception on IO or JSON error
   */
  @Test
  public void testForDrmsAllegationDescriptionDocId() throws Exception {
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new ReferralEntityBuilder().build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentToCreate = new DrmsDocumentEntityBuilder().setId("ABD1234568").build();
    when(drmsDocumentDao.create(any(DrmsDocument.class))).thenReturn(drmsDocumentToCreate);

    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new ChildClientEntityBuilder().build();
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Client clientToCreate = new ClientEntityBuilder().build();
    when(clientDao.create(any(Client.class))).thenReturn(clientToCreate);

    ReferralClient referralClientToCreate = new ReferralClientEntityBuilder().build();
    when(referralClientDao.create(any(ReferralClient.class))).thenReturn(referralClientToCreate);

    Allegation allegationToCreate = new AllegationEntityBuilder().build();
    when(allegationDao.create(any(Allegation.class))).thenReturn(allegationToCreate);

    AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new AllegationPerpetratorHistoryEntityBuilder().build();
    when(allegationPerpetratorHistoryDao.create(any(AllegationPerpetratorHistory.class)))
        .thenReturn(allegationPerpHistoryToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
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

    Assignment assignmentToCreate = new AssignmentEntityBuilder().build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId("ABC1234567").build();
    CaseLoad[] caseLoadList = new CaseLoad[1];
    caseLoadList[0] = caseLoad;
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);
    when(assignmentDao.findCaseLoads(any(String.class))).thenReturn(caseLoadList);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation)
              throws Throwable {
            referral = (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            return referral;
          }
        });

    mockParticipantService(screeningToReferral);

    screeningToReferralService.create(screeningToReferral);
    assertThat(referral.getDrmsAllegationDescriptionDoc(), is(equalTo("ABD1234568")));
  }

  /**
   * test for drmsErReferralDoc Id
   * 
   * @throws Exception on IO or JSON error
   */
  @Test
  public void testForDrmsErReferralDocId() throws Exception {
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new ReferralEntityBuilder().build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentToCreate = new DrmsDocumentEntityBuilder().setId("ABZ123456k").build();
    when(drmsDocumentDao.create(any(DrmsDocument.class))).thenReturn(drmsDocumentToCreate);

    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new ChildClientEntityBuilder().build();
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Client clientToCreate = new ClientEntityBuilder().build();
    when(clientDao.create(any(Client.class))).thenReturn(clientToCreate);

    ReferralClient referralClientToCreate = new ReferralClientEntityBuilder().build();
    when(referralClientDao.create(any(ReferralClient.class))).thenReturn(referralClientToCreate);

    Allegation allegationToCreate = new AllegationEntityBuilder().build();
    when(allegationDao.create(any(Allegation.class))).thenReturn(allegationToCreate);

    AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new AllegationPerpetratorHistoryEntityBuilder().build();
    when(allegationPerpetratorHistoryDao.create(any(AllegationPerpetratorHistory.class)))
        .thenReturn(allegationPerpHistoryToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
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

    Assignment assignmentToCreate = new AssignmentEntityBuilder().build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId("ABC1234567").build();
    CaseLoad[] caseLoadList = new CaseLoad[1];
    caseLoadList[0] = caseLoad;
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);
    when(assignmentDao.findCaseLoads(any(String.class))).thenReturn(caseLoadList);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation)
              throws Throwable {
            referral = (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            return referral;
          }
        });

    mockParticipantService(screeningToReferral);

    screeningToReferralService.create(screeningToReferral);
    assertThat(referral.getDrmsErReferralDoc(), is(equalTo("ABZ123456k")));
  }

  /**
   * test for drmsInvestigationDoc Id
   * 
   * @throws Exception on IO or JSON error
   */
  @Test
  public void testForDrmsInvestigationDocId() throws Exception {
    gov.ca.cwds.data.persistence.cms.Referral referralToCreate =
        new ReferralEntityBuilder().build();
    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referralToCreate);

    DrmsDocument drmsDocumentToCreate = new DrmsDocumentEntityBuilder().setId("AYZ12340X5").build();
    when(drmsDocumentDao.create(any(DrmsDocument.class))).thenReturn(drmsDocumentToCreate);

    gov.ca.cwds.data.persistence.cms.ChildClient childClientToCreate =
        new ChildClientEntityBuilder().build();
    when(childClientDao.create(any(gov.ca.cwds.data.persistence.cms.ChildClient.class)))
        .thenReturn(childClientToCreate);

    Client clientToCreate = new ClientEntityBuilder().build();
    when(clientDao.create(any(Client.class))).thenReturn(clientToCreate);

    ReferralClient referralClientToCreate = new ReferralClientEntityBuilder().build();
    when(referralClientDao.create(any(ReferralClient.class))).thenReturn(referralClientToCreate);

    Allegation allegationToCreate = new AllegationEntityBuilder().build();
    when(allegationDao.create(any(Allegation.class))).thenReturn(allegationToCreate);

    AllegationPerpetratorHistory allegationPerpHistoryToCreate =
        new AllegationPerpetratorHistoryEntityBuilder().build();
    when(allegationPerpetratorHistoryDao.create(any(AllegationPerpetratorHistory.class)))
        .thenReturn(allegationPerpHistoryToCreate);

    Set<CrossReport> crossReportDomain =
        MAPPER.readValue(fixture("fixtures/domain/ScreeningToReferral/valid/validCrossReport.json"),
            new TypeReference<Set<CrossReport>>() {});
    gov.ca.cwds.data.persistence.cms.CrossReport crossReportToCreate =
        new gov.ca.cwds.data.persistence.cms.CrossReport("3456789ABC",
            // ((CrossReport) crossReportDomain).getThirdId(),
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

    Assignment assignmentToCreate = new AssignmentEntityBuilder().build();
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(assignmentToCreate);
    CaseLoad caseLoad = new CaseLoadEntityBuilder().setId("ABC1234567").build();
    CaseLoad[] caseLoadList = new CaseLoad[1];
    caseLoadList[0] = caseLoad;
    when(assignmentDao.findCaseLoads(any(String.class))).thenReturn(caseLoadList);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();

    when(referralDao.create(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenAnswer(new Answer<gov.ca.cwds.data.persistence.cms.Referral>() {

          @Override
          public gov.ca.cwds.data.persistence.cms.Referral answer(InvocationOnMock invocation)
              throws Throwable {
            referral = (gov.ca.cwds.data.persistence.cms.Referral) invocation.getArguments()[0];
            return referral;
          }
        });

    mockParticipantService(screeningToReferral);

    screeningToReferralService.create(screeningToReferral);
    assertThat(referral.getDrmsInvestigationDoc(), is(equalTo("AYZ12340X5")));
  }

  private void mockParticipantService(ScreeningToReferral screeningToReferral) {
    final ClientParticipants clientParticipants = new ClientParticipants();
    final Set<Participant> participants = screeningToReferral.getParticipants();
    final CmsIdGenerator generator = new CmsIdGenerator();

    for (Participant participant : participants) {
      participant.setLegacyId(generator.generate());
    }

    clientParticipants.addParticipants(participants);
    when(participantService.saveParticipants(any(), any(), any(), any(), any()))
        .thenReturn(clientParticipants);
  }

}
