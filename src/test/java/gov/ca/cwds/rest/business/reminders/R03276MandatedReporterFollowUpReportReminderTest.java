package gov.ca.cwds.rest.business.reminders;

import static org.mockito.Mockito.mock;

import org.junit.Before;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.rest.services.cms.TickleService;

/**
 * Unit tests for R03276MandatedReporterFollowUpReportReminder rule
 * 
 * @author CWDS API Team
 *
 */
public class R03276MandatedReporterFollowUpReportReminderTest {

  private TickleService tickleService;
  private ReferralDao referralDao;


  /**
   * @throws Exception general test error
   */
  @Before
  public void setup() throws Exception {
    tickleService = mock(TickleService.class);
    referralDao = mock(ReferralDao.class);
  }

  // TODO write tests - see R04631ReferralInvestigationContactDueTest OR R05443StateIdMissingTest

}
