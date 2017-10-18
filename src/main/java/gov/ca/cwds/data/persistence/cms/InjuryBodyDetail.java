package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;


@NamedQueries({@NamedQuery(
    name = "gov.ca.cwds.data.dao.investigation.InjuryBodyDetail.findInjuryBodyDetailsByInjuryHarmDetailId",
    query = "FROM InjuryBodyDetail WHERE secondaryInjuryHarmDetailId = :secondaryInjuryHarmDetailId"),
    @NamedQuery(
        name = "gov.ca.cwds.data.dao.investigation.InjuryBodyDetail.findInjuryBodyDetailsByAllegationId",
        query = "FROM InjuryBodyDetail WHERE primaryInjuryHarmDetailId = :allegationId")})
@SuppressWarnings("serial")
@Entity
@Table(name = "IJBD_DET")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InjuryBodyDetail extends CmsPersistentObject {
  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ABS_BPTC")
  private Short physicalAbuseBodyPartType;

  @Column(name = "FKIJHM_DET")
  private String primaryInjuryHarmDetailId;

  @Column(name = "FKIJHM_DE0")
  private String secondaryInjuryHarmDetailId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Override
  public Serializable getPrimaryKey() {
    return this.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the physicalAbuseBodyPartType
   */
  public Short getPhysicalAbuseBodyPartType() {
    return physicalAbuseBodyPartType;
  }

  /**
   * @return the primaryInjuryHarmDetailId
   */
  public String getPrimaryInjuryHarmDetailId() {
    return primaryInjuryHarmDetailId;
  }

  /**
   * @return the secondaryInjuryHarmDetailId
   */
  public String getSecondaryInjuryHarmDetailId() {
    return secondaryInjuryHarmDetailId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

}
