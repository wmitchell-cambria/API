package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportEntityBuilder;
import gov.ca.cwds.fixture.GovernmentOrganizationCrossReportResourceBuilder;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentOrganizationCrossReportTest {

  private String thirdId = "AbalBln0Ki";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  /**
   * Constructor test
   * 
   * @throws Exception general error
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(GovernmentOrganizationCrossReport.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent constructor test
   * 
   * @throws Exception general error
   */
  @Test
  public void testPersistentConstructor() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport validGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistent =
        new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(
            validGovernmentOrganizationCrossReport.getThirdId(),
            validGovernmentOrganizationCrossReport.getCountySpecificCode(),
            validGovernmentOrganizationCrossReport.getCrossReportThirdId(),
            validGovernmentOrganizationCrossReport.getReferralId(),
            validGovernmentOrganizationCrossReport.getGovernmentOrganizationId(),
            validGovernmentOrganizationCrossReport.getOrganizationTypeInd());

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getPrimaryKey(), is(equalTo(thirdId)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(validGovernmentOrganizationCrossReport.getCountySpecificCode())));
    assertThat(persistent.getCrossReportThirdId(), is(equalTo(validGovernmentOrganizationCrossReport.getCrossReportThirdId())));
    assertThat(persistent.getReferralId(), is(equalTo(validGovernmentOrganizationCrossReport.getReferralId())));
    assertThat(persistent.getGovernmentOrganizationId(),
        is(equalTo(validGovernmentOrganizationCrossReport.getGovernmentOrganizationId())));
    assertThat(persistent.getOrganizationTypeInd(),
        is(equalTo(validGovernmentOrganizationCrossReport.getOrganizationTypeInd())));
    assertThat(persistent.getReferral(), is(equalTo(null)));
    assertThat(persistent.getCrossReport(), is(equalTo(null)));
    assertThat(persistent.getGovernmentOrganization(), is(equalTo(null)));
  }

  /**
   * domain constructor test
   * 
   * @throws Exception general error
   */
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport domainGovernmentOrganizationCrossReport =
        new GovernmentOrganizationCrossReportResourceBuilder().build();

    GovernmentOrganizationCrossReport persistent = new GovernmentOrganizationCrossReport(thirdId,
        domainGovernmentOrganizationCrossReport, lastUpdatedId, lastUpdatedTime);

    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getCountySpecificCode())));
    assertThat(persistent.getCrossReportThirdId(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getCrossReportThirdId())));
    assertThat(persistent.getReferralId(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getReferralId())));
    assertThat(persistent.getGovernmentOrganizationId(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getGovernmentOrganizationId())));
    assertThat(persistent.getOrganizationTypeInd(),
        is(equalTo(domainGovernmentOrganizationCrossReport.getOrganizationTypeInd())));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	GovernmentOrganizationCrossReport gocr = new GovernmentOrganizationCrossReportEntityBuilder().build();
	assertTrue(gocr.equals(gocr));
  }
  
  @Test
  public void shouldHaveSameHashCodesForObjectsWithSameValues() {
	GovernmentOrganizationCrossReport gocr = new GovernmentOrganizationCrossReportEntityBuilder().build();
	GovernmentOrganizationCrossReport gocr1 = new GovernmentOrganizationCrossReportEntityBuilder().build();
	assertEquals("Expecting GovernmentOrganizationCrossReport to have same hash code", gocr, gocr1);

  }

}
