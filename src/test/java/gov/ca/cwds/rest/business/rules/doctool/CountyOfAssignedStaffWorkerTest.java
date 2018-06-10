package gov.ca.cwds.rest.business.rules.doctool;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.CountyOfAssignedStaffWorker;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 */
public class CountyOfAssignedStaffWorkerTest {
  private StaffPersonDao staffPersonDao;

  @Before
  public void setup() {
    staffPersonDao = mock(StaffPersonDao.class);
    when(staffPersonDao.find("q1p")).thenReturn(new StaffPersonEntityBuilder().build());
  }

  @Test
  public void failsValidationWhenCountyOfAssignedSocialWorkerIsDifferentFromIncidentCounty()
      throws Exception {
    Referral referral = new ReferralResourceBuilder().setPrimaryContactStaffPersonId("q1p")
        .setCountySpecificCode("45").build();
    Boolean expected = new CountyOfAssignedStaffWorker(referral, staffPersonDao).isValid();
    assertEquals(expected, false);
  }

  @Test
  public void successWhenCountyOfAssignedSocialWorkerIsSameAsIncidentCounty() throws Exception {
    Referral referral = new ReferralResourceBuilder().setPrimaryContactStaffPersonId("q1p")
        .setCountySpecificCode("99").build();
    Boolean expected = new CountyOfAssignedStaffWorker(referral, staffPersonDao).isValid();
    assertEquals(expected, true);
  }

  @Test(expected = ServiceException.class)
  public void exceptionWhenAssignedSocialWorkerIdIsNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setPrimaryContactStaffPersonId(null)
        .setCountySpecificCode("45").build();
    new CountyOfAssignedStaffWorker(referral, staffPersonDao).isValid();
  }

}
