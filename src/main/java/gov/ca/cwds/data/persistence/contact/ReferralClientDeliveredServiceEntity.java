package gov.ca.cwds.data.persistence.contact;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;

/**
 * {@link CmsPersistentObject} Class representing an ReferralClientDeliveredService.
 * 
 * @author CWDS API Team
 */
@NamedQuery(
    name = "gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity.findAllForReferralId",
    query = "FROM ReferralClientDeliveredServiceEntity WHERE FKREFR_CL0 = :referralId")
@Entity
@Table(name = "RFDLSVCT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferralClientDeliveredServiceEntity extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  private ReferralClientDeliveredServiceEmbeddable referralClientDeliveredServiceEmbeddable;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ReferralClientDeliveredServiceEntity() {
    super();
  }

  /**
   * @param deliveredServiceId - deliveredServiceId
   * @param referralId - referralId
   * @param clientId - clientId
   * @param countySpecificCode - countySpecificCode
   * @param lastUpdatedId The Identifier of logged in user
   * @param lastUpdatedTime The time of update
   */
  public ReferralClientDeliveredServiceEntity(String deliveredServiceId, String referralId,
      String clientId, String countySpecificCode, String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.countySpecificCode = countySpecificCode;
    this.referralClientDeliveredServiceEmbeddable =
        new ReferralClientDeliveredServiceEmbeddable(deliveredServiceId, referralId, clientId);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public ReferralClientDeliveredServiceEmbeddable getPrimaryKey() {
    return getReferralClientDeliveredServiceEmbeddable();
  }

  /**
   * @return the referralClientDeliveredServiceEmbeddable
   */
  public ReferralClientDeliveredServiceEmbeddable getReferralClientDeliveredServiceEmbeddable() {
    return referralClientDeliveredServiceEmbeddable;
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
