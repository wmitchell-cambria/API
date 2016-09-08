package gov.ca.cwds.rest.api.domain.legacy;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.core.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a CrossReport
 * 
 * @author CWDS API Team
 */
@ApiModel
@InjectLinks({
    @InjectLink(value = "/{resource}/{id}", rel = "self", style = Style.ABSOLUTE, bindings = {
        @Binding(name = "id",
            value = "referralId=${instance.referralId},thirdId=${instance.thirdId}"),
        @Binding(name = "resource", value = Api.RESOURCE_CROSS_REPORT)}),
    @InjectLink(value = "/{resource}/{id}", rel = "referralId", style = Style.ABSOLUTE,
        bindings = {@Binding(name = "id", value = "${instance.referralId}"),
            @Binding(name = "resource", value = Api.RESOURCE_REFERRAL)},
        condition = "${not empty instance.referralId }"),
    @InjectLink(value = "/{resource}/{id}", rel = "staffPersonId", style = Style.ABSOLUTE,
        bindings = {@Binding(name = "id", value = "${instance.staffPersonId}"),
            @Binding(name = "resource", value = Api.RESOURCE_STAFF_PERSON)},
        condition = "${not empty instance.staffPersonId }"),
    @InjectLink(value = "/{resource}/{id}", rel = "lawEnforcementId", style = Style.ABSOLUTE,
        bindings = {@Binding(name = "id", value = "${instance.lawEnforcementId}"),
            @Binding(name = "resource", value = Api.RESOURCE_LAW_ENFORCEMENT)})})
public class CrossReport extends DomainObject {
  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC123")
  private String thirdId;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short crossReportMethodType;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean filedOutOfStateIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean governmentOrgCrossRptIndicatorVar;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "informTime")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, example = "16.41.49")
  private String informTime;

  @NotEmpty
  @Size(min = 1, max = 6)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String recipientBadgeNumber;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Integer recipientPhoneExtensionNumber;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private BigDecimal recipientPhoneNumber;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "informDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = true)
  @ApiModelProperty(required = true, readOnly = false, value = "yyyy-MM-dd", example = "2000-01-01")
  private String informDate;

  @NotEmpty
  @Size(min = 1, max = 30)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String recipientPositionTitleDesc;

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String referenceNumber;

  @NotEmpty
  @Size(min = 1, max = 10)
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC123")
  private String referralId;

  @Size(max = 10)
  @ApiModelProperty(required = false, readOnly = true, value = "", example = "ABC123")
  private String lawEnforcementId;

  @NotEmpty
  @Size(min = 1, max = 3)
  @ApiModelProperty(required = true, readOnly = true, value = "", example = "ABC")
  private String staffPersonId;

  @NotEmpty
  @Size(min = 1, max = 120)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String description;

  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String recipientName;

  @NotEmpty
  @Size(min = 1, max = 254)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "ABC123")
  private String outstateLawEnforcementAddr;

  @NotEmpty
  @Size(min = 1, max = 2)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "AB")
  private String countySpecificCode;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean lawEnforcementIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean outStateLawEnforcementIndicator;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false)
  private Boolean satisfyCrossReportIndicator;

  @JsonCreator
  public CrossReport(@JsonProperty("thirdId") String thirdId,
      @JsonProperty("crossReportMethodType") Short crossReportMethodType,
      @JsonProperty("filedOutOfStateIndicator") Boolean filedOutOfStateIndicator,
      @JsonProperty("governmentOrgCrossRptIndicatorVar") Boolean governmentOrgCrossRptIndicatorVar,
      @JsonProperty("informTime") String informTime,
      @JsonProperty("recipientBadgeNumber") String recipientBadgeNumber,
      @JsonProperty("recipientPhoneExtensionNumber") Integer recipientPhoneExtensionNumber,
      @JsonProperty("recipientPhoneNumber") BigDecimal recipientPhoneNumber,
      @JsonProperty("informDate") String informDate,
      @JsonProperty("recipientPositionTitleDesc") String recipientPositionTitleDesc,
      @JsonProperty("referenceNumber") String referenceNumber,
      @JsonProperty("referralId") String referralId,
      @JsonProperty("lawEnforcementId") String lawEnforcementId,
      @JsonProperty("staffPersonId") String staffPersonId,
      @JsonProperty("description") String description,
      @JsonProperty("recipientName") String recipientName,
      @JsonProperty("outstateLawEnforcementAddr") String outstateLawEnforcementAddr,
      @JsonProperty("countySpecificCode") String countySpecificCode,
      @JsonProperty("lawEnforcementIndicator") Boolean lawEnforcementIndicator,
      @JsonProperty("outStateLawEnforcementIndicator") Boolean outStateLawEnforcementIndicator,
      @JsonProperty("satisfyCrossReportIndicator") Boolean satisfyCrossReportIndicator) {
    super();
    this.thirdId = thirdId;
    this.crossReportMethodType = crossReportMethodType;
    this.filedOutOfStateIndicator = filedOutOfStateIndicator;
    this.governmentOrgCrossRptIndicatorVar = governmentOrgCrossRptIndicatorVar;
    this.informTime = informTime;
    this.recipientBadgeNumber = recipientBadgeNumber;
    this.recipientPhoneExtensionNumber = recipientPhoneExtensionNumber;
    this.recipientPhoneNumber = recipientPhoneNumber;
    this.informDate = informDate;
    this.recipientPositionTitleDesc = recipientPositionTitleDesc;
    this.referenceNumber = referenceNumber;
    this.referralId = referralId;
    this.lawEnforcementId = lawEnforcementId;
    this.staffPersonId = staffPersonId;
    this.description = description;
    this.recipientName = recipientName;
    this.outstateLawEnforcementAddr = outstateLawEnforcementAddr;
    this.countySpecificCode = countySpecificCode;
    this.lawEnforcementIndicator = lawEnforcementIndicator;
    this.outStateLawEnforcementIndicator = outStateLawEnforcementIndicator;
    this.satisfyCrossReportIndicator = satisfyCrossReportIndicator;
  }

  public CrossReport(gov.ca.cwds.rest.api.persistence.legacy.CrossReport persistedCrossReport) {
    this.referralId = persistedCrossReport.getReferralId();
    this.thirdId = persistedCrossReport.getThirdId();
    this.crossReportMethodType = persistedCrossReport.getCrossReportMethodType();
    this.filedOutOfStateIndicator =
        DomainObject.uncookBooleanString(persistedCrossReport.getFiledOutOfStateIndicator());
    this.governmentOrgCrossRptIndicatorVar =
        DomainObject.uncookBooleanString(persistedCrossReport
            .getGovernmentOrgCrossRptIndicatorVar());
    this.informTime = DomainObject.cookTime(persistedCrossReport.getInformTime());
    this.recipientBadgeNumber = persistedCrossReport.getRecipientBadgeNumber();
    this.recipientPhoneExtensionNumber = persistedCrossReport.getRecipientPhoneExtensionNumber();
    this.recipientPhoneNumber = persistedCrossReport.getRecipientPhoneNumber();
    this.informDate = DomainObject.cookDate(persistedCrossReport.getInformDate());
    this.recipientPositionTitleDesc = persistedCrossReport.getRecipientPositionTitleDesc();
    this.referenceNumber = persistedCrossReport.getReferenceNumber();
    this.lawEnforcementId = persistedCrossReport.getLawEnforcementId();
    this.staffPersonId = persistedCrossReport.getStaffPersonId();
    this.description = persistedCrossReport.getDescription();
    this.recipientName = persistedCrossReport.getRecipientName();
    this.outstateLawEnforcementAddr = persistedCrossReport.getOutstateLawEnforcementAddr();
    this.countySpecificCode = persistedCrossReport.getCountySpecificCode();
    this.lawEnforcementIndicator =
        DomainObject.uncookBooleanString(persistedCrossReport.getLawEnforcementIndicator());
    this.outStateLawEnforcementIndicator =
        DomainObject.uncookBooleanString(persistedCrossReport.getOutStateLawEnforcementIndicator());
    this.satisfyCrossReportIndicator =
        DomainObject.uncookBooleanString(persistedCrossReport.getSatisfyCrossReportIndicator());
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the crossReportMethodType
   */
  public Short getCrossReportMethodType() {
    return crossReportMethodType;
  }

  /**
   * @return the filedOutOfStateIndicator
   */
  public Boolean getFiledOutOfStateIndicator() {
    return filedOutOfStateIndicator;
  }

  /**
   * @return the governmentOrgCrossRptIndicatorVar
   */
  public Boolean getGovernmentOrgCrossRptIndicatorVar() {
    return governmentOrgCrossRptIndicatorVar;
  }

  /**
   * @return the informTime
   */
  public String getInformTime() {
    return informTime;
  }

  /**
   * @return the recipientBadgeNumber
   */
  public String getRecipientBadgeNumber() {
    return recipientBadgeNumber;
  }

  /**
   * @return the recipientPhoneExtensionNumber
   */
  public Integer getRecipientPhoneExtensionNumber() {
    return recipientPhoneExtensionNumber;
  }

  /**
   * @return the recipientPhoneNumber
   */
  public BigDecimal getRecipientPhoneNumber() {
    return recipientPhoneNumber;
  }

  /**
   * @return the informDate
   */
  public String getInformDate() {
    return informDate;
  }

  /**
   * @return the recipientPositionTitleDesc
   */
  public String getRecipientPositionTitleDesc() {
    return recipientPositionTitleDesc;
  }

  /**
   * @return the referenceNumber
   */
  public String getReferenceNumber() {
    return referenceNumber;
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
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return staffPersonId;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the recipientName
   */
  public String getRecipientName() {
    return recipientName;
  }

  /**
   * @return the outstateLawEnforcementAddr
   */
  public String getOutstateLawEnforcementAddr() {
    return outstateLawEnforcementAddr;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the lawEnforcementIndicator
   */
  public Boolean getLawEnforcementIndicator() {
    return lawEnforcementIndicator;
  }

  /**
   * @return the outStateLawEnforcementIndicator
   */
  public Boolean getOutStateLawEnforcementIndicator() {
    return outStateLawEnforcementIndicator;
  }

  /**
   * @return the satisfyCrossReportIndicator
   */
  public Boolean getSatisfyCrossReportIndicator() {
    return satisfyCrossReportIndicator;
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
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result =
        prime * result + ((crossReportMethodType == null) ? 0 : crossReportMethodType.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result =
        prime * result
            + ((filedOutOfStateIndicator == null) ? 0 : filedOutOfStateIndicator.hashCode());
    result =
        prime
            * result
            + ((governmentOrgCrossRptIndicatorVar == null) ? 0 : governmentOrgCrossRptIndicatorVar
                .hashCode());
    result = prime * result + ((informDate == null) ? 0 : informDate.hashCode());
    result = prime * result + ((informTime == null) ? 0 : informTime.hashCode());
    result = prime * result + ((lawEnforcementId == null) ? 0 : lawEnforcementId.hashCode());
    result =
        prime * result
            + ((lawEnforcementIndicator == null) ? 0 : lawEnforcementIndicator.hashCode());
    result =
        prime
            * result
            + ((outStateLawEnforcementIndicator == null) ? 0 : outStateLawEnforcementIndicator
                .hashCode());
    result =
        prime * result
            + ((outstateLawEnforcementAddr == null) ? 0 : outstateLawEnforcementAddr.hashCode());
    result =
        prime * result + ((recipientBadgeNumber == null) ? 0 : recipientBadgeNumber.hashCode());
    result = prime * result + ((recipientName == null) ? 0 : recipientName.hashCode());
    result =
        prime
            * result
            + ((recipientPhoneExtensionNumber == null) ? 0 : recipientPhoneExtensionNumber
                .hashCode());
    result =
        prime * result + ((recipientPhoneNumber == null) ? 0 : recipientPhoneNumber.hashCode());
    result =
        prime * result
            + ((recipientPositionTitleDesc == null) ? 0 : recipientPositionTitleDesc.hashCode());
    result = prime * result + ((referenceNumber == null) ? 0 : referenceNumber.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result =
        prime * result
            + ((satisfyCrossReportIndicator == null) ? 0 : satisfyCrossReportIndicator.hashCode());
    result = prime * result + ((staffPersonId == null) ? 0 : staffPersonId.hashCode());
    result = prime * result + ((thirdId == null) ? 0 : thirdId.hashCode());
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
    CrossReport other = (CrossReport) obj;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (crossReportMethodType == null) {
      if (other.crossReportMethodType != null)
        return false;
    } else if (!crossReportMethodType.equals(other.crossReportMethodType))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.trim().equals(other.description.trim()))
      return false;
    if (filedOutOfStateIndicator == null) {
      if (other.filedOutOfStateIndicator != null)
        return false;
    } else if (!filedOutOfStateIndicator.equals(other.filedOutOfStateIndicator))
      return false;
    if (governmentOrgCrossRptIndicatorVar == null) {
      if (other.governmentOrgCrossRptIndicatorVar != null)
        return false;
    } else if (!governmentOrgCrossRptIndicatorVar.equals(other.governmentOrgCrossRptIndicatorVar))
      return false;
    if (informDate == null) {
      if (other.informDate != null)
        return false;
    } else if (!informDate.equals(other.informDate))
      return false;
    if (informTime == null) {
      if (other.informTime != null)
        return false;
    } else if (!informTime.equals(other.informTime))
      return false;
    if (lawEnforcementId == null) {
      if (other.lawEnforcementId != null)
        return false;
    } else if (!lawEnforcementId.equals(other.lawEnforcementId))
      return false;
    if (lawEnforcementIndicator == null) {
      if (other.lawEnforcementIndicator != null)
        return false;
    } else if (!lawEnforcementIndicator.equals(other.lawEnforcementIndicator))
      return false;
    if (outStateLawEnforcementIndicator == null) {
      if (other.outStateLawEnforcementIndicator != null)
        return false;
    } else if (!outStateLawEnforcementIndicator.equals(other.outStateLawEnforcementIndicator))
      return false;
    if (outstateLawEnforcementAddr == null) {
      if (other.outstateLawEnforcementAddr != null)
        return false;
    } else if (!outstateLawEnforcementAddr.trim().equals(other.outstateLawEnforcementAddr.trim()))
      return false;
    if (recipientBadgeNumber == null) {
      if (other.recipientBadgeNumber != null)
        return false;
    } else if (!recipientBadgeNumber.trim().equals(other.recipientBadgeNumber.trim()))
      return false;
    if (recipientName == null) {
      if (other.recipientName != null)
        return false;
    } else if (!recipientName.trim().equals(other.recipientName.trim()))
      return false;
    if (recipientPhoneExtensionNumber == null) {
      if (other.recipientPhoneExtensionNumber != null)
        return false;
    } else if (!recipientPhoneExtensionNumber.equals(other.recipientPhoneExtensionNumber))
      return false;
    if (recipientPhoneNumber == null) {
      if (other.recipientPhoneNumber != null)
        return false;
    } else if (!recipientPhoneNumber.equals(other.recipientPhoneNumber))
      return false;
    if (recipientPositionTitleDesc == null) {
      if (other.recipientPositionTitleDesc != null)
        return false;
    } else if (!recipientPositionTitleDesc.trim().equals(other.recipientPositionTitleDesc.trim()))
      return false;
    if (referenceNumber == null) {
      if (other.referenceNumber != null)
        return false;
    } else if (!referenceNumber.trim().equals(other.referenceNumber.trim()))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (satisfyCrossReportIndicator == null) {
      if (other.satisfyCrossReportIndicator != null)
        return false;
    } else if (!satisfyCrossReportIndicator.equals(other.satisfyCrossReportIndicator))
      return false;
    if (staffPersonId == null) {
      if (other.staffPersonId != null)
        return false;
    } else if (!staffPersonId.equals(other.staffPersonId))
      return false;
    if (thirdId == null) {
      if (other.thirdId != null)
        return false;
    } else if (!thirdId.equals(other.thirdId))
      return false;
    return true;
  }

}
