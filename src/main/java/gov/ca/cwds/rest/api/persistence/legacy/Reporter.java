package gov.ca.cwds.rest.api.persistence.legacy;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Reporter
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "REPTR_T")
public class Reporter extends PersistentObject {

  @Id
  @Column(name = "FKREFERL_T")
  private String referralId;

  @Column(name = "RPTR_BDGNO")
  private String badgeNumber;

  @Column(name = "RPTR_CTYNM")
  private String cityName;

  @Type(type = "short")
  @Column(name = "COL_RELC")
  private Short colltrClientRptrReltnshpType;

  @Type(type = "short")
  @Column(name = "CMM_MTHC")
  private Short communicationMethodType;

  @Column(name = "CNFWVR_IND")
  private String confidentialWaiverIndicator;

  @Column(name = "FDBACK_DOC")
  private String drmsMandatedRprtrFeedback;

  @Column(name = "RPTR_EMPNM")
  private String employerName;

  @Type(type = "date")
  @Column(name = "FEEDBCK_DT")
  private Date feedbackDate;

  @Column(name = "FB_RQR_IND")
  private String feedbackRequiredIndicator;

  @Column(name = "RPTR_FSTNM")
  private String firstName;

  @Column(name = "RPTR_LSTNM")
  private String lastName;

  @Column(name = "MNRPTR_IND")
  private String mandatedReporterIndicator;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO")
  private Integer messagePhoneExtensionNumber;

  @Column(name = "MSG_TEL_NO")
  private BigDecimal messagePhoneNumber;

  @Column(name = "MID_INI_NM")
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC")
  private String namePrefixDescription;

  @Column(name = "PRM_TEL_NO")
  private BigDecimal primaryPhoneNumber;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO")
  private Integer primaryPhoneExtensionNumber;

  @Type(type = "short")
  @Column(name = "STATE_C")
  private Short stateCodeType;

  @Column(name = "RPTR_ST_NM")
  private String streetName;

  @Column(name = "RPTR_ST_NO")
  private String streetNumber;

  @Column(name = "SUFX_TLDSC")
  private String suffixTitleDescription;

  @Type(type = "integer")
  @Column(name = "RPTR_ZIPNO")
  private Integer zipNumber;

  @Column(name = "FKLAW_ENFT")
  private String lawEnforcementId;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO")
  private Short zipSuffixNumber;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Reporter() {
    super();
  }

  /**
   * Constructor
   * 
   * @param reporter The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Reporter(gov.ca.cwds.rest.api.domain.legacy.Reporter reporter, String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.badgeNumber = reporter.getBadgeNumber();
      this.cityName = reporter.getCityName();
      this.colltrClientRptrReltnshpType = reporter.getColltrClientRptrReltnshpType();
      this.communicationMethodType = reporter.getCommunicationMethodType();
      this.confidentialWaiverIndicator =
          DomainObject.cookBoolean(reporter.getConfidentialWaiverIndicator());
      this.drmsMandatedRprtrFeedback = reporter.getDrmsMandatedRprtrFeedback();
      this.employerName = reporter.getEmployerName();
      this.feedbackDate = DomainObject.uncookDateString(reporter.getFeedbackDate());
      this.feedbackRequiredIndicator =
          DomainObject.cookBoolean(reporter.getFeedbackRequiredIndicator());
      this.firstName = reporter.getFirstName();
      this.lastName = reporter.getLastName();
      this.mandatedReporterIndicator =
          DomainObject.cookBoolean(reporter.getMandatedReporterIndicator());
      this.messagePhoneExtensionNumber = reporter.getMessagePhoneExtensionNumber();
      this.messagePhoneNumber = reporter.getMessagePhoneNumber();
      this.middleInitialName = reporter.getMiddleInitialName();
      this.namePrefixDescription = reporter.getNamePrefixDescription();
      this.primaryPhoneNumber = reporter.getPrimaryPhoneNumber();
      this.primaryPhoneExtensionNumber = reporter.getPrimaryPhoneExtensionNumber();
      this.stateCodeType = reporter.getStateCodeType();
      this.streetName = reporter.getStreetName();
      this.streetNumber = reporter.getStreetNumber();
      this.suffixTitleDescription = reporter.getSuffixTitleDescription();
      this.zipNumber = DomainObject.uncookZipcodeString(reporter.getZipcode());
      this.referralId = reporter.getReferralId();
      this.lawEnforcementId = reporter.getLawEnforcementId();
      this.zipSuffixNumber = reporter.getZipSuffixNumber();
      this.countySpecificCode = reporter.getCountySpecificCode();
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
  public String getPrimaryKey() {
    return getReferralId();
  }

  /**
   * @return the badgeNumber
   */
  public String getBadgeNumber() {
    return StringUtils.trimToEmpty(badgeNumber);
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return StringUtils.trimToEmpty(cityName);
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
  public String getConfidentialWaiverIndicator() {
    return StringUtils.trimToEmpty(confidentialWaiverIndicator);
  }

  /**
   * @return the drmsMandatedRprtrFeedback
   */
  public String getDrmsMandatedRprtrFeedback() {
    return StringUtils.trimToEmpty(drmsMandatedRprtrFeedback);
  }

  /**
   * @return the employerName
   */
  public String getEmployerName() {
    return StringUtils.trimToEmpty(employerName);
  }

  /**
   * @return the feedbackDate
   */
  public Date getFeedbackDate() {
    return feedbackDate;
  }

  /**
   * @return the feedbackRequiredIndicator
   */
  public String getFeedbackRequiredIndicator() {
    return StringUtils.trimToEmpty(feedbackRequiredIndicator);
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return StringUtils.trimToEmpty(firstName);
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return StringUtils.trimToEmpty(lastName);
  }

  /**
   * @return the mandatedReporterIndicator
   */
  public String getMandatedReporterIndicator() {
    return StringUtils.trimToEmpty(mandatedReporterIndicator);
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
    return StringUtils.trimToEmpty(middleInitialName);
  }

  /**
   * @return the namePrefixDescription
   */
  public String getNamePrefixDescription() {
    return StringUtils.trimToEmpty(namePrefixDescription);
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
    return StringUtils.trimToEmpty(streetName);
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber() {
    return StringUtils.trimToEmpty(streetNumber);
  }

  /**
   * @return the suffixTitleDescription
   */
  public String getSuffixTitleDescription() {
    return StringUtils.trimToEmpty(suffixTitleDescription);
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
    return StringUtils.trimToEmpty(referralId);
  }

  /**
   * @return the lawEnforcementId
   */
  public String getLawEnforcementId() {
    return StringUtils.trimToEmpty(lawEnforcementId);
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
    return StringUtils.trimToEmpty(countySpecificCode);
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
    } else if (!namePrefixDescription.trim().equals(other.namePrefixDescription.trim()))
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
