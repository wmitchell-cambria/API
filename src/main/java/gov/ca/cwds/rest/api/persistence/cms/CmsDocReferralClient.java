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
    query = "SELECT rf.identifier as referl_id, cl.identifier as client_id,"
        + " rtrim(cl.com_fst_nm) as com_fst_nm, rtrim(cl.com_mid_nm) as com_mid_nm "
        + ", rtrim(cl.com_lst_nm) as com_lst_nm "
        + ", ( SELECT rtrim(first_nm)||' '||rtrim(middle_nm)||' '||rtrim(last_nm) "
        + "FROM cwsint.ocl_nm_t "
        + "WHERE fkclient_t = rc.fkclient_t fetch first rows only) as otherName, ( "
        + "SELECT rtrim(short_dsc) FROM cwsint.ocl_nm_t n, cwsint.sys_cd_c m "
        + "WHERE sys_id = name_tpc AND fkclient_t = rc.fkclient_t AND fks_meta_t = 'NAME_TPC' fetch first rows only) as name_type "
        + " , ( SELECT rtrim(street_nm) ||' '|| street_no ||' '||rtrim(city_nm)||' '||zip_no "
        + "FROM cwsint.addrs_t adr , cwsint.cl_addrt cla "
        + "WHERE cla.fkclient_t = cl.identifier AND adr.identifier = cla.fkaddrs_t AND cla.eff_end_dt IS null) as address "
        + " , ( SELECT rtrim(short_dsc) FROM cwsint.cl_addrt cla, cwsint.sys_cd_c m "
        + "WHERE sys_id = addr_tpc AND cla.fkclient_t = cl.identifier AND cla.eff_end_dt IS null AND fks_meta_t = 'ADDR_TPC') as address_type "
        + " , cl.birth_dt, ctrl.doc_handle, rtrim(ctrl.doc_name) as doc_name "
        + " , ctrl.doc_date as doc_added_upd, rf.LST_UPD_ID, rf.LST_UPD_TS "
        + "FROM cwsint.oth_doct od JOIN cwsint.referl_t rf "
        + " ON rf.identifier = od.fkreferl_t JOIN cwsint.drmsdoct dd "
        + " ON dd.identifier = od.fkdrmsdoct JOIN cwsint.TSCNTRLT ctrl "
        + " ON ctrl.doc_handle = dd.dochndl_nm AND ctrl.cmprs_prg != 'DELETED' AND ctrl.doc_handle != 'DUMMY' JOIN cwsint.refr_clt rc "
        + " ON od.fkreferl_t = rc.fkreferl_t JOIN cwsint.client_t cl "
        + " ON cl.identifier = rc.fkclient_t WHERE ctrl.DOC_HANDLE = :docHandle ORDER BY 1,2 "
        + "FOR READ ONLY",
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

  @Column(name = "OTHERNAME")
  private String otherName;

  @Column(name = "NAME_TYPE")
  private String nameType;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "ADDRESS_TYPE")
  private String addressType;

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocReferralClient() {
    super();
  }

  public CmsDocReferralClient(String docHandle, String referlId, String clientId,
      String commonFirstName, String commonMiddleName, String commonLastName, Date birthDate,
      String otherName, String nameType, String address, String addressType) {
    super();
    this.docHandle = docHandle;
    this.referlId = referlId;
    this.clientId = clientId;
    this.commonFirstName = commonFirstName;
    this.commonMiddleName = commonMiddleName;
    this.commonLastName = commonLastName;
    this.birthDate = birthDate;
    this.otherName = otherName;
    this.nameType = nameType;
    this.address = address;
    this.addressType = addressType;
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

  /**
   * @return the otherName
   */
  public String getOtherName() {
    return otherName;
  }

  /**
   * @param otherName the otherName to set
   */
  public void setOtherName(String otherName) {
    this.otherName = otherName;
  }

  /**
   * @return the nameType
   */
  public String getNameType() {
    return nameType;
  }

  /**
   * @param nameType the nameType to set
   */
  public void setNameType(String nameType) {
    this.nameType = nameType;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the addressType
   */
  public String getAddressType() {
    return addressType;
  }

  /**
   * @param addressType the addressType to set
   */
  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

}
