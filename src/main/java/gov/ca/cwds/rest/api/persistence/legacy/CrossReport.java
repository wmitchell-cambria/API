package gov.ca.cwds.rest.api.persistence.legacy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.legacy.CrossReport.PrimaryKey;

/**
 * {@link PersistentObject} representing a CrossReport
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CRSS_RPT")
@IdClass(PrimaryKey.class)
public class CrossReport extends PersistentObject {
  @Id
  @Column(name = "FKREFERL_T")
  private String referralId;

  @Id
  @Column(name = "THIRD_ID")
  private String thirdId;

  @Type(type = "short")
  @Column(name = "XRPT_MTC")
  private Short crossReportMethodType;

  @Column(name = "OUT_ST_IND")
  private String filedOutOfStateIndicator;

  @Column(name = "GV_XRPT_B")
  private String governmentOrgCrossRptIndicatorVar;

  @Type(type = "time")
  @Column(name = "INFORM_TM")
  private Date informTime;

  @Column(name = "RCPT_BDGNO")
  private String recipientBadgeNumber;

  @Type(type = "integer")
  @Column(name = "RCPT_EXTNO")
  private Integer recipientPhoneExtensionNumber;

  @Column(name = "RCPT_TELNO")
  private BigDecimal recipientPhoneNumber;

  @Type(type = "date")
  @Column(name = "INFORM_DT")
  private Date informDate;

  @Column(name = "POS_TILDSC")
  private String recipientPositionTitleDesc;

  @Column(name = "REFERNC_NO")
  private String referenceNumber;

  @Column(name = "FKLAW_ENFT")
  private String lawEnforcementId;

  @Column(name = "FKSTFPERST")
  private String staffPersonId;

  @Column(name = "XRPT_DSC")
  private String description;

  @Column(name = "RECIPNT_NM")
  private String recipientName;

  @Column(name = "OSLWNFADDR")
  private String outstateLawEnforcementAddr;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "LAW_IND")
  private String lawEnforcementIndicator;

  @Column(name = "OS_LAW_IND")
  private String outStateLawEnforcementIndicator;

  @Column(name = "SXRPT_IND")
  private String satisfyCrossReportIndicator;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CrossReport() {
    super();
  }

  /**
   * Constructor
   * 
   * @param crossReport The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public CrossReport(gov.ca.cwds.rest.api.domain.legacy.CrossReport crossReport,
      String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.referralId = crossReport.getReferralId();
      this.thirdId = crossReport.getThirdId();
      this.crossReportMethodType = crossReport.getCrossReportMethodType();
      this.filedOutOfStateIndicator =
          DomainObject.cookBoolean(crossReport.getFiledOutOfStateIndicator());
      this.governmentOrgCrossRptIndicatorVar =
          DomainObject.cookBoolean(crossReport.getGovernmentOrgCrossRptIndicatorVar());
      this.informTime = DomainObject.uncookTimestampString(crossReport.getInformTime());
      this.recipientBadgeNumber = crossReport.getRecipientBadgeNumber();
      this.recipientPhoneExtensionNumber = crossReport.getRecipientPhoneExtensionNumber();
      this.recipientPhoneNumber = crossReport.getRecipientPhoneNumber();
      this.informDate = DomainObject.uncookDateString(crossReport.getInformDate());
      this.recipientPositionTitleDesc = crossReport.getRecipientPositionTitleDesc();
      this.referenceNumber = crossReport.getReferenceNumber();
      this.lawEnforcementId = crossReport.getLawEnforcementId();
      this.staffPersonId = crossReport.getStaffPersonId();
      this.description = crossReport.getDescription();
      this.recipientName = crossReport.getRecipientName();
      this.outstateLawEnforcementAddr = crossReport.getOutstateLawEnforcementAddr();
      this.countySpecificCode = crossReport.getCountySpecificCode();
      this.lawEnforcementIndicator =
          DomainObject.cookBoolean(crossReport.getLawEnforcementIndicator());
      this.outStateLawEnforcementIndicator =
          DomainObject.cookBoolean(crossReport.getOutStateLawEnforcementIndicator());
      this.satisfyCrossReportIndicator =
          DomainObject.cookBoolean(crossReport.getSatisfyCrossReportIndicator());
    } catch (DomainException e) {
      throw new PersistenceException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return new PrimaryKey(this.getReferralId(), this.getThirdId());
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return StringUtils.trimToEmpty(thirdId);
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
  public String getFiledOutOfStateIndicator() {
    return StringUtils.trimToEmpty(filedOutOfStateIndicator);
  }

  /**
   * @return the governmentOrgCrossRptIndicatorVar
   */
  public String getGovernmentOrgCrossRptIndicatorVar() {
    return StringUtils.trimToEmpty(governmentOrgCrossRptIndicatorVar);
  }

  /**
   * @return the informTime
   */
  public Date getInformTime() {
    return informTime;
  }

  /**
   * @return the recipientBadgeNumber
   */
  public String getRecipientBadgeNumber() {
    return StringUtils.trimToEmpty(recipientBadgeNumber);
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
  public Date getInformDate() {
    return informDate;
  }

  /**
   * @return the recipientPositionTitleDesc
   */
  public String getRecipientPositionTitleDesc() {
    return StringUtils.trimToEmpty(recipientPositionTitleDesc);
  }

  /**
   * @return the referenceNumber
   */
  public String getReferenceNumber() {
    return StringUtils.trimToEmpty(referenceNumber);
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return StringUtils.trimToEmpty(referralId);
  }

  /**
   * @return the lawEnforcementId
   */
  public String getLawEnforcementId() {
    return StringUtils.trimToEmpty(lawEnforcementId);
  }

  /**
   * @return the staffPersonId
   */
  public String getStaffPersonId() {
    return StringUtils.trimToEmpty(staffPersonId);
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return StringUtils.trimToEmpty(description);
  }

  /**
   * @return the recipientName
   */
  public String getRecipientName() {
    return StringUtils.trimToEmpty(recipientName);
  }

  /**
   * @return the outstateLawEnforcementAddr
   */
  public String getOutstateLawEnforcementAddr() {
    return StringUtils.trimToEmpty(outstateLawEnforcementAddr);
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return StringUtils.trimToEmpty(countySpecificCode);
  }

  /**
   * @return the lawEnforcementIndicator
   */
  public String getLawEnforcementIndicator() {
    return StringUtils.trimToEmpty(lawEnforcementIndicator);
  }

  /**
   * @return the outStateLawEnforcementIndicator
   */
  public String getOutStateLawEnforcementIndicator() {
    return StringUtils.trimToEmpty(outStateLawEnforcementIndicator);
  }

  /**
   * @return the satisfyCrossReportIndicator
   */
  public String getSatisfyCrossReportIndicator() {
    return StringUtils.trimToEmpty(satisfyCrossReportIndicator);
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
    } else if (!description.equals(other.description))
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
    } else if (!outstateLawEnforcementAddr.equals(other.outstateLawEnforcementAddr))
      return false;
    if (recipientBadgeNumber == null) {
      if (other.recipientBadgeNumber != null)
        return false;
    } else if (!recipientBadgeNumber.equals(other.recipientBadgeNumber))
      return false;
    if (recipientName == null) {
      if (other.recipientName != null)
        return false;
    } else if (!recipientName.equals(other.recipientName))
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
    } else if (!recipientPositionTitleDesc.equals(other.recipientPositionTitleDesc))
      return false;
    if (referenceNumber == null) {
      if (other.referenceNumber != null)
        return false;
    } else if (!referenceNumber.equals(other.referenceNumber))
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

  public static class PrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private String referralId;
    private String thirdId;

    public PrimaryKey() {}

    public PrimaryKey(String referralId, String thirdId) {
      this.referralId = referralId;
      this.thirdId = thirdId;
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
      result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
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
      PrimaryKey other = (PrimaryKey) obj;
      if (referralId == null) {
        if (other.referralId != null)
          return false;
      } else if (!referralId.equals(other.referralId))
        return false;
      if (thirdId == null) {
        if (other.thirdId != null)
          return false;
      } else if (!thirdId.equals(other.thirdId))
        return false;
      return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "referralId=" + referralId.trim() + ",thirdId=" + thirdId.trim();
    }
  }
}
