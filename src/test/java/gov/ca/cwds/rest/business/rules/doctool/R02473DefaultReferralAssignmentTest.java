package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.cms.AssignmentService;

public class R02473DefaultReferralAssignmentTest {

  private AssignmentService assignmentService;
  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;
  private CountyOwnershipDao countyOwnershipDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private MessageBuilder messageBuilder;
  private ScreeningToReferral screeningToReferral;
  private CaseLoadDao caseLoadDao;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;
  private Date lastUpdatedTime = new Date();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    assignmentDao = mock(AssignmentDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);
    countyOwnershipDao = mock(CountyOwnershipDao.class);
    referralDao = mock(ReferralDao.class);
    referralClientDao = mock(ReferralClientDao.class);
    messageBuilder = mock(MessageBuilder.class);
    screeningToReferral = mock(ScreeningToReferral.class);
    caseLoadDao = mock(CaseLoadDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    when(screeningToReferral.getAssigneeStaffId()).thenReturn("0X5");
    nonLACountyTriggers =
        new NonLACountyTriggers(countyOwnershipDao, referralDao, referralClientDao);
    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);

  }


  @Test
  public void shouldSaveNewAssignment() {

    Referral referral = new ReferralResourceBuilder().build();

    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p",
            lastUpdatedTime);

    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    assignmentService.createDefaultAssignmentForNewReferral(screeningToReferral, "ABC1234567",
        referral, messageBuilder);
    verify(assignmentDao, times(1)).create(any());
  }

  @Test
  public void shouldNotSaveWhenAssigneeIdIsDifferentFromCurrentUserId() {

    Referral referral = new ReferralResourceBuilder().build();

    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p",
            lastUpdatedTime);
    when(screeningToReferral.getAssigneeStaffId()).thenReturn("con");
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    assignmentService.createDefaultAssignmentForNewReferral(screeningToReferral, "ABC1234567",
        referral, messageBuilder);

    verify(assignmentDao, times(0)).create(any());
  }

  @Test
  public void shouldNotSaveWhenReferralReceivedDateNotEqualToAssignmentStartDate() {
    MessageBuilder mb = new MessageBuilder();

    Referral referral = new ReferralResourceBuilder().setReceivedDate("2016-12-08")
        .setReceivedTime("16:01:01").build();

    Assignment assignmentDomain = new AssignmentResourceBuilder().setStartDate("2017-12-08")
        .setStartTime("15:01:01").buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p",
            lastUpdatedTime);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    CaseLoad caseload = new CaseLoadEntityBuilder().build();
    when(caseLoadDao.find(any())).thenReturn(caseload);

    assignmentService.createDefaultAssignmentForNewReferral(screeningToReferral, "ABC1234567",
        referral, mb);

    Boolean messageFound = Boolean.FALSE;

    List<ErrorMessage> messages = mb.getMessages();
    for (ErrorMessage em : messages) {
      if (em.getMessage().contains("03731")) {
        messageFound = Boolean.TRUE;
      }
    }
    assertThat(messageFound, is(equalTo(Boolean.TRUE)));
  }

  @Test
  public void shouldNotSaveWhenCaseloadIsInactiveOrOnHold() {
    MessageBuilder mb = new MessageBuilder();
    Referral referral = new ReferralResourceBuilder().build();

    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p",
            lastUpdatedTime);

    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    when(assignmentDao.findCaseLoads(any())).thenReturn(new CaseLoad[]{});

    assignmentService.createDefaultAssignmentForNewReferral(screeningToReferral, "ABC1234567",
        referral, mb);

    Boolean messageFound = Boolean.FALSE;
    List<ErrorMessage> messages = mb.getMessages();
    for (ErrorMessage em : messages) {
      if (em.getMessage().equals("R - 02473 Caseload is either inactive or on hold")) {
        messageFound = Boolean.TRUE;
      }
    }
    assertThat(messageFound, is(equalTo(Boolean.TRUE)));
  }

}
