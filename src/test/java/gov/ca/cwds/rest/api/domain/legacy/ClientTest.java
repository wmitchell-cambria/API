package gov.ca.cwds.rest.api.domain.legacy;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.core.Api;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ClientTest {
  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_CLIENT + "/";;

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  @SuppressWarnings("unused")
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private final static DateFormat timeOnlyFormat = new SimpleDateFormat("HH:mm:ss");

  @Before
  public void setup() {

    try {
      Client validClient = validClient();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Referral.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    gov.ca.cwds.rest.api.persistence.cms.Client persistent =
        new gov.ca.cwds.rest.api.persistence.cms.Client(validClient(), "2016-12-06");

    Client totest = new Client(persistent);
    assertThat(totest.getAdjudicatedDelinquentIndicator(), is(
        equalTo(DomainObject.uncookBooleanString(persistent.getAdjudicatedDelinquentIndicator()))));
    assertThat(totest.getAdoptionStatusCode(), is(equalTo(persistent.getAdoptionStatusCode())));
    assertThat(totest.getAlienRegistrationNumber(),
        is(equalTo(persistent.getAlienRegistrationNumber())));
    assertThat(totest.getBirthCity(), is(equalTo(persistent.getBirthCity())));
    assertThat(totest.getBirthCountryCodeType(), is(equalTo(persistent.getBirthCountryCodeType())));
    assertThat(totest.getBirthDate(), is(equalTo(df.format(persistent.getBirthDate()))));
    assertThat(totest.getBirthFacilityName(), is(equalTo(persistent.getBirthFacilityName())));
    assertThat(totest.getBirthplaceVerifiedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getBirthplaceVerifiedIndicator()))));
    assertThat(totest.getBirthStateCodeType(), is(equalTo(persistent.getBirthStateCodeType())));
    assertThat(totest.getChildClientIndicatorVar(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getChildClientIndicatorVar()))));
    assertThat(totest.getClientIndexNumber(), is(equalTo(persistent.getClientIndexNumber())));
    assertThat(totest.getCommentDescription(), is(equalTo(persistent.getCommentDescription())));
    assertThat(totest.getCommonFirstName(), is(equalTo(persistent.getCommonFirstName())));
    assertThat(totest.getCommonLastName(), is(equalTo(persistent.getCommonLastName())));
    assertThat(totest.getCommonMiddleName(), is(equalTo(persistent.getCommonMiddleName())));
    assertThat(totest.getConfidentialityActionDate(),
        is(equalTo(df.format(persistent.getConfidentialityActionDate()))));
    assertThat(totest.getConfidentialityInEffectIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getConfidentialityInEffectIndicator()))));
    assertThat(totest.getCreationDate(), is(equalTo(df.format(persistent.getCreationDate()))));
    assertThat(totest.getCurrCaChildrenServIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getCurrCaChildrenServIndicator()))));
    assertThat(totest.getCurrentlyOtherDescription(),
        is(equalTo(persistent.getCurrentlyOtherDescription())));
    assertThat(totest.getCurrentlyRegionalCenterIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getCurrentlyRegionalCenterIndicator()))));
    assertThat(totest.getDeathDate(), is(equalTo(df.format(persistent.getDeathDate()))));
    assertThat(totest.getDeathDateVerifiedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getDeathDateVerifiedIndicator()))));
    assertThat(totest.getDeathReasonText(), is(equalTo(persistent.getDeathReasonText())));
    assertThat(totest.getDriverLicenseNumber(), is(equalTo(persistent.getDriverLicenseNumber())));
    assertThat(totest.getDriverLicenseStateCodeType(),
        is(equalTo(persistent.getDriverLicenseStateCodeType())));
    assertThat(totest.getEmailAddress(), is(equalTo(persistent.getEmailAddress())));
    assertThat(totest.getEstimatedDobCode(), is(equalTo(persistent.getEstimatedDobCode())));
    assertThat(totest.getEthUnableToDetReasonCode(),
        is(equalTo(persistent.getEthUnableToDetReasonCode())));
    assertThat(totest.getFatherParentalRightTermDate(),
        is(equalTo(df.format(persistent.getFatherParentalRightTermDate()))));
    assertThat(totest.getGenderCode(), is(equalTo(persistent.getGenderCode())));
    assertThat(totest.getHealthSummaryText(), is(equalTo(persistent.getHealthSummaryText())));
    assertThat(totest.getHispUnableToDetReasonCode(),
        is(equalTo(persistent.getHispUnableToDetReasonCode())));
    assertThat(totest.getHispanicOriginCode(), is(equalTo(persistent.getHispanicOriginCode())));
    assertThat(totest.getImmigrationCountryCodeType(),
        is(equalTo(persistent.getImmigrationCountryCodeType())));
    assertThat(totest.getImmigrationStatusType(),
        is(equalTo(persistent.getImmigrationStatusType())));
    assertThat(totest.getIncapacitatedParentCode(),
        is(equalTo(persistent.getIncapacitatedParentCode())));
    assertThat(totest.getIndividualHealthCarePlanIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getIndividualHealthCarePlanIndicator()))));
    assertThat(totest.getLimitationOnScpHealthIndicator(), is(
        equalTo(DomainObject.uncookBooleanString(persistent.getLimitationOnScpHealthIndicator()))));
    assertThat(totest.getLiterateCode(), is(equalTo(persistent.getLiterateCode())));
    assertThat(totest.getMaritalCohabitatnHstryIndicatorVar(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getMaritalCohabitatnHstryIndicatorVar()))));
    assertThat(totest.getMaritalStatusType(), is(equalTo(persistent.getMaritalStatusType())));
    assertThat(totest.getMilitaryStatusCode(), is(equalTo(persistent.getMilitaryStatusCode())));
    assertThat(totest.getMotherParentalRightTermDate(),
        is(equalTo(df.format(persistent.getMotherParentalRightTermDate()))));
    assertThat(totest.getNamePrefixDescription(),
        is(equalTo(persistent.getNamePrefixDescription())));
    assertThat(totest.getNameType(), is(equalTo(persistent.getNameType())));
    assertThat(totest.getOutstandingWarrantIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getOutstandingWarrantIndicator()))));
    assertThat(totest.getPrevCaChildrenServIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getPrevCaChildrenServIndicator()))));
    assertThat(totest.getPrevRegionalCenterIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getPrevRegionalCenterIndicator()))));
    assertThat(totest.getPrevOtherDescription(), is(equalTo(persistent.getPrevOtherDescription())));
    assertThat(totest.getPrimaryEthnicityType(), is(equalTo(persistent.getPrimaryEthnicityType())));
    assertThat(totest.getPrimaryLanguageType(), is(equalTo(persistent.getPrimaryLanguageType())));
    assertThat(totest.getReligionType(), is(equalTo(persistent.getReligionType())));
    assertThat(totest.getSecondaryLanguageType(),
        is(equalTo(persistent.getSecondaryLanguageType())));
    assertThat(totest.getSensitiveHlthInfoOnFileIndicator(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getSensitiveHlthInfoOnFileIndicator()))));
    assertThat(totest.getSensitivityIndicator(), is(equalTo(persistent.getSensitivityIndicator())));
    assertThat(totest.getSoc158PlacementCode(), is(equalTo(persistent.getSoc158PlacementCode())));
    assertThat(totest.getSoc158SealedClientIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getSoc158SealedClientIndicator()))));
    assertThat(totest.getSocialSecurityNumChangedCode(),
        is(equalTo(persistent.getSocialSecurityNumChangedCode())));
    assertThat(totest.getSocialSecurityNumber(), is(equalTo(persistent.getSocialSecurityNumber())));
    assertThat(totest.getSuffixTitleDescription(),
        is(equalTo(persistent.getSuffixTitleDescription())));
    assertThat(totest.getTribalAncestryClientIndicatorVar(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getTribalAncestryClientIndicatorVar()))));
    assertThat(totest.getTribalMembrshpVerifctnIndicatorVar(), is(equalTo(
        DomainObject.uncookBooleanString(persistent.getTribalMembrshpVerifctnIndicatorVar()))));
    assertThat(totest.getUnemployedParentCode(), is(equalTo(persistent.getUnemployedParentCode())));
    assertThat(totest.getZippyCreatedIndicator(),
        is(equalTo(DomainObject.uncookBooleanString(persistent.getZippyCreatedIndicator()))));

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Client vc = validClient();

    Client totest = new Client(vc.getAdjudicatedDelinquentIndicator(), vc.getAdoptionStatusCode(),
        vc.getAlienRegistrationNumber(), vc.getBirthCity(), vc.getBirthCountryCodeType(),
        vc.getBirthDate(), vc.getBirthFacilityName(), vc.getBirthStateCodeType(),
        vc.getBirthplaceVerifiedIndicator(), vc.getChildClientIndicatorVar(),
        vc.getClientIndexNumber(), vc.getCommentDescription(), vc.getCommonFirstName(),
        vc.getCommonLastName(), vc.getCommonMiddleName(), vc.getConfidentialityActionDate(),
        vc.getConfidentialityInEffectIndicator(), vc.getCreationDate(),
        vc.getCurrCaChildrenServIndicator(), vc.getCurrentlyOtherDescription(),
        vc.getCurrentlyRegionalCenterIndicator(), vc.getDeathDate(),
        vc.getDeathDateVerifiedIndicator(), vc.getDeathPlace(), vc.getDeathReasonText(),
        vc.getDriverLicenseNumber(), vc.getDriverLicenseStateCodeType(), vc.getEmailAddress(),
        vc.getEstimatedDobCode(), vc.getEthUnableToDetReasonCode(),
        vc.getFatherParentalRightTermDate(), vc.getGenderCode(), vc.getHealthSummaryText(),
        vc.getHispUnableToDetReasonCode(), vc.getHispanicOriginCode(), vc.getId(),
        vc.getImmigrationCountryCodeType(), vc.getImmigrationStatusType(),
        vc.getIncapacitatedParentCode(), vc.getIndividualHealthCarePlanIndicator(),
        vc.getLimitationOnScpHealthIndicator(), vc.getLiterateCode(),
        vc.getMaritalCohabitatnHstryIndicatorVar(), vc.getMaritalStatusType(),
        vc.getMilitaryStatusCode(), vc.getMotherParentalRightTermDate(),
        vc.getNamePrefixDescription(), vc.getNameType(), vc.getOutstandingWarrantIndicator(),
        vc.getPrevCaChildrenServIndicator(), vc.getPrevOtherDescription(),
        vc.getPrevRegionalCenterIndicator(), vc.getPrimaryEthnicityType(),
        vc.getPrimaryLanguageType(), vc.getReligionType(), vc.getSecondaryLanguageType(),
        vc.getSensitiveHlthInfoOnFileIndicator(), vc.getSensitivityIndicator(),
        vc.getSoc158PlacementCode(), vc.getSoc158SealedClientIndicator(),
        vc.getSocialSecurityNumChangedCode(), vc.getSocialSecurityNumber(),
        vc.getSuffixTitleDescription(), vc.getTribalAncestryClientIndicatorVar(),
        vc.getTribalMembrshpVerifctnIndicatorVar(), vc.getUnemployedParentCode(),
        vc.getZippyCreatedIndicator());

    assertThat(totest.getAdjudicatedDelinquentIndicator(),
        is(equalTo((vc.getAdjudicatedDelinquentIndicator()))));
    assertThat(totest.getAdoptionStatusCode(), is(equalTo(vc.getAdoptionStatusCode())));
    assertThat(totest.getAlienRegistrationNumber(), is(equalTo(vc.getAlienRegistrationNumber())));
    assertThat(totest.getBirthCity(), is(equalTo(vc.getBirthCity())));
    assertThat(totest.getBirthCountryCodeType(), is(equalTo(vc.getBirthCountryCodeType())));
    assertThat(totest.getBirthDate(), is(equalTo(vc.getBirthDate())));
    assertThat(totest.getBirthFacilityName(), is(equalTo(vc.getBirthFacilityName())));
    assertThat(totest.getBirthplaceVerifiedIndicator(),
        is(equalTo(vc.getBirthplaceVerifiedIndicator())));
    assertThat(totest.getBirthStateCodeType(), is(equalTo(vc.getBirthStateCodeType())));
    assertThat(totest.getChildClientIndicatorVar(), is(equalTo(vc.getChildClientIndicatorVar())));
    assertThat(totest.getClientIndexNumber(), is(equalTo(vc.getClientIndexNumber())));
    assertThat(totest.getCommentDescription(), is(equalTo(vc.getCommentDescription())));
    assertThat(totest.getCommonFirstName(), is(equalTo(vc.getCommonFirstName())));
    assertThat(totest.getCommonLastName(), is(equalTo(vc.getCommonLastName())));
    assertThat(totest.getCommonMiddleName(), is(equalTo(vc.getCommonMiddleName())));
    assertThat(totest.getConfidentialityActionDate(),
        is(equalTo(vc.getConfidentialityActionDate())));
    assertThat(totest.getConfidentialityInEffectIndicator(),
        is(equalTo(vc.getConfidentialityInEffectIndicator())));
    assertThat(totest.getCreationDate(), is(equalTo(vc.getCreationDate())));
    assertThat(totest.getCurrCaChildrenServIndicator(),
        is(equalTo(vc.getCurrCaChildrenServIndicator())));
    assertThat(totest.getCurrentlyOtherDescription(),
        is(equalTo(vc.getCurrentlyOtherDescription())));
    assertThat(totest.getCurrentlyRegionalCenterIndicator(),
        is(equalTo(vc.getCurrentlyRegionalCenterIndicator())));
    assertThat(totest.getDeathDate(), is(equalTo(vc.getDeathDate())));
    assertThat(totest.getDeathDateVerifiedIndicator(),
        is(equalTo(vc.getDeathDateVerifiedIndicator())));
    assertThat(totest.getDeathReasonText(), is(equalTo(vc.getDeathReasonText())));
    assertThat(totest.getDriverLicenseNumber(), is(equalTo(vc.getDriverLicenseNumber())));
    assertThat(totest.getDriverLicenseStateCodeType(),
        is(equalTo(vc.getDriverLicenseStateCodeType())));
    assertThat(totest.getEmailAddress(), is(equalTo(vc.getEmailAddress())));
    assertThat(totest.getEstimatedDobCode(), is(equalTo(vc.getEstimatedDobCode())));
    assertThat(totest.getEthUnableToDetReasonCode(), is(equalTo(vc.getEthUnableToDetReasonCode())));
    assertThat(totest.getFatherParentalRightTermDate(),
        is(equalTo(vc.getFatherParentalRightTermDate())));
    assertThat(totest.getGenderCode(), is(equalTo(vc.getGenderCode())));
    assertThat(totest.getHealthSummaryText(), is(equalTo(vc.getHealthSummaryText())));
    assertThat(totest.getHispUnableToDetReasonCode(),
        is(equalTo(vc.getHispUnableToDetReasonCode())));
    assertThat(totest.getHispanicOriginCode(), is(equalTo(vc.getHispanicOriginCode())));
    assertThat(totest.getImmigrationCountryCodeType(),
        is(equalTo(vc.getImmigrationCountryCodeType())));
    assertThat(totest.getImmigrationStatusType(), is(equalTo(vc.getImmigrationStatusType())));
    assertThat(totest.getIncapacitatedParentCode(), is(equalTo(vc.getIncapacitatedParentCode())));
    assertThat(totest.getIndividualHealthCarePlanIndicator(),
        is(equalTo(vc.getIndividualHealthCarePlanIndicator())));
    assertThat(totest.getLimitationOnScpHealthIndicator(),
        is(equalTo(vc.getLimitationOnScpHealthIndicator())));
    assertThat(totest.getLiterateCode(), is(equalTo(vc.getLiterateCode())));
    assertThat(totest.getMaritalCohabitatnHstryIndicatorVar(),
        is(equalTo(vc.getMaritalCohabitatnHstryIndicatorVar())));
    assertThat(totest.getMaritalStatusType(), is(equalTo(vc.getMaritalStatusType())));
    assertThat(totest.getMilitaryStatusCode(), is(equalTo(vc.getMilitaryStatusCode())));
    assertThat(totest.getMotherParentalRightTermDate(),
        is(equalTo(vc.getMotherParentalRightTermDate())));
    assertThat(totest.getNamePrefixDescription(), is(equalTo(vc.getNamePrefixDescription())));
    assertThat(totest.getNameType(), is(equalTo(vc.getNameType())));
    assertThat(totest.getOutstandingWarrantIndicator(),
        is(equalTo(vc.getOutstandingWarrantIndicator())));
    assertThat(totest.getPrevCaChildrenServIndicator(),
        is(equalTo(vc.getPrevCaChildrenServIndicator())));
    assertThat(totest.getPrevRegionalCenterIndicator(),
        is(equalTo(vc.getPrevRegionalCenterIndicator())));
    assertThat(totest.getPrevOtherDescription(), is(equalTo(vc.getPrevOtherDescription())));
    assertThat(totest.getPrimaryEthnicityType(), is(equalTo(vc.getPrimaryEthnicityType())));
    assertThat(totest.getPrimaryLanguageType(), is(equalTo(vc.getPrimaryLanguageType())));
    assertThat(totest.getReligionType(), is(equalTo(vc.getReligionType())));
    assertThat(totest.getSecondaryLanguageType(), is(equalTo(vc.getSecondaryLanguageType())));
    assertThat(totest.getSensitiveHlthInfoOnFileIndicator(),
        is(equalTo(vc.getSensitiveHlthInfoOnFileIndicator())));
    assertThat(totest.getSensitivityIndicator(), is(equalTo(vc.getSensitivityIndicator())));
    assertThat(totest.getSoc158PlacementCode(), is(equalTo(vc.getSoc158PlacementCode())));
    assertThat(totest.getSoc158SealedClientIndicator(),
        is(equalTo(vc.getSoc158SealedClientIndicator())));
    assertThat(totest.getSocialSecurityNumChangedCode(),
        is(equalTo(vc.getSocialSecurityNumChangedCode())));
    assertThat(totest.getSocialSecurityNumber(), is(equalTo(vc.getSocialSecurityNumber())));
    assertThat(totest.getSuffixTitleDescription(), is(equalTo(vc.getSuffixTitleDescription())));
    assertThat(totest.getTribalAncestryClientIndicatorVar(),
        is(equalTo(vc.getTribalAncestryClientIndicatorVar())));
    assertThat(totest.getTribalMembrshpVerifctnIndicatorVar(),
        is(equalTo(vc.getTribalMembrshpVerifctnIndicatorVar())));
    assertThat(totest.getUnemployedParentCode(), is(equalTo(vc.getUnemployedParentCode())));
    assertThat(totest.getZippyCreatedIndicator(), is(equalTo(vc.getZippyCreatedIndicator())));

  }


  private Client validClient() throws Exception {

    Client validClient =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Client/valid/valid.json"), Client.class);

    return validClient;

  }
}
