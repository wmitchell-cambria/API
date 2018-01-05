package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.NamedQuery;

/**
 * CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.StateId.findByClientId",
    query = "FROM StateId WHERE fkClientId = :clientId")
@Entity
@Table(name = "ST_ID_T")
public class StateId extends CmsPersistentObject {
  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String id;

  @Basic
  @Column(name = "AST_UNT_CD", nullable = false, length = 1)
  private String assistanceUnitCode;

  @Basic
  @Column(name = "GVR_ENTC", nullable = false)
  private short governmentEntityType;

  @Basic
  @Column(name = "PERSON_NO", nullable = false, length = 2)
  private String personNumber;

  @Basic
  @Column(name = "SERIAL_NO", nullable = false, length = 7)
  private String serialNumber;

  @Basic
  @Column(name = "START_DT", nullable = false)
  private Date startDate;

  @Basic
  @Column(name = "END_DT", nullable = true)
  private Date endDate;

  @Basic
  @Column(name = "STID_FSTNM", nullable = false, length = 20)
  private String stateIdCaseFirstName;

  @Basic
  @Column(name = "STID_LSTNM", nullable = false, length = 25)
  private String stateIdCaseLastName;

  @Basic
  @Column(name = "STID_MIDNM", nullable = false, length = 20)
  private String stateIdCaseMiddleName;

  @Basic
  @Column(name = "FKCLIENT_T", nullable = false, length = 10)
  private String fkClientId;

  public String getId() {
    return id;
  }

  public void setId(String identifier) {
    this.id = identifier;
  }

  public String getAssistanceUnitCode() {
    return assistanceUnitCode;
  }

  public void setAssistanceUnitCode(String astUntCd) {
    this.assistanceUnitCode = astUntCd;
  }

  public short getGovernmentEntityType() {
    return governmentEntityType;
  }

  public void setGovernmentEntityType(short gvrEntc) {
    this.governmentEntityType = gvrEntc;
  }

  public String getPersonNumber() {
    return personNumber;
  }

  public void setPersonNumber(String personNo) {
    this.personNumber = personNo;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNo) {
    this.serialNumber = serialNo;
  }

  public Date getStartDate() {
    return new Date(startDate.getTime());
  }

  public void setStartDate(Date startDt) {
    this.startDate = new Date(startDt.getTime());
  }

  public Date getEndDate() {
    return new Date(endDate.getTime());
  }

  public void setEndDate(Date endDt) {
    this.endDate = new Date(endDt.getTime());
  }

  public String getStateIdCaseFirstName() {
    return stateIdCaseFirstName;
  }

  public void setStateIdCaseFirstName(String stidFstnm) {
    this.stateIdCaseFirstName = stidFstnm;
  }

  public String getStateIdCaseLastName() {
    return stateIdCaseLastName;
  }

  public void setStateIdCaseLastName(String stidLstnm) {
    this.stateIdCaseLastName = stidLstnm;
  }

  public String getStateIdCaseMiddleName() {
    return stateIdCaseMiddleName;
  }

  public void setStateIdCaseMiddleName(String stidMidnm) {
    this.stateIdCaseMiddleName = stidMidnm;
  }

  public String getFkClientId() {
    return fkClientId;
  }

  public void setFkClientId(String fkclientT) {
    this.fkClientId = fkclientT;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }
}
