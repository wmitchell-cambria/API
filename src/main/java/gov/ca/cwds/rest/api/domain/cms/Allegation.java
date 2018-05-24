package gov.ca.cwds.rest.api.domain.cms;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import gov.ca.cwds.rest.validation.NotEqual;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an Allegation
 * 
 * @author CWDS API Team
 */
@ApiModel("cmsAllegation")
@NotEqual(ifProperty = "victimClientId", thenProperty = "perpetratorClientId")
public class Allegation extends ReportingDomain implements Request, Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "abuseEndDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date when abuse allegedly ended",
      example = "2016-11-30")
  private String abuseEndDate;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short abuseFrequency;

  @NotNull
  @Size(max = 1)
  @OneOf(value = {"D", "M", "W", "Y", ""}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = false, readOnly = false, value = "frequency of abuse", example = "D")
  private String abuseFrequencyPeriodCode;

  @Size(min = 0, max = 75)
  @ApiModelProperty(required = true, readOnly = false, value = "location description",
      example = "school yard")
  private String abuseLocationDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "abuseStartDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "start date of alleged abuse",
      example = "2016-11-30")
  private String abuseStartDate;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short allegationDispositionType;

  @SystemCodeSerializer(logical = true, description = true)
  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short allegationType;

  @NotNull
  @Size(max = 254)
  @ApiModelProperty(required = false, readOnly = false,
      value = "description of allegation disposition", example = "investigate")
  private String dispositionDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "dispositionDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "date of allegation disposition",
      example = "2016-11-30")
  private String dispositionDate;

  @NotNull
  @ApiModelProperty(required = false, readOnly = false)
  private Boolean injuryHarmDetailIndicator;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @OneOf(value = {"U", "P", "Y", "N"}, ignoreCase = true, ignoreWhitespace = true)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "U",
      allowableValues = "U, P, Y, N")
  private String nonProtectingParentCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean staffPersonAddedIndicator;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "CLIENT ID of victim",
      example = "ABC1234567")
  private String victimClientId;

  @Size(max = CMS_ID_LEN)
  @ApiModelProperty(required = false, readOnly = false, value = "CLIENT ID of perpetrator",
      example = "ABC1234567")
  private String perpetratorClientId;

  @NotEmpty
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC1234567")
  private String referralId;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "County code", example = "A1")
  private String countySpecificCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean zippyCreatedIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @ApiModelProperty(required = false, readOnly = false, example = "1234")
  private Short placementFacilityType;

  /**
   * Constructor. Build from JSON.
   * 
   * @param abuseEndDate abuse end date
   * @param abuseFrequency abuse frequency
   * @param abuseFrequencyPeriodCode abuse frequency period code
   * @param abuseLocationDescription abuse location
   * @param abuseStartDate abuse start date
   * @param allegationDispositionType abuse disposition
   * @param allegationType type of allegation
   * @param dispositionDescription description of disposition
   * @param dispositionDate disposition date
   * @param injuryHarmDetailIndicator flag injury or harm
   * @param nonProtectingParentCode non-protecting parent code
   * @param staffPersonAddedIndicator flag added by staff
   * @param victimClientId victim or client id
   * @param perpetratorClientId perpetrator's client id
   * @param referralId referral id
   * @param countySpecificCode county code
   * @param zippyCreatedIndicator created by Zippy
   * @param placementFacilityType type of placement facility
   */
  @JsonCreator
  public Allegation(@JsonProperty("abuseEndDate") String abuseEndDate,
      @JsonProperty("abuseFrequency") Short abuseFrequency,
      @JsonProperty("abuseFrequencyPeriodCode") String abuseFrequencyPeriodCode,
      @JsonProperty("abuseLocationDescription") String abuseLocationDescription,
      @JsonProperty("abuseStartDate") String abuseStartDate,
      @JsonProperty("allegationDispositionType") Short allegationDispositionType,
      @JsonProperty("allegationType") Short allegationType,
      @JsonProperty("dispositionDescription") String dispositionDescription,
      @JsonProperty("dispositionDate") String dispositionDate,
      @JsonProperty("injuryHarmDetailIndicator") Boolean injuryHarmDetailIndicator,
      @JsonProperty("nonProtectingParentCode") String nonProtectingParentCode,
      @JsonProperty("staffPersonAddedIndicator") Boolean staffPersonAddedIndicator,
      @JsonProperty("victimClientId") String victimClientId,
      @JsonProperty("perpetratorClientId") String perpetratorClientId,
      @JsonProperty("referralId") String referralId,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("zippyCreatedIndicator") Boolean zippyCreatedIndicator,
      @JsonProperty("placementFacilityType") Short placementFacilityType) {
    super();
    this.abuseEndDate = abuseEndDate;
    this.abuseFrequency = abuseFrequency;
    this.abuseFrequencyPeriodCode = abuseFrequencyPeriodCode;
    this.abuseLocationDescription = abuseLocationDescription;
    this.abuseStartDate = abuseStartDate;
    this.allegationDispositionType = allegationDispositionType;
    this.allegationType = allegationType;
    this.dispositionDescription = dispositionDescription;
    this.dispositionDate = dispositionDate;
    this.injuryHarmDetailIndicator = injuryHarmDetailIndicator;
    this.nonProtectingParentCode = nonProtectingParentCode;
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
    this.victimClientId = victimClientId;
    this.perpetratorClientId = perpetratorClientId;
    this.referralId = referralId;
    this.countySpecificCode = countySpecificCode;
    this.zippyCreatedIndicator = zippyCreatedIndicator;
    this.placementFacilityType = placementFacilityType;
  }

  /**
   * Construct from persistence layer CMS allegation.
   * 
   * @param persistedAllegation persistence level allegation
   */
  public Allegation(gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation) {
    this.abuseEndDate = DomainChef.cookDate(persistedAllegation.getAbuseEndDate());
    this.abuseFrequency = persistedAllegation.getAbuseFrequency();
    this.abuseFrequencyPeriodCode = persistedAllegation.getAbuseFrequencyPeriodCode();
    this.abuseLocationDescription = persistedAllegation.getAbuseLocationDescription();
    this.abuseStartDate = DomainChef.cookDate(persistedAllegation.getAbuseStartDate());
    this.allegationDispositionType = persistedAllegation.getAllegationDispositionType();
    this.allegationType = persistedAllegation.getAllegationType();
    this.dispositionDescription = persistedAllegation.getDispositionDescription();
    this.dispositionDate = DomainChef.cookDate(persistedAllegation.getDispositionDate());
    this.injuryHarmDetailIndicator =
        DomainChef.uncookBooleanString(persistedAllegation.getInjuryHarmDetailIndicator());
    this.nonProtectingParentCode = persistedAllegation.getNonProtectingParentCode();
    this.staffPersonAddedIndicator =
        DomainChef.uncookBooleanString(persistedAllegation.getStaffPersonAddedIndicator());
    this.victimClientId = persistedAllegation.getVictimClientId();
    this.perpetratorClientId = persistedAllegation.getPerpetratorClientId();
    this.referralId = persistedAllegation.getReferralId();
    this.countySpecificCode = persistedAllegation.getCountySpecificCode();
    this.zippyCreatedIndicator =
        DomainChef.uncookBooleanString(persistedAllegation.getZippyCreatedIndicator());
    this.placementFacilityType = persistedAllegation.getPlacementFacilityType();
  }

  /**
   * @return the abuseEndDate
   */
  public String getAbuseEndDate() {
    return abuseEndDate;
  }

  /**
   * @return the abuseFrequency
   */
  public Short getAbuseFrequency() {
    return abuseFrequency;
  }

  /**
   * @return the abuseFrequencyPeriodCode
   */
  public String getAbuseFrequencyPeriodCode() {
    return abuseFrequencyPeriodCode;
  }

  /**
   * @return the abuseLocationDescription
   */
  public String getAbuseLocationDescription() {
    return abuseLocationDescription;
  }

  /**
   * @return the abuseStartDate
   */
  public String getAbuseStartDate() {
    return abuseStartDate;
  }

  /**
   * @return the allegationDispositionType
   */
  public Short getAllegationDispositionType() {
    return allegationDispositionType;
  }

  /**
   * @return the allegationType
   */
  public Short getAllegationType() {
    return allegationType;
  }

  public void setAllegationType(Short allegationType) {
    this.allegationType = allegationType;
  }

  /**
   * @return the dispositionDescription
   */
  public String getDispositionDescription() {
    return dispositionDescription;
  }

  /**
   * @return the dispositionDate
   */
  public String getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the injuryHarmDetailIndicator
   */
  public Boolean getInjuryHarmDetailIndicator() {
    return injuryHarmDetailIndicator;
  }

  /**
   * @return the nonProtectingParentCode
   */
  public String getNonProtectingParentCode() {
    return nonProtectingParentCode;
  }

  /**
   * @return the staffPersonAddedIndicator
   */
  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  /**
   * @return the victimClientId
   */
  public String getVictimClientId() {
    return victimClientId;
  }

  public void setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
  }

  /**
   * @return the perpetratorClientId
   */
  public String getPerpetratorClientId() {
    return perpetratorClientId;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  /**
   * @return the placementFacilityType
   */
  public Short getPlacementFacilityType() {
    return placementFacilityType;
  }

  /**
   * Allows limited fields to be updated after creation
   *
   * @param nonProtectingParentCode non protecting parent code
   */
  public void update(String nonProtectingParentCode){
    this.nonProtectingParentCode = nonProtectingParentCode;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
