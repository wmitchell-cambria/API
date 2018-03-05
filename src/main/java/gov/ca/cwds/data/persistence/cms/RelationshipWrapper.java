package gov.ca.cwds.data.persistence.cms;

import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NamedNativeQuery(
        name = "gov.ca.cwds.data.persistence.cms.RelationshipWrapper.findAllRelatedClientsByClientId",
        resultClass = RelationshipWrapper.class, readOnly = true,
        query = "SELECT DISTINCT \n"
                + "    CLNS.IDENTIFIER        AS Primary_LEGACY_ID, \n"
                + "    CLNS.COM_FST_NM        AS Primary_FIRST_NAME, \n"
                + "    CLNS.COM_LST_NM        AS Primary_LAST_NAME, \n"
                + "    CLNR.IDENTIFIER        AS RELATION_ID, \n"
                + "    CLNR.CLNTRELC          AS Primary_REL_ID, \n"
                + "    sc2.SHORT_DSC          AS Secondary_REL_TYPE, \n"
                + "    sc2.SYS_ID             AS Secondary_REL_CODE, \n"
                + "    CLNP.IDENTIFIER        AS Secondary_LEGACY_ID, \n"
                + "    CLNP.COM_FST_NM        AS Secondary_FIRST_NAME, \n"
                + "    CLNP.COM_LST_NM        AS Secondary_LAST_NAME \n"
                + "FROM  {h-schema}CLIENT_T  GT \n"
                + "JOIN {h-schema}CLN_RELT CLNR  ON GT.IDENTIFIER   = CLNR.FKCLIENT_T \n"
                + "JOIN {h-schema}CLIENT_T CLNS  ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
                + "JOIN {h-schema}CLIENT_T CLNP  ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
                + "JOIN {h-schema}SYS_CD_C SC  ON SC.SYS_ID         = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
                + "JOIN {h-schema}SYS_CD_C SC2 ON SC2.SYS_ID        = CAST(SC.LONG_DSC AS SMALLINT) \n"
                + "WHERE \n"
                + "    CLNS.IDENTIFIER = :clientId \n"
                + "    OR CLNP.IDENTIFIER = :clientId \n"
                + "UNION \n"
                + "SELECT DISTINCT \n"
                + "    CLNS.IDENTIFIER        AS Primary_LEGACY_ID, \n"
                + "    CLNS.COM_FST_NM        AS Primary_FIRST_NAME, \n"
                + "    CLNS.COM_LST_NM        AS Primary_LAST_NAME, \n"
                + "    CLNR.IDENTIFIER        AS RELATION_ID, \n"
                + "    CLNR.CLNTRELC          AS Primary_REL_ID, \n"
                + "    sc2.SHORT_DSC          AS Secondary_REL_TYPE, \n"
                + "    sc2.SYS_ID             AS Secondary_REL_CODE, \n"
                + "    CLNP.IDENTIFIER        AS Secondary_LEGACY_ID, \n"
                + "    CLNP.COM_FST_NM        AS Secondary_FIRST_NAME, \n"
                + "    CLNP.COM_LST_NM        AS Secondary_LAST_NAME \n"
                + "FROM  {h-schema}CLIENT_T GT \n"
                + "JOIN {h-schema}CLN_RELT CLNR ON GT.IDENTIFIER   = CLNR.FKCLIENT_0 \n"
                + "JOIN {h-schema}CLIENT_T CLNS ON CLNR.FKCLIENT_0 = CLNS.IDENTIFIER \n"
                + "JOIN {h-schema}CLIENT_T CLNP ON CLNR.FKCLIENT_T = CLNP.IDENTIFIER \n"
                + "JOIN {h-schema}SYS_CD_C SC  ON SC.SYS_ID        = CLNR.CLNTRELC AND SC.FKS_META_T = 'CLNTRELC' \n"
                + "JOIN {h-schema}SYS_CD_C SC2 ON SC2.SYS_ID       = CAST(SC.LONG_DSC AS SMALLINT) \n"
                + "WHERE \n"
                + "    CLNS.IDENTIFIER = :clientId \n"
                + "    OR CLNP.IDENTIFIER = :clientId \n"
                )
public class RelationshipWrapper {
    @Id
    @Column(name = "RELATION_ID")
    private String relationId;
    @Column(name = "Primary_LEGACY_ID")
    private String primaryLegacyId;
    @Column(name = "Primary_FIRST_NAME")
    private String primaryFirstName;
    @Column(name = "Primary_LAST_NAME")
    private String primaryLastName;
    @Column(name = "Secondary_LEGACY_ID")
    private String secondaryLegacyId;
    @Column(name = "Secondary_FIRST_NAME")
    private String secondaryFirstName;
    @Column(name = "Secondary_LAST_NAME")
    private String secondaryLastName;
    @Column(name = "Primary_REL_ID")
    private String primaryRelationshipCode;
    @Column(name = "Secondary_REL_CODE")
    private String secondaryRelationshipCode;

    public RelationshipWrapper() { }

    public RelationshipWrapper( String relationId, String primaryLegacyId, String secondaryLegacyId, String primaryFirstName, String primaryLastName, String secondaryFirstName, String secondaryLastName, String primaryRelationshipCode, String secondaryRelationshipCode) {
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
}
