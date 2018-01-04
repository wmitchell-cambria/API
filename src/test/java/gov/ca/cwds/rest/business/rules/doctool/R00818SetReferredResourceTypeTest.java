package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ScreeningToReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.R00818SetReferredResourceType;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;

/**
 * 
 * @author CWDS API Team
 */
public class R00818SetReferredResourceTypeTest {

  private String id = "1234567ABC";
  private String currentLocationOfChildren = "current Location Of Children";
  private String drmsAllegationDescriptionDoc = "f";
  private String drmsErReferralDoc = "g";
  private String drmsInvestigationDoc = "h";
  private Boolean familyAwarenessIndicator = Boolean.FALSE;
  private Short govtEntityType = 3;
  private String limitedAccessCode = "j";
  private String firstResponseDeterminedByStaffPersonId = "t";
  private boolean filedCrossReport = true;
  private String responsibleAgencyCode = "w";
  private String responseRationaleText = "1234567ABC";
  private static final short NOT_REFERRED = (short) 3225;

  private GovernmentOrganizationCrossReportService governmentOrganizationCrossReportService;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   * <blockquote>
   *
   * <pre>
   * BUSINESS RULE: "R - 00818"
   *
   * IF    referralResponseTypeCode is set to Evaluate Out
   * THEN  referredToResourceType should be set to Not Referred
   *
   * </pre>
   *
   * </blockquote>
   * 
   * @throws Exception - Exception
   */
  @Test
  public void shouldSetReferredToResourceTypeDefault() throws Exception {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setResponseTime((short) 1519).createScreeningToReferral();
    assertEquals(new R00818SetReferredResourceType(screeningToReferral).isValid(), Boolean.TRUE);
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotSetReferredToResourceTypeDefault() throws Exception {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setResponseTime((short) 1520).createScreeningToReferral();
    assertEquals(new R00818SetReferredResourceType(screeningToReferral).isValid(), Boolean.FALSE);

  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldSetReferredToResourceOnReferral() throws Exception {
    ScreeningToReferral screeningToReferral = new ScreeningToReferralResourceBuilder()
        .setResponseTime((short) 1519).createScreeningToReferral();
    Boolean referredToResourceType =
        new R00818SetReferredResourceType(screeningToReferral).isValid();
    Referral referral = Referral.createWithDefaults(null, (short) 0, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc, familyAwarenessIndicator,
        govtEntityType, "name", "", "", referredToResourceType ? NOT_REFERRED : 0,
        (short) 0, "", firstResponseDeterminedByStaffPersonId,
        "", "", (short) 0, "", responseRationaleText, responsibleAgencyCode,
        limitedAccessCode, "", null, (short) 0);

    assertThat(referral.getReferralResponseType(), is(equalTo(NOT_REFERRED)));

  }
}
