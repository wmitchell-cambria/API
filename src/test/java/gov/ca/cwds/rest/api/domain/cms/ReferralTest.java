package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ReferralTest {

   @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private Referral validReferral = validReferral();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");
  private String id = "1234567ABC";
  private Boolean additionalInfoIncludedCode = Boolean.FALSE;
  private Boolean anonymousReporterIndicator = Boolean.TRUE;
  private Boolean applicationForPetitionIndicator = Boolean.FALSE;
  private String approvalNumber = "c";
  private Short approvalStatusType = 1;
  private Boolean caretakersPerpetratorCode = Boolean.FALSE;
  private String closureDate = "1991-06-06";
  private Short communicationMethodType = 2;
  private String currentLocationOfChildren = "current Location Of Children";
  private String drmsAllegationDescriptionDoc = "f";
  private String drmsErReferralDoc = "g";
  private String drmsInvestigationDoc = "h";
  private Boolean filedSuspectedChildAbuseReporttoLawEnforcementIndicator = Boolean.TRUE;
  private Boolean familyAwarenessIndicator = Boolean.FALSE;
  private Short govtEntityType = 3;
  private String legalDefinitionCode = "i";
  private Boolean legalRightsNoticeIndicator = Boolean.FALSE;
  private String limitedAccessCode = "j";
  private String mandatedCrossReportReceivedDate = "2001-01-01";
  private String referralName = "k";
  private String openAdequateCaseCode = "l";
  private String receivedDate = "2010-06-30";
  private String receivedTime = "16:41:49";
  private Short referralResponseType = 4;
  private Short referredToResourceType = 5;
  private String responseDeterminationDate = "1985-09-04";
  private String responseDeterminationTime = "16:41:49";
  private String responseRationaleText = "1234567ABC";
  private String screenerNoteText = "n";
  private String specificsIncludedCode = "o";
  private String sufficientInformationCode = "p";
  private String unfoundedSeriesCode = "q";
  private String linkToPrimaryReferralId = "r";
  private String allegesAbuseOccurredAtAddressId = "s";
  private String firstResponseDeterminedByStaffPersonId = "t";
  private String primaryContactStaffPersonId = "u";
  private String countySpecificCode = "v";
  private Boolean specialProjectReferralIndicator = Boolean.TRUE;
  private Boolean zippyCreatedIndicator = Boolean.FALSE;
  private Boolean homelessIndicator = Boolean.TRUE;
  private Boolean familyRefusedServicesIndicator = Boolean.FALSE;
  private String firstEvaluatedOutApprovalDate = "1995-07-31";
  private String responsibleAgencyCode = "w";
  private Short limitedAccessGovtAgencyType = 6;
  private String limitedAccessDate = "2001-01-01";
  private String limitedAccessDesc = "x";
  private String originalClosureDate = "2017-02-09";
  private boolean filedCrossReport = true;

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();
    
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Referral.class));
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    Referral domain = new Referral(additionalInfoIncludedCode, anonymousReporterIndicator,
        applicationForPetitionIndicator, approvalNumber, approvalStatusType,
        caretakersPerpetratorCode, closureDate, communicationMethodType, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator, familyAwarenessIndicator,
        govtEntityType, legalDefinitionCode, legalRightsNoticeIndicator, limitedAccessCode,
        mandatedCrossReportReceivedDate, referralName, openAdequateCaseCode, receivedDate,
        receivedTime, referralResponseType, referredToResourceType, responseDeterminationDate,
        responseDeterminationTime, responseRationaleText, screenerNoteText, specificsIncludedCode,
        sufficientInformationCode, unfoundedSeriesCode, linkToPrimaryReferralId,
        allegesAbuseOccurredAtAddressId, firstResponseDeterminedByStaffPersonId,
        primaryContactStaffPersonId, countySpecificCode, specialProjectReferralIndicator,
        zippyCreatedIndicator, homelessIndicator, familyRefusedServicesIndicator,
        firstEvaluatedOutApprovalDate, responsibleAgencyCode, limitedAccessGovtAgencyType,
        limitedAccessDate, limitedAccessDesc, originalClosureDate, null, null, null, null, null,
        null, null);

    gov.ca.cwds.data.persistence.cms.Referral persistent =
        new gov.ca.cwds.data.persistence.cms.Referral(id, domain, "lastUpdatedId");

    Referral totest = new Referral(persistent);
    assertThat(totest.getAdditionalInfoIncludedCode(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getAdditionalInfoIncludedCode()))));
    assertThat(totest.getAnonymousReporterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getAnonymousReporterIndicator()))));
    assertThat(totest.getApplicationForPetitionIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getApplicationForPetitionIndicator()))));
    assertThat(totest.getApprovalNumber(), is(equalTo(persistent.getApprovalNumber())));
    assertThat(totest.getApprovalStatusType(), is(equalTo(persistent.getApprovalStatusType())));
    assertThat(totest.getCaretakersPerpetratorCode(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getCaretakersPerpetratorCode()))));
    assertThat(totest.getClosureDate(), is(equalTo(df.format(persistent.getClosureDate()))));
    assertThat(totest.getCommunicationMethodType(),
        is(equalTo(persistent.getCommunicationMethodType())));
    assertThat(totest.getCurrentLocationOfChildren(),
        is(equalTo(persistent.getCurrentLocationOfChildren())));
    assertThat(totest.getDrmsAllegationDescriptionDoc(),
        is(equalTo(persistent.getDrmsAllegationDescriptionDoc())));
    assertThat(totest.getDrmsErReferralDoc(), is(equalTo(persistent.getDrmsErReferralDoc())));
    assertThat(totest.getDrmsInvestigationDoc(), is(equalTo(persistent.getDrmsInvestigationDoc())));
    assertThat(totest.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(
            persistent.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator()))));
    assertThat(totest.getFamilyAwarenessIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getFamilyAwarenessIndicator()))));
    assertThat(totest.getGovtEntityType(), is(equalTo(persistent.getGovtEntityType())));
    assertThat(totest.getLegalDefinitionCode(), is(equalTo(persistent.getLegalDefinitionCode())));
    assertThat(totest.getLegalRightsNoticeIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getLegalRightsNoticeIndicator()))));
    assertThat(totest.getLimitedAccessCode(), is(equalTo(persistent.getLimitedAccessCode())));
    assertThat(totest.getMandatedCrossReportReceivedDate(),
        is(equalTo(df.format(persistent.getMandatedCrossReportReceivedDate()))));
    assertThat(totest.getReferralName(), is(equalTo(persistent.getReferralName())));
    assertThat(totest.getOpenAdequateCaseCode(), is(equalTo(persistent.getOpenAdequateCaseCode())));
    assertThat(totest.getReceivedDate(), is(equalTo(df.format(persistent.getReceivedDate()))));
    assertThat(totest.getReceivedTime(),
        is(equalTo(timeOnlyFormat.format(persistent.getReceivedTime()))));
    assertThat(totest.getReferralResponseType(), is(equalTo(persistent.getReferralResponseType())));
    assertThat(totest.getReferredToResourceType(),
        is(equalTo(persistent.getReferredToResourceType())));
    assertThat(totest.getResponseDeterminationDate(),
        is(equalTo(df.format(persistent.getResponseDeterminationDate()))));
    assertThat(totest.getResponseDeterminationTime(),
        is(equalTo(timeOnlyFormat.format(persistent.getResponseDeterminationTime()))));
    assertThat(totest.getResponseRationaleText(),
        is(equalTo(persistent.getResponseRationaleText())));
    assertThat(totest.getScreenerNoteText(), is(equalTo(persistent.getScreenerNoteText())));
    assertThat(totest.getSpecificsIncludedCode(),
        is(equalTo(persistent.getSpecificsIncludedCode())));
    assertThat(totest.getSufficientInformationCode(),
        is(equalTo(persistent.getSufficientInformationCode())));
    assertThat(totest.getUnfoundedSeriesCode(), is(equalTo(persistent.getUnfoundedSeriesCode())));
    assertThat(totest.getLinkToPrimaryReferralId(),
        is(equalTo(persistent.getLinkToPrimaryReferralId())));
    assertThat(totest.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(persistent.getAllegesAbuseOccurredAtAddressId())));
    assertThat(totest.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(persistent.getFirstResponseDeterminedByStaffPersonId())));
    assertThat(totest.getPrimaryContactStaffPersonId(),
        is(equalTo(persistent.getPrimaryContactStaffPersonId())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getSpecialProjectReferralIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getSpecialProjectReferralIndicator()))));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getZippyCreatedIndicator()))));
    assertThat(totest.getHomelessIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getHomelessIndicator()))));
    assertThat(totest.getFamilyRefusedServicesIndicator(), is(
        equalTo(DomainChef.uncookBooleanString(persistent.getFamilyRefusedServicesIndicator()))));
    assertThat(totest.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(df.format(persistent.getFirstEvaluatedOutApprovalDate()))));
    assertThat(totest.getResponsibleAgencyCode(),
        is(equalTo(persistent.getResponsibleAgencyCode())));
    assertThat(totest.getLimitedAccessGovtAgencyType(),
        is(equalTo(persistent.getLimitedAccessGovtAgencyType())));
    assertThat(totest.getLimitedAccessDate(),
        is(equalTo(df.format(persistent.getLimitedAccessDate()))));
    assertThat(totest.getLimitedAccessDesc(), is(equalTo(persistent.getLimitedAccessDesc())));
    assertThat(totest.getOriginalClosureDate(),
        is(equalTo(df.format(persistent.getOriginalClosureDate()))));
  }

  @Test
  public void shouldCreateReferralWithEmptySetsFromPersistedReferralWhenCollectionsAreEmpty() {
    gov.ca.cwds.data.persistence.cms.Referral savedReferral = new ReferralEntityBuilder()
        .setAddresses(null).setReporter(null).setCrossReports(null).setAllegations(null).build();
    Referral referral = new Referral(savedReferral);
    assertTrue(referral.getAddress().isEmpty());
    assertThat(referral.getReporter(), is(nullValue()));
    assertTrue(referral.getCrossReport().isEmpty());
    assertTrue(referral.getAllegation().isEmpty());
  }

  @Test
  public void shouldSetValuesWhenPassedIntoConstructor() {
    boolean anonReporter = true;
    Short communicationsMethodCode = 44;
    String currentLocationOfChildren = "currentLocationOfChildren";
    String drmsAllegationDescriptionDoc = "ABC1234569";
    String drmsErReferralDoc = "1234567ABC";
    String drmsInvestigationDoc = "ABD1234567";
    String referalName = "referralName";
    String dateStarted = "may 22";
    String timeStarted = "6 o'clock";
    Short referralResponseTypeCode = 4;
    String allegesAbuseOccurredAtAddressId = "ABC1234567";
    String firstResponseDeterminedByStaffPersonId = "0X5";
    String longTextId = "LongText";
    String countyCode = "sacramento";
    Short approvalCode = 4;
    String staffId = "098";
    String limitedAccessCode = "N";
    Short limitedAccessGovtAgencyType = 123;
    String limitedAccessDate = "2019-10-20";
    String limitedAccessDesc = "Some description";
    boolean filedCrossReport = true;


    Referral referral = Referral.createWithDefaults(anonReporter, communicationsMethodCode,
        currentLocationOfChildren, drmsAllegationDescriptionDoc, drmsErReferralDoc,
        drmsInvestigationDoc, familyAwarenessIndicator, govtEntityType, referalName,
        dateStarted, timeStarted, referralResponseTypeCode, referredToResourceType, allegesAbuseOccurredAtAddressId,
        firstResponseDeterminedByStaffPersonId, longTextId, countyCode,
        approvalCode, staffId, longTextId, responsibleAgencyCode, limitedAccessCode, limitedAccessDesc,
        limitedAccessDate, limitedAccessGovtAgencyType);
    assertEquals("Expected anonReporter field to have presetValues", anonReporter,
        referral.getAnonymousReporterIndicator());
    assertEquals("Expected communicationsMethodCode field to have presetValues",
        communicationsMethodCode, referral.getCommunicationMethodType());
    assertEquals("Expected currentLocationOfChildren field to have presetValues",
        currentLocationOfChildren, referral.getCurrentLocationOfChildren());
    assertEquals("Expected drmsAllegationDescriptionDoc field to have presetValues",
        drmsAllegationDescriptionDoc, referral.getDrmsAllegationDescriptionDoc());
    assertEquals("Expected drmsErReferralDoc field to have presetValues", drmsErReferralDoc,
        referral.getDrmsErReferralDoc());
    assertEquals("Expected drmsInvestigationDoc field to have presetValues", drmsInvestigationDoc,
        referral.getDrmsInvestigationDoc());
    assertEquals("Expected referalName field to have presetValues", referalName,
        referral.getReferralName());
    assertEquals("Expected dateStarted field to have presetValues", dateStarted,
        referral.getReceivedDate());
    assertEquals("Expected timeStarted field to have presetValues", timeStarted,
        referral.getReceivedTime());
    assertEquals("Expected referralResponseTypeCode field to have presetValues",
        referralResponseTypeCode, referral.getReferralResponseType());
    assertEquals("Expected allegesAbuseOccurredAtAddressId field to have presetValues",
        allegesAbuseOccurredAtAddressId, referral.getAllegesAbuseOccurredAtAddressId());
    assertEquals("Expected firstResponseDeterminedByStaffPersonId field to have presetValues",
        firstResponseDeterminedByStaffPersonId,
        referral.getFirstResponseDeterminedByStaffPersonId());
    assertEquals("Expected longTextId field to have presetValues", longTextId,
        referral.getScreenerNoteText());
    assertEquals("Expected longTextId field to have presetValues", longTextId,
        referral.getResponseRationaleText());
    assertEquals("Expected countyCode field to have presetValues", countyCode,
        referral.getCountySpecificCode());
    assertEquals("Expected approvalCode field to have presetValues", approvalCode,
        referral.getApprovalStatusType());
    assertEquals("Expected staffId field to have presetValues", staffId,
        referral.getPrimaryContactStaffPersonId());
    assertEquals("Expected limitedAccessCode field to have presetValues", limitedAccessCode,
        referral.getLimitedAccessCode());
    assertEquals("Expected limitedAccessGovtAgencyType field to have presetValues",
        limitedAccessGovtAgencyType, referral.getLimitedAccessGovtAgencyType());
    assertEquals("Expected limitedAccessDate field to have presetValues", limitedAccessDate,
        referral.getLimitedAccessDate());
    assertEquals("Expected limitedAccessDesc field to have presetValues", limitedAccessDesc,
        referral.getLimitedAccessDesc());
  }

  @Test
  public void shouldUseDefaultValuesForFieldsNotPassedToConstructor() {
    boolean anonReporter = true;
    short communicationsMethodCode = 44;
    String currentLocationOfChildren = "currentLocationOfChildren";
    String drmsAllegationDescriptionDoc = "ABC1234569";
    String drmsErReferralDoc = "1234567ABC";
    String drmsInvestigationDoc = "ABD1234567";
    String referalName = "referralName";
    String dateStarted = "may 22";
    String timeStarted = "6 o'clock";
    short referralResponseTypeCode = 4;
    String firstResponseDeterminedByStaffPersonId = "0X5";
    String allegesAbuseOccurredAtAddressId = "ABC1234567";
    String longTextId = "LongText";
    String countyCode = "sacramento";
    short approvalCode = 4;
    String staffId = "098";

    Boolean additionalInfoIncludedCode = false;
    Boolean applicationForPetitionIndicator = false;
    String approvalNumber = "";
    Boolean caretakersPerpetratorCode = false;
    String closureDate = "";
    Boolean familyAwarenessIndicator = false;
    Short govtEntityType = 0;
    String legalDefinitionCode = "N";
    Boolean legalRightsNoticeIndicator = false;
    String madatedCrossReportReceivedDate = "";
    String openAdequateCaseCode = "";
    Short referredToResourceType = 0;
    String responseDeterminationDate = "";
    String responseDeterminationTime = "";
    String responseRationaleText = "1234567ABC";
    String specificsIncludedCode = "N";
    String sufficientInformationCode = "N";
    String unfoundedSeriesCode = "N";
    String linkToPrimaryReferralId = "";
    Boolean specialProjectReferralIndicator = false;
    Boolean zippyCreatedIndicator = true;
    Boolean homelessIndicator = false;
    Boolean familyRefusedServicesIndicator = false;
    String firstEvaluatedOutApprovalDate = "";
    String responsibleAgencyCode = "C";
    String originalClosureDate = "";
    Set<gov.ca.cwds.rest.api.domain.cms.Address> address = null;
    Set<Reporter> reporter = null;
    Set<CrossReport> crossReport = null;
    Set<Allegation> allegation = null;
    Set<Client> victimClient = null;
    Set<Client> perpetratorClient = null;

    Referral referral = Referral.createWithDefaults(anonReporter, communicationsMethodCode,
        currentLocationOfChildren, drmsAllegationDescriptionDoc, drmsErReferralDoc,
        drmsInvestigationDoc, familyAwarenessIndicator, govtEntityType, referalName,
        dateStarted, timeStarted, referralResponseTypeCode, referredToResourceType, allegesAbuseOccurredAtAddressId,
        firstResponseDeterminedByStaffPersonId, longTextId, countyCode,
        approvalCode, staffId, responseRationaleText, responsibleAgencyCode, "N", "", null,
        (short) 0);
    assertEquals("Expected additionalInfoIncludedCode field to have presetValues",
        additionalInfoIncludedCode, referral.getAdditionalInfoIncludedCode());
    assertEquals("Expected applicationForPetitionIndicator field to have presetValues",
        applicationForPetitionIndicator, referral.getApplicationForPetitionIndicator());
    assertEquals("Expected approvalNumber field to have presetValues", approvalNumber,
        referral.getApprovalNumber());
    assertEquals("Expected caretakersPerpetratorCode field to have presetValues",
        caretakersPerpetratorCode, referral.getCaretakersPerpetratorCode());
    assertEquals("Expected closureDate field to have presetValues", closureDate,
        referral.getClosureDate());
    assertEquals("Expected currentLocationOfChildren field to have presetValues",
        currentLocationOfChildren, referral.getCurrentLocationOfChildren());
    assertEquals("Expected drmsAllegationDescriptionDoc field to have presetValues",
        drmsAllegationDescriptionDoc, referral.getDrmsAllegationDescriptionDoc());
    assertEquals("Expected drmsErReferralDoc field to have presetValues", drmsErReferralDoc,
        referral.getDrmsErReferralDoc());
    assertEquals("Expected drmsInvestigationDoc field to have presetValues", drmsInvestigationDoc,
        referral.getDrmsInvestigationDoc());
    assertEquals("Expected familyAwarenessIndicator field to have presetValues",
        familyAwarenessIndicator, referral.getFamilyAwarenessIndicator());
    assertEquals("Expected govtEntityType field to have presetValues", govtEntityType,
        referral.getGovtEntityType());
    assertEquals("Expected legalDefinitionCode field to have presetValues", legalDefinitionCode,
        referral.getLegalDefinitionCode());
    assertEquals("Expected legalRightsNoticeIndicator field to have presetValues",
        legalRightsNoticeIndicator, referral.getLegalRightsNoticeIndicator());
    assertEquals("Expected mandatedCrossReportReceivedDate field to have presetValues",
        madatedCrossReportReceivedDate, referral.getMandatedCrossReportReceivedDate());
    assertEquals("Expected openAdequateCaseCode field to have presetValues", openAdequateCaseCode,
        referral.getOpenAdequateCaseCode());
    assertEquals("Expected referredToResourceType field to have presetValues",
        referredToResourceType, referral.getReferredToResourceType());
    assertEquals("Expected responseDeterminationDate field to have presetValues",
        responseDeterminationDate, referral.getResponseDeterminationDate());
    assertEquals("Expected responseDeterminationTime field to have presetValues",
        responseDeterminationTime, referral.getResponseDeterminationTime());
    assertEquals("Expected responseRationaleText field to have presetValues", responseRationaleText,
        referral.getResponseRationaleText());
    assertEquals("Expected specificsIncludedCode field to have presetValues", specificsIncludedCode,
        referral.getSpecificsIncludedCode());
    assertEquals("Expected sufficientInformationCode field to have presetValues",
        sufficientInformationCode, referral.getSufficientInformationCode());
    assertEquals("Expected unfoundedSeriesCode field to have presetValues", unfoundedSeriesCode,
        referral.getUnfoundedSeriesCode());
    assertEquals("Expected linkToPrimaryReferralId field to have presetValues",
        linkToPrimaryReferralId, referral.getLinkToPrimaryReferralId());
    assertEquals("Expected allegesAbuseOccurredAtAddressId field to have presetValues",
        allegesAbuseOccurredAtAddressId, referral.getAllegesAbuseOccurredAtAddressId());
    assertEquals("Expected firstResponseDeterminedByStaffPersonId field to have presetValues",
        firstResponseDeterminedByStaffPersonId,
        referral.getFirstResponseDeterminedByStaffPersonId());
    assertEquals("Expected specialProjectReferralIndicator field to have presetValues",
        specialProjectReferralIndicator, referral.getSpecialProjectReferralIndicator());
    assertEquals("Expected zippyCreatedIndicator field to have presetValues", zippyCreatedIndicator,
        referral.getZippyCreatedIndicator());
    assertEquals("Expected homelessIndicator field to have presetValues", homelessIndicator,
        referral.getHomelessIndicator());
    assertEquals("Expected familyRefusedServicesIndicator field to have presetValues",
        familyRefusedServicesIndicator, referral.getFamilyRefusedServicesIndicator());
    assertEquals("Expected firstEvaluatedOutApprovalDate field to have presetValues",
        firstEvaluatedOutApprovalDate, referral.getFirstEvaluatedOutApprovalDate());
    assertEquals("Expected responsibleAgencyCode field to have presetValues", responsibleAgencyCode,
        referral.getResponsibleAgencyCode());
    assertEquals("Expected originalClosureDate field to have presetValues", originalClosureDate,
        referral.getOriginalClosureDate());
    assertEquals("Expected address field to have presetValues", address, referral.getAddress());
    assertEquals("Expected reporter field to have presetValues", reporter, referral.getReporter());
    assertEquals("Expected crossReport field to have presetValues", crossReport,
        referral.getCrossReport());
    assertEquals("Expected allegation field to have presetValues", allegation,
        referral.getAllegation());
    assertEquals("Expected victimClient field to have presetValues", victimClient,
        referral.getVictimClient());
    assertEquals("Expected perpetratorClient field to have presetValues", perpetratorClient,
        referral.getPerpetratorClient());
  }

  @Test
  public void shouldSetLimitedAccessCodeToNWhenNull() {
    String limitedAccessCode = null;

    Referral referral = Referral.createWithDefaults(null, (short) 0, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc, familyAwarenessIndicator,
        govtEntityType, "name", "", "", (short) 0, (short) 0, "", firstResponseDeterminedByStaffPersonId,
        "", "", (short) 0, "", responseRationaleText, responsibleAgencyCode,
        limitedAccessCode, "", null, (short) 0);

    assertThat(referral.getLimitedAccessCode(), is(equalTo("N")));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Referral domain = new Referral(additionalInfoIncludedCode, anonymousReporterIndicator,
        applicationForPetitionIndicator, approvalNumber, approvalStatusType,
        caretakersPerpetratorCode, closureDate, communicationMethodType, currentLocationOfChildren,
        drmsAllegationDescriptionDoc, drmsErReferralDoc, drmsInvestigationDoc,
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator, familyAwarenessIndicator,
        govtEntityType, legalDefinitionCode, legalRightsNoticeIndicator, limitedAccessCode,
        mandatedCrossReportReceivedDate, referralName, openAdequateCaseCode, receivedDate,
        receivedTime, referralResponseType, referredToResourceType, responseDeterminationDate,
        responseDeterminationTime, responseRationaleText, screenerNoteText, specificsIncludedCode,
        sufficientInformationCode, unfoundedSeriesCode, linkToPrimaryReferralId,
        allegesAbuseOccurredAtAddressId, firstResponseDeterminedByStaffPersonId,
        primaryContactStaffPersonId, countySpecificCode, specialProjectReferralIndicator,
        zippyCreatedIndicator, homelessIndicator, familyRefusedServicesIndicator,
        firstEvaluatedOutApprovalDate, responsibleAgencyCode, limitedAccessGovtAgencyType,
        limitedAccessDate, limitedAccessDesc, originalClosureDate, null, null, null, null, null,
        null, null);

    assertThat(domain.getAdditionalInfoIncludedCode(), is(equalTo(additionalInfoIncludedCode)));
    assertThat(domain.getAnonymousReporterIndicator(), is(equalTo(anonymousReporterIndicator)));
    assertThat(domain.getApplicationForPetitionIndicator(),
        is(equalTo(applicationForPetitionIndicator)));
    assertThat(domain.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(domain.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(domain.getCaretakersPerpetratorCode(), is(equalTo(caretakersPerpetratorCode)));
    assertThat(domain.getClosureDate(), is(equalTo(closureDate)));
    assertThat(domain.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(domain.getCurrentLocationOfChildren(), is(equalTo(currentLocationOfChildren)));
    assertThat(domain.getDrmsAllegationDescriptionDoc(), is(equalTo(drmsAllegationDescriptionDoc)));
    assertThat(domain.getDrmsErReferralDoc(), is(equalTo(drmsErReferralDoc)));
    assertThat(domain.getDrmsInvestigationDoc(), is(equalTo(drmsInvestigationDoc)));
    assertThat(domain.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(),
        is(equalTo(filedSuspectedChildAbuseReporttoLawEnforcementIndicator)));
    assertThat(domain.getFamilyAwarenessIndicator(), is(equalTo(familyAwarenessIndicator)));
    assertThat(domain.getGovtEntityType(), is(equalTo(govtEntityType)));
    assertThat(domain.getLegalDefinitionCode(), is(equalTo(legalDefinitionCode)));
    assertThat(domain.getLegalRightsNoticeIndicator(), is(equalTo(legalRightsNoticeIndicator)));
    assertThat(domain.getLimitedAccessCode(), is(equalTo(limitedAccessCode)));
    assertThat(domain.getMandatedCrossReportReceivedDate(),
        is(equalTo(mandatedCrossReportReceivedDate)));
    assertThat(domain.getReferralName(), is(equalTo(referralName)));
    assertThat(domain.getOpenAdequateCaseCode(), is(equalTo(openAdequateCaseCode)));
    assertThat(domain.getReceivedDate(), is(equalTo(receivedDate)));
    assertThat(domain.getReceivedTime(), is(equalTo(receivedTime)));
    assertThat(domain.getReferralResponseType(), is(equalTo(referralResponseType)));
    assertThat(domain.getReferredToResourceType(), is(equalTo(referredToResourceType)));
    assertThat(domain.getResponseDeterminationDate(), is(equalTo(responseDeterminationDate)));
    assertThat(domain.getResponseDeterminationTime(), is(equalTo(responseDeterminationTime)));
    assertThat(domain.getResponseRationaleText(), is(equalTo(responseRationaleText)));
    assertThat(domain.getScreenerNoteText(), is(equalTo(screenerNoteText)));
    assertThat(domain.getSpecificsIncludedCode(), is(equalTo(specificsIncludedCode)));
    assertThat(domain.getSufficientInformationCode(), is(equalTo(sufficientInformationCode)));
    assertThat(domain.getUnfoundedSeriesCode(), is(equalTo(unfoundedSeriesCode)));
    assertThat(domain.getLinkToPrimaryReferralId(), is(equalTo(linkToPrimaryReferralId)));
    assertThat(domain.getAllegesAbuseOccurredAtAddressId(),
        is(equalTo(allegesAbuseOccurredAtAddressId)));
    assertThat(domain.getFirstResponseDeterminedByStaffPersonId(),
        is(equalTo(firstResponseDeterminedByStaffPersonId)));
    assertThat(domain.getPrimaryContactStaffPersonId(), is(equalTo(primaryContactStaffPersonId)));
    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getSpecialProjectReferralIndicator(),
        is(equalTo(specialProjectReferralIndicator)));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(zippyCreatedIndicator)));
    assertThat(domain.getHomelessIndicator(), is(equalTo(homelessIndicator)));
    assertThat(domain.getFamilyRefusedServicesIndicator(),
        is(equalTo(familyRefusedServicesIndicator)));
    assertThat(domain.getFirstEvaluatedOutApprovalDate(),
        is(equalTo(firstEvaluatedOutApprovalDate)));
    assertThat(domain.getResponsibleAgencyCode(), is(equalTo(responsibleAgencyCode)));
    assertThat(domain.getLimitedAccessGovtAgencyType(), is(equalTo(limitedAccessGovtAgencyType)));
    assertThat(domain.getLimitedAccessDate(), is(equalTo(limitedAccessDate)));
    assertThat(domain.getLimitedAccessDesc(), is(equalTo(limitedAccessDesc)));
    assertThat(domain.getOriginalClosureDate(), is(equalTo(originalClosureDate)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Referral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/SerializeJsonValid.json"), Referral.class));

    assertThat(MAPPER.writeValueAsString(validReferral()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Referral/valid/deserializeJsonValid.json"),
            Referral.class),
        is(equalTo(validReferral())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    Referral referral = new ReferralResourceBuilder().build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
   }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    Referral referral = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Referral/valid/optionalsNotIncluded.json"), Referral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * additionalInfoIncludedCode Tests
   */
  @Test
  public void failsWhenAdditionalInfoIncludedCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setAdditionalInfoIncludedCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("additionalInfoIncludedCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * anonymousReporterIndicator Tests
   */
  @Test
  public void failsWhenAnonymousReporterIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setAnonymousReporterIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("anonymousReporterIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * applicationForPetitionIndicator Tests
   */
  @Test
  public void failsWhenApplicationForPetitionIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setApplicationForPetitionIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("applicationForPetitionIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * approvalNumber Tests
   */

  @Test
  public void failsWhenApprovalNumberTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setApprovalNumber("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("approvalNumber size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * approvalStatusType Tests
   */
  @Test
  public void failsWhenApprovalStatusTypeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setApprovalStatusType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("approvalStatusType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * caretakersPerpetratorCode Tests
   */
  @Test
  public void failsWhenCaretakersPerpetratorCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setCaretakersPerpetratorCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("caretakersPerpetratorCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * closureDate Tests
   */
  @Test
  public void successWhenClosureDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setClosureDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenClosureDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setClosureDate("01-10-2017").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("closureDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * communicationMethodType Tests
   */
  @Test
  public void failsWhenCommunicationMethodTypeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setCommunicationMethodType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("communicationMethodType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * currentLocationOfChildren Tests
   */
  @Test
  public void successWhenCurrentLocationOfChildrenTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setCurrentLocationOfChildren("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("currentLocationOfChildren size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * drmsAllegationDescriptionDoc Tests
   */
  @Test
  public void failsWhenDrmsAllegationDescriptionDocTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setDrmsAllegationDescriptionDoc("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsAllegationDescriptionDoc size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenDrmsErReferralDocTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setDrmsErReferralDoc("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsErReferralDoc size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * drmsInvestigationDoc Tests
   */
  @Test
  public void failsWhenDrmsInvestigationDocTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setDrmsInvestigationDoc("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsInvestigationDoc size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * filedSuspectedChildAbuseReporttoLawEnforcementIndicator Tests
   */
  @Test
  public void failsWhenFiledSuspectedChildAbuseReporttoLawEnforcementIndicatorNull()
      throws Exception {
    Referral referral = new ReferralResourceBuilder().setFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("filedSuspectedChildAbuseReporttoLawEnforcementIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * familyAwarenessIndicator Tests
   */
  @Test
  public void failsWhenFamilyAwarenessIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setFamilyAwarenessIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("familyAwarenessIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * govtEntityType Tests
   */
  @Test
  public void failsWhenGovtEntityTypeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setGovtEntityType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("govtEntityType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * legalDefinitionCode Tests
   */
  @Test
  public void failsWhenLegalDefinitionCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLegalDefinitionCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("legalDefinitionCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLegalDefinitionCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLegalDefinitionCode("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("legalDefinitionCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }
  
  /*
   * legalRightsNoticeIndicator Tests
   */
  @Test
  public void failsWhenLegalRightsNoticeIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLegalRightsNoticeIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("legalRightsNoticeIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

   @Test
  public void failsWhenLimitedAccessCodeTooLong() throws Exception {
     Referral referral = new ReferralResourceBuilder().setLimitedAccessCode("12").build();
     validator = Validation.buildDefaultValidatorFactory().getValidator();
     messageBuilder.addDomainValidationError(validator.validate(referral));
     Boolean theErrorDetected = false;

     List<ErrorMessage> validationErrors = messageBuilder.getMessages();
     for (ErrorMessage message : validationErrors) {
//       System.out.println(message.getMessage());
       if (message.getMessage().equals("limitedAccessCode size must be 1")) {
         theErrorDetected = true;
       }
     }
     assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLimitedAccessCodeNotValidValue() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLimitedAccessCode("Z").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("limitedAccessCode must be one of [S, R, N]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * mandatedCrossReportReceivedDate Tests
   */
  @Test
  public void successWhenMandatedCrossReportReceivedDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setMandatedCrossReportReceivedDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenMandatedCrossReportReceivedDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setMandatedCrossReportReceivedDate("01-10-2017").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("mandatedCrossReportReceivedDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * referralName Tests
   */
  @Test
  public void failsWhenReferralNameNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReferralName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReferralNameTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReferralName("1234567890123456789012345678901234567890").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralName size must be between 0 and 35")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * openAdequateCaseCode Tests
   */
  @Test
  public void failsWhenOpenAdequateCaseCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setOpenAdequateCaseCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("openAdequateCaseCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenOpenAdequateCaseCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setOpenAdequateCaseCode("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("openAdequateCaseCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * receivedDate Tests
   */
  @Test
  public void failsWhenReceivedDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReceivedDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("receivedDate may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReceivedDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReceivedDate("01-10-2017").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("receivedDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * receivedTime Tests
   */
  @Test
  public void failsWhenReceivedTimeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReceivedTime(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("receivedTime may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReceivedTimeWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReceivedTime("25:61:61").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("receivedTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * referralResponseType Tests
   */
  @Test
  public void failsWhenReferralResponseTypeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReferralResponseType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralResponseType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  // Rule R-00807
  @Test
  public void TestForRule00807WhenReferrralResponseTypeIsEvaluateOut() throws Exception {
    Referral referral = new ReferralResourceBuilder().setApplicationForPetitionIndicator(true)
        .setReferralResponseType((short) 1519).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralResponseType is not valid since applicationForPetitionIndicator is set to true")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * referredToResourceType Tests
   */
  @Test
  public void failsWhenReferredToResourceTypeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setReferredToResourceType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referredToResourceType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * responseDeterminationDate Tests
   */
  @Test
  public void successWhenResponseDeterminationDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponseDeterminationDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenResponseDeterminationDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponseDeterminationDate("01-01-2017").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("responseDeterminationDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * responseDeterminationTime Tests
   */
  @Test
  public void successWhenResponseDeterminationTimeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponseDeterminationTime(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenResponseDeterminationTimeWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponseDeterminationTime("23-33:24").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("responseDeterminationTime must be in the format of HH:mm:ss")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * responseRationaleText Tests
   */
  @Test
  public void successWhenResponseRationaleTextNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponseRationaleText(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenResponseRationaleTextTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponseRationaleText("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("responseRationaleText size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * screenerNoteText Tests
   */
  @Test
  public void successWhenScreenerNoteTextNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setScreenerNoteText(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenScreenerNoteTextTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setScreenerNoteText("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("screenerNoteText size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * specificsIncludedCode Tests
   */
  @Test
  public void failsWhenSpecificsIncludedCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setSpecificsIncludedCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("specificsIncludedCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }
  @Test
  public void failsWhenSpecificsIncludedCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setSpecificsIncludedCode("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("specificsIncludedCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * sufficientInformationCode Tests
   */
  @Test
  public void failsWhenSufficientInformationCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setSufficientInformationCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sufficientInformationCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenSufficientInformationCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setSufficientInformationCode("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sufficientInformationCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
 }

  /*
   * unfoundedSeriesCode Tests
   */
  @Test
  public void failsWhenUnfoundedSeriesCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setUnfoundedSeriesCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("unfoundedSeriesCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenUnfoundedSeriesCodeEmpty() throws Exception {
    Referral referral = new ReferralResourceBuilder().setUnfoundedSeriesCode("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("unfoundedSeriesCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenUnfoundedSeriesCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setUnfoundedSeriesCode("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("unfoundedSeriesCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * linkToPrimaryReferralId Tests
   */
  @Test
  public void successWhenLinkToPrimaryReferralIdNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLinkToPrimaryReferralId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenLinkToPrimaryReferralIdTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLinkToPrimaryReferralId("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("linkToPrimaryReferralId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * allegesAbuseOccurredAtAddressId Tests
   */
  @Test
  public void successWhenAllegesAbuseOccurredAtAddressIdNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setAllegesAbuseOccurredAtAddressId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenAllegesAbuseOccurredAtAddressIdTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setAllegesAbuseOccurredAtAddressId("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("allegesAbuseOccurredAtAddressId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * firstResponseDeterminedByStaffPersonId Tests
   */
  @Test
  public void successWhenFirstResponseDeterminedByStaffPersonIdNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setFirstResponseDeterminedByStaffPersonId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
     }

  @Test
  public void failsWhenFirstResponseDeterminedByStaffPersonIdTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setFirstResponseDeterminedByStaffPersonId("12345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("firstResponseDeterminedByStaffPersonId size must be between 0 and 3")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * primaryContactStaffPersonId Tests
   */
  @Test
  public void failsWhenPrimaryContactStaffPersonIdNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setPrimaryContactStaffPersonId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryContactStaffPersonId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenPrimaryContactStaffPersonIdTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setPrimaryContactStaffPersonId("1234").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryContactStaffPersonId size must be between 3 and 3")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setCountySpecificCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setCountySpecificCode("123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode size must be between 1 and 2")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * specialProjectReferralIndicator Tests
   */
  @Test
  public void failsWhenSpecialProjectReferralIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setSpecialProjectReferralIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("specialProjectReferralIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * zippyCreatedIndicator Tests
   */
  @Test
  public void failsWhenZippyCreatedIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setZippyCreatedIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("zippyCreatedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * homelessIndicator Tests
   */
  @Test
  public void failsWhenHomelessIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setHomelessIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("homelessIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * familyRefusedServicesIndicator Tests
   */
  @Test
  public void failsWhenFamilyRefusedServicesIndicatorNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setFamilyRefusedServicesIndicator(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("familyRefusedServicesIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * firstEvaluatedOutApprovalDate Tests
   */
  @Test
  public void successWhenFirstEvaluatedOutApprovalDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setFirstEvaluatedOutApprovalDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenFirstEvaluatedOutApprovalDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setFirstEvaluatedOutApprovalDate("01-10-2017").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("firstEvaluatedOutApprovalDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * responsibleAgencyCode Tests
   */
  @Test
  public void failsWhenResponsibleAgencyCodeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponsibleAgencyCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("responsibleAgencyCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenResponsibleAgencyCodeTooLong() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponsibleAgencyCode("12").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("responsibleAgencyCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenResponsibleAgencyCodeNotValidValue() throws Exception {
    Referral referral = new ReferralResourceBuilder().setResponsibleAgencyCode("X").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("responsibleAgencyCode must be one of [C, P, O, A, S, I, K, M]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * limitedAccessGovtAgencyType Tests
   */
  @Test
  public void successWhenLimitedAccessGovtAgencyTypeNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLimitedAccessGovtAgencyType(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * limitedAccessDate Tests
   */
  @Test
  public void successWhenLimitedAccessDateEmpty() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLimitedAccessDate("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenLimitedAccessDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLimitedAccessDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
   }

  @Test
  public void failsWhenLimitedAccessDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLimitedAccessDate("01-10-2018").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("limitedAccessDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * limitedAccessDesc Tests
   */
  @Test
  public void successWhenLimitedAccessDescNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setLimitedAccessDesc(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * originalClosureDate Tests
   */
  @Test
  public void successWhenOriginalClosureDateNull() throws Exception {
    Referral referral = new ReferralResourceBuilder().setOriginalClosureDate(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenOriginalClosureDateWrongFormat() throws Exception {
    Referral referral = new ReferralResourceBuilder().setOriginalClosureDate("01-10-2018").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("originalClosureDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /**
   * Rule - 06998
   */
  @Test
  public void testZippyReferralCreation() {

    boolean anonReporter = true;
    Short communicationsMethodCode = 44;
    String currentLocationOfChildren = "currentLocationOfChildren";
    String drmsAllegationDescriptionDoc = "ABC1234569";
    String drmsErReferralDoc = "1234567ABC";
    String drmsInvestigationDoc = "ABD1234567";
    String referalName = "zippy referral";
    String dateStarted = "may 22";
    String timeStarted = "6 o'clock";
    Short referralResponseTypeCode = 4;
    String allegesAbuseOccurredAtAddressId = "ABC1234567";
    String firstResponseDeterminedByStaffPersonId = "0X5";
    String longTextId = "LongText";
    String countyCode = "sacramento";
    Short approvalCode = 4;
    String staffId = "098";
    String limitedAccessCode = "N";
    Short limitedAccessGovtAgencyType = 123;
    String limitedAccessDate = "2019-10-20";
    String limitedAccessDesc = "Some description";


    Referral referral = Referral.createWithDefaults(anonReporter, communicationsMethodCode,
        currentLocationOfChildren, drmsAllegationDescriptionDoc, drmsErReferralDoc,
        drmsInvestigationDoc, familyAwarenessIndicator, govtEntityType, referalName,
        dateStarted, timeStarted, referralResponseTypeCode, referredToResourceType, allegesAbuseOccurredAtAddressId,
        firstResponseDeterminedByStaffPersonId, longTextId, countyCode,
        approvalCode, staffId, longTextId, responsibleAgencyCode, limitedAccessCode, limitedAccessDesc,
        limitedAccessDate, limitedAccessGovtAgencyType);
    assertEquals("Expected zippyCreatedIndicator field to be initialized as  True", Boolean.TRUE,
        referral.getZippyCreatedIndicator());
  }

  /**
   * DocTool rule R - 02535
   */
  @Test
  public void testFiledCrossReportWithLawEnforcementIndicatorIsFalse() {
	   boolean anonReporter = true;
	    Short communicationsMethodCode = 44;
	    String currentLocationOfChildren = "currentLocationOfChildren";
	    String drmsAllegationDescriptionDoc = "ABC1234569";
	    String drmsErReferralDoc = "1234567ABC";
	    String drmsInvestigationDoc = "ABD1234567";
	    String referalName = "zippy referral";
	    String dateStarted = "may 22";
	    String timeStarted = "6 o'clock";
	    Short referralResponseTypeCode = 4;
	    String allegesAbuseOccurredAtAddressId = "ABC1234567";
	    String firstResponseDeterminedByStaffPersonId = "0X5";
	    String longTextId = "LongText";
	    String countyCode = "sacramento";
	    Short approvalCode = 4;
	    String staffId = "098";
	    String limitedAccessCode = "N";
	    Short limitedAccessGovtAgencyType = 123;
	    String limitedAccessDate = "2019-10-20";
	    String limitedAccessDesc = "Some description";


	    Referral referral = Referral.createWithDefaults(anonReporter, communicationsMethodCode,
	        currentLocationOfChildren, drmsAllegationDescriptionDoc, drmsErReferralDoc,
	        drmsInvestigationDoc, familyAwarenessIndicator, govtEntityType, referalName,
	        dateStarted, timeStarted, referralResponseTypeCode, referredToResourceType, allegesAbuseOccurredAtAddressId,
	        firstResponseDeterminedByStaffPersonId, longTextId, countyCode,
	        approvalCode, staffId, longTextId, responsibleAgencyCode, limitedAccessCode, limitedAccessDesc,
	        limitedAccessDate, limitedAccessGovtAgencyType);
	    assertEquals("Expected filedCrossReportWithLawEnforcementIndicator field to be initialized as False", Boolean.FALSE,
	            referral.getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator());
  }
  
  /*
   * Utils
   */
  private Referral validReferral() {
    return new Referral(false, false, false, "A3CDEOm0Ab", new Short((short) 122), false,
        "2000-03-03", new Short((short) 409), "current", "1234567ABC", "A3B7sSC0Ab", "asdbdfdrsd",
        false, false, new Short((short) 1118), "A", false, "N", "2000-01-31",
        "Verification (R3)                  ", "A", "2000-01-01", "16:41:49",
        new Short((short) 1520), new Short((short) 0), "2000-01-31", "16:41:49", "1234567ABC",
        "1234567ABC", "A", "A", "A", "1234567ABC", "1234567ABC", "0Ab", "q1p", "51", false, false,
        false, false, "2000-05-05", "C", new Short((short) 1234), "2000-05-05", "thjkl",
        "2000-05-05", null, null, null, null, null, null, null);
  }
}
