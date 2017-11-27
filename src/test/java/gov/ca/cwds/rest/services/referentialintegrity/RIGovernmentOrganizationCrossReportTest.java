
package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.fixture.CmsCrossReportResourceBuilder;
import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 *
 */
public class RIGovernmentOrganizationCrossReportTest {

  private CrossReportDao crossReportDao;
  private ReferralDao referralDao;
  private GovernmentOrganizationDao governmentOrganizationDao;

  /**
   * 
   */
  @Before
  public void setup() {
    crossReportDao = mock(CrossReportDao.class);
    referralDao = mock(ReferralDao.class);
    governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void type() throws Exception {
    assertThat(RIGovernmentOrganizationCrossReport.class, notNullValue());
  }

  /**
   * @throws Exception- exception
   */
  @Test
  public void instantiation() throws Exception {
    RIGovernmentOrganizationCrossReport target = new RIGovernmentOrganizationCrossReport(
        crossReportDao, referralDao, governmentOrganizationDao);
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenCrossReportNotFound() throws Exception {

    GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport("ABC1234567",
            domainGovernmentOrganizationCrossReport, "0X5", new Date());

    when(crossReportDao.find(any(String.class))).thenReturn(null);
    when(referralDao.find(any(String.class))).thenReturn(null);
    when(governmentOrganizationDao.find(any(String.class))).thenReturn(null);
    RIGovernmentOrganizationCrossReport target = new RIGovernmentOrganizationCrossReport(
        crossReportDao, referralDao, governmentOrganizationDao);
    target.apply(governmentOrganizationCrossReport);
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckFailureWhenReferralNotFound() throws Exception {

    GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport("ABC1234567",
            domainGovernmentOrganizationCrossReport, "0X5", new Date());

    CrossReport crossReportDomain = new CmsCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234567", crossReportDomain, "0X5",
            new Date());

    when(crossReportDao.find(any(String.class))).thenReturn(crossReport);
    when(referralDao.find(any(String.class))).thenReturn(null);
    when(governmentOrganizationDao.find(any(String.class))).thenReturn(null);
    RIGovernmentOrganizationCrossReport target = new RIGovernmentOrganizationCrossReport(
        crossReportDao, referralDao, governmentOrganizationDao);
    target.apply(governmentOrganizationCrossReport);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void riCheckPassWhenGovernmentOrganizationNull() throws Exception {

    GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().setGovernmentOrganizationId(null)
            .build();
    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport("ABC1234567",
            domainGovernmentOrganizationCrossReport, "0X5", new Date());

    CrossReport crossReportDomain = new CmsCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234567", crossReportDomain, "0X5",
            new Date());

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234ftd", referralDomain, "0X5");

    when(crossReportDao.find(any(String.class))).thenReturn(crossReport);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(governmentOrganizationDao.find(any(String.class))).thenReturn(null);
    RIGovernmentOrganizationCrossReport target = new RIGovernmentOrganizationCrossReport(
        crossReportDao, referralDao, governmentOrganizationDao);
    Boolean result = target.apply(governmentOrganizationCrossReport);
    assertThat(result, is(equalTo(true)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test(expected = ReferentialIntegrityException.class)
  public void riCheckPassWhenGovernmentOrganizationNotFound() throws Exception {

    GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport("ABC1234567",
            domainGovernmentOrganizationCrossReport, "0X5", new Date());

    CrossReport crossReportDomain = new CmsCrossReportResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.CrossReport crossReport =
        new gov.ca.cwds.data.persistence.cms.CrossReport("ABC1234567", crossReportDomain, "0X5",
            new Date());

    Referral referralDomain = new ReferralResourceBuilder().build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234ftd", referralDomain, "0X5");

    when(crossReportDao.find(any(String.class))).thenReturn(crossReport);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(governmentOrganizationDao.find(any(String.class))).thenReturn(null);
    RIGovernmentOrganizationCrossReport target = new RIGovernmentOrganizationCrossReport(
        crossReportDao, referralDao, governmentOrganizationDao);
    target.apply(governmentOrganizationCrossReport);
  }

}
