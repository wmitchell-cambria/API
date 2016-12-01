package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Client
 * 
 * @author CWDS API Team
 */
@ApiModel
public class Client extends DomainObject {
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean adjudicatedDelinquentIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String adoptionStatusCode;

  @NotEmpty
  @Size(max = 12)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789012")
  private String alienRegistrationNumber;

  @NotNull
  @Size(max = 56)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Fresno")
  private String birthCity;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short birthCountryCodeType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "birthDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String birthDate;

  @NotNull
  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "facility name")
  private String birthFacilityName;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short birthStateCodeType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean birthplaceVerifiedIndicator;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean childClientIndicatorVar;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean clientIndexNumber;

  @NotNull
  @Size(max = 120)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "a description")
  private String commentDescription;

  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "first name")
  private String commonFirstName;

  @NotEmpty
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "last name")
  private String commonLastName;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "middle name")
  private String commonMiddleName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "confidentialityActionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String confidentialityActionDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean confidentialityInEffectIndicator;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "creationDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2016-01-01")
  private String creationDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean currCaChildrenServIndicator;

  @NotNull
  @Size(max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "other description")
  private String currentlyOtherDescription;

  @NotEmpty
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean currentlyRegionalCenterIndicator;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "deathDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String deathDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean deathDateVerifiedIndicator;

  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "some location")
  private String deathPlace;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "reason text")
  private String deathReasonText;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "CA1234567891234567")
  private String driverLicenseNumber;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1828")
  private Short driverLicenseStateCodeType;

  @Size(max = 50)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "abc@def.com")
  private String emailAddress;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Y")
  private String estimatedDobCode;

  @Size(max = 1)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "K")
  private String ethUnableToDetReasonCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "fatherParentalRightTermDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String fatherParentalRightTermDate;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String genderCode;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "summary text")
  private String healthSummaryText;

  @Size(max = 1)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String hispUnableToDetReasonCode;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "X")
  private String hispanicOriginCode;

  @NotEmpty
  @Size(min = 10, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC1234567")
  private String id;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short immigrationCountryCodeType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1199")
  private Short immigrationStatusType;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String incapacitatedParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean individualHealthCarePlanIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean limitationOnScpHealthIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String literateCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean maritalCohabitatnHstryIndicatorVar;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "0")
  private Short maritalStatusType;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String militaryStatusCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "motherParentalRightTermDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2016-01-01")
  private String motherParentalRightTermDate;

  @NotNull
  @Size(max = 6)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = " ")
  private String namePrefixDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short nameType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean outstandingWarrantIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean prevCaChildrenServIndicator;

  @NotEmpty
  @Size(max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "other description")
  private String prevOtherDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean prevRegionalCenterIndicator;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "839")
  private Short primaryEthnicityType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1253")
  private Short primaryLanguageType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short religionType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1255")
  private Short secondaryLanguageType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean sensitiveHlthInfoOnFileIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean sensitivityIndicatoricator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String soc158PlacementCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean soc158SealedClientIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private String socialSecurityNumChangedCode;

  @NotEmpty
  @Size(max = 9)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789")
  private String socialSecurityNumber;

  @NotEmpty
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A1")
  private String suffixTitleDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalAncestryClientIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalMembrshpVerifctnIndicatorVar;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String unemployedParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;


  @JsonCreator
  public Client(
      @JsonProperty("adjudicatedDelinquentIndicator") Boolean adjudicatedDelinquentIndicator,
      @JsonProperty("adoptionStatusCode") String adoptionStatusCode,
      @JsonProperty("alienRegistrationNumber") String alienRegistrationNumber,
      @JsonProperty("birthCity") String birthCity,
      @JsonProperty("birthCountryCodeType") Short birthCountryCodeType,
      @JsonProperty("birthDate") String birthDate,
      @JsonProperty("birthFacilityName") String birthFacilityName,
      @JsonProperty("birthStateCodeType") Short birthStateCodeType,
      @JsonProperty("birthplaceVerifiedIndicator") Boolean birthplaceVerifiedIndicator,
      @JsonProperty("childClientIndicatorVar") Boolean childClientIndicatorVar,
      @JsonProperty("clientIndexNumber") Boolean clientIndexNumber,
      @JsonProperty("commentDescription") String commentDescription,
      @JsonProperty("commonFirstName") String commonFirstName,
      @JsonProperty("commonLastName") String commonLastName,
      @JsonProperty("commonMiddleName") String commonMiddleName,
      @JsonProperty("confidentialityActionDate") String confidentialityActionDate,
      @JsonProperty("confidentialityInEffectIndicator") Boolean confidentialityInEffectIndicator,
      @JsonProperty("creationDate") String creationDate,
      @JsonProperty("currCaChildrenServIndicator") Boolean currCaChildrenServIndicator,
      @JsonProperty("currentlyOtherDescription") String currentlyOtherDescription,
      @JsonProperty("currentlyRegionalCenterIndicator") Boolean currentlyRegionalCenterIndicator,
      @JsonProperty("deathDate") String deathDate,
      @JsonProperty("deathDateVerifiedIndicator") Boolean deathDateVerifiedIndicator,
      @JsonProperty("deathPlace") String deathPlace,
      @JsonProperty("deathReasonText") String deathReasonText,
      @JsonProperty("driverLicenseNumber") String driverLicenseNumber,
      @JsonProperty("driverLicenseStateCodeType") Short driverLicenseStateCodeType,
      @JsonProperty("emailAddress") String emailAddress,
      @JsonProperty("estimatedDobCode") String estimatedDobCode,
      @JsonProperty("ethUnableToDetReasonCode") String ethUnableToDetReasonCode,
      @JsonProperty("fatherParentalRightTermDate") String fatherParentalRightTermDate,
      @JsonProperty("genderCode") String genderCode,
      @JsonProperty("healthSummaryText") String healthSummaryText,
      @JsonProperty("hispUnableToDetReasonCode") String hispUnableToDetReasonCode,
      @JsonProperty("hispanicOriginCode") String hispanicOriginCode, @JsonProperty("id") String id,
      @JsonProperty("immigrationCountryCodeType") Short immigrationCountryCodeType,
      @JsonProperty("immigrationStatusType") Short immigrationStatusType,
      @JsonProperty("incapacitatedParentCode") String incapacitatedParentCode,
      @JsonProperty("individualHealthCarePlanIndicator") Boolean individualHealthCarePlanIndicator,
      @JsonProperty("limitationOnScpHealthIndicator") Boolean limitationOnScpHealthIndicator,
      @JsonProperty("literateCode") String literateCode,
      @JsonProperty("maritalCohabitatnHstryIndicatorVar") Boolean maritalCohabitatnHstryIndicatorVar,
      @JsonProperty("maritalStatusType") Short maritalStatusType,
      @JsonProperty("militaryStatusCode") String militaryStatusCode,
      @JsonProperty("motherParentalRightTermDate") String motherParentalRightTermDate,
      @JsonProperty("namePrefixDescription") String namePrefixDescription,
      @JsonProperty("nameType") Short nameType,
      @JsonProperty("outstandingWarrantIndicator") Boolean outstandingWarrantIndicator,
      @JsonProperty("prevCaChildrenServIndicator") Boolean prevCaChildrenServIndicator,
      @JsonProperty("prevOtherDescription") String prevOtherDescription,
      @JsonProperty("prevRegionalCenterIndicator") Boolean prevRegionalCenterIndicator,
      @JsonProperty("primaryEthnicityType") Short primaryEthnicityType,
      @JsonProperty("primaryLanguageType") Short primaryLanguageType,
      @JsonProperty("religionType") Short religionType,
      @JsonProperty("secondaryLanguageType") Short secondaryLanguageType,
      @JsonProperty("sensitiveHlthInfoOnFileIndicator") Boolean sensitiveHlthInfoOnFileIndicator,
      @JsonProperty("sensitivityIndicatoricator") Boolean sensitivityIndicatoricator,
      @JsonProperty("soc158PlacementCode") String soc158PlacementCode,
      @JsonProperty("soc158SealedClientIndicator") Boolean soc158SealedClientIndicator,
      @JsonProperty("socialSecurityNumChangedCode") String socialSecurityNumChangedCode,
      @JsonProperty("socialSecurityNumber") String socialSecurityNumber,
      @JsonProperty("suffixTitleDescription") String suffixTitleDescription,
      @JsonProperty("tribalAncestryClientIndicatorVar") Boolean tribalAncestryClientIndicatorVar,
      @JsonProperty("tribalMembrshpVerifctnIndicatorVar") Boolean tribalMembrshpVerifctnIndicatorVar,
      @JsonProperty("unemployedParentCode") String unemployedParentCode,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator) {
    super();
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
    this.adoptionStatusCode = adoptionStatusCode;
    this.alienRegistrationNumber = alienRegistrationNumber;
    this.birthCity = birthCity;
    this.birthCountryCodeType = birthCountryCodeType;
    this.birthDate = birthDate;
    this.birthFacilityName = birthFacilityName;
    this.birthStateCodeType = birthStateCodeType;
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
    this.childClientIndicatorVar = childClientIndicatorVar;
    this.clientIndexNumber = clientIndexNumber;
    this.commentDescription = commentDescription;
    this.commonFirstName = commonFirstName;
    this.commonLastName = commonLastName;
    this.commonMiddleName = commonMiddleName;
    this.confidentialityActionDate = confidentialityActionDate;
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
    this.creationDate = creationDate;
    this.currCaChildrenServIndicator = currCaChildrenServIndicator;
    this.currentlyOtherDescription = currentlyOtherDescription;
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
    this.deathDate = deathDate;
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
    this.deathPlace = deathPlace;
    this.deathReasonText = deathReasonText;
    this.driverLicenseNumber = driverLicenseNumber;
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
    this.emailAddress = emailAddress;
    this.estimatedDobCode = estimatedDobCode;
    this.ethUnableToDetReasonCode = ethUnableToDetReasonCode;
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
    this.genderCode = genderCode;
    this.healthSummaryText = healthSummaryText;
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.id = id;
    this.immigrationCountryCodeType = immigrationCountryCodeType;
    this.immigrationStatusType = immigrationStatusType;
    this.incapacitatedParentCode = incapacitatedParentCode;
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
    this.literateCode = literateCode;
    this.maritalCohabitatnHstryIndicatorVar = maritalCohabitatnHstryIndicatorVar;
    this.maritalStatusType = maritalStatusType;
    this.militaryStatusCode = militaryStatusCode;
    this.motherParentalRightTermDate = motherParentalRightTermDate;
    this.namePrefixDescription = namePrefixDescription;
    this.nameType = nameType;
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
    this.prevCaChildrenServIndicator = prevCaChildrenServIndicator;
    this.prevOtherDescription = prevOtherDescription;
    this.prevRegionalCenterIndicator = prevRegionalCenterIndicator;
    this.primaryEthnicityType = primaryEthnicityType;
    this.primaryLanguageType = primaryLanguageType;
    this.religionType = religionType;
    this.secondaryLanguageType = secondaryLanguageType;
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    this.sensitivityIndicatoricator = sensitivityIndicatoricator;
    this.soc158PlacementCode = soc158PlacementCode;
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    this.socialSecurityNumber = socialSecurityNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    this.unemployedParentCode = unemployedParentCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  public Client(gov.ca.cwds.rest.api.persistence.cms.Client persistedClient) {
    this.adjudicatedDelinquentIndicator =
        DomainObject.uncookBooleanString(persistedClient.getAdjudicatedDelinquentIndicator());
    this.adoptionStatusCode = persistedClient.getAdoptionStatusCode();
    this.alienRegistrationNumber = persistedClient.getAlienRegistrationNumber();
    this.birthCity = persistedClient.getBirthCity();
    this.birthCountryCodeType = persistedClient.getBirthCountryCodeType();
    this.birthDate = DomainObject.cookDate(persistedClient.getBirthDate());
    this.birthFacilityName = persistedClient.getBirthFacilityName();
    this.birthStateCodeType = persistedClient.getBirthStateCodeType();
    this.birthplaceVerifiedIndicator =
        DomainObject.uncookBooleanString(persistedClient.getBirthplaceVerifiedIndicator());
    this.childClientIndicatorVar =
        DomainObject.uncookBooleanString(persistedClient.getChildClientIndicatorVar());
    this.clientIndexNumber = DomainObject.uncookBooleanString(persistedClient.clientIndexNumber());
    this.commentDescription = persistedClient.getCommentDescription();
    this.commonFirstName = persistedClient.getCommonFirstName();
    this.commonLastName = persistedClient.getCommonLastName();
    this.commonMiddleName = persistedClient.getCommonMiddleName();
    this.confidentialityActionDate =
        DomainObject.cookDate(persistedClient.getConfidentialityActionDate());
    this.confidentialityInEffectIndicator =
        DomainObject.uncookBooleanString(persistedClient.getConfidentialityInEffectIndicator());
    this.creationDate = DomainObject.cookDate(persistedClient.getCreationDate());
    this.currCaChildrenServIndicator =
        DomainObject.uncookBooleanString(persistedClient.getCurrCaChildrenServIndicator());
    this.currentlyOtherDescription = persistedClient.getCurrentlyOtherDescription();
    this.currentlyRegionalCenterIndicator =
        DomainObject.uncookBooleanString(persistedClient.getCurrentlyRegionalCenterIndicator());
    this.deathDate = DomainObject.cookDate(persistedClient.getDeathDate());
    this.deathDateVerifiedIndicator =
        DomainObject.uncookBooleanString(persistedClient.getDeathDateVerifiedIndicator());
    this.deathPlace = persistedClient.getDeathPlace();
    this.deathReasonText = persistedClient.getDeathReasonText();
    this.driverLicenseNumber = persistedClient.getDriverLicenseNumber();
    this.driverLicenseStateCodeType = persistedClient.getDriverLicenseStateCodeType();
    this.emailAddress = persistedClient.getEmailAddress();
    this.estimatedDobCode = persistedClient.getEstimatedDobCode();
    this.ethUnableToDetReasonCode = persistedClient.getEthUnableToDetReasonCode();
    this.fatherParentalRightTermDate =
        DomainObject.cookDate(persistedClient.getFatherParentalRightTermDate());
    this.genderCode = persistedClient.getGenderCode();
    this.healthSummaryText = persistedClient.getHealthSummaryText();
    this.hispUnableToDetReasonCode = persistedClient.getHispUnableToDetReasonCode();
    this.hispanicOriginCode = persistedClient.getHispanicOriginCode();
    this.id = persistedClient.getId();
    this.immigrationCountryCodeType = persistedClient.getImmigrationCountryCodeType();
    this.immigrationStatusType = persistedClient.getImmigrationStatusType();
    this.incapacitatedParentCode = persistedClient.getIncapacitatedParentCode();
    this.individualHealthCarePlanIndicator =
        DomainObject.uncookBooleanString(persistedClient.getIndividualHealthCarePlanIndicator());
    this.limitationOnScpHealthIndicator =
        DomainObject.uncookBooleanString(persistedClient.getLimitationOnScpHealthIndicator());
    this.literateCode = persistedClient.getLiterateCode();
    this.maritalCohabitatnHstryIndicatorVar =
        DomainObject.uncookBooleanString(persistedClient.getMaritalCohabitatnHstryIndicatorVar());
    this.maritalStatusType = persistedClient.getMaritalStatusType();
    this.militaryStatusCode = persistedClient.getMilitaryStatusCode();
    this.motherParentalRightTermDate =
        DomainObject.cookDate(persistedClient.getMotherParentalRightTermDate());
    this.namePrefixDescription = persistedClient.getNamePrefixDescription();
    this.nameType = persistedClient.getNameType();
    this.outstandingWarrantIndicator =
        DomainObject.uncookBooleanString(persistedClient.getOutstandingWarrantIndicator());
    this.prevCaChildrenServIndicator =
        DomainObject.uncookBooleanString(persistedClient.getPrevCaChildrenServIndicator());
    this.prevOtherDescription = persistedClient.getPrevOtherDescription();
    this.prevRegionalCenterIndicator =
        DomainObject.uncookBooleanString(persistedClient.getPrevRegionalCenterIndicator());
    this.primaryEthnicityType = persistedClient.getPrimaryEthnicityType();
    this.primaryLanguageType = persistedClient.getPrimaryLanguageType();
    this.religionType = persistedClient.getReligionType();
    this.secondaryLanguageType = persistedClient.getSecondaryLanguageType();
    this.sensitiveHlthInfoOnFileIndicator =
        DomainObject.uncookBooleanString(persistedClient.getSensitiveHlthInfoOnFileIndicator());
    this.sensitivityIndicatoricator =
        DomainObject.uncookBooleanString(persistedClient.getSensitivityIndicatoricator());
    this.soc158PlacementCode = persistedClient.getSoc158PlacementCode();
    this.soc158SealedClientIndicator =
        DomainObject.uncookBooleanString(persistedClient.getSoc158SealedClientIndicator());
    this.socialSecurityNumChangedCode = persistedClient.getSocialSecurityNumChangedCode();
    this.socialSecurityNumber = persistedClient.getSocialSecurityNumber();
    this.suffixTitleDescription = persistedClient.getSuffixTitleDescription();
    this.tribalAncestryClientIndicatorVar =
        DomainObject.uncookBooleanString(persistedClient.getTribalAncestryClientIndicatorVar());
    this.tribalMembrshpVerifctnIndicatorVar =
        DomainObject.uncookBooleanString(persistedClient.getTribalMembrshpVerifctnIndicatorVar());
    this.unemployedParentCode = persistedClient.getUnemployedParentCode();
    this.zippyCreatedIndicator =
        DomainObject.uncookBooleanString(persistedClient.getZippyCreatedIndicator());
  }

  /**
   * @return the adjudicatedDelinquentIndicator
   */
  public Boolean getAdjudicatedDelinquentIndicator() {
    return adjudicatedDelinquentIndicator;
  }

  /**
   * @return the adoptionStatusCode
   */
  public String getAdoptionStatusCode() {
    return adoptionStatusCode;
  }

  /**
   * @return the alienRegistrationNumber
   */
  public String getAlienRegistrationNumber() {
    return alienRegistrationNumber;
  }

  /**
   * @return the birthCity
   */
  public String getBirthCity() {
    return birthCity;
  }

  /**
   * @return the birthCountryCodeType
   */
  public Short getBirthCountryCodeType() {
    return birthCountryCodeType;
  }

  /**
   * @return the birthDate
   */
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * @return the birthFacilityName
   */
  public String getBirthFacilityName() {
    return birthFacilityName;
  }

  /**
   * @return the birthStateCodeType
   */
  public Short getBirthStateCodeType() {
    return birthStateCodeType;
  }

  /**
   * @return the birthplaceVerifiedIndicator
   */
  public Boolean getBirthplaceVerifiedIndicator() {
    return birthplaceVerifiedIndicator;
  }

  /**
   * @return the childClientIndicatorVar
   */
  public Boolean getChildClientIndicatorVar() {
    return childClientIndicatorVar;
  }

  /**
   * @return the clientIndexNumber
   */
  public Boolean clientIndexNumber() {
    return clientIndexNumber;
  }

  /**
   * @return the commentDescription
   */
  public String getCommentDescription() {
    return commentDescription;
  }

  /**
   * @return the commonFirstName
   */
  public String getCommonFirstName() {
    return commonFirstName;
  }

  /**
   * @return the commonLastName
   */
  public String getCommonLastName() {
    return commonLastName;
  }

  /**
   * @return the commonMiddleName
   */
  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  /**
   * @return the confidentialityActionDate
   */
  public String getConfidentialityActionDate() {
    return confidentialityActionDate;
  }

  /**
   * @return the confidentialityInEffectIndicator
   */
  public Boolean getConfidentialityInEffectIndicator() {
    return confidentialityInEffectIndicator;
  }

  /**
   * @return the creationDate
   */
  public String getCreationDate() {
    return creationDate;
  }

  /**
   * @return the currCaChildrenServIndicator
   */
  public Boolean getCurrCaChildrenServIndicator() {
    return currCaChildrenServIndicator;
  }

  /**
   * @return the currentlyOtherDescription
   */
  public String getCurrentlyOtherDescription() {
    return currentlyOtherDescription;
  }

  /**
   * @return the currentlyRegionalCenterIndicator
   */
  public Boolean getCurrentlyRegionalCenterIndicator() {
    return currentlyRegionalCenterIndicator;
  }

  /**
   * @return the deathDate
   */
  public String getDeathDate() {
    return deathDate;
  }

  /**
   * @return the deathDateVerifiedIndicator
   */
  public Boolean getDeathDateVerifiedIndicator() {
    return deathDateVerifiedIndicator;
  }

  /**
   * @return the deathPlace
   */
  public String getDeathPlace() {
    return deathPlace;
  }

  /**
   * @return the deathReasonText
   */
  public String getDeathReasonText() {
    return deathReasonText;
  }

  /**
   * @return the driverLicenseNumber
   */
  public String getDriverLicenseNumber() {
    return driverLicenseNumber;
  }

  /**
   * @return the driverLicenseStateCodeType
   */
  public Short getDriverLicenseStateCodeType() {
    return driverLicenseStateCodeType;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return the estimatedDobCode
   */
  public String getEstimatedDobCode() {
    return estimatedDobCode;
  }

  /**
   * @return the ethUnableToDetReasonCode
   */
  public String getEthUnableToDetReasonCode() {
    return ethUnableToDetReasonCode;
  }

  /**
   * @return the fatherParentalRightTermDate
   */
  public String getFatherParentalRightTermDate() {
    return fatherParentalRightTermDate;
  }

  /**
   * @return the genderCode
   */
  public String getGenderCode() {
    return genderCode;
  }

  /**
   * @return the healthSummaryText
   */
  public String getHealthSummaryText() {
    return healthSummaryText;
  }

  /**
   * @return the hispUnableToDetReasonCode
   */
  public String getHispUnableToDetReasonCode() {
    return hispUnableToDetReasonCode;
  }

  /**
   * @return the hispanicOriginCode
   */
  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the immigrationCountryCodeType
   */
  public Short getImmigrationCountryCodeType() {
    return immigrationCountryCodeType;
  }

  /**
   * @return the immigrationStatusType
   */
  public Short getImmigrationStatusType() {
    return immigrationStatusType;
  }

  /**
   * @return the incapacitatedParentCode
   */
  public String getIncapacitatedParentCode() {
    return incapacitatedParentCode;
  }

  /**
   * @return the individualHealthCarePlanIndicator
   */
  public Boolean getIndividualHealthCarePlanIndicator() {
    return individualHealthCarePlanIndicator;
  }

  /**
   * @return the limitationOnScpHealthIndicator
   */
  public Boolean getLimitationOnScpHealthIndicator() {
    return limitationOnScpHealthIndicator;
  }

  /**
   * @return the literateCode
   */
  public String getLiterateCode() {
    return literateCode;
  }

  /**
   * @return the maritalCohabitatnHstryIndicatorVar
   */
  public Boolean getMaritalCohabitatnHstryIndicatorVar() {
    return maritalCohabitatnHstryIndicatorVar;
  }

  /**
   * @return the maritalStatusType
   */
  public Short getMaritalStatusType() {
    return maritalStatusType;
  }

  /**
   * @return the militaryStatusCode
   */
  public String getMilitaryStatusCode() {
    return militaryStatusCode;
  }

  /**
   * @return the motherParentalRightTermDate
   */
  public String getMotherParentalRightTermDate() {
    return motherParentalRightTermDate;
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  /**
   * @return the nameType
   */
  public Short getNameType() {
    return nameType;
  }

  /**
   * @return the outstandingWarrantIndicator
   */
  public Boolean getOutstandingWarrantIndicator() {
    return outstandingWarrantIndicator;
  }

  /**
   * @return the prevCaChildrenServIndicator
   */
  public Boolean getPrevCaChildrenServIndicator() {
    return prevCaChildrenServIndicator;
  }

  /**
   * @return the prevOtherDescription
   */
  public String getPrevOtherDescription() {
    return prevOtherDescription;
  }

  /**
   * @return the prevRegionalCenterIndicator
   */
  public Boolean getPrevRegionalCenterIndicator() {
    return prevRegionalCenterIndicator;
  }

  /**
   * @return the primaryEthnicityType
   */
  public Short getPrimaryEthnicityType() {
    return primaryEthnicityType;
  }

  /**
   * @return the primaryLanguageType
   */
  public Short getPrimaryLanguageType() {
    return primaryLanguageType;
  }

  /**
   * @return the religionType
   */
  public Short getReligionType() {
    return religionType;
  }

  /**
   * @return the secondaryLanguageType
   */
  public Short getSecondaryLanguageType() {
    return secondaryLanguageType;
  }

  /**
   * @return the sensitiveHlthInfoOnFileIndicator
   */
  public Boolean getSensitiveHlthInfoOnFileIndicator() {
    return sensitiveHlthInfoOnFileIndicator;
  }

  /**
   * @return the sensitivityIndicatoricator
   */
  public Boolean getSensitivityIndicatoricator() {
    return sensitivityIndicatoricator;
  }

  /**
   * @return the soc158PlacementCode
   */
  public String getSoc158PlacementCode() {
    return soc158PlacementCode;
  }

  /**
   * @return the soc158SealedClientIndicator
   */
  public Boolean getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  /**
   * @return the socialSecurityNumChangedCode
   */
  public String getSocialSecurityNumChangedCode() {
    return socialSecurityNumChangedCode;
  }

  /**
   * @return the socialSecurityNumber
   */
  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  /**
   * @return the tribalAncestryClientIndicatorVar
   */
  public Boolean getTribalAncestryClientIndicatorVar() {
    return tribalAncestryClientIndicatorVar;
  }

  /**
   * @return the tribalMembrshpVerifctnIndicatorVar
   */
  public Boolean getTribalMembrshpVerifctnIndicatorVar() {
    return tribalMembrshpVerifctnIndicatorVar;
  }

  /**
   * @return the unemployedParentCode
   */
  public String getUnemployedParentCode() {
    return unemployedParentCode;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }


  // <HashCode>
  // <Equals>
}
