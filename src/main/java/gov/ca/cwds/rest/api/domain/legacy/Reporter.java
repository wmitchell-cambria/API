package gov.ca.cwds.rest.api.domain.legacy;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.legacy.Referral;
import gov.ca.cwds.rest.core.Api;
//import gov.ca.cwds.rest.validation.AgencyName;
import gov.ca.cwds.rest.validation.ForeignKey;
import gov.ca.cwds.rest.validation.LawEnforcementBR;
import gov.ca.cwds.rest.validation.ZipCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Reporter
 * 
 * @author CWDS API Team
 */
@ApiModel
@InjectLinks({
    @InjectLink(value = "/{resource}/{id}", rel = "self", style = Style.ABSOLUTE, bindings = {
        @Binding(name = "id", value = "${instance.referralId}"),
        @Binding(name = "resource", value = Api.RESOURCE_STAFF_PERSON)}),
    @InjectLink(value = "/{resource}/{id}", rel = "referralId", style = Style.ABSOLUTE,
        bindings = {@Binding(name = "id", value = "${instance.referralId}"),
            @Binding(name = "resource", value = Api.RESOURCE_REFERRAL)},
        condition = "${not empty instance.referralId }"),
    @InjectLink(value = "/{resource}/{id}", rel = "lawEnforcementId", style = Style.ABSOLUTE,
        bindings = {@Binding(name = "id", value = "${instance.lawEnforcementId}"),
            @Binding(name = "resource", value = Api.RESOURCE_LAW_ENFORCEMENT)})})

@LawEnforcementBR.List({
    @LawEnforcementBR(
        fieldName = "lawEnforcementId",
        dependentFieldName = "badgeNumber",
        SecondFieldName = "employerName")
})

public class Reporter extends DomainObject {

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  @ForeignKey(required = true, persistentObjectClass = Referral.class)
  private String referralId;
  
  @NotEmpty
  @Size(min = 1, max = 6)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String badgeNumber;

  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String cityName;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short colltrClientRptrReltnshpType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short communicationMethodType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean confidentialWaiverIndicator;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String drmsMandatedRprtrFeedback;

  @NotEmpty
  @Size(min = 1, max = 35)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String employerName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "feedbackDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String feedbackDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean feedbackRequiredIndicator;

  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String firstName;

  @NotEmpty
  @Size(min = 1, max = 25)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String lastName;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean mandatedReporterIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Integer messagePhoneExtensionNumber;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private BigDecimal messagePhoneNumber;

  @NotEmpty
  @Size(min = 1, max = 1, message = "size must be 1")
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "A")
  private String middleInitialName;

  @NotEmpty
  @Size(min = 1, max = 6)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String namePrefixDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private BigDecimal primaryPhoneNumber;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Integer primaryPhoneExtensionNumber;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short stateCodeType;

  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String streetName;

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String streetNumber;

  @NotEmpty
  @Size(min = 1, max = 4)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "A1")
  private String suffixTitleDescription;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  @ZipCode(required = true)
  private Integer zipNumber;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC123")
  private String lawEnforcementId;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short zipSuffixNumber;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "A1")
  private String countySpecificCode;

  public Reporter(gov.ca.cwds.rest.api.persistence.legacy.Reporter persistedReporter) {
    this.referralId = persistedReporter.getReferralId().trim();
    this.badgeNumber = persistedReporter.getBadgeNumber();
    this.cityName = persistedReporter.getCityName();
    this.colltrClientRptrReltnshpType = persistedReporter.getColltrClientRptrReltnshpType();
    this.communicationMethodType = persistedReporter.getCommunicationMethodType();
    this.confidentialWaiverIndicator =
        DomainObject.uncookBooleanString(persistedReporter.getConfidentialWaiverIndicator());
    this.drmsMandatedRprtrFeedback = persistedReporter.getDrmsMandatedRprtrFeedback();
    this.employerName = persistedReporter.getEmployerName();
    this.feedbackDate = DomainObject.cookDate(persistedReporter.getFeedbackDate());
    this.feedbackRequiredIndicator =
        DomainObject.uncookBooleanString(persistedReporter.getFeedbackRequiredIndicator());
    this.firstName = persistedReporter.getFirstName();
    this.lastName = persistedReporter.getLastName();
    this.mandatedReporterIndicator =
        DomainObject.uncookBooleanString(persistedReporter.getMandatedReporterIndicator());
    this.messagePhoneExtensionNumber = persistedReporter.getMessagePhoneExtensionNumber();
    this.messagePhoneNumber = persistedReporter.getMessagePhoneNumber();
    this.middleInitialName = persistedReporter.getMiddleInitialName();
    this.namePrefixDescription = persistedReporter.getNamePrefixDescription();
    this.primaryPhoneNumber = persistedReporter.getPrimaryPhoneNumber();
    this.primaryPhoneExtensionNumber = persistedReporter.getPrimaryPhoneExtensionNumber();
    this.stateCodeType = persistedReporter.getStateCodeType();
    this.streetName = persistedReporter.getStreetName();
    this.streetNumber = persistedReporter.getStreetNumber();
    this.suffixTitleDescription = persistedReporter.getSuffixTitleDescription();
    this.zipNumber = persistedReporter.getZipNumber();
    this.lawEnforcementId = persistedReporter.getLawEnforcementId().trim();
    this.zipSuffixNumber = persistedReporter.getZipSuffixNumber();
    this.countySpecificCode = persistedReporter.getCountySpecificCode();
  }

  @JsonCreator
  public Reporter(@JsonProperty("badgeNumber") String badgeNumber,
      @JsonProperty("cityName") String cityName,
      @JsonProperty("colltrClientRptrReltnshpType") Short colltrClientRptrReltnshpType,
      @JsonProperty("communicationMethodType") Short communicationMethodType,
      @JsonProperty("confidentialWaiverIndicator") Boolean confidentialWaiverIndicator,
      @JsonProperty("drmsMandatedRprtrFeedback") String drmsMandatedRprtrFeedback,
      @JsonProperty("employerName") String employerName,
      @JsonProperty("feedbackDate") String feedbackDate,
      @JsonProperty("feedbackRequiredIndicator") Boolean feedbackRequiredIndicator,
      @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
      @JsonProperty("mandatedReporterIndicator") Boolean mandatedReporterIndicator,
      @JsonProperty("messagePhoneExtensionNumber") Integer messagePhoneExtensionNumber,
      @JsonProperty("messagePhoneNumber") BigDecimal messagePhoneNumber,
      @JsonProperty("middleInitialName") String middleInitialName,
      @JsonProperty("namePrefixDescription") String namePrefixDescription,
      @JsonProperty("primaryPhoneNumber") BigDecimal primaryPhoneNumber,
      @JsonProperty("primaryPhoneExtensionNumber") Integer primaryPhoneExtensionNumber,
      @JsonProperty("stateCodeType") Short stateCodeType,
      @JsonProperty("streetName") String streetName,
      @JsonProperty("streetNumber") String streetNumber,
      @JsonProperty("suffixTitleDescription") String suffixTitleDescription,
      @JsonProperty("zipNumber") Integer zipNumber, @JsonProperty("referralId") String referralId,
      @JsonProperty("lawEnforcementId") String lawEnforcementId,
      @JsonProperty("zipSuffixNumber") Short zipSuffixNumber,
      @JsonProperty("countySpecificCode") String countySpecificCode) {
    super();
    this.badgeNumber = badgeNumber;
    this.cityName = cityName;
    this.colltrClientRptrReltnshpType = colltrClientRptrReltnshpType;
    this.communicationMethodType = communicationMethodType;
    this.confidentialWaiverIndicator = confidentialWaiverIndicator;
    this.drmsMandatedRprtrFeedback = drmsMandatedRprtrFeedback;
    this.employerName = employerName;
    this.feedbackDate = feedbackDate;
    this.feedbackRequiredIndicator = feedbackRequiredIndicator;
    this.firstName = firstName;
    this.lastName = lastName;
    this.mandatedReporterIndicator = mandatedReporterIndicator;
    this.messagePhoneExtensionNumber = messagePhoneExtensionNumber;
    this.messagePhoneNumber = messagePhoneNumber;
    this.middleInitialName = middleInitialName;
    this.namePrefixDescription = namePrefixDescription;
    this.primaryPhoneNumber = primaryPhoneNumber;
    this.primaryPhoneExtensionNumber = primaryPhoneExtensionNumber;
    this.stateCodeType = stateCodeType;
    this.streetName = streetName;
    this.streetNumber = streetNumber;
    this.suffixTitleDescription = suffixTitleDescription;
    this.zipNumber = zipNumber;
    this.referralId = referralId;
    this.lawEnforcementId = lawEnforcementId;
    this.zipSuffixNumber = zipSuffixNumber;
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * @return the badgeNumber
   */
  public String getBadgeNumber() {
    return badgeNumber;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @return the colltrClientRptrReltnshpType
   */
  public Short getColltrClientRptrReltnshpType() {
    return colltrClientRptrReltnshpType;
  }

  /**
   * @return the communicationMethodType
   */
  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  /**
   * @return the confidentialWaiverIndicator
   */
  public Boolean getConfidentialWaiverIndicator() {
    return confidentialWaiverIndicator;
  }

  /**
   * @return the drmsMandatedRprtrFeedback
   */
  public String getDrmsMandatedRprtrFeedback() {
    return drmsMandatedRprtrFeedback;
  }

  /**
   * @return the employerName
   */
  public String getEmployerName() {
    return employerName;
  }

  /**
   * @return the feedbackDate
   */
  public String getFeedbackDate() {
    return feedbackDate;
  }

  /**
   * @return the feedbackRequiredIndicator
   */
  public Boolean getFeedbackRequiredIndicator() {
    return feedbackRequiredIndicator;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the mandatedReporterIndicator
   */
  public Boolean getMandatedReporterIndicator() {
    return mandatedReporterIndicator;
  }

  /**
   * @return the messagePhoneExtensionNumber
   */
  public Integer getMessagePhoneExtensionNumber() {
    return messagePhoneExtensionNumber;
  }

  /**
   * @return the messagePhoneNumber
   */
  public BigDecimal getMessagePhoneNumber() {
    return messagePhoneNumber;
  }

  /**
   * @return the middleInitialName
   */
  public String getMiddleInitialName() {
    return middleInitialName;
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  /**
   * @return the primaryPhoneNumber
   */
  public BigDecimal getPrimaryPhoneNumber() {
    return primaryPhoneNumber;
  }

  /**
   * @return the primaryPhoneExtensionNumber
   */
  public Integer getPrimaryPhoneExtensionNumber() {
    return primaryPhoneExtensionNumber;
  }

  /**
   * @return the stateCodeType
   */
  public Short getStateCodeType() {
    return stateCodeType;
  }

  /**
   * @return the streetName
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  /**
   * @return the zipNumber
   */
  public Integer getZipNumber() {
    return zipNumber;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the lawEnforcementId
   */
  public String getLawEnforcementId() {
    return lawEnforcementId;
  }

  /**
   * @return the zipSuffixNumber
   */
  public Short getZipSuffixNumber() {
    return zipSuffixNumber;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((badgeNumber == null) ? 0 : badgeNumber.hashCode());
    result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
    result =
        prime
            * result
            + ((colltrClientRptrReltnshpType == null) ? 0 : colltrClientRptrReltnshpType.hashCode());
    result =
        prime * result
            + ((communicationMethodType == null) ? 0 : communicationMethodType.hashCode());
    result =
        prime * result
            + ((confidentialWaiverIndicator == null) ? 0 : confidentialWaiverIndicator.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result =
        prime * result
            + ((drmsMandatedRprtrFeedback == null) ? 0 : drmsMandatedRprtrFeedback.hashCode());
    result = prime * result + ((employerName == null) ? 0 : employerName.hashCode());
    result = prime * result + ((feedbackDate == null) ? 0 : feedbackDate.hashCode());
    result =
        prime * result
            + ((feedbackRequiredIndicator == null) ? 0 : feedbackRequiredIndicator.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((lawEnforcementId == null) ? 0 : lawEnforcementId.hashCode());
    result =
        prime * result
            + ((mandatedReporterIndicator == null) ? 0 : mandatedReporterIndicator.hashCode());
    result =
        prime * result
            + ((messagePhoneExtensionNumber == null) ? 0 : messagePhoneExtensionNumber.hashCode());
    result = prime * result + ((messagePhoneNumber == null) ? 0 : messagePhoneNumber.hashCode());
    result = prime * result + ((middleInitialName == null) ? 0 : middleInitialName.hashCode());
    result =
        prime * result + ((namePrefixDescription == null) ? 0 : namePrefixDescription.hashCode());
    result =
        prime * result
            + ((primaryPhoneExtensionNumber == null) ? 0 : primaryPhoneExtensionNumber.hashCode());
    result = prime * result + ((primaryPhoneNumber == null) ? 0 : primaryPhoneNumber.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result = prime * result + ((stateCodeType == null) ? 0 : stateCodeType.hashCode());
    result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
    result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
    result =
        prime * result + ((suffixTitleDescription == null) ? 0 : suffixTitleDescription.hashCode());
    result = prime * result + ((zipNumber == null) ? 0 : zipNumber.hashCode());
    result = prime * result + ((zipSuffixNumber == null) ? 0 : zipSuffixNumber.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Reporter other = (Reporter) obj;
    if (badgeNumber == null) {
      if (other.badgeNumber != null)
        return false;
    } else if (!badgeNumber.equals(other.badgeNumber))
      return false;
    if (cityName == null) {
      if (other.cityName != null)
        return false;
    } else if (!cityName.equals(other.cityName))
      return false;
    if (colltrClientRptrReltnshpType == null) {
      if (other.colltrClientRptrReltnshpType != null)
        return false;
    } else if (!colltrClientRptrReltnshpType.equals(other.colltrClientRptrReltnshpType))
      return false;
    if (communicationMethodType == null) {
      if (other.communicationMethodType != null)
        return false;
    } else if (!communicationMethodType.equals(other.communicationMethodType))
      return false;
    if (confidentialWaiverIndicator == null) {
      if (other.confidentialWaiverIndicator != null)
        return false;
    } else if (!confidentialWaiverIndicator.equals(other.confidentialWaiverIndicator))
      return false;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (drmsMandatedRprtrFeedback == null) {
      if (other.drmsMandatedRprtrFeedback != null)
        return false;
    } else if (!drmsMandatedRprtrFeedback.equals(other.drmsMandatedRprtrFeedback))
      return false;
    if (employerName == null) {
      if (other.employerName != null)
        return false;
    } else if (!employerName.equals(other.employerName))
      return false;
    if (feedbackDate == null) {
      if (other.feedbackDate != null)
        return false;
    } else if (!feedbackDate.equals(other.feedbackDate))
      return false;
    if (feedbackRequiredIndicator == null) {
      if (other.feedbackRequiredIndicator != null)
        return false;
    } else if (!feedbackRequiredIndicator.equals(other.feedbackRequiredIndicator))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (lawEnforcementId == null) {
      if (other.lawEnforcementId != null)
        return false;
    } else if (!lawEnforcementId.equals(other.lawEnforcementId))
      return false;
    if (mandatedReporterIndicator == null) {
      if (other.mandatedReporterIndicator != null)
        return false;
    } else if (!mandatedReporterIndicator.equals(other.mandatedReporterIndicator))
      return false;
    if (messagePhoneExtensionNumber == null) {
      if (other.messagePhoneExtensionNumber != null)
        return false;
    } else if (!messagePhoneExtensionNumber.equals(other.messagePhoneExtensionNumber))
      return false;
    if (messagePhoneNumber == null) {
      if (other.messagePhoneNumber != null)
        return false;
    } else if (!messagePhoneNumber.equals(other.messagePhoneNumber))
      return false;
    if (middleInitialName == null) {
      if (other.middleInitialName != null)
        return false;
    } else if (!middleInitialName.equals(other.middleInitialName))
      return false;
    if (namePrefixDescription == null) {
      if (other.namePrefixDescription != null)
        return false;
    } else if (!namePrefixDescription.equals(other.namePrefixDescription))
      return false;
    if (primaryPhoneExtensionNumber == null) {
      if (other.primaryPhoneExtensionNumber != null)
        return false;
    } else if (!primaryPhoneExtensionNumber.equals(other.primaryPhoneExtensionNumber))
      return false;
    if (primaryPhoneNumber == null) {
      if (other.primaryPhoneNumber != null)
        return false;
    } else if (!primaryPhoneNumber.equals(other.primaryPhoneNumber))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (stateCodeType == null) {
      if (other.stateCodeType != null)
        return false;
    } else if (!stateCodeType.equals(other.stateCodeType))
      return false;
    if (streetName == null) {
      if (other.streetName != null)
        return false;
    } else if (!streetName.equals(other.streetName))
      return false;
    if (streetNumber == null) {
      if (other.streetNumber != null)
        return false;
    } else if (!streetNumber.equals(other.streetNumber))
      return false;
    if (suffixTitleDescription == null) {
      if (other.suffixTitleDescription != null)
        return false;
    } else if (!suffixTitleDescription.equals(other.suffixTitleDescription))
      return false;
    if (zipNumber == null) {
      if (other.zipNumber != null)
        return false;
    } else if (!zipNumber.equals(other.zipNumber))
      return false;
    if (zipSuffixNumber == null) {
      if (other.zipSuffixNumber != null)
        return false;
    } else if (!zipSuffixNumber.equals(other.zipSuffixNumber))
      return false;
    return true;
  }
}
