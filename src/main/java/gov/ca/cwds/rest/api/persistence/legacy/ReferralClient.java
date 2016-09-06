package gov.ca.cwds.rest.api.persistence.legacy;

import java.io.Serializable;
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
import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey;

/**
 * {@link PersistentObject} representing a ReferralClient
 * 
 * @author CWDS API Team
 */
@Entity
@Table(schema = "CWSINT", name = "REFR_CLT")
@IdClass(PrimaryKey.class)
public class ReferralClient extends PersistentObject {

  @Id
  @Column(name = "FKREFERL_T")
  private String referralId;

  @Id
  @Column(name = "FKCLIENT_T")
  private String clientId;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @Type(type = "short")
  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Type(type = "short")
  @Column(name = "DSP_RSNC")
  private Short dispositionClosureReasonType;

  @Column(name = "DISPSTN_CD")
  private String dispositionCode;

  @Type(type = "date")
  @Column(name = "RCL_DISPDT")
  private Date dispositionDate;

  @Column(name = "SLFRPT_IND")
  private String selfReportedIndicator;

  @Column(name = "STFADD_IND")
  private String staffPersonAddedIndicator;

  @Column(name = "DSP_CLSDSC")
  private String dispositionClosureDescription;

  @Type(type = "short")
  @Column(name = "RFCL_AGENO")
  private Short ageNumber;

  @Column(name = "AGE_PRD_CD")
  private String agePeriodCode;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "MHLTH_IND")
  private String mentalHealthIssuesIndicator;

  @Column(name = "ALCHL_IND")
  private String alcoholIndicator;

  @Column(name = "DRUG_IND")
  private String drugIndicator;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ReferralClient() {
    super();
  }

  /**
   * Constructor
   * 
   * @param referralClient The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public ReferralClient(gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient,
      String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.referralId = referralClient.getReferralId();
      this.clientId = referralClient.getClientId();
      this.approvalNumber = referralClient.getApprovalNumber();
      this.approvalStatusType = referralClient.getApprovalStatusType();
      this.dispositionClosureReasonType = referralClient.getDispositionClosureReasonType();
      this.dispositionCode = referralClient.getDispositionCode();
      this.dispositionDate = DomainObject.uncookDateString(referralClient.getDispositionDate());
      this.selfReportedIndicator =
          DomainObject.cookBoolean(referralClient.getSelfReportedIndicator());
      this.staffPersonAddedIndicator =
          DomainObject.cookBoolean(referralClient.getStaffPersonAddedIndicator());
      this.dispositionClosureDescription = referralClient.getDispositionClosureDescription();
      this.ageNumber = referralClient.getAgeNumber();
      this.agePeriodCode = referralClient.getAgePeriodCode();
      this.countySpecificCode = referralClient.getCountySpecificCode();
      this.mentalHealthIssuesIndicator =
          DomainObject.cookBoolean(referralClient.getMentalHealthIssuesIndicator());
      this.alcoholIndicator = DomainObject.cookBoolean(referralClient.getAlcoholIndicator());
      this.drugIndicator = DomainObject.cookBoolean(referralClient.getDrugIndicator());

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
    return new PrimaryKey(this.getReferralId(), this.getClientId());
  }

  /**
   * @return the approvalNumber
   */
  public String getApprovalNumber() {
    return StringUtils.trimToEmpty(approvalNumber);
  }

  /**
   * @return the approvalStatusType
   */
  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  /**
   * @return the dispositionClosureReasonType
   */
  public Short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  /**
   * @return the dispositionCode
   */
  public String getDispositionCode() {
    return StringUtils.trimToEmpty(dispositionCode);
  }

  /**
   * @return the dispositionDate
   */
  public Date getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the selfReportedIndicator
   */
  public String getSelfReportedIndicator() {
    return StringUtils.trimToEmpty(selfReportedIndicator);
  }

  /**
   * @return the staffPersonAddedIndicator
   */
  public String getStaffPersonAddedIndicator() {
    return StringUtils.trimToEmpty(staffPersonAddedIndicator);
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return StringUtils.trimToEmpty(referralId);
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return StringUtils.trimToEmpty(clientId);
  }

  /**
   * @return the dispositionClosureDescription
   */
  public String getDispositionClosureDescription() {
    return StringUtils.trimToEmpty(dispositionClosureDescription);
  }

  /**
   * @return the ageNumber
   */
  public Short getAgeNumber() {
    return ageNumber;
  }

  /**
   * @return the agePeriodCode
   */
  public String getAgePeriodCode() {
    return StringUtils.trimToEmpty(agePeriodCode);
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return StringUtils.trimToEmpty(countySpecificCode);
  }

  /**
   * @return the mentalHealthIssuesIndicator
   */
  public String getMentalHealthIssuesIndicator() {
    return StringUtils.trimToEmpty(mentalHealthIssuesIndicator);
  }

  /**
   * @return the alcoholIndicator
   */
  public String getAlcoholIndicator() {
    return StringUtils.trimToEmpty(alcoholIndicator);
  }

  /**
   * @return the drugIndicator
   */
  public String getDrugIndicator() {
    return StringUtils.trimToEmpty(drugIndicator);
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
    result = prime * result + ((ageNumber == null) ? 0 : ageNumber.hashCode());
    result = prime * result + ((agePeriodCode == null) ? 0 : agePeriodCode.hashCode());
    result = prime * result + ((alcoholIndicator == null) ? 0 : alcoholIndicator.hashCode());
    result = prime * result + ((approvalNumber == null) ? 0 : approvalNumber.hashCode());
    result = prime * result + ((approvalStatusType == null) ? 0 : approvalStatusType.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result = prime * result + ((countySpecificCode == null) ? 0 : countySpecificCode.hashCode());
    result =
        prime
            * result
            + ((dispositionClosureDescription == null) ? 0 : dispositionClosureDescription
                .hashCode());
    result =
        prime
            * result
            + ((dispositionClosureReasonType == null) ? 0 : dispositionClosureReasonType.hashCode());
    result = prime * result + ((dispositionCode == null) ? 0 : dispositionCode.hashCode());
    result = prime * result + ((dispositionDate == null) ? 0 : dispositionDate.hashCode());
    result = prime * result + ((drugIndicator == null) ? 0 : drugIndicator.hashCode());
    result =
        prime * result
            + ((mentalHealthIssuesIndicator == null) ? 0 : mentalHealthIssuesIndicator.hashCode());
    result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
    result =
        prime * result + ((selfReportedIndicator == null) ? 0 : selfReportedIndicator.hashCode());
    result =
        prime * result
            + ((staffPersonAddedIndicator == null) ? 0 : staffPersonAddedIndicator.hashCode());
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
    ReferralClient other = (ReferralClient) obj;
    if (ageNumber == null) {
      if (other.ageNumber != null)
        return false;
    } else if (!ageNumber.equals(other.ageNumber))
      return false;
    if (agePeriodCode == null) {
      if (other.agePeriodCode != null)
        return false;
    } else if (!agePeriodCode.equals(other.agePeriodCode))
      return false;
    if (alcoholIndicator == null) {
      if (other.alcoholIndicator != null)
        return false;
    } else if (!alcoholIndicator.equals(other.alcoholIndicator))
      return false;
    if (approvalNumber == null) {
      if (other.approvalNumber != null)
        return false;
    } else if (!approvalNumber.equals(other.approvalNumber))
      return false;
    if (approvalStatusType == null) {
      if (other.approvalStatusType != null)
        return false;
    } else if (!approvalStatusType.equals(other.approvalStatusType))
      return false;
    if (clientId == null) {
      if (other.clientId != null)
        return false;
    } else if (!clientId.equals(other.clientId))
      return false;
    if (countySpecificCode == null) {
      if (other.countySpecificCode != null)
        return false;
    } else if (!countySpecificCode.equals(other.countySpecificCode))
      return false;
    if (dispositionClosureDescription == null) {
      if (other.dispositionClosureDescription != null)
        return false;
    } else if (!dispositionClosureDescription.equals(other.dispositionClosureDescription))
      return false;
    if (dispositionClosureReasonType == null) {
      if (other.dispositionClosureReasonType != null)
        return false;
    } else if (!dispositionClosureReasonType.equals(other.dispositionClosureReasonType))
      return false;
    if (dispositionCode == null) {
      if (other.dispositionCode != null)
        return false;
    } else if (!dispositionCode.equals(other.dispositionCode))
      return false;
    if (dispositionDate == null) {
      if (other.dispositionDate != null)
        return false;
    } else if (!dispositionDate.equals(other.dispositionDate))
      return false;
    if (drugIndicator == null) {
      if (other.drugIndicator != null)
        return false;
    } else if (!drugIndicator.equals(other.drugIndicator))
      return false;
    if (mentalHealthIssuesIndicator == null) {
      if (other.mentalHealthIssuesIndicator != null)
        return false;
    } else if (!mentalHealthIssuesIndicator.equals(other.mentalHealthIssuesIndicator))
      return false;
    if (referralId == null) {
      if (other.referralId != null)
        return false;
    } else if (!referralId.equals(other.referralId))
      return false;
    if (selfReportedIndicator == null) {
      if (other.selfReportedIndicator != null)
        return false;
    } else if (!selfReportedIndicator.equals(other.selfReportedIndicator))
      return false;
    if (staffPersonAddedIndicator == null) {
      if (other.staffPersonAddedIndicator != null)
        return false;
    } else if (!staffPersonAddedIndicator.equals(other.staffPersonAddedIndicator))
      return false;
    return true;
  }



  public static class PrimaryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private String referralId;
    private String clientId;

    public PrimaryKey() {}

    public PrimaryKey(String referralId, String clientId) {
      this.referralId = referralId;
      this.clientId = clientId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "referralId=" + referralId.trim() + ",clientId=" + clientId.trim();
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
      result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
      result = prime * result + ((referralId == null) ? 0 : referralId.hashCode());
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
      if (clientId == null) {
        if (other.clientId != null)
          return false;
      } else if (!clientId.equals(other.clientId))
        return false;
      if (referralId == null) {
        if (other.referralId != null)
          return false;
      } else if (!referralId.equals(other.referralId))
        return false;
      return true;
    }
  }

}
