package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.cms.ReferralClient.PrimaryKey;


/**
 * {@link PersistentObject} represents a record in TSCNTRLT.
 * 
 * @author CWDS API Team
 */
@Entity
public class CmsDocReferralClientDetail extends CmsPersistentObject {

  @Id
  @Column(name = "REFERL_ID")
  private String referlId;

  @Id
  @Column(name = "CLIENT_ID")
  private String clientId;

  @Column(name = "COM_FST_NM")
  private String commonFirstName;

  @Column(name = "COM_MID_NM")
  private String commonMiddleName;

  @Column(name = "COM_LST_NM")
  private String commonLastName;

  @Type(type = "date")
  @Column(name = "BIRTH_DT")
  private Date birthDate;

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocReferralClientDetail() {
    super();
  }

  public CmsDocReferralClientDetail(String referlId, String clientId, String commonFirstName,
      String commonMiddleName, String commonLastName, Date birthDate) {
    super();
    this.referlId = referlId;
    this.clientId = clientId;
    this.commonFirstName = commonFirstName;
    this.commonMiddleName = commonMiddleName;
    this.commonLastName = commonLastName;
    this.birthDate = birthDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return new PrimaryKey(this.getReferlId(), this.getClientId());
  }

  public String getReferlId() {
    return referlId;
  }

  public void setReferlId(String referlId) {
    this.referlId = referlId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getCommonFirstName() {
    return commonFirstName;
  }

  public void setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
  }

  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  public void setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
  }

  public String getCommonLastName() {
    return commonLastName;
  }

  public void setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

}
