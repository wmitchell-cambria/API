package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.*;
import gov.ca.cwds.fixture.*;
import gov.ca.cwds.rest.services.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * CWDS API Team
 */
public class R01054PrimaryAssignmentAddingTest {

  private ReferralDao referralDao;
  private CaseDao caseDao;
  private CaseLoadDao caseLoadDao;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;

  @Before
  public void setUp() {
    referralDao = mock(ReferralDao.class);
    caseDao = mock(CaseDao.class);
    caseLoadDao = mock(CaseLoadDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
  }

  @Test
  public void testReferralAssignment() {
    short governmentEntityType = (short) -1;

    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P").setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();
    CwsOffice cwsOffice = new CwsOfficeEntityBuilder().setGovernmentEntityType(governmentEntityType).build();
    Referral referral = new ReferralEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(cwsOffice);
    when(referralDao.find(anyString())).thenReturn(referral);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    rule.execute();

    assertEquals((int)governmentEntityType, (int)referral.getGovtEntityType());
    verify(referralDao, times(1)).update(any());
    verify(caseDao, times(0)).update(any());
  }

  @Test
  public void testCaseAssignment() {
    short governmentEntityType = (short) -1;

    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P").setEstablishedForCode("C").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();
    CwsOffice cwsOffice = new CwsOfficeEntityBuilder().setGovernmentEntityType(governmentEntityType).build();
    CmsCase cmsCase = new CaseEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(cwsOffice);
    when(caseDao.find(anyString())).thenReturn(cmsCase);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    rule.execute();

    assertEquals((int)governmentEntityType, (int)cmsCase.getGovernmentEntityType());
    verify(referralDao, times(0)).update(any());
    verify(caseDao, times(1)).update(any());
  }

  @Test
  public void testReferralAssignmentNoReferralFound() {
    short governmentEntityType = (short) -1;

    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P").setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();
    CwsOffice cwsOffice = new CwsOfficeEntityBuilder().setGovernmentEntityType(governmentEntityType).build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(cwsOffice);
    when(referralDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch(ServiceException e) {
      assertEquals("Cannot find referral for assignment: SlCAr46088", e.getMessage());
    }
    verify(referralDao, times(0)).update(any());
    verify(caseDao, times(0)).update(any());
  }

  @Test
  public void testCaseAssignmentNoCaseFound() {
    short governmentEntityType = (short) -1;

    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P").setEstablishedForCode("C").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();
    CwsOffice cwsOffice = new CwsOfficeEntityBuilder().setGovernmentEntityType(governmentEntityType).build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(cwsOffice);
    when(caseDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch(ServiceException e) {
      assertEquals("Cannot find case for assignment: SlCAr46088", e.getMessage());
    }
    verify(referralDao, times(0)).update(any());
    verify(caseDao, times(0)).update(any());
  }

  @Test
  public void testNoCwsOfficeFound() {
    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P").setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch(ServiceException e) {
      assertEquals("Cannot find cwsOffice for assignmentUnit: assignmentUnitId", e.getMessage());
    }

    verify(caseDao, times(0)).update(any());
  }

  @Test
  public void testAssignmentUnitFound() {
    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P").setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch(ServiceException e) {
      assertEquals("Cannot find assignmentUnit for caseLoad: ABC1234567", e.getMessage());
    }
    verify(referralDao, times(0)).update(any());
    verify(caseDao, times(0)).update(any());
  }

  @Test
  public void testCaseLoadFound() {
    Assignment assignment = new AssignmentEntityBuilder().setEstablishedForCode("R").build();
    when(caseLoadDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch(ServiceException e) {
      assertEquals("Cannot find caseLoad for assignment: SlCAr46088", e.getMessage());
    }
    verify(referralDao, times(0)).update(any());
    verify(caseDao, times(0)).update(any());
  }

  @Test
  public void testNoPrimaryAssignment() {
    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("N").build();
    when(caseLoadDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao, caseDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    rule.execute();

    verify(referralDao, times(0)).update(any());
    verify(caseDao, times(0)).update(any());
  }
}