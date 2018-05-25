package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.business.rules.R06998ZippyIndicator;
import gov.ca.cwds.rest.validation.AfterDateValid;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Client
 * 
 * @author CWDS API Team
 */
@ApiModel
@AfterDateValid.List({@AfterDateValid(ifProperty = "birthDate", thenProperty = "creationDate"),
    @AfterDateValid(ifProperty = "birthDate", thenProperty = "deathDate")})
public class Client extends ReportingDomain implements Request, Response {

  public static final String DEFAULT_ADOPTION_STATUS_CODE = "N";
  public static final short DEFAULT_CODE = 0;
  public static final Boolean DEFAULT_CHILD_CLIENT_INDICATOR = Boolean.FALSE;
  public static final String ESTIMATED_DOB_CODE_ACTUALLY_ENTERED = "N";
  public static final String ESTIMATED_DOB_CODE_NOT_PROVIDED = "U";
  public static final String DEFAULT_INCAPCITATED_PARENT_CODE = "U";
  public static final String DEFAULT_LITERATE_CODE = "U";
  public static final String DEFAULT_MILITARY_STATUS_CODE = "N";
  public static final short DEFAULT_NAME_TYPE = 1313;
  public static final short DEFAULT_SECONDARY_LANGUAGE_TYPE = 1253; // english
  public static final String DEFAULT_SENSITIVITY_INDICATOR = "N";
  public static final String DEFAULT_SOC158_PLACEMENT_CODE = "N";
  public static final String DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE = "N";
  public static final String DEFAULT_UNEMPLOYED_PARENT_CODE = "U";
  public static final short DEFAULT_SEXUAL_ORIENTATION_TYPE = 7066;
  public static final String DEFAULT_SO_UNABLE_TO_DETERMINE_CODE = "D";
  public static final short DEFAULT_GENDER_IDENTITY_TYPE = 7075;
  public static final short DEFAULT_GENDER_EXPRESSION_TYPE = 7081;

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(required = false, readOnly = false, value = "Last Updated Time",
      example = "2004-03-31T09:45:58.000-0800")
  private DateTime lastUpdatedTime;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "Client Id", example = "ABC1234567")
  private String clientId;

  @ApiModelProperty(required = false, readOnly = false)
  private Boolean adjudicatedDelinquentIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"T", "P", "N", "A"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false,
      value = "T=totally free - P=partially free - N=not free - A=not applicable", example = "N")
  private String adoptionStatusCode;

  @NotNull
  @Size(max = 12)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789012")
  private String alienRegistrationNumber;

  @NotNull
  @Size(max = 35)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Fresno")
  private String birthCity;

  @SystemCodeSerializer(logical = true, description = true)
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

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short birthStateCodeType;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean birthplaceVerifiedIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean childClientIndicatorVar;

  @Size(max = 12)
  @ApiModelProperty(required = false, readOnly = false, value = "Client Index Number", example = "")
  private String clientIndexNumber;

  @NotNull
  @Size(max = 120)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "a description")
  private String commentDescription;

  @NotBlank
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "first name of Client",
      example = "first name")
  private String commonFirstName;

  @Size(min = 0, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "middle name")
  private String commonMiddleName;

  @NotBlank
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "last name")
  private String commonLastName;

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

  @NotNull
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
  @ApiModelProperty(required = false, readOnly = false, value = "Death Reason Text",
      example = "0123456ABC")
  private String deathReasonText;

  @NotNull
  @Size(max = 20)
  @ApiModelProperty(required = false, readOnly = false,
      value = "driver license number of the CLIENT", example = "CA12345678901234567")
  private String driverLicenseNumber;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1828")
  private Short driverLicenseStateCodeType;

  @Size(max = 50)
  @ApiModelProperty(required = false, readOnly = false, value = "Email Address",
      example = "abc@def.com")
  private String emailAddress;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"Y", "N", "U"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Y")
  private String estimatedDobCode;

  @OneOf(value = {"A", "I", "K", ""}, ignoreCase = false, ignoreWhitespace = true)
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
  @OneOf(value = {"M", "F", "U", "I"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String genderCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "7075")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.GENDER_IDENTITY_TYPE,
      ignoreable = false)
  private Short genderIdentityType;

  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false, example = "gi not listed descrption")
  private String giNotListedDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "7081")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.GENDER_EXPRESSION_TYPE,
      ignoreable = false)
  private Short genderExpressionType;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "Health Summary Text",
      example = "0123456ABC")
  private String healthSummaryText;

  @Size(max = 1)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String hispUnableToDetReasonCode;

  @NotNull
  @Size(min = 0, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "X")
  private String hispanicOriginCode;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short immigrationCountryCodeType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1199")
  private Short immigrationStatusType;

  @NotEmpty
  @Size(min = 1, max = 2)
  @OneOf(value = {"N", "NA", "U", "Y"}, ignoreCase = false, ignoreWhitespace = true)
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
  @OneOf(value = {"Y", "N", "U", "D"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String literateCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean maritalCohabitatnHstryIndicatorVar;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "0")
  private Short maritalStatusType;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"D", "A", "V", "N"}, ignoreCase = false, ignoreWhitespace = true)
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

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short nameType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean outstandingWarrantIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean prevCaChildrenServIndicator;

  @NotNull
  @Size(max = 25)
  @ApiModelProperty(required = false, readOnly = false, value = "Previously Receiving Care",
      example = "some other care")
  private String prevOtherDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean prevRegionalCenterIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "839")
  private Short primaryEthnicityType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1253")
  private Short primaryLanguage;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short religionType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1255")
  private Short secondaryLanguage;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean sensitiveHlthInfoOnFileIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "R")
  private String sensitivityIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "7066")
  @ValidSystemCodeId(required = true, category = SystemCodeCategoryId.SEXUAL_ORIENTATION_TYPE,
      ignoreable = false)
  private Short sexualOrientationType;

  @OneOf(value = {"D", "C", ""}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, example = "D")
  private String soUnableToDetermineCode;

  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false, example = "So not listed descrption")
  private String soNotListedDescrption;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"Y", "M", "N"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String soc158PlacementCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean soc158SealedClientIndicator;

  @NotNull
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"Y", "N"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "N")
  private String socialSecurityNumChangedCode;

  @Size(min = 0, max = 9)
  // This regualr expression(regexp) validates the socialSecurityNumber should be only numeric and
  // length 9
  @Pattern(regexp = "^(|[0-9]{9})$")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "123456789")
  private String socialSecurityNumber;

  @NotNull
  @Size(max = 4)
  @ApiModelProperty(required = false, readOnly = false, value = "Suffix Title Description",
      example = "MS")
  private String suffixTitleDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalAncestryClientIndicatorVar;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean tribalMembrshpVerifctnIndicatorVar;

  @NotEmpty
  @Size(min = 1, max = 2)
  @OneOf(value = {"N", "NA", "U", "Y"}, ignoreCase = false, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U")
  private String unemployedParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;

  @JsonProperty("address")
  private Set<Address> address;

  /**
   * @param existingClientId - existingClientId
   * @param lastUpdatedTime - lastUpdatedTime
   * @param adjudicatedDelinquentIndicator - adjudicatedDelinquentIndicator
   * @param adoptionStatusCode - adoptionStatusCode
   * @param alienRegistrationNumber - alienRegistrationNumber
   * @param birthCity - birthCity
   * @param birthCountryCodeType - birthCountryCodeType
   * @param birthDate - birthDate
   * @param birthFacilityName - birthFacilityName
   * @param birthStateCodeType - birthStateCodeType
   * @param birthplaceVerifiedIndicator - birthplaceVerifiedIndicator
   * @param childClientIndicatorVar - childClientIndicatorVar
   * @param clientIndexNumber - clientIndexNumber
   * @param commentDescription - commentDescription
   * @param commonFirstName - commonFirstName
   * @param commonLastName - commonLastName
   * @param commonMiddleName - commonMiddleName
   * @param confidentialityActionDate - confidentialityActionDate
   * @param confidentialityInEffectIndicator - confidentialityInEffectIndicator
   * @param creationDate - creationDate
   * @param currCaChildrenServIndicator - currCaChildrenServIndicator
   * @param currentlyOtherDescription - currentlyOtherDescription
   * @param currentlyRegionalCenterIndicator - currentlyRegionalCenterIndicator
   * @param deathDate - deathDate
   * @param deathDateVerifiedIndicator - deathDateVerifiedIndicator
   * @param deathPlace - deathPlace
   * @param deathReasonText - deathReasonText
   * @param driverLicenseNumber - driverLicenseNumber
   * @param driverLicenseStateCodeType - driverLicenseStateCodeType
   * @param emailAddress - emailAddress
   * @param estimatedDobCode - estimatedDobCode
   * @param ethUnableToDetReasonCode - ethUnableToDetReasonCode
   * @param fatherParentalRightTermDate - fatherParentalRightTermDate
   * @param genderCode - genderCode
   * @param genderIdentityType - genderIdentityType
   * @param giNotListedDescription - giNotListedDescription
   * @param genderExpressionType - genderExpressionType
   * @param healthSummaryText - healthSummaryText
   * @param hispUnableToDetReasonCode - hispUnableToDetReasonCode
   * @param hispanicOriginCode - hispanicOriginCode
   * @param immigrationCountryCodeType - immigrationCountryCodeType
   * @param immigrationStatusType - immigrationStatusType
   * @param incapacitatedParentCode - incapacitatedParentCode
   * @param individualHealthCarePlanIndicator - individualHealthCarePlanIndicator
   * @param limitationOnScpHealthIndicator - limitationOnScpHealthIndicator
   * @param literateCode - literateCode
   * @param maritalCohabitatnHstryIndicatorVar - maritalCohabitatnHstryIndicatorVar
   * @param maritalStatusType - maritalStatusType
   * @param militaryStatusCode - militaryStatusCode
   * @param motherParentalRightTermDate - motherParentalRightTermDate
   * @param namePrefixDescription - namePrefixDescription
   * @param nameType - nameType
   * @param outstandingWarrantIndicator - outstandingWarrantIndicator
   * @param prevCaChildrenServIndicator - prevCaChildrenServIndicator
   * @param prevOtherDescription - prevOtherDescription
   * @param prevRegionalCenterIndicator - prevRegionalCenterIndicator
   * @param primaryEthnicityType - primaryEthnicityType
   * @param primaryLanguage - primaryLanguage
   * @param religionType - religionType
   * @param secondaryLanguage - secondaryLanguage
   * @param sensitiveHlthInfoOnFileIndicator - sensitiveHlthInfoOnFileIndicator
   * @param sensitivityIndicator - sensitivityIndicator
   * @param sexualOrientationType - sexualOrientationType
   * @param soUnableToDetermineCode - soUnableToDetermineCode
   * @param soNotListedDescrption - soNotListedDescrption
   * @param soc158PlacementCode - soc158PlacementCode
   * @param soc158SealedClientIndicator - soc158SealedClientIndicator
   * @param socialSecurityNumChangedCode - socialSecurityNumChangedCode
   * @param socialSecurityNumber - socialSecurityNumber
   * @param suffixTitleDescription - suffixTitleDescription
   * @param tribalAncestryClientIndicatorVar - tribalAncestryClientIndicatorVar
   * @param tribalMembrshpVerifctnIndicatorVar - tribalMembrshpVerifctnIndicatorVar
   * @param unemployedParentCode - unemployedParentCode
   * @param zippyCreatedIndicator - zippyCreatedIndicator
   * @param address - address
   */
  @JsonCreator
  public Client(@JsonProperty("existingClientId") String existingClientId,
      @JsonProperty("lastUpdatedTime") DateTime lastUpdatedTime,
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
      @JsonProperty("clientIndexNumber") String clientIndexNumber,
      @JsonProperty("commentDescription") String commentDescription,
      @JsonProperty("commonFirstName") String commonFirstName,
      @JsonProperty("commonMiddleName") String commonMiddleName,
      @JsonProperty("commonLastName") String commonLastName,
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
      @JsonProperty("genderIdentityType") Short genderIdentityType,
      @JsonProperty("giNotListedDescription") String giNotListedDescription,
      @JsonProperty("genderExpressionType") Short genderExpressionType,
      @JsonProperty("healthSummaryText") String healthSummaryText,
      @JsonProperty("hispUnableToDetReasonCode") String hispUnableToDetReasonCode,
      @JsonProperty("hispanicOriginCode") String hispanicOriginCode,
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
      @JsonProperty("primaryLanguage") Short primaryLanguage,
      @JsonProperty("religionType") Short religionType,
      @JsonProperty("secondaryLanguage") Short secondaryLanguage,
      @JsonProperty("sensitiveHlthInfoOnFileIndicator") Boolean sensitiveHlthInfoOnFileIndicator,
      @JsonProperty("sensitivityIndicator") String sensitivityIndicator,
      @JsonProperty("sexualOrientationType") Short sexualOrientationType,
      @JsonProperty("soUnableToDetermineCode") String soUnableToDetermineCode,
      @JsonProperty("soNotListedDescrption") String soNotListedDescrption,
      @JsonProperty("soc158PlacementCode") String soc158PlacementCode,
      @JsonProperty("soc158SealedClientIndicator") Boolean soc158SealedClientIndicator,
      @JsonProperty("socialSecurityNumChangedCode") String socialSecurityNumChangedCode,
      @JsonProperty("socialSecurityNumber") String socialSecurityNumber,
      @JsonProperty("suffixTitleDescription") String suffixTitleDescription,
      @JsonProperty("tribalAncestryClientIndicatorVar") Boolean tribalAncestryClientIndicatorVar,
      @JsonProperty("tribalMembrshpVerifctnIndicatorVar") Boolean tribalMembrshpVerifctnIndicatorVar,
      @JsonProperty("unemployedParentCode") String unemployedParentCode,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator,
      @JsonProperty("address") Set<Address> address) {
    super();
    this.clientId = existingClientId;
    this.lastUpdatedTime = lastUpdatedTime;
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
    this.commonMiddleName = commonMiddleName;
    this.commonLastName = commonLastName;
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
    this.genderIdentityType = genderIdentityType;
    this.giNotListedDescription = giNotListedDescription;
    this.genderExpressionType = genderExpressionType;
    this.healthSummaryText = healthSummaryText;
    this.hispUnableToDetReasonCode = hispUnableToDetReasonCode;
    this.hispanicOriginCode = hispanicOriginCode;
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
    this.primaryLanguage = primaryLanguage != null ? primaryLanguage : 0;
    this.religionType = religionType;
    this.secondaryLanguage = secondaryLanguage != null ? secondaryLanguage : 0;
    this.sensitiveHlthInfoOnFileIndicator = sensitiveHlthInfoOnFileIndicator;
    this.sensitivityIndicator = sensitivityIndicator;
    this.sexualOrientationType = sexualOrientationType;
    this.soUnableToDetermineCode = soUnableToDetermineCode;
    this.soNotListedDescrption = soNotListedDescrption;
    this.soc158PlacementCode = soc158PlacementCode;
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
    this.socialSecurityNumChangedCode = socialSecurityNumChangedCode;
    this.socialSecurityNumber = socialSecurityNumber;
    this.suffixTitleDescription = suffixTitleDescription == null ? "" : suffixTitleDescription;
    this.tribalAncestryClientIndicatorVar = tribalAncestryClientIndicatorVar;
    this.tribalMembrshpVerifctnIndicatorVar = tribalMembrshpVerifctnIndicatorVar;
    this.unemployedParentCode = unemployedParentCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.address = address;
  }

  /**
   * @param persistedClient - persistedClient object
   * @param isExist - ExistingId
   */
  public Client(gov.ca.cwds.data.persistence.cms.Client persistedClient, boolean isExist) {
    this.clientId = isExist ? persistedClient.getId() : "";
    this.lastUpdatedTime = new DateTime(persistedClient.getLastUpdatedTime());
    this.adjudicatedDelinquentIndicator =
        DomainChef.uncookBooleanString(persistedClient.getAdjudicatedDelinquentIndicator());
    this.adoptionStatusCode = persistedClient.getAdoptionStatusCode();
    this.alienRegistrationNumber = persistedClient.getAlienRegistrationNumber();
    this.birthCity = persistedClient.getBirthCity();
    this.birthCountryCodeType = persistedClient.getBirthCountryCodeType();
    this.birthDate = DomainChef.cookDate(persistedClient.getBirthDate());
    this.birthFacilityName = persistedClient.getBirthFacilityName();
    this.birthStateCodeType = persistedClient.getBirthStateCodeType();
    this.birthplaceVerifiedIndicator =
        DomainChef.uncookBooleanString(persistedClient.getBirthplaceVerifiedIndicator());
    this.childClientIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getChildClientIndicatorVar());
    this.clientIndexNumber = persistedClient.getClientIndexNumber();
    this.commentDescription = persistedClient.getCommentDescription();
    this.commonFirstName = persistedClient.getCommonFirstName();
    this.commonMiddleName = StringUtils.isBlank(persistedClient.getCommonMiddleName()) ? ""
        : persistedClient.getCommonMiddleName();
    this.commonLastName = persistedClient.getCommonLastName();
    this.confidentialityActionDate =
        DomainChef.cookDate(persistedClient.getConfidentialityActionDate());
    this.confidentialityInEffectIndicator =
        DomainChef.uncookBooleanString(persistedClient.getConfidentialityInEffectIndicator());
    this.creationDate = DomainChef.cookDate(persistedClient.getCreationDate());
    this.currCaChildrenServIndicator =
        DomainChef.uncookBooleanString(persistedClient.getCurrCaChildrenServIndicator());
    this.currentlyOtherDescription = persistedClient.getCurrentlyOtherDescription();
    this.currentlyRegionalCenterIndicator =
        DomainChef.uncookBooleanString(persistedClient.getCurrentlyRegionalCenterIndicator());
    this.deathDate = DomainChef.cookDate(persistedClient.getDeathDate());
    this.deathDateVerifiedIndicator =
        DomainChef.uncookBooleanString(persistedClient.getDeathDateVerifiedIndicator());
    this.deathPlace = persistedClient.getDeathPlace();
    this.deathReasonText = persistedClient.getDeathReasonText();
    this.driverLicenseNumber = persistedClient.getDriverLicenseNumber();
    this.driverLicenseStateCodeType = persistedClient.getDriverLicenseStateCodeType();
    this.emailAddress = persistedClient.getEmailAddress();
    this.estimatedDobCode = persistedClient.getEstimatedDobCode();
    this.ethUnableToDetReasonCode =
        StringUtils.isBlank(persistedClient.getEthUnableToDetReasonCode()) ? null
            : persistedClient.getEthUnableToDetReasonCode();
    this.fatherParentalRightTermDate =
        DomainChef.cookDate(persistedClient.getFatherParentalRightTermDate());
    this.genderCode = persistedClient.getGenderCode();
    this.genderIdentityType = persistedClient.getGenderIdentityType();
    this.giNotListedDescription = persistedClient.getGiNotListedDescription();
    this.genderExpressionType = persistedClient.getGenderExpressionType();
    this.healthSummaryText = persistedClient.getHealthSummaryText();
    this.hispUnableToDetReasonCode =
        StringUtils.isBlank(persistedClient.getHispUnableToDetReasonCode()) ? null
            : persistedClient.getHispUnableToDetReasonCode();
    this.hispanicOriginCode = StringUtils.isBlank(persistedClient.getHispanicOriginCode()) ? ""
        : persistedClient.getHispanicOriginCode();
    this.immigrationCountryCodeType = persistedClient.getImmigrationCountryCodeType();
    this.immigrationStatusType = persistedClient.getImmigrationStatusType();
    this.incapacitatedParentCode = persistedClient.getIncapacitatedParentCode();
    this.individualHealthCarePlanIndicator =
        DomainChef.uncookBooleanString(persistedClient.getIndividualHealthCarePlanIndicator());
    this.limitationOnScpHealthIndicator =
        DomainChef.uncookBooleanString(persistedClient.getLimitationOnScpHealthIndicator());
    this.literateCode = persistedClient.getLiterateCode();
    this.maritalCohabitatnHstryIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getMaritalCohabitatnHstryIndicatorVar());
    this.maritalStatusType = persistedClient.getMaritalStatusType();
    this.militaryStatusCode = persistedClient.getMilitaryStatusCode();
    this.motherParentalRightTermDate =
        DomainChef.cookDate(persistedClient.getMotherParentalRightTermDate());
    this.namePrefixDescription = persistedClient.getNamePrefixDescription();
    this.nameType = persistedClient.getNameType();
    this.outstandingWarrantIndicator =
        DomainChef.uncookBooleanString(persistedClient.getOutstandingWarrantIndicator());
    this.prevCaChildrenServIndicator =
        DomainChef.uncookBooleanString(persistedClient.getPrevCaChildrenServIndicator());
    this.prevOtherDescription = persistedClient.getPrevOtherDescription();
    this.prevRegionalCenterIndicator =
        DomainChef.uncookBooleanString(persistedClient.getPrevRegionalCenterIndicator());
    this.primaryEthnicityType = persistedClient.getPrimaryEthnicityType();
    this.primaryLanguage =
        persistedClient.getPrimaryLanguageType() != null ? persistedClient.getPrimaryLanguageType()
            : 0;
    this.religionType = persistedClient.getReligionType();
    this.secondaryLanguage = persistedClient.getSecondaryLanguageType() != null
        ? persistedClient.getSecondaryLanguageType()
        : 0;
    this.sensitiveHlthInfoOnFileIndicator =
        DomainChef.uncookBooleanString(persistedClient.getSensitiveHlthInfoOnFileIndicator());
    this.sensitivityIndicator = persistedClient.getSensitivityIndicator();
    this.sexualOrientationType = persistedClient.getSexualOrientationType();
    this.soUnableToDetermineCode = persistedClient.getSoUnableToDetermineCode();
    this.soNotListedDescrption = persistedClient.getSoNotListedDescrption();
    this.soc158PlacementCode = persistedClient.getSoc158PlacementCode();
    this.soc158SealedClientIndicator =
        DomainChef.uncookBooleanString(persistedClient.getSoc158SealedClientIndicator());
    this.socialSecurityNumChangedCode = persistedClient.getSocialSecurityNumChangedCode();
    this.socialSecurityNumber = persistedClient.getSocialSecurityNumber();
    this.suffixTitleDescription = persistedClient.getSuffixTitleDescription();
    this.tribalAncestryClientIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getTribalAncestryClientIndicatorVar());
    this.tribalMembrshpVerifctnIndicatorVar =
        DomainChef.uncookBooleanString(persistedClient.getTribalMembrshpVerifctnIndicatorVar());
    this.unemployedParentCode = persistedClient.getUnemployedParentCode();
    this.zippyCreatedIndicator =
        DomainChef.uncookBooleanString(persistedClient.getZippyCreatedIndicator());
    this.address = new HashSet<>();
    if (persistedClient.getClientAddress() != null
        && !persistedClient.getClientAddress().isEmpty()) {
      for (gov.ca.cwds.data.persistence.cms.ClientAddress persistedClientAddress : persistedClient
          .getClientAddress()) {
        this.address.add(new Address(persistedClientAddress.getAddresses(), true));
      }
    }
  }

  /**
   * @param participant - participant
   * @param dateStarted - dateStarted
   * @param genderCode - genderCode
   * @param raceCode - raceCode
   * @param childClientIndicatorVar - childClientIndicatorVar
   * @return the client
   */
  public static Client createWithDefaults(Participant participant, String dateStarted,
      String genderCode, Short raceCode, Boolean childClientIndicatorVar) {

    String unableToDetermineCode = participant.getRaceAndEthnicity() != null
        ? participant.getRaceAndEthnicity().getUnableToDetermineCode()
        : "";
    String hispanicUnableToDetermineCode = participant.getRaceAndEthnicity() != null
        ? participant.getRaceAndEthnicity().getHispanicUnableToDetermineCode()
        : "";
    String hispanicOriginCode = participant.getRaceAndEthnicity() != null
        ? participant.getRaceAndEthnicity().getHispanicOriginCode()
        : "";

    return new Client("", participant.getLegacyDescriptor().getLastUpdated(), Boolean.FALSE,
        DEFAULT_ADOPTION_STATUS_CODE, "", "", DEFAULT_CODE, participant.getDateOfBirth(), "",
        DEFAULT_CODE, Boolean.FALSE, childClientIndicatorVar, "", "", participant.getFirstName(),
        participant.getMiddleName(), participant.getLastName(), "", Boolean.FALSE, dateStarted,
        Boolean.FALSE, "", Boolean.FALSE, "", Boolean.FALSE, "", "", "", DEFAULT_CODE, "",
        ESTIMATED_DOB_CODE_ACTUALLY_ENTERED, unableToDetermineCode, "", genderCode,
        DEFAULT_GENDER_IDENTITY_TYPE, null, DEFAULT_GENDER_EXPRESSION_TYPE, "",
        hispanicUnableToDetermineCode, hispanicOriginCode, DEFAULT_CODE, DEFAULT_CODE,
        DEFAULT_INCAPCITATED_PARENT_CODE, Boolean.FALSE, Boolean.FALSE, DEFAULT_LITERATE_CODE,
        Boolean.FALSE, DEFAULT_CODE, DEFAULT_MILITARY_STATUS_CODE, "", "", DEFAULT_NAME_TYPE,
        Boolean.FALSE, Boolean.FALSE, "", Boolean.FALSE, raceCode, participant.getPrimaryLanguage(),
        DEFAULT_CODE, participant.getSecondaryLanguage(), Boolean.FALSE,
        DEFAULT_SENSITIVITY_INDICATOR, DEFAULT_SEXUAL_ORIENTATION_TYPE,
        DEFAULT_SO_UNABLE_TO_DETERMINE_CODE, null, DEFAULT_SOC158_PLACEMENT_CODE, Boolean.FALSE,
        DEFAULT_SOCIAL_SECURITY_NUM_CHANGE_CODE, participant.getSsn(), participant.getNameSuffix(),
        Boolean.FALSE, Boolean.FALSE, DEFAULT_UNEMPLOYED_PARENT_CODE,
        R06998ZippyIndicator.YES.getCode(), null);
  }

  /**
   * @return the id
   */
  public DateTime getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  /**
   * @return the existingClientId
   */
  public String getExistingClientId() {
    return clientId;
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
  public String getClientIndexNumber() {
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
   * @return the primaryLanguage
   */
  public Short getPrimaryLanguage() {
    return primaryLanguage;
  }

  /**
   * @return the religionType
   */
  public Short getReligionType() {
    return religionType;
  }

  /**
   * @return the secondaryLanguage
   */
  public Short getSecondaryLanguage() {
    return secondaryLanguage;
  }

  /**
   * @return the sensitiveHlthInfoOnFileIndicator
   */
  public Boolean getSensitiveHlthInfoOnFileIndicator() {
    return sensitiveHlthInfoOnFileIndicator;
  }

  /**
   * @return the sensitivityIndicator
   */
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
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
   * @return the genderIdentityType
   */
  public Short getGenderIdentityType() {
    return genderIdentityType;
  }

  /**
   * @return the giNotListedDescription
   */
  public String getGiNotListedDescription() {
    return giNotListedDescription;
  }

  /**
   * @return the genderExpressionType
   */
  public Short getGenderExpressionType() {
    return genderExpressionType;
  }

  /**
   * @return the sexualOrientationType
   */
  public Short getSexualOrientationType() {
    return sexualOrientationType;
  }

  /**
   * @return the soUnableToDetermineCode
   */
  public String getSoUnableToDetermineCode() {
    return soUnableToDetermineCode;
  }

  /**
   * @return the soNotListedDescrption
   */
  public String getSoNotListedDescrption() {
    return soNotListedDescrption;
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

  public void applySensitivityIndicator(LimitedAccessType limitedAccessType) {
    this.sensitivityIndicator = limitedAccessType.getValue();
  }

  /**
   * @return the address
   */
  public Set<Address> getAddress() {
    return address;
  }

  /**
   * Allows limited fields to be updated after creation
   * 
   * @param firstName first name
   * @param lastName last name
   * @param middleName middle name
   * @param nameSuffix name suffix
   * @param gender gender
   * @param ssn ssn
   * @param raceCode race code
   * @param unableToDetermineCode unable to determine code
   * @param hispanicUnableToDetermineCode hispanic unable to determine code
   * @param hispanicOriginCode hispanic origin code
   * @param birthDate  date of birth
   */
  public void update(String firstName, String middleName, String lastName, String nameSuffix,
      String gender, String ssn, Short raceCode, String unableToDetermineCode,
      String hispanicUnableToDetermineCode, String hispanicOriginCode,
      String birthDate) {
    this.commonFirstName = firstName;
    this.commonLastName = lastName;
    this.commonMiddleName = middleName;
    this.suffixTitleDescription = nameSuffix;
    this.genderCode = gender;
    this.socialSecurityNumber = ssn;
    this.primaryEthnicityType = raceCode;
    this.ethUnableToDetReasonCode = unableToDetermineCode;
    this.hispUnableToDetReasonCode = hispanicUnableToDetermineCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.birthDate = birthDate;
  }

  public boolean hasSameLastUpdate(Client otherClient) {
    return this.lastUpdatedTime.equals(otherClient.lastUpdatedTime);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
