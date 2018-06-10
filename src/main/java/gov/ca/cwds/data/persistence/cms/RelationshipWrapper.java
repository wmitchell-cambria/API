package gov.ca.cwds.data.persistence.cms;

import static gov.ca.cwds.data.persistence.cms.RelationshipWrapper.FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.NamedNativeQuery;

@Entity
//@formatter:off
@NamedNativeQuery(name = FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID,
    resultClass = RelationshipWrapper.class, readOnly = true,
    query = "SELECT DISTINCT \n"
        + "    CLNS.IDENTIFIER        AS PRIMARY_LEGACY_ID, \n"
        + "    TRIM(CLNS.COM_FST_NM)  AS PRIMARY_FIRST_NAME, \n"
        + "    TRIM(CLNS.COM_LST_NM)  AS PRIMARY_LAST_NAME, \n"
        + "    TRIM(CLNS.SUFX_TLDSC)  AS PRIMARY_NAME_SUFFIX, \n"
        + "    CLNR.IDENTIFIER        AS RELATION_ID, \n"
        + "    CLNR.CLNTRELC          AS PRIMARY_REL_ID, \n"
        + "    TRIM(sc2.SHORT_DSC)    AS SECONDARY_REL_TYPE, \n"
        + "    sc2.SYS_ID             AS SECONDARY_REL_CODE, \n"
        + "    CLNP.IDENTIFIER        AS SECONDARY_LEGACY_ID, \n"
        + "    TRIM(CLNP.COM_FST_NM)  AS SECONDARY_FIRST_NAME, \n"
        + "    TRIM(CLNP.COM_LST_NM)  AS SECONDARY_LAST_NAME, \n"
        + "    TRIM(CLNP.SUFX_TLDSC)  AS SECONDARY_NAME_SUFFIX \n"
        + "FROM {h-schema}CLIENT_T GT \n"
        + "JOIN {h-schema}CLN_RELT CLNR  ON GT.IDENTIFIER   = CLNR.FKCLIENT_T \n"
        + "JOIN {h-schema}CLIENT_T CLNS  ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
        + "JOIN {h-schema}CLIENT_T CLNP  ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
        + "JOIN {h-schema}SYS_CD_C SC  ON SC.SYS_ID         = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
        + "JOIN {h-schema}SYS_CD_C SC2 ON SC2.SYS_ID        = CAST(SC.LONG_DSC AS SMALLINT) \n"
        + "WHERE CLNR.FKCLIENT_0 = :clientId OR CLNR.FKCLIENT_T = :clientId \n"
        + "UNION \n"
        + "SELECT DISTINCT \n"
        + "    CLNS.IDENTIFIER        AS PRIMARY_LEGACY_ID, \n"
        + "    TRIM(CLNS.COM_FST_NM)  AS PRIMARY_FIRST_NAME, \n"
        + "    TRIM(CLNS.COM_LST_NM)  AS PRIMARY_LAST_NAME, \n"
        + "    TRIM(CLNS.SUFX_TLDSC)  AS PRIMARY_NAME_SUFFIX, \n"
        + "    CLNR.IDENTIFIER        AS RELATION_ID, \n"
        + "    CLNR.CLNTRELC          AS PRIMARY_REL_ID, \n"
        + "    TRIM(sc2.SHORT_DSC     AS SECONDARY_REL_TYPE, \n"
        + "    sc2.SYS_ID             AS SECONDARY_REL_CODE, \n"
        + "    CLNP.IDENTIFIER        AS SECONDARY_LEGACY_ID, \n"
        + "    TRIM(CLNP.COM_FST_NM)  AS SECONDARY_FIRST_NAME, \n"
        + "    TRIM(CLNP.COM_LST_NM)  AS SECONDARY_LAST_NAME, \n"
        + "    TRIM(CLNP.SUFX_TLDSC)  AS SECONDARY_NAME_SUFFIX \n"
        + "FROM {h-schema}CLIENT_T GT \n"
        + "JOIN {h-schema}CLN_RELT CLNR ON GT.IDENTIFIER   = CLNR.FKCLIENT_0 \n"
        + "JOIN {h-schema}CLIENT_T CLNS ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
        + "JOIN {h-schema}CLIENT_T CLNP ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
        + "JOIN {h-schema}SYS_CD_C SC  ON SC.SYS_ID        = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
        + "JOIN {h-schema}SYS_CD_C SC2 ON SC2.SYS_ID       = CAST(SC.LONG_DSC AS SMALLINT) \n"
        + "WHERE \n CLNR.FKCLIENT_0 = :clientId OR CLNR.FKCLIENT_T = :clientId \n" 
        + "WITH UR")
//@formatter:on
public class RelationshipWrapper {

  public static final String FIND_ALL_RELATED_CLIENTS_BY_CLIENT_ID =
      "gov.ca.cwds.data.persistence.cms.RelationshipWrapper.findAllRelatedClientsByClientId";

  @Id
  @Column(name = "RELATION_ID")
  private String relationId;

  @Column(name = "Primary_LEGACY_ID")
  private String primaryLegacyId;

  @Column(name = "Primary_FIRST_NAME")
  private String primaryFirstName;

  @Column(name = "Primary_LAST_NAME")
  private String primaryLastName;

  @Column(name = "Primary_NAME_SUFFIX")
  private String primaryNameSuffix;

  @Column(name = "Secondary_LEGACY_ID")
  private String secondaryLegacyId;

  @Column(name = "Secondary_FIRST_NAME")
  private String secondaryFirstName;

  @Column(name = "Secondary_LAST_NAME")
  private String secondaryLastName;

  @Column(name = "Secondary_NAME_SUFFIX")
  private String secondaryNameSuffix;

  @Column(name = "Primary_REL_ID")
  private String primaryRelationshipCode;

  @Column(name = "Secondary_REL_CODE")
  private String secondaryRelationshipCode;

  public RelationshipWrapper() {}

  public RelationshipWrapper(String relationId, String primaryLegacyId, String secondaryLegacyId,
      String primaryFirstName, String primaryLastName, String secondaryFirstName,
      String secondaryLastName, String primaryRelationshipCode, String secondaryRelationshipCode) {
    this.relationId = relationId;
    this.primaryLegacyId = primaryLegacyId;
    this.secondaryLegacyId = secondaryLegacyId;
    this.primaryFirstName = primaryFirstName;
    this.primaryLastName = primaryLastName;
    this.secondaryFirstName = secondaryFirstName;
    this.secondaryLastName = secondaryLastName;
    this.primaryRelationshipCode = primaryRelationshipCode;
    this.secondaryRelationshipCode = secondaryRelationshipCode;
  }

  public String getRelationId() {
    return relationId;
  }

  public void setRelationId(String relationId) {
    this.relationId = relationId;
  }

  public String getPrimaryLegacyId() {
    return primaryLegacyId;
  }

  public void setPrimaryLegacyId(String primaryLegacyId) {
    this.primaryLegacyId = primaryLegacyId;
  }

  public String getSecondaryLegacyId() {
    return secondaryLegacyId;
  }

  public void setSecondaryLegacyId(String secondaryLegacyId) {
    this.secondaryLegacyId = secondaryLegacyId;
  }

  public String getPrimaryFirstName() {
    return primaryFirstName;
  }

  public void setPrimaryFirstName(String primaryFirstName) {
    this.primaryFirstName = primaryFirstName;
  }

  public String getPrimaryLastName() {
    return primaryLastName;
  }

  public void setPrimaryLastName(String primaryLastName) {
    this.primaryLastName = primaryLastName;
  }

  public String getSecondaryFirstName() {
    return secondaryFirstName;
  }

  public void setSecondaryFirstName(String secondaryFirstName) {
    this.secondaryFirstName = secondaryFirstName;
  }

  public String getSecondaryLastName() {
    return secondaryLastName;
  }

  public void setSecondaryLastName(String secondaryLastName) {
    this.secondaryLastName = secondaryLastName;
  }

  public String getPrimaryRelationshipCode() {
    return primaryRelationshipCode;
  }

  public void setPrimaryRelationshipCode(String primaryRelationshipCode) {
    this.primaryRelationshipCode = primaryRelationshipCode;
  }

  public String getSecondaryRelationshipCode() {
    return secondaryRelationshipCode;
  }

  public void setSecondaryRelationshipCode(String secondaryRelationshipCode) {
    this.secondaryRelationshipCode = secondaryRelationshipCode;
  }

  public String getPrimaryNameSuffix() {
    return primaryNameSuffix;
  }

  public void setPrimaryNameSuffix(String primaryNameSuffix) {
    this.primaryNameSuffix = primaryNameSuffix;
  }

  public String getSecondaryNameSuffix() {
    return secondaryNameSuffix;
  }

  public void setSecondaryNameSuffix(String secondaryNameSuffix) {
    this.secondaryNameSuffix = secondaryNameSuffix;
  }
}
