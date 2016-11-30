package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.CrossReport.PrimaryKey;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class PrimaryKeyTest {

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat tf = new SimpleDateFormat("HH:mm:ss");

  private String thirdId = "1234567ABC";
  private Short crossReportMethodType = 2097;
  private Boolean filedOutOfStateIndicator = Boolean.TRUE;
  private Boolean governmentOrgCrossRptIndicatorVar = Boolean.FALSE;
  private String informTime = "16:41:49";
  private String recipientBadgeNumber = "B10101";
  private Integer recipientPhoneExtensionNumber = 321;
  private BigDecimal recipientPhoneNumber = new BigDecimal(9876543);
  private String informDate = "2016-01-01";
  private String recipientPositionTitleDesc = "supervisor";
  private String referenceNumber = "REF1234";
  private String referralId = "ABC1234567";
  private String lawEnforcementId = "";
  private String staffPersonId = "0X5";
  private String description = "CWS referral description";
  private String recipientName = "jose nomas";
  private String outstateLawEnforcementAddr = "";
  private String countySpecificCode = "99";
  private Boolean lawEnforcementIndicator = Boolean.TRUE;
  private Boolean outStateLawEnforcementIndicator = Boolean.FALSE;
  private Boolean satisfyCrossReportIndicator = Boolean.TRUE;
  private String lastUpdatedId = "0X5";

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(CrossReport.PrimaryKey.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReport.PrimaryKey.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

  @Test
  public void domainConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.CrossReport domain =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport(thirdId, crossReportMethodType,
            filedOutOfStateIndicator, governmentOrgCrossRptIndicatorVar, informTime,
            recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber, informDate,
            recipientPositionTitleDesc, referenceNumber, referralId, lawEnforcementId,
            staffPersonId, description, recipientName, outstateLawEnforcementAddr,
            countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
            satisfyCrossReportIndicator);

    CrossReport persistent = new CrossReport(domain, "0X5");
    assertThat(persistent.getThirdId(), is(equalTo(thirdId)));
    assertThat(persistent.getCrossReportMethodType(), is(equalTo(crossReportMethodType)));
    assertThat(persistent.getFiledOutOfStateIndicator(), is(equalTo("Y")));
    assertThat(persistent.getGovernmentOrgCrossRptIndicatorVar(), is(equalTo("N")));
    assertThat(persistent.getInformTime(), is(equalTo(tf.parse(informTime))));
    assertThat(persistent.getRecipientBadgeNumber(), is(equalTo(recipientBadgeNumber)));
    assertThat(persistent.getRecipientPhoneExtensionNumber(),
        is(equalTo(recipientPhoneExtensionNumber)));
    assertThat(persistent.getRecipientPhoneNumber(), is(equalTo(recipientPhoneNumber)));
    assertThat(persistent.getInformDate(), is(equalTo(df.parse(informDate))));
    assertThat(persistent.getRecipientPositionTitleDesc(), is(equalTo(recipientPositionTitleDesc)));
    assertThat(persistent.getReferenceNumber(), is(equalTo(referenceNumber)));
    assertThat(persistent.getReferralId(), is(equalTo(referralId)));
    assertThat(persistent.getLawEnforcementId(), is(equalTo(lawEnforcementId)));
    assertThat(persistent.getStaffPersonId(), is(equalTo(staffPersonId)));
    assertThat(persistent.getDescription(), is(equalTo(description)));
    assertThat(persistent.getRecipientName(), is(equalTo(recipientName)));
    assertThat(persistent.getOutstateLawEnforcementAddr(), is(equalTo(outstateLawEnforcementAddr)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getLawEnforcementIndicator(), is(equalTo("Y")));
    assertThat(persistent.getOutStateLawEnforcementIndicator(), is(equalTo("N")));
    assertThat(persistent.getSatisfyCrossReportIndicator(), is(equalTo("Y")));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Test
  public void successWithEqualPrimaryKey() {

    gov.ca.cwds.rest.api.domain.cms.CrossReport domain =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport(thirdId, crossReportMethodType,
            filedOutOfStateIndicator, governmentOrgCrossRptIndicatorVar, informTime,
            recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber, informDate,
            recipientPositionTitleDesc, referenceNumber, referralId, lawEnforcementId,
            staffPersonId, description, recipientName, outstateLawEnforcementAddr,
            countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
            satisfyCrossReportIndicator);

    CrossReport persistent = new CrossReport(domain, "0X5");
    CrossReport.PrimaryKey persistentPrimaryKey = (PrimaryKey) persistent.getPrimaryKey();

    CrossReport.PrimaryKey primaryKey = new CrossReport.PrimaryKey(referralId, thirdId);

    assertThat(persistentPrimaryKey.equals(primaryKey), is(Boolean.TRUE));

  }

  @Test
  public void failureWithReferralIdNotEqualPrimaryKey() {

    String notEqualReferralId = "1234567abc";

    gov.ca.cwds.rest.api.domain.cms.CrossReport domain =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport(thirdId, crossReportMethodType,
            filedOutOfStateIndicator, governmentOrgCrossRptIndicatorVar, informTime,
            recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber, informDate,
            recipientPositionTitleDesc, referenceNumber, referralId, lawEnforcementId,
            staffPersonId, description, recipientName, outstateLawEnforcementAddr,
            countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
            satisfyCrossReportIndicator);

    CrossReport persistent = new CrossReport(domain, "0X5");
    CrossReport.PrimaryKey persistentPrimaryKey = (PrimaryKey) persistent.getPrimaryKey();

    CrossReport.PrimaryKey primaryKey = new CrossReport.PrimaryKey(notEqualReferralId, thirdId);

    assertThat(persistentPrimaryKey.equals(primaryKey), is(Boolean.FALSE));

  }

  @Test
  public void failureWithThirdIdNotEqualPrimaryKey() {

    String notEqualThirdId = "1234567abc";

    gov.ca.cwds.rest.api.domain.cms.CrossReport domain =
        new gov.ca.cwds.rest.api.domain.cms.CrossReport(thirdId, crossReportMethodType,
            filedOutOfStateIndicator, governmentOrgCrossRptIndicatorVar, informTime,
            recipientBadgeNumber, recipientPhoneExtensionNumber, recipientPhoneNumber, informDate,
            recipientPositionTitleDesc, referenceNumber, referralId, lawEnforcementId,
            staffPersonId, description, recipientName, outstateLawEnforcementAddr,
            countySpecificCode, lawEnforcementIndicator, outStateLawEnforcementIndicator,
            satisfyCrossReportIndicator);

    CrossReport persistent = new CrossReport(domain, "0X5");
    CrossReport.PrimaryKey persistentPrimaryKey = (PrimaryKey) persistent.getPrimaryKey();

    CrossReport.PrimaryKey primaryKey = new CrossReport.PrimaryKey(referralId, notEqualThirdId);

    assertThat(persistentPrimaryKey.equals(primaryKey), is(Boolean.FALSE));

  }

}
