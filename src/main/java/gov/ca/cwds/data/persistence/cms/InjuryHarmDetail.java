package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} representing a Referral.
 * 
 * @author CWDS API Team
 */
@NamedQuery(
    name = "gov.ca.cwds.data.dao.investigation.InjuryHarmDetail.findInjuryHarmDetailsByAllegationId",
    query = "FROM InjuryHarmDetail WHERE allegationId = :allegationId")
@Entity
@Table(name = "IJHM_DET")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InjuryHarmDetail extends CmsPersistentObject {

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String id;

  @Type(type = "short")
  @Column(name = "INJR_HMC")
  private Short injuryHarmType;

  @Column(name = "IJBD_DET_B")
  private String injuryToBodyDetailIndicator;

  @Id
  @Column(name = "FKALLGTN_T", length = CMS_ID_LEN)
  private String allegationId;

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
   * @return the injuryHarmType
   */
  public Short getInjuryHarmType() {
    return injuryHarmType;
  }

  /**
   * @return the injuryToBodyDetailIndicator
   */
  public String getInjuryToBodyDetailIndicator() {
    return injuryToBodyDetailIndicator;
  }

  /**
   * @return the allegationId
   */
  public String getAllegationId() {
    return allegationId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

}
