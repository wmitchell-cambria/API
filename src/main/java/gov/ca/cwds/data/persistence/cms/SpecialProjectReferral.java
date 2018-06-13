package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import gov.ca.cwds.rest.api.domain.DomainChef;

  /**
   * {@link CmsPersistentObject} Class representing a Special Project Referral.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  @Entity
  @Table(name = "SPRJ_RFT")
  public class SpecialProjectReferral extends CmsPersistentObject {
    
    public static final String FIND_BY_REFERRAL_ID_AND_SPECIAL_PROJECT_ID = 
        "gov.ca.cwds.data.persistence.cms.SpecialProjectReferral.findByReferralIdAndSpecialProjectId";
    static final String FIND_BY_REFERRAL_ID_AND_SPECIAL_PROJECT_ID_QUERY =
        "FROM SpecialProjectReferral WHERE FKREFERL_T = referralId AND FKSPC_PRJT = specialProjectId";
    
    public static final String PARAM_REFERRAL_ID = "referralId";
    public static final String PARAM_SPECIAL_PROJECT_ID = "specialProjectId";
    
    @Column(name = "CNTY_SPFCD")
    private String countySpecificCode;
    
    @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
    private String referralId;
    
    @Column(name = "FKSPC_PRJT", length = CMS_ID_LEN)
    private String specialProjectId;
    
    @Type(type = "date")
    @Column(name = "PART_ENDT")
    private Date participationEndDate;
    
    @Type(type = "date")
    @Column(name = "PART_STRDT")
    private Date participationStartDate;
    
    @Column(name = "SFSURB_IND")
    @Type(type = "yes_no")
    private String safelySurrenderedBabiesIndicator;
    
    @Id
    @Column(name = "THRID_ID", length = CMS_ID_LEN)
    private String id;
  
    /**
     * Default constructor
     * 
     * Required for Hibernate
     */
  public SpecialProjectReferral() {
    super();
  }

  /**
   * Constructor
   * 
   * @param countySpecificCode - county specific code
   * @param referralId - referral ID
   * @param specialProjectId - special project ID
   * @param participationEndDate - participation end date
   * @param participationStartDate - participation start date
   * @param safelySurrenderedBabiesIndicator - safely surrendered babies indicator
   * @param id - third ID
   * 
   */
  public SpecialProjectReferral(String countySpecificCode, String referralId, String specialProjectId,
      Date participationEndDate, Date participationStartDate, String safelySurrenderedBabiesIndicator,
      String id) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.referralId = referralId;
    this.specialProjectId = specialProjectId;
    this.participationEndDate = freshDate(participationEndDate);
    this.participationStartDate = freshDate(participationStartDate);
    this.safelySurrenderedBabiesIndicator = safelySurrenderedBabiesIndicator;
    this.id = id;
  }
  
  /**
   * Constructor
   * 
   * @param id - third ID
   * @param domain - domain special project referral
   * @param lastUpdatedId - last updated ID
   * @param lastUpdatedTime - last updated time
   * 
   */
  public SpecialProjectReferral(String id, gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral domain, 
      String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.id = id;
    this.countySpecificCode = domain.getCountySpecificCode();
    this.referralId = domain.getReferralId();
    this.specialProjectId = domain.getSpecialProjectId();
    this.participationEndDate = DomainChef.uncookDateString(domain.getParticipationEndDate());
    this.participationStartDate = DomainChef.uncookDateString(domain.getParticipationStartDate());
    this.safelySurrenderedBabiesIndicator = DomainChef.cookBoolean(domain.getSafelySurrenderedBabiesIndicator());
  }
  
  /**
   * return the primary key
   */
  @Override
  public Serializable getPrimaryKey() {

    return this.getId();
  }

  /**
   * @return county specific code
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * 
   * @param countySpecificCode - county specific code
   */
  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * 
   * @return referral id
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * 
   * @param referralId - referral ID
   */
  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  /**
   * 
   * @return special project ID
   */
  public String getSpecialProjectId() {
    return specialProjectId;
  }

  /**
   * 
   * @param specialProjectId - special project ID
   */
  public void setSpecialProjectId(String specialProjectId) {
    this.specialProjectId = specialProjectId;
  }

  /**
   * @return participation end date
   * 
   */
  public Date getParticipationEndDate() {
    return freshDate(participationEndDate);
  }

  /**
   * 
   * @param participationEndDate - participation end date
   */
  public void setParticipationEndDate(Date participationEndDate) {
    this.participationEndDate = freshDate(participationEndDate);
  }

  /**
   * 
   * @return - participation start date
   */
  public Date getParticipationStartDate() {
    return freshDate(participationStartDate);
  }

  /**
   * 
   * @param participationStartDate - participation start date
   */
  public void setParticipationStartDate(Date participationStartDate) {
    this.participationStartDate = freshDate(participationStartDate);
  }

  /**
   * 
   * @return - safely surrendered babies indicator
   */
  public String getSafelySurrenderedBabiesIndicator() {
    return safelySurrenderedBabiesIndicator;
  }

  /**
   * 
   * @param safelySurrenderedBabiesIndicator - safely surrendered babies indicator
   */
  public void setSafelySurrenderedBabiesIndicator(String safelySurrenderedBabiesIndicator) {
    this.safelySurrenderedBabiesIndicator = safelySurrenderedBabiesIndicator;
  }

  /**
   * 
   * @return - third ID
   */
  public String getId() {
    return id;
  }

  /**
   * 
   * @param id - third id
   */
  public void setId(String id) {
    this.id = id;
  }
  
  

}
