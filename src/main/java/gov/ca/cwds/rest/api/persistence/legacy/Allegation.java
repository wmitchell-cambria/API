package gov.ca.cwds.rest.api.persistence.legacy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import org.apache.commons.lang3.StringUtils;
import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

@Entity
@Table(name = "ALLGTN_T")
public class Allegation extends PersistentObject {
  protected static final String DATE_FORMAT = "yyyy-MM-dd";

  @Id
  @Column(name = "IDENTIFIER")
  private String id;

  @Type(type = "date")
  @Column(name = "ABUSE_ENDT")
  private Date abuseEndDate;

  @Type(type = "short")
  @Column(name = "ABUSE_FREQ")
  private Short abuseFrequency;

  @Column(name = "ABUSE_PDCD")
  private String abuseFrequencyPeriodCode;

  @Column(name = "LOC_DSC")
  private String abuseLocationDescription;

  @Type(type = "date")
  @Column(name = "ABUSE_STDT")
  private Date abuseStartDate;

  @Type(type = "short")
  @Column(name = "ALG_DSPC")
  private Short allegationDispositionType;

  @Type(type = "short")
  @Column(name = "ALG_TPC")
  private Short allegationType;

  @Column(name = "DISPSN_DSC")
  private String dispositionDescription;

  @Type(type = "date")
  @Column(name = "DISPSN_DT")
  private Date dispositionDate;

  @Column(name = "IJHM_DET_B")
  private String injuryHarmDetailIndicator;

  @Column(name = "NON_PRT_CD")
  private String nonProtectingParentCode;

  @Column(name = "STFADD_IND")
  private String staffPersonAddedIndicator;

  @Column(name = "FKCLIENT_T")
  private String fkClientT;

  @Column(name = "FKCLIENT_0")
  private String fkClient0;

  @Column(name = "FKREFERL_T")
  private String fkReferralT;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "ZIPPY_IND")
  private String zippyCreatedIndicator;

  @Type(type = "short")
  @Column(name = "PLC_FCLC")
  private Short placementFacilityType;


  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Allegation() {
    super();
  }

  /**
   * Constructor
   * 
   * @param allegation The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Allegation(gov.ca.cwds.rest.api.domain.legacy.Allegation allegation, String lastUpdatedId) {
    super(lastUpdatedId);
    try {
      this.id = allegation.getId();
      this.abuseEndDate = DomainObject.uncookDateString(allegation.getAbuseEndDate());
      this.abuseFrequency = allegation.getAbuseFrequency();
      this.abuseFrequencyPeriodCode = allegation.getAbuseFrequencyPeriodCode();
      this.abuseLocationDescription = allegation.getAbuseLocationDescription();
      this.abuseStartDate = DomainObject.uncookDateString(allegation.getAbuseStartDate());
      this.allegationDispositionType = allegation.getAllegationDispositionType();
      this.allegationType = allegation.getAllegationType();
      this.dispositionDescription = allegation.getDispositionDescription();
      this.dispositionDate = DomainObject.uncookDateString(allegation.getDispositionDate());
      this.injuryHarmDetailIndicator =
          DomainObject.cookBoolean(allegation.getInjuryHarmDetailIndicator());
      this.nonProtectingParentCode = allegation.getNonProtectingParentCode();
      this.staffPersonAddedIndicator =
          DomainObject.cookBoolean(allegation.getStaffPersonAddedIndicator());
      this.fkClientT = allegation.getFkClientT();
      this.fkClient0 = allegation.getFkClient0();
      this.fkReferralT = allegation.getFkReferralT();
      this.countySpecificCode = allegation.getCountySpecificCode();
      this.zippyCreatedIndicator = DomainObject.cookBoolean(allegation.getZippyCreatedIndicator());
      this.placementFacilityType = allegation.getPlacementFacilityType();
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
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the abuseEndDate
   */
  public Date getAbuseEndDate() {
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
    return StringUtils.trimToEmpty(abuseFrequencyPeriodCode);
  }

  /**
   * @return the abuseLocationDescription
   */
  public String getAbuseLocationDescription() {
    return StringUtils.trimToEmpty(abuseLocationDescription);
  }

  /**
   * @return the abuseStartDate
   */
  public Date getAbuseStartDate() {
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

  /**
   * @return the dispositionDescription
   */
  public String getDispositionDescription() {
    return StringUtils.trimToEmpty(dispositionDescription);
  }

  /**
   * @return the dispositionDate
   */
  public Date getDispositionDate() {
    return dispositionDate;
  }

  /**
   * @return the injuryHarmDetailIndicator
   */
  public String getInjuryHarmDetailIndicator() {
    return StringUtils.trimToEmpty(injuryHarmDetailIndicator);
  }

  /**
   * @return the nonProtectingParentCode
   */
  public String getNonProtectingParentCode() {
    return StringUtils.trimToEmpty(nonProtectingParentCode);
  }

  /**
   * @return the staffPersonAddedIndicator
   */
  public String getStaffPersonAddedIndicator() {
    return StringUtils.trimToEmpty(staffPersonAddedIndicator);
  }

  /**
   * @return the fkClientT
   */
  public String getFkClientT() {
    return StringUtils.trimToEmpty(fkClientT);
  }

  /**
   * @return the fkClient0
   */
  public String getFkClient0() {
    return StringUtils.trimToEmpty(fkClient0);
  }

  /**
   * @return the fkReferralT
   */
  public String getFkReferralT() {
    return StringUtils.trimToEmpty(fkReferralT);
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return StringUtils.trimToEmpty(countySpecificCode);
  }

  /**
   * @return the zippyCreatedIndicator
   */
  public String getZippyCreatedIndicator() {
    return StringUtils.trimToEmpty(zippyCreatedIndicator);
  }

  /**
   * @return the placementFacilityType
   */
  public Short getPlacementFacilityType() {
    return placementFacilityType;
  }

}
