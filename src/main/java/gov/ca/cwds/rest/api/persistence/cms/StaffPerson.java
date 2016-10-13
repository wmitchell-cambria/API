package gov.ca.cwds.rest.api.persistence.cms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "STFPERST")
public class StaffPerson extends PersistentObject {
  @Id
  @Column(name = "IDENTIFIER")
  private String id;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FIRST_NM")
  private String firstName;

  @Column(name = "JOB_TL_DSC")
  private String jobTitle;

  @Column(name = "LAST_NM")
  private String lastName;

  @Column(name = "MID_INI_NM")
  private String middleInitial;

  @Column(name = "NMPRFX_DSC")
  private String namePrefix;

  @Column(name = "PHONE_NO")
  private BigDecimal phoneNumber;

  @Type(type = "integer")
  @Column(name = "TEL_EXT_NO")
  private Integer phoneExt;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Column(name = "SUFX_TLDSC")
  private String nameSuffix;

  @Column(name = "TLCMTR_IND")
  private String telecommuterIndicator;

  @Column(name = "FKCWS_OFFT")
  private String cwsOffice;

  @Column(name = "AVLOC_DSC")
  private String availabilityAndLocationDescription;

  @Column(name = "SSRS_WKRID")
  private String ssrsLicensingWorkerId;

  @Column(name = "CNTY_SPFCD")
  private String countyCode;

  @Column(name = "DTYWKR_IND")
  private String dutyWorkerIndicator;

  @Column(name = "FKCWSADDRT")
  private String cwsOfficeAddress;

  @Column(name = "EMAIL_ADDR")
  private String emailAddress;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public StaffPerson() {
    super();
  }

  public StaffPerson(String id, Date endDate, String firstName, String jobTitle, String lastName,
      String middleInitial, String namePrefix, BigDecimal phoneNumber, Integer phoneExt,
      Date startDate, String nameSuffix, String telecommuterIndicator, String cwsOffice,
      String availabilityAndLocationDescription, String ssrsLicensingWorkerId, String countyCode,
      String dutyWorkerIndicator, String cwsOfficeAddress, String emailAddress) {
    super();
    this.id = id;
    this.endDate = endDate;
    this.firstName = firstName;
    this.jobTitle = jobTitle;
    this.lastName = lastName;
    this.middleInitial = middleInitial;
    this.namePrefix = namePrefix;
    this.phoneNumber = phoneNumber;
    this.phoneExt = phoneExt;
    this.startDate = startDate;
    this.nameSuffix = nameSuffix;
    this.telecommuterIndicator = telecommuterIndicator;
    this.cwsOffice = cwsOffice;
    this.availabilityAndLocationDescription = availabilityAndLocationDescription;
    this.ssrsLicensingWorkerId = ssrsLicensingWorkerId;
    this.countyCode = countyCode;
    this.dutyWorkerIndicator = dutyWorkerIndicator;
    this.cwsOfficeAddress = cwsOfficeAddress;
    this.emailAddress = emailAddress;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the jobTitle
   */
  public String getJobTitle() {
    return jobTitle;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the middleInitial
   */
  public String getMiddleInitial() {
    return middleInitial;
  }

  /**
   * @return the namePrefix
   */
  public String getNamePrefix() {
    return namePrefix;
  }

  /**
   * @return the phoneNumber
   */
  public BigDecimal getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the phoneExt
   */
  public Integer getPhoneExt() {
    return phoneExt;
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the nameSuffix
   */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return the telecommuterIndicator
   */
  public String getTelecommuterIndicator() {
    return telecommuterIndicator;
  }

  /**
   * @return the cwsOffice
   */
  public String getCwsOffice() {
    return cwsOffice;
  }

  /**
   * @return the availabilityAndLocationDescription
   */
  public String getAvailabilityAndLocationDescription() {
    return availabilityAndLocationDescription;
  }

  /**
   * @return the ssrsLicensingWorkerId
   */
  public String getSsrsLicensingWorkerId() {
    return ssrsLicensingWorkerId;
  }

  /**
   * @return the countyCode
   */
  public String getCountyCode() {
    return countyCode;
  }

  /**
   * @return the dutyWorkerIndicator
   */
  public String getDutyWorkerIndicator() {
    return dutyWorkerIndicator;
  }

  /**
   * @return the cwsOfficeAddress
   */
  public String getCwsOfficeAddress() {
    return cwsOfficeAddress;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

}
