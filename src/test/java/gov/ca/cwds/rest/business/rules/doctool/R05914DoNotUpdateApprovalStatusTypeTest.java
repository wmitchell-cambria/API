package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.Validation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.DrmsDocument;
import gov.ca.cwds.fixture.DrmsDocumentEntityBuilder;
import gov.ca.cwds.fixture.LongTextEntityBuilder;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * Test Class for DocTool Rule: R - 05914 - Do Not Update Approval Status Type
 * 
 * @author CWDS API Team
 */
public class R05914DoNotUpdateApprovalStatusTypeTest extends Doofenshmirtz<ClientAddress> {

  private ReferralService referralService;
  private AddressService addressService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    longTextService = new LongTextService(longTextDao);
    drmsDocumentService = new DrmsDocumentService(drmsDocumentDao);
    addressService = new AddressService(addressDao, ssaName3Dao, upperCaseTables, validator);

    referralService =
        new ReferralService(referralDao, nonLACountyTriggers, laCountyTrigger, triggerTablesDao,
            staffpersonDao, assignmentService, validator, cmsDocumentService, drmsDocumentService,
            drmsDocumentTemplateService, addressService, longTextService, riReferral);

    clientService = new ClientService(clientDao, staffpersonDao, triggerTablesDao,
        nonLACountyTriggers, ssaName3Dao, upperCaseTables, externalInterfaceTables);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffpersonDao, riReferralClient);
    allegationService = new AllegationService(allegationDao, riAllegation);

    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, riAllegationPerpetratorHistory);

    crossReportService = new CrossReportService(crossReportDao, riCrossReport);
    reporterService = new ReporterService(reporterDao, riReporter);

    clientAddressService =
        new ClientAddressService(clientAddressDao, staffpersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);
    childClientService = new ChildClientService(childClientDao, riChildClient);

    messageBuilder = mock(MessageBuilder.class);
    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);

    participantService = mock(ParticipantService.class);
  }

  /**
   * <blockquote>
   *
   * <pre>
   *
   * DocTool Rule: "R - 05914" -  Do Not Update Approval Status Type
   * 
   * When creating the Referral entity, set the Approval Status Type = 'Request Not Submitted'.
   * When updating the Referral entity, update every attribute except Approval Status Type.
   * The Approval Status Type will be updated by the Host, not the workstation.
   * </pre>
   *
   * </blockquote>
   *
   * @throws Exception general error
   */
  @Test
  public void createReferralWithDefaultsSetsApprovalCodeNotSubmitted() throws Exception {
    ScreeningToReferral screeningToReferral =
        new ScreeningToReferralResourceBuilder().createScreeningToReferral();

    gov.ca.cwds.data.persistence.cms.LongText longTextToCreate =
        new LongTextEntityBuilder().build();
    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(longTextToCreate);

    DrmsDocument drmsDocumentToCreate = new DrmsDocumentEntityBuilder().build();
    when(drmsDocumentDao.create(any(DrmsDocument.class))).thenReturn(drmsDocumentToCreate);

    Referral referralCreated = referralService.createReferralWithDefaults(screeningToReferral,
        "2016-08-03T01:00:00.000Z", "2016-08-03T01:00:00.000Z", new MessageBuilder());
    System.out.println(referralCreated.getApprovalStatusType());
    assertThat(referralCreated.getApprovalStatusType(), is(equalTo((short) 118)));
  }

}
