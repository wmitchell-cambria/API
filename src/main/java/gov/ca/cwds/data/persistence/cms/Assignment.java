package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link CmsPersistentObject} representing an Assignment.
 * 
 * <p>
 * WARNING: Prefer Assignment classes that manage the "folded key" code, such as
 * {@link ReferralAssignment} and {@link CaseAssignment}.
 * </p>
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Assignment.findCaseLoads",
    query = "SELECT C FROM StaffPersonCaseLoad S JOIN CaseLoad C ON S.fkCaseLoad = C.id AND C.endDate IS NULL "
        + "AND S.fkStaffPerson = :fkStaffPerson AND S.endDate IS NULL "
        + "AND (C.archiveAssociationIndicator = 'N' OR C.archiveAssociationIndicator = 'Y') "
        + "AND C.onHoldIndicator = 'N' " + "ORDER BY C.archiveAssociationIndicator DESC")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.Assignment.findAssignmentsByReferralId",
    query = "FROM Assignment WHERE referral.id = :referralId")
@SuppressWarnings("serial")
@Entity
@Table(name = "ASGNM_T")
public class Assignment extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "CNTY_SPFCD", length = 2)
  private String countySpecificCode;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Type(type = "time")
  @Column(name = "END_TM")
  private Date endTime;

  @Column(name = "ESTBLSH_CD", length = 1)
  private String establishedForCode;

  @Column(name = "ESTBLSH_ID", length = CMS_ID_LEN)
  private String establishedForId;

  @Column(name = "FKCASE_LDT", length = CMS_ID_LEN)
  private String fkCaseLoad;

  @Column(name = "FKOST_PTYT", length = CMS_ID_LEN)
  private String fkOutOfStateContactParty;

  @Column(name = "RESPL_DSC", length = 160)
  private String responsibilityDescription;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ASSG_RLC")
  private Short secondaryAssignmentRoleType;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  @Type(type = "time")
  @Column(name = "START_TM")
  private Date startTime;

  @Column(name = "ASGNMNT_CD")
  private String typeOfAssignmentCode;

  // @Type(type = "decimal")
  @Column(name = "WGHTNG_NO", length = 5)
  private BigDecimal weightingNumber;

  /**
   * Referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent client record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "ESTBLSH_ID", nullable = false, updatable = false, insertable = false)
  private Referral referral;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Assignment() {
    super();
  }

  /**
   * @param id - the primaryKey
   * @param countySpecificCode - county code of case load with this assignment
   * @param endDate - end date of assignment
   * @param endTime - end time of assignment
   * @param establishedForCode - referral or case
   * @param establishedForId - referral or case Id
   * @param fkCaseLoad - foreign key to the case load
   * @param fkOutOfStateContactParty - foreign ky to the out of state contact party
   * @param responsibilityDescription - description
   * @param secondaryAssignmentRoleType - primary or secondary
   * @param startDate - start date of assignment
   * @param startTime - end date of assignment
   * @param typeOfAssignmentCode - primary, secondary, or read only
   * @param weightingNumber - weighting within case load
   */
  public Assignment(String id, String countySpecificCode, Date endDate, Date endTime,
      String establishedForCode, String establishedForId, String fkCaseLoad,
      String fkOutOfStateContactParty, String responsibilityDescription,
      Short secondaryAssignmentRoleType, Date startDate, Date startTime,
      String typeOfAssignmentCode, BigDecimal weightingNumber) {
    super();
    this.id = id;
    this.countySpecificCode = countySpecificCode;
    this.endDate = freshDate(endDate);
    this.endTime = freshDate(endTime);
    this.establishedForCode = establishedForCode;
    this.establishedForId = establishedForId;
    this.fkCaseLoad = fkCaseLoad;
    this.fkOutOfStateContactParty = fkOutOfStateContactParty;
    this.responsibilityDescription = responsibilityDescription;
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
    this.startDate = freshDate(startDate);
    this.startTime = freshDate(startTime);
    this.typeOfAssignmentCode = typeOfAssignmentCode;
    this.weightingNumber = weightingNumber;
  }

  /**
   * @param id - IDENTIFIER
   * @param pa - domain ASSIGNMENT
   * @param lastUpdateId - staff person id
   * @param lastUpdateTime - the time of last update this object
   */
  public Assignment(String id, gov.ca.cwds.rest.api.domain.cms.Assignment pa, String lastUpdateId,
      Date lastUpdateTime) {
    super(lastUpdateId, lastUpdateTime);
    this.id = id;
    this.countySpecificCode = pa.getCountySpecificCode();
    this.endDate = DomainChef.uncookDateString(pa.getEndDate());
    this.endTime = DomainChef.uncookTimeString(pa.getEndTime());
    this.establishedForCode = pa.getEstablishedForCode();
    this.establishedForId = pa.getEstablishedForId();
    this.fkCaseLoad = pa.getCaseLoadId();
    this.fkOutOfStateContactParty = pa.getOutOfStateContactId();
    this.responsibilityDescription = pa.getResponsibilityDescription();
    this.secondaryAssignmentRoleType = pa.getSecondaryAssignmentRoleType();
    this.startDate = DomainChef.uncookDateString(pa.getStartDate());
    this.startTime = DomainChef.uncookTimeString(pa.getStartTime());
    this.typeOfAssignmentCode = pa.getTypeOfAssignmentCode();
    this.weightingNumber = pa.getWeightingNumber();
  }

  @SuppressWarnings("javadoc")
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  @SuppressWarnings("javadoc")
  public Date getEndDate() {
    return freshDate(endDate);
  }

  @SuppressWarnings("javadoc")
  public Date getEndTime() {
    return freshDate(endTime);
  }

  @SuppressWarnings("javadoc")
  public String getEstablishedForCode() {
    return establishedForCode;
  }

  @SuppressWarnings("javadoc")
  public String getEstablishedForId() {
    return establishedForId;
  }

  @SuppressWarnings("javadoc")
  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  @SuppressWarnings("javadoc")
  public String getFkOutOfStateContactParty() {
    return fkOutOfStateContactParty;
  }

  @SuppressWarnings("javadoc")
  public String getId() {
    return id;
  }

  @SuppressWarnings("javadoc")
  public String getResponsibilityDescription() {
    return responsibilityDescription;
  }

  @SuppressWarnings("javadoc")
  public Short getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  @SuppressWarnings("javadoc")
  public Date getStartDate() {
    return freshDate(startDate);
  }

  public void setStartDate(Date startDate) {
    this.startDate = freshDate(startDate);
  }

  @SuppressWarnings("javadoc")
  public Date getStartTime() {
    return freshDate(startTime);
  }

  public void setStartTime(Date startTime) {
    this.startTime = freshDate(startTime);
  }

  @SuppressWarnings("javadoc")
  public String getTypeOfAssignmentCode() {
    return typeOfAssignmentCode;
  }

  @SuppressWarnings("javadoc")
  public BigDecimal getWeightingNumber() {
    return weightingNumber;
  }

  public Referral getReferral() {
    return referral;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
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
