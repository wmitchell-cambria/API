package gov.ca.cwds.data.persistence.ns;
import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@NamedNativeQuery(
	name = "gov.ca.cwds.data.persistence.nw.findScreeningsOfUser",
	resultClass = ScreeningWrapper.class, readOnly = true,
	query = "SELECT \n"
	+ "  ")

public class ScreeningWrapper {

  @Id
  @Column(name = "id")
  private String id;
  
  @Column(name = "reference")
  private String reference;
  
  @Column(name = "name")
  private String name;

  @Column(name = "screening_decision")
  private String screeningDecision;

  @Column(name = "screening_decision_detail")
  private String screeningDecisionDetail;

  @Column(name = "assignee")
  private String assignee;

  @Column(name = "assignee_staff_id")
  private String assigneeStaffId;

  @Column(name = "started_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startedAt;
  
  public ScreeningWrapper() {}
  
  public ScreeningWrapper( String id, String reference, String name, String screeningDecision,
	  String screeningDecisionDetail, String assignee, String assigneeStaffId, Date startedAt) {
	this.id = id;
	this.reference = reference;
	this.name = name;
	this.screeningDecision = screeningDecision;
	this.screeningDecisionDetail = screeningDecisionDetail;
	this.assignee = assignee;
	this.assigneeStaffId = assigneeStaffId;
	this.startedAt = freshDate(startedAt);	
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScreeningDecision() {
    return screeningDecision;
  }

  public void setScreeningDecision(String screeningDecision) {
    this.screeningDecision = screeningDecision;
  }

  public String getScreeningDecisionDetail() {
    return screeningDecisionDetail;
  }

  public void setScreeningDecisionDetail(String screeningDecisionDetail) {
    this.screeningDecisionDetail = screeningDecisionDetail;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getAssigneeStaffId() {
    return assigneeStaffId;
  }

  public void setAssigneeStaffId(String assigneeStaffId) {
    this.assigneeStaffId = assigneeStaffId;
  }

  public Date getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
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
