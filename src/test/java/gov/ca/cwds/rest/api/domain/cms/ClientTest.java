package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.junit.template.DomainTestTemplate;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.ClientResource;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ClientTest implements DomainTestTemplate {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_LEGACY_CLIENT + "/";

  private static final ClientResource mockedClientResource = mock(ClientResource.class);

  /**
   * Initialize system code cache
   */
  @SuppressWarnings("unused")
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedClientResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime1 = new Date();

  private String existingClientId = "ABC1234567";
  private DateTime lastUpdatedTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
      .parseDateTime("2004-03-31T09:45:58.000-0800");
  private Boolean adjudicatedDelinquentIndicator = Boolean.FALSE;
  private String adoptionStatusCode = "A";
  private String alienRegistrationNumber = "";
  private String birthCity = "Coletown";
  private Short birthCountryCodeType = 563;
  private String birthDate = "2007-01-31";
  private String birthFacilityName = "Mercy";
  private Short birthStateCodeType = 1829;
  private Boolean birthplaceVerifiedIndicator = Boolean.FALSE;
  private Boolean childClientIndicatorVar = Boolean.FALSE;
  private String clientIndexNumber = "123456789012";
  private String commentDescription = "client description";
  private String commonFirstName = "fname";
  private String commonLastName = "lname";
  private String commonMiddleName = "mname";
  private String confidentialityActionDate = null;
  private Boolean confidentialityInEffectIndicator = Boolean.FALSE;
  private String creationDate = "2016-10-31";
  private Boolean currCaChildrenServIndicator = Boolean.FALSE;
  private String currentlyOtherDescription = "other description";
  private Boolean currentlyRegionalCenterIndicator = Boolean.FALSE;
  private String deathDate = null;
  private Boolean deathDateVerified = Boolean.FALSE;
  private String deathPlace = "";
  private String deathReasonText = "";
  private String driversLicenseNumber = "C87634563";
  private Short driversLicenseStateCodeType = 1829;
  private String emailAddress = "anyone@yahoo.com";
  private String estimatedDateOfBirthCode = "Y";
  private String ethUnableToDetReasonCode = "K";
  private String fatherParentalRightTermDate = "";
  private String genderCode = "M";
  private Short genderIdentityType = 7075;
  private String giNotListedDescription = "some descrption";
  private Short genderExpressionType = 7081;
  private String healthSummaryText = "good";
  private String hispUnableToDetReasonCode = "";
  private String hispanicOriginCode = "X";
  private String id = "1234567ABC";
  private Short immigrationCountryCodeType = 0;
  private Short immigrationStatusType = 1199;
  private String incapcitatedParentCode = "N";
  private Boolean individualHealthCarePlanIndicator = Boolean.FALSE;
  private Boolean limitationOnScpHealthIndicator = Boolean.FALSE;
  private String literateCode = "Y";
  private Boolean maritalCohabitatnHstryIndicatorVar = Boolean.FALSE;
  private Short maritalStatusType = 1308;
  private String militaryStatusCode = "N";
  private String motherParentalRightTermDate = null;
  private String namePrefixDescription = "";
  private Short nameType = 0;
  private Boolean outstandingWarrantIndicator = Boolean.FALSE;
  private Boolean prevCaChildrenServIndicator = Boolean.FALSE;
  private String prevOtherDescription = "the previous description";
  private Boolean prevRegionalCenterIndicator = Boolean.FALSE;
  private Short primaryEthnicityType = 839;
  private Short primaryLanguageType = 1253;
  private Short religionType = 0;
  private Short secondaryLanguageType = 0;
  private Boolean sensitiveHlthInfoOnFileIndicator = Boolean.FALSE;
  private String sensitivityIndicator = "N";
  private Short sexualOrientationType = 7066;
  private String soUnableToDetermineCode = "D";
  private String soNotListedDescrption = "Sample Descrpion";
  private String soc158PlacementCode = "N";
  private Boolean soc158SealedClientIndicator = Boolean.FALSE;
  private String socialSecurityNumChangedCode = "N";
  private String socialSecurityNumber = "111122333";
  private String suffixTitleDescription = "MR";
  private Boolean tribalAncestryClientIndicatorVar = Boolean.FALSE;
  private Boolean tribalMembrshpVerifctnIndicatorVar = Boolean.FALSE;
  private String unemployedParentCode = "N";
  private Boolean zippyCreatedIndicator = Boolean.FALSE;
  private Boolean reporterConfidentialWaiver = Boolean.FALSE;
  private String reporterEmployerName = "Employer Name";
  private Boolean clientStaffPersonAdded = Boolean.FALSE;

  @Override
  public void setup() throws Exception {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Client.class));

    Client validClient = validClient();
    when(mockedClientResource.create(eq(validClient)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  @Override
  public void teardown() throws Exception {

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {
    Client domain = new Client(existingClientId, lastUpdatedTime, adjudicatedDelinquentIndicator,
        adoptionStatusCode, alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate,
        birthFacilityName, birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar,
        clientIndexNumber, commentDescription, commonFirstName, commonMiddleName, commonLastName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, genderIdentityType,
        giNotListedDescription, genderExpressionType, healthSummaryText, hispUnableToDetReasonCode,
        hispanicOriginCode, immigrationCountryCodeType, immigrationStatusType,
        incapcitatedParentCode, individualHealthCarePlanIndicator, limitationOnScpHealthIndicator,
        literateCode, maritalCohabitatnHstryIndicatorVar, maritalStatusType, militaryStatusCode,
        motherParentalRightTermDate, namePrefixDescription, nameType, outstandingWarrantIndicator,
        prevCaChildrenServIndicator, prevOtherDescription, prevRegionalCenterIndicator,
        primaryEthnicityType, primaryLanguageType, religionType, secondaryLanguageType,
        sensitiveHlthInfoOnFileIndicator, sensitivityIndicator, sexualOrientationType,
        soUnableToDetermineCode, soNotListedDescrption, soc158PlacementCode,
        soc158SealedClientIndicator, socialSecurityNumChangedCode, socialSecurityNumber,
        suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator, null);
    gov.ca.cwds.data.persistence.cms.Client persistent =
        new gov.ca.cwds.data.persistence.cms.Client(id, domain, lastUpdatedId, lastUpdatedTime1);

    assertThat(persistent.getAdjudicatedDelinquentIndicator(),
        is(equalTo(DomainChef.cookBoolean(adjudicatedDelinquentIndicator))));
    assertThat(persistent.getAdoptionStatusCode(), is(equalTo(adoptionStatusCode)));
    assertThat(persistent.getAlienRegistrationNumber(), is(equalTo(alienRegistrationNumber)));
    assertThat(persistent.getBirthCity(), is(equalTo(birthCity)));
    assertThat(persistent.getBirthCountryCodeType(), is(equalTo(birthCountryCodeType)));
    assertThat(persistent.getBirthDate(), is(equalTo(DomainChef.uncookDateString(birthDate))));
    assertThat(persistent.getBirthFacilityName(), is(equalTo(birthFacilityName)));
    assertThat(persistent.getBirthStateCodeType(), is(equalTo(birthStateCodeType)));
    assertThat(persistent.getBirthplaceVerifiedIndicator(),
        is(equalTo(DomainChef.cookBoolean(birthplaceVerifiedIndicator))));
    assertThat(persistent.getChildClientIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(childClientIndicatorVar))));
    assertThat(persistent.getClientIndexNumber(), is(equalTo(clientIndexNumber)));
    assertThat(persistent.getCommentDescription(), is(equalTo(commentDescription)));
    assertThat(persistent.getCommonFirstName(), is(equalTo(commonFirstName)));
    assertThat(persistent.getCommonLastName(), is(equalTo(commonLastName)));
    assertThat(persistent.getCommonMiddleName(), is(equalTo(commonMiddleName)));
    assertThat(persistent.getConfidentialityActionDate(), is(equalTo(confidentialityActionDate)));
    assertThat(persistent.getConfidentialityInEffectIndicator(),
        is(equalTo(DomainChef.cookBoolean(confidentialityInEffectIndicator))));
    assertThat(persistent.getCreationDate(),
        is(equalTo(DomainChef.uncookDateString(creationDate))));
    assertThat(persistent.getCurrCaChildrenServIndicator(),
        is(equalTo(DomainChef.cookBoolean(currCaChildrenServIndicator))));
    assertThat(persistent.getCurrentlyOtherDescription(), is(equalTo(currentlyOtherDescription)));
    assertThat(persistent.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(DomainChef.cookBoolean(currentlyRegionalCenterIndicator))));
    assertThat(persistent.getDeathDate(), is(equalTo(DomainChef.uncookDateString(deathDate))));
    assertThat(persistent.getDeathDateVerifiedIndicator(),
        is(equalTo(DomainChef.cookBoolean(deathDateVerified))));
    assertThat(persistent.getDeathReasonText(), is(equalTo(deathReasonText)));
    assertThat(persistent.getDriverLicenseNumber(), is(equalTo(driversLicenseNumber)));
    assertThat(persistent.getDriverLicenseStateCodeType(),
        is(equalTo(driversLicenseStateCodeType)));
    assertThat(persistent.getEmailAddress(), is(equalTo(emailAddress)));
    assertThat(persistent.getEstimatedDobCode(), is(equalTo(estimatedDateOfBirthCode)));
    assertThat(persistent.getEthUnableToDetReasonCode(), is(equalTo(ethUnableToDetReasonCode)));
    assertThat(persistent.getFatherParentalRightTermDate(),
        is(equalTo(DomainChef.uncookDateString(fatherParentalRightTermDate))));
    assertThat(persistent.getGenderCode(), is(equalTo(genderCode)));
    assertThat(persistent.getHealthSummaryText(), is(equalTo(healthSummaryText)));
    assertThat(persistent.getHispUnableToDetReasonCode(), is(equalTo(hispUnableToDetReasonCode)));
    assertThat(persistent.getHispanicOriginCode(), is(equalTo(hispanicOriginCode)));
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getImmigrationCountryCodeType(), is(equalTo(immigrationCountryCodeType)));
    assertThat(persistent.getIncapacitatedParentCode(), is(equalTo(incapcitatedParentCode)));
    assertThat(persistent.getIndividualHealthCarePlanIndicator(),
        is(equalTo(DomainChef.cookBoolean(individualHealthCarePlanIndicator))));
    assertThat(persistent.getLimitationOnScpHealthIndicator(),
        is(equalTo(DomainChef.cookBoolean(limitationOnScpHealthIndicator))));
    assertThat(persistent.getLiterateCode(), is(equalTo(literateCode)));
    assertThat(persistent.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(maritalCohabitatnHstryIndicatorVar))));
    assertThat(persistent.getMaritalStatusType(), is(equalTo(maritalStatusType)));
    assertThat(persistent.getMilitaryStatusCode(), is(equalTo(militaryStatusCode)));
    assertThat(persistent.getMotherParentalRightTermDate(),
        is(equalTo(DomainChef.uncookDateString(motherParentalRightTermDate))));
    assertThat(persistent.getNamePrefixDescription(), is(equalTo(namePrefixDescription)));
    assertThat(persistent.getNameType(), is(equalTo(nameType)));
    assertThat(persistent.getOutstandingWarrantIndicator(),
        is(equalTo(DomainChef.cookBoolean(outstandingWarrantIndicator))));
    assertThat(persistent.getPrevCaChildrenServIndicator(),
        is(equalTo(DomainChef.cookBoolean(prevCaChildrenServIndicator))));
    assertThat(persistent.getPrevOtherDescription(), is(equalTo(prevOtherDescription)));
    assertThat(persistent.getPrimaryEthnicityType(), is(equalTo(primaryEthnicityType)));
    assertThat(persistent.getPrimaryLanguageType(), is(equalTo(primaryLanguageType)));
    assertThat(persistent.getReligionType(), is(equalTo(religionType)));
    assertThat(persistent.getSecondaryLanguageType(), is(equalTo(secondaryLanguageType)));
    assertThat(persistent.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(sensitiveHlthInfoOnFileIndicator))));
    assertThat(persistent.getSensitivityIndicator(), is(equalTo(sensitivityIndicator)));
    assertThat(persistent.getSoc158PlacementCode(), is(equalTo(soc158PlacementCode)));
    assertThat(persistent.getSoc158SealedClientIndicator(),
        is(equalTo(DomainChef.cookBoolean(soc158SealedClientIndicator))));
    assertThat(persistent.getSocialSecurityNumChangedCode(),
        is(equalTo(socialSecurityNumChangedCode)));
    assertThat(persistent.getSocialSecurityNumber(), is(equalTo(socialSecurityNumber)));
    assertThat(persistent.getSuffixTitleDescription(), is(equalTo(suffixTitleDescription)));
    assertThat(persistent.getTribalAncestryClientIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(tribalAncestryClientIndicatorVar))));
    assertThat(persistent.getUnemployedParentCode(), is(equalTo(unemployedParentCode)));
    assertThat(persistent.getZippyCreatedIndicator(),
        is(equalTo(DomainChef.cookBoolean(zippyCreatedIndicator))));

  }

  @Override
  @Test
  public void testJSONCreatorConstructor() throws Exception {
    Client vc = validClient();

    Client domain = new Client(existingClientId, lastUpdatedTime, adjudicatedDelinquentIndicator,
        adoptionStatusCode, alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate,
        birthFacilityName, birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar,
        clientIndexNumber, commentDescription, commonFirstName, commonMiddleName, commonLastName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, genderIdentityType,
        giNotListedDescription, genderExpressionType, healthSummaryText, hispUnableToDetReasonCode,
        hispanicOriginCode, immigrationCountryCodeType, immigrationStatusType,
        incapcitatedParentCode, individualHealthCarePlanIndicator, limitationOnScpHealthIndicator,
        literateCode, maritalCohabitatnHstryIndicatorVar, maritalStatusType, militaryStatusCode,
        motherParentalRightTermDate, namePrefixDescription, nameType, outstandingWarrantIndicator,
        prevCaChildrenServIndicator, prevOtherDescription, prevRegionalCenterIndicator,
        primaryEthnicityType, primaryLanguageType, religionType, secondaryLanguageType,
        sensitiveHlthInfoOnFileIndicator, sensitivityIndicator, sexualOrientationType,
        soUnableToDetermineCode, soNotListedDescrption, soc158PlacementCode,
        soc158SealedClientIndicator, socialSecurityNumChangedCode, socialSecurityNumber,
        suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator, null);

    assertThat(domain.getAdjudicatedDelinquentIndicator(),
        is(equalTo(vc.getAdjudicatedDelinquentIndicator())));
    assertThat(domain.getAdoptionStatusCode(), is(equalTo(vc.getAdoptionStatusCode())));
    assertThat(domain.getAlienRegistrationNumber(), is(equalTo(vc.getAlienRegistrationNumber())));
    assertThat(domain.getBirthCity(), is(equalTo(vc.getBirthCity())));
    assertThat(domain.getBirthCountryCodeType(), is(equalTo(vc.getBirthCountryCodeType())));
    assertThat(domain.getBirthDate(), is(equalTo(vc.getBirthDate())));
    assertThat(domain.getBirthFacilityName(), is(equalTo(vc.getBirthFacilityName())));
    assertThat(domain.getBirthStateCodeType(), is(equalTo(vc.getBirthStateCodeType())));
    assertThat(domain.getBirthplaceVerifiedIndicator(),
        is(equalTo(vc.getBirthplaceVerifiedIndicator())));
    assertThat(domain.getChildClientIndicatorVar(), is(equalTo(vc.getChildClientIndicatorVar())));
    assertThat(domain.getClientIndexNumber(), is(equalTo(vc.getClientIndexNumber())));
    assertThat(domain.getCommentDescription(), is(equalTo(vc.getCommentDescription())));
    assertThat(domain.getCommonFirstName(), is(equalTo(vc.getCommonFirstName())));
    assertThat(domain.getCommonMiddleName(), is(equalTo(vc.getCommonMiddleName())));
    assertThat(domain.getCommonLastName(), is(equalTo(vc.getCommonLastName())));
    assertThat(domain.getConfidentialityActionDate(),
        is(equalTo(vc.getConfidentialityActionDate())));
    assertThat(domain.getConfidentialityInEffectIndicator(),
        is(equalTo(vc.getConfidentialityInEffectIndicator())));
    assertThat(domain.getCreationDate(), is(equalTo(vc.getCreationDate())));
    assertThat(domain.getCurrCaChildrenServIndicator(),
        is(equalTo(vc.getCurrCaChildrenServIndicator())));
    assertThat(domain.getCurrentlyOtherDescription(),
        is(equalTo(vc.getCurrentlyOtherDescription())));
    assertThat(domain.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(vc.getCurrentlyRegionalCenterIndicator())));
    assertThat(domain.getDeathDate(), is(equalTo(vc.getDeathDate())));
    assertThat(domain.getDeathDateVerifiedIndicator(),
        is(equalTo(vc.getDeathDateVerifiedIndicator())));
    assertThat(domain.getDeathReasonText(), is(equalTo(vc.getDeathReasonText())));
    assertThat(domain.getDriverLicenseNumber(), is(equalTo(vc.getDriverLicenseNumber())));
    assertThat(domain.getDriverLicenseStateCodeType(),
        is(equalTo(vc.getDriverLicenseStateCodeType())));
    assertThat(domain.getEmailAddress(), is(equalTo(vc.getEmailAddress())));
    assertThat(domain.getEstimatedDobCode(), is(equalTo(vc.getEstimatedDobCode())));
    assertThat(domain.getEthUnableToDetReasonCode(), is(equalTo(vc.getEthUnableToDetReasonCode())));
    assertThat(domain.getFatherParentalRightTermDate(),
        is(equalTo(vc.getFatherParentalRightTermDate())));
    assertThat(domain.getGenderCode(), is(equalTo(vc.getGenderCode())));
    assertThat(domain.getHealthSummaryText(), is(equalTo(vc.getHealthSummaryText())));
    assertThat(domain.getHispUnableToDetReasonCode(),
        is(equalTo(vc.getHispUnableToDetReasonCode())));
    assertThat(domain.getHispanicOriginCode(), is(equalTo(vc.getHispanicOriginCode())));
    assertThat(domain.getImmigrationCountryCodeType(),
        is(equalTo(vc.getImmigrationCountryCodeType())));
    assertThat(domain.getIncapacitatedParentCode(), is(equalTo(vc.getIncapacitatedParentCode())));
    assertThat(domain.getIndividualHealthCarePlanIndicator(),
        is(equalTo(vc.getIndividualHealthCarePlanIndicator())));
    assertThat(domain.getLimitationOnScpHealthIndicator(),
        is(equalTo(vc.getLimitationOnScpHealthIndicator())));
    assertThat(domain.getLiterateCode(), is(equalTo(vc.getLiterateCode())));
    assertThat(domain.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(vc.getMaritalCohabitatnHstryIndicatorVar())));
    assertThat(domain.getMaritalStatusType(), is(equalTo(vc.getMaritalStatusType())));
    assertThat(domain.getMilitaryStatusCode(), is(equalTo(vc.getMilitaryStatusCode())));
    assertThat(domain.getMotherParentalRightTermDate(),
        is(equalTo(vc.getMotherParentalRightTermDate())));
    assertThat(domain.getNamePrefixDescription(), is(equalTo(vc.getNamePrefixDescription())));
    assertThat(domain.getNameType(), is(equalTo(vc.getNameType())));
    assertThat(domain.getOutstandingWarrantIndicator(),
        is(equalTo(vc.getOutstandingWarrantIndicator())));
    assertThat(domain.getPrevCaChildrenServIndicator(),
        is(equalTo(vc.getPrevCaChildrenServIndicator())));
    assertThat(domain.getPrevOtherDescription(), is(equalTo(vc.getPrevOtherDescription())));
    assertThat(domain.getPrimaryEthnicityType(), is(equalTo(vc.getPrimaryEthnicityType())));
    assertThat(domain.getPrimaryLanguage(), is(equalTo(vc.getPrimaryLanguage())));
    assertThat(domain.getReligionType(), is(equalTo(vc.getReligionType())));
    assertThat(domain.getSecondaryLanguage(), is(equalTo(vc.getSecondaryLanguage())));
    assertThat(domain.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(vc.getSensitiveHlthInfoOnFileIndicator())));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(vc.getSensitivityIndicator())));
    assertThat(domain.getSoc158PlacementCode(), is(equalTo(vc.getSoc158PlacementCode())));
    assertThat(domain.getSoc158SealedClientIndicator(),
        is(equalTo(vc.getSoc158SealedClientIndicator())));
    assertThat(domain.getSocialSecurityNumChangedCode(),
        is(equalTo(vc.getSocialSecurityNumChangedCode())));
    assertThat(domain.getSocialSecurityNumber(), is(equalTo(vc.getSocialSecurityNumber())));
    assertThat(domain.getSuffixTitleDescription(), is(equalTo(vc.getSuffixTitleDescription())));
    assertThat(domain.getTribalAncestryClientIndicatorVar(),
        is(equalTo(vc.getTribalAncestryClientIndicatorVar())));
    assertThat(domain.getUnemployedParentCode(), is(equalTo(vc.getUnemployedParentCode())));
    assertThat(domain.getZippyCreatedIndicator(), is(equalTo(vc.getZippyCreatedIndicator())));

  }

  @Test
  public void testCreateWithDefaultCreatesWithValues() {
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(),
        "firstName", "middleName", "lastName", "jr", "gender", "ssn", "dob", primaryLanguageType,
        secondaryLanguageType, 4, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(),
        raceAndEthnicity);

    String genderCode = "male";
    String dateStarted = "now";
    Short raceCode = (short) 0;
    Boolean childClientIndicatorVar = Boolean.TRUE;

    Client client = Client.createWithDefaults(participant, dateStarted, genderCode, raceCode,
        childClientIndicatorVar);

    assertEquals("Expected BirthDate field to be initialized with values",
        participant.getDateOfBirth(), client.getBirthDate());
    assertEquals("Expected First Name field to be initialized with values",
        participant.getFirstName(), client.getCommonFirstName());
    assertEquals("Expected Middle Name field to be initialized with values",
        participant.getMiddleName(), client.getCommonMiddleName());
    assertEquals("Expected LastName field to be initialized with values", participant.getLastName(),
        client.getCommonLastName());
    assertEquals("Expected suffix name field to be initialized with values",
        participant.getNameSuffix(), client.getSuffixTitleDescription());
    assertEquals("Expected SSN field to be initialized with values", participant.getSsn(),
        client.getSocialSecurityNumber());
    assertEquals("Expected genderCode field to be initialized with values", genderCode,
        client.getGenderCode());
    assertEquals("Expected dateStarted field to be initialized with values", dateStarted,
        client.getCreationDate());
  }

  @Test
  public void shouldAllowClientNamesToBeUpdatedAfterInitialization() {

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant =
        new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(), "Fred", "Wilson",
            "Bill", "", "gender", "ssn", "dob", primaryLanguageType, secondaryLanguageType, 4,
            reporterConfidentialWaiver, reporterEmployerName, clientStaffPersonAdded,
            sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(), raceAndEthnicity);

    Client client = Client.createWithDefaults(participant, "", "", (short) 0, true);

    client.update("Barney", "middlestone", "Rubble", "jr", "F", true, (short) 0, "A", "A", "X");

    assertEquals("Expected Client first name to have been changed", "Barney",
        client.getCommonFirstName());
    assertEquals("Expected Client middle name to have been changed", "middlestone",
        client.getCommonMiddleName());
    assertEquals("Expected Client last name to have been changed", "Rubble",
        client.getCommonLastName());
    assertEquals("Expected Client name suffix to have been changed", "jr",
        client.getSuffixTitleDescription());
  }

  @Test
  public void testCreateWithDefaultCreatesWithDefaultValues() {
    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(),
        "firstName", "middleName", "lastName", "", "gender", "ssn", "dob", primaryLanguageType,
        secondaryLanguageType, 4, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(),
        raceAndEthnicity);

    String genderCode = "male";
    String dateStarted = "now";
    Short raceCode = (short) 0;
    Boolean childClientIndicatorVar = Boolean.TRUE;

    Client client = Client.createWithDefaults(participant, dateStarted, genderCode, raceCode,
        childClientIndicatorVar);

    assertEquals("Expected existingClientId field to be initialized with default values", "",
        client.getExistingClientId());
    assertEquals(
        "Expected adjudicatedDelinquentIndicator field to be initialized with default values",
        false, client.getAdjudicatedDelinquentIndicator());
    assertEquals("Expected adoptionStatusCode field to be initialized with default values", "N",
        client.getAdoptionStatusCode());
    assertEquals("Expected alienRegistrationNumber field to be initialized with default values", "",
        client.getAlienRegistrationNumber());
    assertEquals("Expected birth city field to be initialized with default values", "",
        client.getBirthCity());
    assertEquals("Expected birthCountryCodeType field to be initialized with default values",
        new Short("0"), client.getBirthCountryCodeType());
    assertEquals("Expected birthFacilityName field to be initialized with default values", "",
        client.getBirthFacilityName());
    assertEquals("Expected birthStateCodeType field to be initialized with default values",
        new Short("0"), client.getBirthStateCodeType());
    assertEquals("Expected birthplaceVerifiedIndicator field to be initialized with default values",
        false, client.getBirthplaceVerifiedIndicator());
    assertEquals("Expected clientIndexNumber field to be initialized with default values", "",
        client.getClientIndexNumber());
    assertEquals("Expected commentDescription field to be initialized with default values", "",
        client.getCommentDescription());
    assertEquals("Expected confidentialityActionDate field to be initialized with default values",
        "", client.getConfidentialityActionDate());
    assertEquals(
        "Expected confidentialityInEffectIndicator field to be initialized with default values",
        false, client.getConfidentialityInEffectIndicator());
    assertEquals("Expected currCaChildrenServIndicator field to be initialized with default values",
        false, client.getCurrCaChildrenServIndicator());
    assertEquals("Expected currentlyOtherDescription field to be initialized with default values",
        "", client.getCurrentlyOtherDescription());
    assertEquals(
        "Expected currentlyRegionalCenterIndicato field to be initialized with default values",
        false, client.getCurrentlyRegionalCenterIndicator());
    assertEquals("Expected deathDate field to be initialized with default values", "",
        client.getDeathDate());
    assertEquals("Expected deathDateVerifiedIndicato field to be initialized with default values",
        false, client.getDeathDateVerifiedIndicator());
    assertEquals("Expected deathPlace field to be initialized with default values", "",
        client.getDeathPlace());
    assertEquals("Expected deathReasonText field to be initialized with default values", "",
        client.getDeathReasonText());
    assertEquals("Expected driverLicenseNumber field to be initialized with default values", "",
        client.getDriverLicenseNumber());
    assertEquals("Expected driverLicenseStateCodeType field to be initialized with default values",
        new Short("0"), client.getDriverLicenseStateCodeType());
    assertEquals("Expected emailAddress field to be initialized with default values", "",
        client.getEmailAddress());
    assertEquals("Expected estimatedDobCode field to be initialized with default values", "N",
        client.getEstimatedDobCode());
    assertEquals("Expected fatherParentalRightTermDate field to be initialized with default values",
        "", client.getFatherParentalRightTermDate());
    assertEquals("Expected healthSummaryText field to be initialized with default values", "",
        client.getHealthSummaryText());
    assertEquals("Expected immigrationCountryCodeTyp field to be initialized with default values",
        new Short("0"), client.getImmigrationCountryCodeType());
    assertEquals("Expected immigrationStatusType field to be initialized with default values",
        new Short("0"), client.getImmigrationStatusType());
    assertEquals("Expected incapacitatedParentCode field to be initialized with default values",
        "U", client.getIncapacitatedParentCode());
    assertEquals(
        "Expected individualHealthCarePlanIndicator field to be initialized with default values",
        false, client.getIndividualHealthCarePlanIndicator());
    assertEquals(
        "Expected limitationOnScpHealthIndicator field to be initialized with default values",
        false, client.getLimitationOnScpHealthIndicator());
    assertEquals("Expected literateCode field to be initialized with default values", "U",
        client.getLiterateCode());
    assertEquals(
        "Expected maritalCohabitatnHstryIndicatorVar field to be initialized with default values",
        false, client.getMaritalCohabitatnHstryIndicatorVar());
    assertEquals("Expected maritalStatusType field to be initialized with default values",
        new Short("0"), client.getMaritalStatusType());
    assertEquals("Expected militaryStatusCod field to be initialized with default values", "N",
        client.getMilitaryStatusCode());
    assertEquals("Expected motherParentalRightTermDate field to be initialized with default values",
        "", client.getMotherParentalRightTermDate());
    assertEquals("Expected namePrefixDescription field to be initialized with default values", "",
        client.getNamePrefixDescription());
    assertEquals("Expected nameType field to be initialized with default values", new Short("1313"),
        client.getNameType());
    assertEquals("Expected outstandingWarrantIndicator field to be initialized with default values",
        false, client.getOutstandingWarrantIndicator());
    assertEquals("Expected prevCaChildrenServIndicator field to be initialized with default values",
        false, client.getPrevCaChildrenServIndicator());
    assertEquals("Expected prevOtherDescription field to be initialized with default values", "",
        client.getPrevOtherDescription());
    assertEquals("Expected prevRegionalCenterIndicato field to be initialized with default values",
        false, client.getPrevRegionalCenterIndicator());
    assertEquals("Expected religionType field to be initialized with default values",
        new Short("0"), client.getReligionType());
    assertEquals(
        "Expected sensitiveHlthInfoOnFileIndicator field to be initialized with default values",
        false, client.getSensitiveHlthInfoOnFileIndicator());
    assertEquals("Expected sensitivityIndicator field to be initialized with default values", "N",
        client.getSensitivityIndicator());
    assertEquals("Expected genderIdentityType field to be initialized with default values",
        new Short("7075"), client.getGenderIdentityType());
    assertEquals("Expected genderExpressionType field to be initialized with default values",
        new Short("7081"), client.getGenderExpressionType());
    assertEquals("Expected sexualOrientationType field to be initialized with default values",
        new Short("7066"), client.getSexualOrientationType());
    assertEquals("Expected soUnableToDetermineCode field to be initialized with default values",
        "D", client.getSoUnableToDetermineCode());
    assertEquals("Expected soc158PlacementCode field to be initialized with default values", "N",
        client.getSoc158PlacementCode());
    assertEquals("Expected soc158SealedClientIndicator field to be initialized with default values",
        false, client.getSoc158SealedClientIndicator());
    assertEquals(
        "Expected socialSecurityNumChangedCode field to be initialized with default values", "N",
        client.getSocialSecurityNumChangedCode());
    assertEquals(
        "Expected tribalAncestryClientIndicatorVar field to be initialized with default values",
        false, client.getTribalAncestryClientIndicatorVar());
    assertEquals(
        "Expected tribalMembrshpVerifctnIndicatorVar field to be initialized with default values",
        false, client.getTribalMembrshpVerifctnIndicatorVar());
    assertEquals("Expected unemployedParentCode field to be initialized with default values", "U",
        client.getUnemployedParentCode());
    assertEquals("Expected zippyCreatedIndicator field to be initialized with default values", true,
        client.getZippyCreatedIndicator());
    assertEquals("Expected address field to be initialized with default values", null,
        client.getAddress());
  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    // EqualsVerifier.forClass(Client.class).withIgnoredFields("messages")
    // .suppress(Warning.NONFINAL_FIELDS).verify();
    Client validClient = validDomainClient();
    assertThat(validClient.hashCode(), is(not(0)));
  }

  @Override
  @Test
  public void testSerializesToJSON() throws Exception {
    Client validClient = validDomainClient();
    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class));

    String client = MAPPER.writeValueAsString(validClient);
    assertThat(client, is(equalTo(expected)));
  }

  @Override
  // TODO: Needs tests
  public void testDeserializesFromJSON() throws Exception {}

  @Override
  @Test
  public void testSuccessWithValid() throws Exception {
    Client validClient = validClient();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Override
  @Test
  public void testSuccessWithOptionalsNotIncluded() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/optionalsNotIncluded.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * adoption status code test
   */
  @Test
  public void testFailAdoptionStatusCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptionStatusCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("X").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptionStatusCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAdoptionStatusCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("adoptionStatusCode must be one of [T, P, N, A]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessAdoptionStatusCodeA() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("A").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessAdoptionStatusCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessAdoptionStatusCodeP() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("P").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessAdoptionStatusCodeT() throws Exception {
    Client validClient = new ClientResourceBuilder().setAdoptionStatusCode("T").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * alienRegistrationNumber test
   */
  @Test
  public void testSuccessAlienRegistrationNumberEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setAlienRegistrationNumber("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailAlienRegistrationNumberNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setAlienRegistrationNumber(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailAlienRegistrationNumberTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setAlienRegistrationNumber("1234567890123").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * birth city test
   * 
   */
  @Test
  public void testSuccessBirthCityEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCity("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailBirthCityNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCity(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailBirthCityTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setBirthCity("123456789012345678901234567890123456").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * birthCountryCode test
   */
  @Test
  public void testSuccessBirthCountryCodeValid() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setBirthCountryCodeType(birthCountryCodeType).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessBirthCountryZero() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCountryCodeType((short) 0).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailBirthCountryCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthCountryCodeType(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailBirthCountryCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthCountryMissing.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * birthDate test
   */
  @Test
  public void successWhenBirthDateValid() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthDate("2007-01-31").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenBirthDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthDate(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenBirthDateBlank() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/birthDateBlank.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenBirthDateWrongFormat() throws Exception {
    Client toCreate = new ClientResourceBuilder().setBirthDate("01/31/2001").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthDate must be in the format of"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * birthFacilityName test
   */
  @Test
  public void sucessWhenBirthFacilityNameSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthFacilityName("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenBirthFacilityNameNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthFacilityName(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthFacilityName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthFacilityNameMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthFacilityNameMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthFacilityName may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthFacilityNameTooLong() throws Exception {
    Client validClient = new ClientResourceBuilder()
        .setBirthFacilityName("123456789012345678901234567890123456").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("birthFacilityName size must be between 0 and 35"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * birthStateCodetype test
   */
  @Test
  public void successWhenBirthStateCodeValid() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setBirthStateCodeType(birthStateCodeType).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenBirthStateCodeZero() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthStateCodeType((short) 0).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenBirthStateCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthStateCodeType(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthStateCodeBlank() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthStateCodeBlank.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthStateCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthStateCodeMissing.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("birthStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * birthplaceVerifiedInd test
   */
  @Test
  public void failWhenBirthplaceVerifiedNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setBirthplaceVerifiedIndicator(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthplaceVerifiedEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthplaceVerifiedIndicatorEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthplaceVerifiedWhiteSpace() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthplaceVerifiedIndicatorWhiteSpace.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenBirthplaceVerifiedMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/birthplaceVerifiedIndicatorMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("birthplaceVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * childClientIndicator test
   */
  @Test
  public void failWhenChildClientIndicatorMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenChildClientIndicatorEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenChildClientIndicatorNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setChildClientIndicatorVar(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenChildClientIndicatorWhiteSpace() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/childClientIndicatorWhiteSpace.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("childClientIndicatorVar may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * clientIndexNumber test
   */
  @Test
  public void successWhenClientIndexNumberNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setClientIndexNumber(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenClientIndexNumberWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setClientIndexNumber("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenClientIndexNumberTooLong() throws Exception {
    Client validClient = new ClientResourceBuilder().setClientIndexNumber("1234567890123").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("clientIndexNumber size must be between 0 and 12"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * commentDescription test
   */
  @Test
  public void successWhenCommentDescriptionWhiteSpace() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/clientIndexNumberWhiteSpace.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void failWhenCommentDescriptionNull() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/commentDescriptionNull.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commentDescription may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenCommentDescriptionTooLong() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/commentDescriptionTooLong.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("commentDescription size must be between 0 and 120"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * commonFirstName test
   */
  @Test
  public void failWhenCommonFirstNameNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonFirstName(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonFirstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCommonFirstNameEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonFirstName("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonFirstName size must be between 1 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCommonFirstNameTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setCommonFirstName("123456789012345678901").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonFirstName size must be between 1 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCommonFirstNameWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonFirstName("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonFirstName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCommonLastNameNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonLastName(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonLastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCommonLastNameEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonLastName("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonLastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCommonLastNameTooLong() throws Exception {
    Client validClient = new ClientResourceBuilder()
        .setCommonLastName("1345123456789012345678901sffvfsvfsvfs").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonLastName size must be between 1 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failsWhenCommonLastNameWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonLastName("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("commonLastName may not be empty"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenCommonMiddleNameEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonMiddleName("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenCommonMiddleNameWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonMiddleName("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failsWhenCommonMiddleNameTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setCommonMiddleName("123456789012345678901").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("commonMiddleName size must be between 0 and 20"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void successWhenCommonMiddleNameNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setCommonMiddleName(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void SuccessWhenCommonMiddleNameMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/commonMiddleNameMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * confidentialityActionDate test
   */
  @Test
  public void successWhenConfidentialityActionDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setConfidentialityActionDate(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenConfidentialityActionDateEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setConfidentialityActionDate("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenConfidentialityActionDateInvalid() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setConfidentialityActionDate("01-01-2010").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("confidentialityActionDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * confidentialityInEffectInd test
   */
  @Test
  public void failWhenConfidentialityInEffectIndEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/confidentialityInEffectIndEmpty.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("confidentialityInEffectIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenConfidentialityInEffectIndMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/confidentialityInEffectIndMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("confidentialityInEffectIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenConfidentialityInEffectIndNull() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setConfidentialityInEffectIndicator(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("confidentialityInEffectIndicator may not be null"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * creationDate test
   */
  @Test
  public void failWhenCreationDateNull() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/creationDateNull.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("creationDate may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenCreationDateEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/creationDateEmpty.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("creationDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenCreationDateWhiteSpace() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/creationDateWhiteSpace.json"), Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("creationDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenCreationDateInvalid() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/creationDateWhiteInvalid.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("creationDate must be in the format of yyyy-MM-dd"), is(greaterThanOrEqualTo(0)));

  }

  /*
   * currCaChildrenServiceInd test
   */
  @Test
  public void failWhenCurrCaChildrenServIndicatorNull() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/currCaChildrenServIndicatorNull.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("currCaChildrenServIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenCurrCaChildrenServIndicatorEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/currCaChildrenServIndicatorEmpty.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("currCaChildrenServIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenCurrCaChildrenServIndicatorMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/currCaChildrenServIndicatorMissing.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("currCaChildrenServIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * currentlyOtherDescription test
   */
  @Test
  public void successWhenCurrentlyOtherDescriptionEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setCurrentlyOtherDescription("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenCurrentlyOtherDescriptionNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setCurrentlyOtherDescription(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("currentlyOtherDescription may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenCurrentlyOtherDescriptionTooLong() throws Exception {
    Client validClient = new ClientResourceBuilder()
        .setCurrentlyOtherDescription("12345678901234567890123456").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("currentlyOtherDescription size must be between 0 and 25"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * deathDate test
   */
  @Test
  public void successWhenDeathDateNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathDate(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenDeathDateInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathDate("01-01-2010").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("deathDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * deathDateVerifiedIndicator test
   */
  @Test
  public void failWhenDeathDateVerifiedNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathDateVerifiedIndicator(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("deathDateVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenDeathDateVerifiedEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/deathDateVerifiedEmpty.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("deathDateVerifiedIndicator may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * deathPlace test
   */
  @Test
  public void successWhenDeathPlaceNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathPlace(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDeathPlaceEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathPlace("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenDeathPlaceTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setDeathPlace("123456789012345678901234567890123456").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    // System.out.println(response.readEntity(String.class));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("deathPlace size must be between 0 and 35"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * deathReason test
   */

  @Test
  public void successWhenDeathResonTextNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathReasonText(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void successWhenDeathReasonTextEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathReasonText("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void failWhenDeathReasonTooLong() throws Exception {
    Client validClient = new ClientResourceBuilder().setDeathReasonText("12345678901").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("deathReasonText size must be between 0 and 10"),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * driversLicenseNumber test
   */
  @Test
  public void successWhenDriverLicenseNumberEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setDriverLicenseNumber("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void successWhenDriverLicenseNumberAllWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setDriverLicenseNumber("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void failWhenDriverLicenseNumberNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setDriverLicenseNumber(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("driverLicenseNumber may not be null"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void failWhenDriverLicenseNumberTooLong() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setDriverLicenseNumber("C87634563C87634563123").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("driverLicenseNumber size must be between 0 and 20"), is(greaterThanOrEqualTo(0)));
  }

  /*
   * driverLicenseStateCodeType test
   */
  @Test
  public void failWhenDriverLicenseStateCodeTypeNull() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/driverLicenseStateCodeTypeNull.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("driverLicenseStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenDriverLicenseStateCodeTypeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/driverLicenseStateCodeTypeMissing.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("driverLicenseStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void failWhenDriverLicenseStateCodeTypeEmpty() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/driverLicenseStateCodeTypeEmpty.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("driverLicenseStateCodeType may not be null"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * emailAddress test
   */
  @Test
  public void testSuccessEmailAddressEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setEmailAddress("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccssEmailAddressMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/emailAddressMissing.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessEmailAddressNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setEmailAddress(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * estimatedDobCode test
   */
  @Test
  public void testFailEstimatedDobCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode("X").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailEstimatedDobCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailEstimatedDobCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/estimatedDobCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailEstimatedDobCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailEstimatedDobCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("estimatedDobCode must be one of [Y, N, U]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessEstimatedDobCodeY() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode("Y").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessEstimatedDobCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessEstimatedDobCodeU() throws Exception {
    Client validClient = new ClientResourceBuilder().setEstimatedDobCode("U").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * ethUnableToDetReasonCode test
   */
  @Test
  public void testFailEthUnableToDetReasonCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setEthUnableToDetReasonCode("NA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testSuccessEthUnableToDetReasonCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setEthUnableToDetReasonCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessEthUnableToDetReasonCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/ethUnableToDetReasonCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testPassEthUnableToDetReasonCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setEthUnableToDetReasonCode(" ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessEthUnableToDetReasonCodeA() throws Exception {
    Client validClient = new ClientResourceBuilder().setEthUnableToDetReasonCode("A").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessEthUnableToDetReasonCodeI() throws Exception {
    Client validClient = new ClientResourceBuilder().setEthUnableToDetReasonCode("I").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));

  }

  @Test
  public void testSuccessEthUnableToDetReasonCodeK() throws Exception {
    Client validClient = new ClientResourceBuilder().setEthUnableToDetReasonCode("K").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * fatherParentalRightTermDate test
   */
  @Test
  public void failWhenFatherParentalRightTermDateInvalid() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/fatherParentalRightTermDateInvalid.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("fatherParentalRightTermDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));

  }

  @Test
  public void testSuccessFatherParentalRightTermDateMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/valid/fatherParentalRightTermDateMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * genderCode test
   */
  @Test
  public void testFailGenderCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("Z").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("genderCode must be one of [M, F, U, I]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testFailGenderCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailGenderCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/genderCodeMissing.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testFailGenderCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));

  }

  @Test
  public void testFailGenderCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode(" ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("genderCode must be one of [M, F, U, I]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessGenderCodeM() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("M").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessGenderCodeF() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("F").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessGenderCodeU() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("U").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessGenderCodeI() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderCode("I").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * genderIdentityType
   */
  @Test
  public void testSuccessGenderIdentityTypeValidCode() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderIdentityType((short) 7075).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailGenderIdentityTypeInValidCode() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderIdentityType((short) 1234).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(
        response.readEntity(String.class)
            .indexOf("genderIdentityType must be a valid system code for category CLNT_GIC"),
        is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailGenderIdentityTypeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderIdentityType(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.readEntity(String.class).indexOf("genderIdentityType may not be null"),
        is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * giNotListedDescription test
   */
  @Test
  public void testSuccessGiNotListedDescriptionNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setGiNotListedDescription(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessGiNotListedDescriptionEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setGiNotListedDescription("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * genderExpressionType test
   */
  @Test
  public void testSuccessGenderExpressionTypeValidCode() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setGenderExpressionType(genderExpressionType).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailGenderExpressionTypeInValidCode() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderExpressionType((short) 1234).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(
        response.readEntity(String.class)
            .indexOf("genderExpressionType must be a valid system code for category CLNT_GEC"),
        is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailGenderExpressionTypeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setGenderExpressionType(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.readEntity(String.class).indexOf("genderExpressionType may not be null"),
        is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * sexualOrientationType test
   */
  @Test
  public void testSuccessSexualOrientationTypeValidCode() throws Exception {
    Client validClient =
        new ClientResourceBuilder().setSexualOrientationType(sexualOrientationType).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailSexualOrientationTypeInValidCode() throws Exception {
    Client validClient = new ClientResourceBuilder().setSexualOrientationType((short) 1234).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(
        response.readEntity(String.class)
            .indexOf("sexualOrientationType must be a valid system code for category CLNT_SOC"),
        is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSexualOrientationTypeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSexualOrientationType(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.readEntity(String.class).indexOf("sexualOrientationType may not be null"),
        is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * soUnableToDetermineCode test
   */
  @Test
  public void testSuccessSoUnableToDetermineCodeD() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode("D").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessSoUnableToDetermineCodeC() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode("C").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessSoUnableToDetermineCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessSoUnableToDetermineCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testFailSoUnableToDetermineCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoUnableToDetermineCode("Z").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.readEntity(String.class)
        .indexOf("soUnableToDetermineCode must be one of [D, C, ]"), is(greaterThanOrEqualTo(0)));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  /*
   * soNotListedDescrption test
   */
  @Test
  public void testSuccessSoNotListedDescrptionNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoNotListedDescrption(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessSoNotListedDescrptionEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoNotListedDescrption("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * incapacitatedParentCode test
   */
  @Test
  public void testFailIncapacitatedParentCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("X").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailIncapacitatedParentCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailIncapacitatedParentCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/incapacitatedParentCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailIncapacitatedParentCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailIncapacitatedParentCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "incapacitatedParentCode must be one of [N, NA, U, Y]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessIncapacitatedParentCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessIncapacitatedParentCodeNA() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("NA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessIncapacitatedParentCodeU() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("U").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessIncapacitatedParentCodeY() throws Exception {
    Client validClient = new ClientResourceBuilder().setIncapacitatedParentCode("Y").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * motherParentalRightTermDate test
   */
  @Test
  public void failWhenMotherParentalRightTermDateInvalid() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/motherParentalRightTermDateInvalid.json"),
        Client.class);

    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));

    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("motherParentalRightTermDate must be in the format of yyyy-MM-dd"),
        is(greaterThanOrEqualTo(0)));

  }

  /*
   * literateCode test
   */
  @Test
  public void testFailLiterateCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("NA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailLiterateCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailLiterateCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/literateCodeMissing.json"), Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailLiterateCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailLiterateCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("literateCode must be one of [Y, N, U, D]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessLiterateCodeY() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("Y").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessLiterateCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessLiterateCodeU() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("U").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessLiterateCodeD() throws Exception {
    Client validClient = new ClientResourceBuilder().setLiterateCode("D").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * militaryStatusCode test
   */
  @Test
  public void testFailMilitaryStatusCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("X").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailMilitaryStatusCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailMilitaryStatusCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/militaryStatusCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailMilitaryStatusCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailMilitaryStatusCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class).indexOf("militaryStatusCode must be one of [D, A, V, N]"),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessMilitaryStatusCodeD() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("D").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessMilitaryStatusCodeA() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("A").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessMilitaryStatusCodeV() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("V").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessMilitaryStatusCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setMilitaryStatusCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * socPlacementCode test
   * 
   * 158 is removed on the naming Json convention to make jenkins build success
   */
  @Test
  public void testFailSoc158PlacementCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoc158PlacementCode("NA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSoc158PlacementCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoc158PlacementCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSoc158PlacementCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/socPlacementCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSoc158PlacementCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoc158PlacementCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testSuccessSoc158PlacementCodeY() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoc158PlacementCode("Y").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessSoc158PlacementCodeM() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoc158PlacementCode("M").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessSoc158PlacementCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setSoc158PlacementCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * socialSecurityNumChangedCode test
   */
  @Test
  public void testFailSocialSecurityNumChangedCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setSocialSecurityNumChangedCode("NA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSocialSecurityNumChangedCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setSocialSecurityNumChangedCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSocialSecurityNumChangedCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/socialSecurityNumChangedCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSocialSecurityNumChangedCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setSocialSecurityNumChangedCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailSocialSecurityNumChangedCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setSocialSecurityNumChangedCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf(
        "socialSecurityNumChangedCode must be one of [Y, N]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessSocialSecurityNumChangedCodeY() throws Exception {
    Client validClient = new ClientResourceBuilder().setSocialSecurityNumChangedCode("Y").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccesSocialSecurityNumChangedCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setSocialSecurityNumChangedCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * socialSecurityNumber test
   */
  @Test
  public void testFailsocialSecurityNumberInvalid() throws Exception {

    Client validClient = new ClientResourceBuilder().setSocialSecurityNumber("123456kk9").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("must match \\\"^(|[0-9]{9})$\\\""),
        is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testFailsocialSecurityNumberTooLong() throws Exception {

    Client validClient = new ClientResourceBuilder().setSocialSecurityNumber("1234567890").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class).indexOf("must match \\\"^(|[0-9]{9})$\\\""),
        is(greaterThanOrEqualTo(0)));
  }

  /*
   * unemployedParentCode test
   */
  @Test
  public void testFailUnemployedParentCodeInvalid() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("UA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailUnemployedParentCodeEmpty() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailUnemployedParentCodeMissing() throws Exception {
    Client validClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/Client/invalid/unemployedParentCodeMissing.json"),
        Client.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailUnemployedParentCodeNull() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode(null).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
  }

  @Test
  public void testFailUnemployedParentCodeWhiteSpace() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("  ").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class)
        .indexOf("unemployedParentCode must be one of [N, NA, U, Y]"), is(greaterThanOrEqualTo(0)));
  }

  @Test
  public void testSuccessUnemployedParentCodeN() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("N").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessUnemployedParentCodeNA() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("NA").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessUnemployedParentCodeU() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("U").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testSuccessUnemployedParentCodeY() throws Exception {
    Client validClient = new ClientResourceBuilder().setUnemployedParentCode("Y").build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(validClient, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void shouldReturnTrueWhenOtherClientHasNotBeenModified() {
    DateTime lastUpdateTime = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.sssZ")
        .parseDateTime("2004-03-31T09:45:58.000-0800");

    Client client1 = new ClientResourceBuilder().setLastUpdateTime(lastUpdateTime).build();
    Client client2 = new ClientResourceBuilder().setLastUpdateTime(lastUpdateTime).build();
    assertTrue("Expect client 1 to have same last update time ",
        client1.hasSameLastUpdate(client2));
  }

  @Test
  public void shouldConvertNullValuesToDefault0() {
    Client client = new ClientResourceBuilder().setPrimaryLanguageType(null)
        .setSecondaryLanguageType(null).build();
    assertEquals(0, (int) client.getPrimaryLanguage());
    assertEquals(0, (int) client.getSecondaryLanguage());
  }

  @Test
  public void applySealedSensitiveIndicator() {
    Client client = new ClientResourceBuilder().setSensitivityIndicator(null).build();

    client.applySensitivityIndicator(LimitedAccessType.SENSITIVE);
    assertEquals("Expected indicator to not be unchanged", LimitedAccessType.SENSITIVE.getValue(),
        client.getSensitivityIndicator());
  }

  @Test
  public void testConstructorWithNullPrimaryLanguage() throws Exception {
    Short zero = 0;
    gov.ca.cwds.data.persistence.cms.Client persistent =
        new ClientEntityBuilder().setPrimaryLanguageType(null).build();

    Client domain = new Client(persistent, Boolean.FALSE);
    assertThat(domain.getPrimaryLanguage(), is(equalTo(zero)));
  }

  @Test
  public void testConstructorWithNullSecondaryLanguage() throws Exception {
    Short zero = 0;
    gov.ca.cwds.data.persistence.cms.Client persistent =
        new ClientEntityBuilder().setSecondaryLanguageType(null).build();
    Client domain = new Client(persistent, Boolean.FALSE);
    assertThat(domain.getSecondaryLanguage(), is(equalTo(zero)));
  }

  @Test
  /**
   * Rule - 06998
   */
  public void testZippyClientCreation() {

    RaceAndEthnicity raceAndEthnicity =
        new RaceAndEthnicity(new ArrayList<>(), "A", new ArrayList<>(), "X", "A");

    Participant participant = new Participant(1, "sourceTable", "clientId", new LegacyDescriptor(),
        "firstName", "middleName", "lastName", "", "gender", "ssn", "dob", primaryLanguageType,
        secondaryLanguageType, 4, reporterConfidentialWaiver, reporterEmployerName,
        clientStaffPersonAdded, sensitivityIndicator, "12", "Y", new HashSet<>(), new HashSet<>(),
        raceAndEthnicity);

    Client client = Client.createWithDefaults(participant, "", "", (short) 0, true);

    assertEquals("Expected zippyCreatedIndicator field to be initialized as  True", Boolean.TRUE,
        client.getZippyCreatedIndicator());

  }

  // @Test
  // public void testConstructorWithNonNullClientAddresses() throws Exception {
  // gov.ca.cwds.data.persistence.cms.ClientAddress persistentClientAddress =
  // new ClientAddressEntityBuilder().buildClientAddress();
  // Set<ClientAddress> persistentClientAddresses = new HashSet<>();
  // persistentClientAddresses.add(persistentClientAddress);
  // Address address = new Address(persistentClientAddress.getAddresses(), Boolean.TRUE);
  //
  // Set<Address> addresses = new HashSet<>();
  // addresses.add(address);
  // List<Address> persistentAddresses = new ArrayList<Address>(addresses);
  //
  // gov.ca.cwds.data.persistence.cms.Client persistent =
  // new ClientEntityBuilder().setClientAddress(persistentClientAddresses).build();
  //
  // Client domain = new Client(persistent, Boolean.FALSE);
  //
  // List<Address> domainAddresses = new ArrayList<Address>(domain.getAddress());
  // Boolean addressesEqual = domainAddresses.containsAll(persistentAddresses)
  // && persistentAddresses.containsAll(domainAddresses);
  //
  // assertThat(addressesEqual, is(equalTo(Boolean.TRUE)));
  // }

  private Client validClient() throws JsonParseException, JsonMappingException, IOException {
    Client vc =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class);
    return vc;
  }


  private Client validDomainClient() {
    Client domain = new Client(existingClientId, lastUpdatedTime, adjudicatedDelinquentIndicator,
        adoptionStatusCode, alienRegistrationNumber, birthCity, birthCountryCodeType, birthDate,
        birthFacilityName, birthStateCodeType, birthplaceVerifiedIndicator, childClientIndicatorVar,
        clientIndexNumber, commentDescription, commonFirstName, commonMiddleName, commonLastName,
        confidentialityActionDate, confidentialityInEffectIndicator, creationDate,
        currCaChildrenServIndicator, currentlyOtherDescription, currentlyRegionalCenterIndicator,
        deathDate, deathDateVerified, deathPlace, deathReasonText, driversLicenseNumber,
        driversLicenseStateCodeType, emailAddress, estimatedDateOfBirthCode,
        ethUnableToDetReasonCode, fatherParentalRightTermDate, genderCode, genderIdentityType,
        giNotListedDescription, genderExpressionType, healthSummaryText, hispUnableToDetReasonCode,
        hispanicOriginCode, immigrationCountryCodeType, immigrationStatusType,
        incapcitatedParentCode, individualHealthCarePlanIndicator, limitationOnScpHealthIndicator,
        literateCode, maritalCohabitatnHstryIndicatorVar, maritalStatusType, militaryStatusCode,
        motherParentalRightTermDate, namePrefixDescription, nameType, outstandingWarrantIndicator,
        prevCaChildrenServIndicator, prevOtherDescription, prevRegionalCenterIndicator,
        primaryEthnicityType, primaryLanguageType, religionType, secondaryLanguageType,
        sensitiveHlthInfoOnFileIndicator, sensitivityIndicator, sexualOrientationType,
        soUnableToDetermineCode, soNotListedDescrption, soc158PlacementCode,
        soc158SealedClientIndicator, socialSecurityNumChangedCode, socialSecurityNumber,
        suffixTitleDescription, tribalAncestryClientIndicatorVar,
        tribalMembrshpVerifctnIndicatorVar, unemployedParentCode, zippyCreatedIndicator, null);

    return domain;

  }
}
