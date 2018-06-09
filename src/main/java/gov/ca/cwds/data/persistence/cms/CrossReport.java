package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing a CrossReport.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CRSS_RPT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrossReport extends CmsPersistentObject {

  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referralId;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
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
  private Long recipientPhoneNumber;

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
  private String outStateLawEnforcementAddr;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "LAW_IND")
  private String lawEnforcementIndicator;

  @Column(name = "OS_LAW_IND")
  private String outStateLawEnforcementIndicator;

  @Column(name = "SXRPT_IND")
  private String satisfyCrossReportIndicator;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKREFERL_T", nullable = false, updatable = false, insertable = false)
  private Referral referral;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKLAW_ENFT", nullable = true, updatable = false, insertable = false)
  private LawEnforcementEntity lawEnforcement;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent referral record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKSTFPERST", nullable = false, updatable = false, insertable = false)
  private StaffPerson staffPerson;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public CrossReport() {
    super();
  }

  /**
   * @param referralId - Referral ID
   * @param thirdId - unique key to CrossReport table
   * @param crossReportMethodType - crossReportMethodType
   * @param filedOutOfStateIndicator - String filedOutOfStateIndicator
   * @param governmentOrgCrossRptIndicatorVar - String governmentOrgCrossRptIndicatorVar
   * @param informTime - Date informTime
   * @param recipientBadgeNumber - String recipientBadgeNumber
   * @param recipientPhoneExtensionNumber - Integer recipientPhoneExtensionNumber
   * @param recipientPhoneNumber - BigDecimal recipientPhoneNumber
   * @param informDate - Date informDate
   * @param recipientPositionTitleDesc - String recipientPositionTitleDesc
   * @param referenceNumber - String referenceNumber
   * @param lawEnforcementId - String lawEnforcementId
   * @param staffPersonId - String staffPersonId
   * @param description - String description
   * @param recipientName - String recipientName
   * @param outStateLawEnforcementAddr - String lawEnforcementId
   * @param countySpecificCode - String countySpecificCode
   * @param lawEnforcementIndicator - String lawEnforcementIndicator
   * @param outStateLawEnforcementIndicator - String outStateLawEnforcementIndicator
   * @param satisfyCrossReportIndicator - String satisfyCrossReportIndicator
   */
  public CrossReport(String referralId, String thirdId, Short crossReportMethodType,
      String filedOutOfStateIndicator, String governmentOrgCrossRptIndicatorVar, Date informTime,
      String recipientBadgeNumber, Integer recipientPhoneExtensionNumber, Long recipientPhoneNumber,
      Date informDate, String recipientPositionTitleDesc, String referenceNumber,
      String lawEnforcementId, String staffPersonId, String description, String recipientName,
      String outStateLawEnforcementAddr, String countySpecificCode, String lawEnforcementIndicator,
      String outStateLawEnforcementIndicator, String satisfyCrossReportIndicator) {
    super();
    this.referralId = referralId;
    this.thirdId = thirdId;
    this.crossReportMethodType = crossReportMethodType;
    this.filedOutOfStateIndicator = filedOutOfStateIndicator;
    this.governmentOrgCrossRptIndicatorVar = governmentOrgCrossRptIndicatorVar;
    this.informTime = freshDate(informTime);
    this.recipientBadgeNumber = recipientBadgeNumber;
    this.recipientPhoneExtensionNumber = recipientPhoneExtensionNumber;
    this.recipientPhoneNumber = recipientPhoneNumber;
    this.informDate = freshDate(informDate);
    this.recipientPositionTitleDesc = recipientPositionTitleDesc;
    this.referenceNumber = referenceNumber;
    this.lawEnforcementId = lawEnforcementId;
    this.staffPersonId = staffPersonId;
    this.description = description;
    this.recipientName = recipientName;
    this.outStateLawEnforcementAddr = outStateLawEnforcementAddr;
    this.countySpecificCode = countySpecificCode;
    this.lawEnforcementIndicator = lawEnforcementIndicator;
    this.outStateLawEnforcementIndicator = outStateLawEnforcementIndicator;
    this.satisfyCrossReportIndicator = satisfyCrossReportIndicator;
  }

  /**
   * Constructor
   * 
   * @param thirdId The thirdId is unique key to CrossReport
   * @param crossReport crossReport The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time of last person to update this object
   */
  public CrossReport(String thirdId, gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport,
      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    try {
      this.referralId = crossReport.getReferralId();
      this.thirdId = thirdId;
      this.crossReportMethodType = crossReport.getCrossReportMethodType();
      this.filedOutOfStateIndicator =
          DomainChef.cookBoolean(crossReport.getFiledOutOfStateIndicator());
      this.governmentOrgCrossRptIndicatorVar =
          DomainChef.cookBoolean(crossReport.getGovernmentOrgCrossRptIndicatorVar());
      this.informTime = DomainChef.uncookTimeString(crossReport.getInformTime());
      this.recipientBadgeNumber = crossReport.getRecipientBadgeNumber();
      this.recipientPhoneExtensionNumber = crossReport.getRecipientPhoneExtensionNumber();
      this.recipientPhoneNumber = crossReport.getRecipientPhoneNumber();
      this.informDate = DomainChef.uncookDateString(crossReport.getInformDate());
      this.recipientPositionTitleDesc = crossReport.getRecipientPositionTitleDesc();
      this.referenceNumber = crossReport.getReferenceNumber();
      this.lawEnforcementId = StringUtils.isBlank(crossReport.getLawEnforcementId()) ? null
          : crossReport.getLawEnforcementId();
      this.staffPersonId = crossReport.getStaffPersonId();
      this.description = crossReport.getDescription();
      this.recipientName = crossReport.getRecipientName();
      this.outStateLawEnforcementAddr = crossReport.getOutStateLawEnforcementAddr();
      this.countySpecificCode = crossReport.getCountySpecificCode();
      this.lawEnforcementIndicator =
          DomainChef.cookBoolean(crossReport.getLawEnforcementIndicator());
      this.outStateLawEnforcementIndicator =
          DomainChef.cookBoolean(crossReport.getOutStateLawEnforcementIndicator());
      this.satisfyCrossReportIndicator =
          DomainChef.cookBoolean(crossReport.getSatisfyCrossReportIndicator());
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
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
  public String getFiledOutOfStateIndicator() {
    return filedOutOfStateIndicator;
  }

  /**
   * @return the governmentOrgCrossRptIndicatorVar
   */
  public String getGovernmentOrgCrossRptIndicatorVar() {
    return governmentOrgCrossRptIndicatorVar;
  }

  /**
   * @return the informTime
   */
  public Date getInformTime() {
    return freshDate(informTime);
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
  public Long getRecipientPhoneNumber() {
    return recipientPhoneNumber;
  }

  /**
   * @return the informDate
   */
  public Date getInformDate() {
    return freshDate(informDate);
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
   * @return the outStateLawEnforcementAddr
   */
  public String getOutStateLawEnforcementAddr() {
    return outStateLawEnforcementAddr;
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
  public String getLawEnforcementIndicator() {
    return lawEnforcementIndicator;
  }

  /**
   * @return the outStateLawEnforcementIndicator
   */
  public String getOutStateLawEnforcementIndicator() {
    return outStateLawEnforcementIndicator;
  }

  /**
   * @return the satisfyCrossReportIndicator
   */
  public String getSatisfyCrossReportIndicator() {
    return satisfyCrossReportIndicator;
  }

  public Referral getReferral() {
    return referral;
  }

  public LawEnforcementEntity getLawEnforcement() {
    return lawEnforcement;
  }

  public StaffPerson getStaffPerson() {
    return staffPerson;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
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
