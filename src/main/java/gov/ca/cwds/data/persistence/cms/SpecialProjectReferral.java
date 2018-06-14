package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import gov.ca.cwds.rest.api.domain.DomainChef;
import liquibase.util.StringUtils;

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
    
    @Column(name = "CNTY_SPFCD", nullable = false, length = 2)
    private String countySpecificCode;
    
    @Column(name = "FKREFERL_T", nullable = false, length = CMS_ID_LEN)
    private String referralId;
    
    @Column(name = "FKSPC_PRJT", nullable = false, length = CMS_ID_LEN)
    private String specialProjectId;
    
    @Type(type = "date")
    @Column(name = "PART_ENDT", nullable = true, length = 10)
    private LocalDate participationEndDate;
    
    @Type(type = "date")
    @Column(name = "PART_STRDT", nullable = false, length = 10)
    private LocalDate participationStartDate;
    
    @Column(name = "SFSURB_IND", nullable = false, length = 1)
    @Type(type = "yes_no")
    private String safelySurrenderedBabiesIndicator;
    
    @Id
    @Column(name = "THRID_ID", nullable = false, length = CMS_ID_LEN)
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
      LocalDate participationEndDate, LocalDate participationStartDate, String safelySurrenderedBabiesIndicator,
      String id) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.referralId = referralId;
    this.specialProjectId = specialProjectId;
    this.participationEndDate = participationEndDate;
    this.participationStartDate = participationStartDate;
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
    this.participationEndDate = 
        StringUtils.isEmpty(domain.getParticipationEndDate()) ? null : LocalDate.parse(domain.getParticipationEndDate());
    this.participationStartDate = 
        StringUtils.isEmpty(domain.getParticipationStartDate()) ? null : LocalDate.parse(domain.getParticipationStartDate());
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
  public LocalDate getParticipationEndDate() {
    return participationEndDate;
  }

  /**
   * 
   * @param participationEndDate - participation end date
   */
  public void setParticipationEndDate(LocalDate participationEndDate) {
    this.participationEndDate = participationEndDate;
  }

  /**
   * 
   * @return - participation start date
   */
  public LocalDate getParticipationStartDate() {
    return participationStartDate;
  }

  /**
   * 
   * @param participationStartDate - participation start date
   */
  public void setParticipationStartDate(LocalDate participationStartDate) {
    this.participationStartDate = participationStartDate;
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

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
