package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.cms.AllegationPerpetratorHistoryService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.ChildClientService;
import gov.ca.cwds.rest.services.cms.ClientAddressService;
import gov.ca.cwds.rest.services.cms.ClientService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsAddressService;
import gov.ca.cwds.rest.services.cms.xa.XaCmsReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferralClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * 
 * @author CWDS API Team
 */
public class R00796ScreeningToReferralDeleteTest extends Doofenshmirtz<ClientAddress> {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    new TestingRequestExecutionContext("0X5");

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

    allegationService = new AllegationService(allegationDao, riAllegation);

    allegationPerpetratorHistoryDao = mock(AllegationPerpetratorHistoryDao.class);
    riAllegationPerpetratorHistory = mock(RIAllegationPerpetratorHistory.class);
    allegationPerpetratorHistoryService = new AllegationPerpetratorHistoryService(
        allegationPerpetratorHistoryDao, riAllegationPerpetratorHistory);

    crossReportService = new CrossReportService(crossReportDao, riCrossReport);

    riReporter = mock(RIReporter.class);
    reporterService = new ReporterService(reporterDao, riReporter);
    addressService = new XaCmsAddressService(addressDao, ssaName3Dao, upperCaseTables, validator);

    clientAddressService =
        new ClientAddressService(clientAddressDao, staffPersonDao, triggerTablesDao,
            laCountyTrigger, nonLACountyTriggers, riClientAddress, validator, addressService);

    longTextService = new LongTextService(longTextDao);

    childClientDao = mock(ChildClientDao.class);
    riChildClient = mock(RIChildClient.class);
    childClientService = new ChildClientService(childClientDao, riChildClient);

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
