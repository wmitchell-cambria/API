package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * {@link CmsPersistentObject} representing a Injury Harm Detail.
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

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

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

  /**
   * Default constructor
   */
  public InjuryHarmDetail() {
    super();
  }

  /**
   * @param thirdId - thirdId
   * @param injuryHarmType - injuryHarmType
   * @param injuryToBodyDetailIndicator - injuryToBodyDetailIndicator
   * @param allegationId - allegationId
   * @param countySpecificCode - countySpecificCode
   */
  public InjuryHarmDetail(String thirdId, Short injuryHarmType, String injuryToBodyDetailIndicator,
      String allegationId, String countySpecificCode) {
    super();
    this.thirdId = thirdId;
    this.injuryHarmType = injuryHarmType;
    this.injuryToBodyDetailIndicator = injuryToBodyDetailIndicator;
    this.allegationId = allegationId;
    this.countySpecificCode = countySpecificCode;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return this.getThirdId();
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
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

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
