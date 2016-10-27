package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} represents a custom query to find Referral and Client references.
 * Documents.
 * 
 * @author CWDS API Team
 */
@Entity
@NamedNativeQuery(name = "DocReferalClient",
    query = "select rf.identifier as referl_id, cl.identifier as client_id, "
        + "rtrim(cl.com_fst_nm) as com_fst_nm, rtrim(cl.com_mid_nm) as com_mid_nm, rtrim(cl.com_lst_nm) as com_lst_nm,"
        + "cl.birth_dt, ctrl.doc_handle, rtrim(ctrl.doc_name) as doc_name, ctrl.doc_date as doc_added_upd,rf.LST_UPD_ID,rf.LST_UPD_TS "
        + "from cwsint.oth_doct od " + "JOIN cwsint.referl_t rf on rf.identifier = od.fkreferl_t "
        + "JOIN cwsint.drmsdoct dd on dd.identifier = od.fkdrmsdoct  "
        + "JOIN cwsint.TSCNTRLT ctrl on ctrl.doc_handle = dd.dochndl_nm and ctrl.cmprs_prg != 'DELETED' and ctrl.doc_handle != 'DUMMY' "
        + "JOIN cwsint.refr_clt rc on od.fkreferl_t = rc.fkreferl_t "
        + "JOIN cwsint.client_t cl on cl.identifier = rc.fkclient_t "
        + "where ctrl.DOC_HANDLE = :docHandle",
    resultClass = CmsDocReferralClient.class)
public class CmsDocReferralClient extends CmsPersistentObject implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "REFERL_ID")
  private String referlId;

  @Id
  @Column(name = "CLIENT_ID")
  private String clientId;

  @Id
  @Column(name = "DOC_HANDLE")
  private String docHandle;

  @Column(name = "DOC_NAME")
  private String docName;

  @Type(type = "date")
  @Column(name = "DOC_ADDED_UPD")
  private Date docAddedDate;

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
  public CmsDocReferralClient() {
    super();
  }

  public CmsDocReferralClient(String docHandle, String referlId, String clientId,
      String commonFirstName, String commonMiddleName, String commonLastName, Date birthDate) {
    super();
    this.docHandle = docHandle;
    this.referlId = referlId;
    this.clientId = clientId;
    this.commonFirstName = commonFirstName;
    this.commonMiddleName = commonMiddleName;
    this.commonLastName = commonLastName;
    this.birthDate = birthDate;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return new PrimaryKey3(this.getDocHandle(), this.getReferlId(), this.getClientId());
  }

  /**
   * @return the id
   */
  public String getId() {
    return this.docHandle;
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

  public String getDocHandle() {
    return docHandle;
  }

  public void setDocHandle(String docHandle) {
    this.docHandle = docHandle;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public Date getDocAddedDate() {
    return docAddedDate;
  }

  public void setDocAddedDate(Date docAddedDate) {
    this.docAddedDate = docAddedDate;
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
