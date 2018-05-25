package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} representing a Safely Surrendered Babies
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SFSUR_BT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SafelySurrenderedBabies extends CmsPersistentObject {

  @Id
  @Column(name = "FKCHLD_CLT")
  private String childClientId;

  @Type(type = "date")
  @Column(name = "SURR_DT")
  private LocalDate surrenderedDate;

  @Type(type = "time")
  @Column(name = "SURR_TM")
  private LocalDate surrenderedTime;

  @Column(name = "SURR_BY_NM")
  private String surrenderedByName;

  @Column(name = "RELTO_CLT")
  private Integer relationToClient;

  @Column(name = "COMM_DESC")
  private String commentDescription;

  @Column(name = "ID_INFO_CD", length = 1)
  private String braceletIdInfoCode;

  @Column(name = "MEDQUES_CD", length = 1)
  private String medicalQuestionnaireCode;

  @Type(type = "date")
  @Column(name = "QUESREC_DT")
  private LocalDate questionnaireReceivedDate;

  @Column(name = "CPSNTF_IND")
  private Boolean cpsNotofiedIndicator;

  @Type(type = "date")
  @Column(name = "CPSNTF_DT")
  private LocalDate cpsNotofiedDate;

  @Type(type = "time")
  @Column(name = "CPSNTF_TM")
  private LocalDate cpsNotofiedTime;

  @Column(name = "LST_UPD_ID")
  private String lastUpdatedId;

  @Type(type = "date")
  @Column(name = "LST_UPD_TS")
  private LocalDate lastUpdatedTimeStamp;

  @Column(name = "FKSPRJ_CST")
  private String specialProjectCase;

  @Column(name = "FKSPRJ_RFT")
  private String specialProjectReferral;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "FKCLIENT_T")
  private ChildClient childClient;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public SafelySurrenderedBabies() {
    super();
  }



  public String getChildClientId() {
    return childClientId;
  }



  public void setChildClientId(String childClientId) {
    this.childClientId = childClientId;
  }



  public LocalDate getSurrenderedDate() {
    return surrenderedDate;
  }



  public void setSurrenderedDate(LocalDate surrenderedDate) {
    this.surrenderedDate = surrenderedDate;
  }



  public LocalDate getSurrenderedTime() {
    return surrenderedTime;
  }



  public void setSurrenderedTime(LocalDate surrenderedTime) {
    this.surrenderedTime = surrenderedTime;
  }



  public String getSurrenderedByName() {
    return surrenderedByName;
  }



  public void setSurrenderedByName(String surrenderedByName) {
    this.surrenderedByName = surrenderedByName;
  }



  public Integer getRelationToClient() {
    return relationToClient;
  }



  public void setRelationToClient(Integer relationToClient) {
    this.relationToClient = relationToClient;
  }



  public String getCommentDescription() {
    return commentDescription;
  }



  public void setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
  }



  public String getBraceletIdInfoCode() {
    return braceletIdInfoCode;
  }



  public void setBraceletIdInfoCode(String braceletIdInfoCode) {
    this.braceletIdInfoCode = braceletIdInfoCode;
  }



  public String getMedicalQuestionnaireCode() {
    return medicalQuestionnaireCode;
  }



  public void setMedicalQuestionnaireCode(String medicalQuestionnaireCode) {
    this.medicalQuestionnaireCode = medicalQuestionnaireCode;
  }



  public LocalDate getQuestionnaireReceivedDate() {
    return questionnaireReceivedDate;
  }



  public void setQuestionnaireReceivedDate(LocalDate questionnaireReceivedDate) {
    this.questionnaireReceivedDate = questionnaireReceivedDate;
  }



  public Boolean getCpsNotofiedIndicator() {
    return cpsNotofiedIndicator;
  }



  public void setCpsNotofiedIndicator(Boolean cpsNotofiedIndicator) {
    this.cpsNotofiedIndicator = cpsNotofiedIndicator;
  }



  public LocalDate getCpsNotofiedDate() {
    return cpsNotofiedDate;
  }



  public void setCpsNotofiedDate(LocalDate cpsNotofiedDate) {
    this.cpsNotofiedDate = cpsNotofiedDate;
  }



  public LocalDate getCpsNotofiedTime() {
    return cpsNotofiedTime;
  }



  public void setCpsNotofiedTime(LocalDate cpsNotofiedTime) {
    this.cpsNotofiedTime = cpsNotofiedTime;
  }



  @Override
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }



  @Override
  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }



  public LocalDate getLastUpdatedTimeStamp() {
    return lastUpdatedTimeStamp;
  }



  public void setLastUpdatedTimeStamp(LocalDate lastUpdatedTimeStamp) {
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
  }



  public String getSpecialProjectCase() {
    return specialProjectCase;
  }



  public void setSpecialProjectCase(String specialProjectCase) {
    this.specialProjectCase = specialProjectCase;
  }



  public String getSpecialProjectReferral() {
    return specialProjectReferral;
  }



  public void setSpecialProjectReferral(String specialProjectReferral) {
    this.specialProjectReferral = specialProjectReferral;
  }



  public ChildClient getChildClient() {
    return childClient;
  }



  public void setChildClient(ChildClient childClient) {
    this.childClient = childClient;
  }



  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }


  @Override
  public Serializable getPrimaryKey() {
    // TODO Auto-generated method stub
    return null;
  }

}
