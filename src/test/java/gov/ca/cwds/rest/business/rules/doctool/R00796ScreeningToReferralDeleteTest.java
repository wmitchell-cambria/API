package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.LongTextDaoImpl;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.xa.XaCmsAddressDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsReferralDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsSsaName3DaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsStaffPersonDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.business.rules.xa.XaNonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.xa.XaUpperCaseTables;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
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
import gov.ca.cwds.rest.services.cms.xa.XaCmsAddressService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferralClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * 
 * @author CWDS API Team
 */
public class R00796ScreeningToReferralDeleteTest extends Doofenshmirtz<ClientAddress> {

  private ScreeningToReferralService screeningToReferralService;

  private XaCmsReferralService referralService;
  private XaCmsAddressService addressService;

  private ClientService clientService;
  private ReferralClientService referralClientService;
  private AllegationService allegationService;
  private AllegationPerpetratorHistoryService allegationPerpetratorHistoryService;
  private CrossReportService crossReportService;
  private ReporterService reporterService;
  private ClientAddressService clientAddressService;
  private ChildClientService childClientService;
  private LongTextService longTextService;
  private CmsDocumentService cmsDocumentService;
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentTemplateService drmsDocumentTemplateService;
  private AssignmentService assignmentService;
  private ParticipantService participantService;

  private RIChildClient riChildClient;
  private RIAllegationPerpetratorHistory riAllegationPerpetratorHistory;
  private RIClientAddress riClientAddress;
  private RIAllegation riAllegation;
  private RICrossReport riCrossReport;
  private RIReporter riReporter;
  private RIReferral riReferral;
  private RIReferralClient riReferralClient;

  private XaCmsReferralDaoImpl referralDao;
  private XaCmsStaffPersonDaoImpl staffPersonDao;
  private XaCmsAddressDaoImpl addressDao;
  private XaCmsSsaName3DaoImpl ssaName3Dao;
  private XaUpperCaseTables upperCaseTables;

  private XaNonLACountyTriggers nonLACountyTriggers;

  private AllegationDao allegationDao;
  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;
  private CrossReportDao crossReportDao;
  private ReporterDao reporterDao;
  private ClientAddressDao clientAddressDao;
  private ChildClientDao childClientDao;
  private LongTextDaoImpl longTextDao;
  private LACountyTrigger laCountyTrigger;
  private TriggerTablesDao triggerTablesDao;
  private AssignmentDao assignmentDao;
  private Reminders reminders;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;
  private MessageBuilder messageBuilder;
  private ClientRelationshipDao clientRelationshipDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    referralDao = mock(XaCmsReferralDaoImpl.class);
    staffPersonDao = mock(XaCmsStaffPersonDaoImpl.class);
    ssaName3Dao = mock(XaCmsSsaName3DaoImpl.class);
    addressDao = mock(XaCmsAddressDaoImpl.class);

    upperCaseTables = mock(XaUpperCaseTables.class);

    nonLACountyTriggers = mock(XaNonLACountyTriggers.class);

    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    staffPersonDao = mock(XaCmsStaffPersonDaoImpl.class);
    riReferral = mock(RIReferral.class);
    clientRelationshipDao = mock(ClientRelationshipDao.class);

    referralService = new XaCmsReferralService(referralDao, nonLACountyTriggers, laCountyTrigger,
        triggerTablesDao, staffPersonDao, assignmentService, validator, cmsDocumentService,
        drmsDocumentService, drmsDocumentTemplateService, addressService, longTextService,
        riReferral);

    triggerTablesDao = mock(TriggerTablesDao.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);

    clientService = new ClientService(clientDao, staffPersonDao, triggerTablesDao,
        nonLACountyTriggers, ssaName3Dao, upperCaseTables, externalInterfaceTables);

    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    riReferralClient = mock(RIReferralClient.class);
    referralClientService = new ReferralClientService(referralClientDao, nonLACountyTriggers,
        laCountyTrigger, triggerTablesDao, staffPersonDao, riReferralClient);

    allegationDao = mock(AllegationDao.class);
    riAllegation = mock(RIAllegation.class);
    allegationService = new AllegationService(allegationDao, riAllegation);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    riAllegationPerpetratorHistory = mock(RIAllegationPerpetratorHistory.class);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, riAllegationPerpetratorHistory);

    crossReportDao = mock(CrossReportDao.class);
    riCrossReport = mock(RICrossReport.class);
    crossReportService = new CrossReportService(crossReportDao, riCrossReport);

    reporterDao = mock(ReporterDao.class);
    riReporter = mock(RIReporter.class);
    reporterService = new ReporterService(reporterDao, riReporter);

    addressService = new XaCmsAddressService(addressDao, ssaName3Dao, upperCaseTables, validator);

    clientAddressDao = mock(ClientAddressDao.class);
    laCountyTrigger = mock(LACountyTrigger.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    riClientAddress = mock(RIClientAddress.class);
    clientAddressService =
        new ClientAddressService(clientAddressDao, staffPersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);

    longTextDao = mock(LongTextDaoImpl.class);
    longTextService = new LongTextService(longTextDao);

    childClientDao = mock(ChildClientDao.class);
    riChildClient = mock(RIChildClient.class);
    childClientService = new ChildClientService(childClientDao, riChildClient);

    assignmentDao = mock(AssignmentDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
    messageBuilder = mock(MessageBuilder.class);

    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffPersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);

    participantService = mock(ParticipantService.class);
    governmentOrganizationCrossReportService = mock(GovernmentOrganizationCrossReportService.class);
    reminders = mock(Reminders.class);
  }

  /**
   * <pre>
   * <blockquote>
   * DocTool Rule R - 00796 
   * 
   * If the user had originally indicated that the call should be treated as a referral,
   * and that referral had been committed to the database, that referral must be deleted from the
   * system. Also deleted would be any referral clients associated with the referral, any clients
   * associated to the referral if that referral had been their only interaction with the system,
   * and all allegations associated with the referral. Do NOT delete the client if the client
   * already exists on the Host and associated to other Case or Referral.
   * 
   * This rule involves deleting a referral and associated referral clients, clients and
   * allegations. Since there is no business requirement at this time to delete a referral we will
   * not be implementing this rule. A NO-OP delete method is provided in the code that gives a Not
   * Implemented Exception.
   * </blockquote>
   * </pre>
   */
  @Test
  public void deleteThrowsNotImplementedException() {
    thrown.expect(NotImplementedException.class);
    screeningToReferralService.delete("string");
  }

  @Test
  public void deleteThrowsAssertionError() {
    thrown.expect(AssertionError.class);
    try {
      screeningToReferralService.delete(1L);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }

}
