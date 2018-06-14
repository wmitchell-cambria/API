package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity.findByScreeningId",
    query = "FROM gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity"
        + " WHERE screeningId = :screeningId")
@Entity
@Table(name = "screening_addresses")
@SuppressWarnings("squid:S2160")
public class ScreeningAddressEntity extends ApiObjectIdentity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screening_addresses_id_seq")
  @GenericGenerator(name = "screening_addresses_id_seq",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name",
          value = "screening_addresses_id_seq")})
  private String id;

  @Column(name = "screening_id")
  private String screeningId;

  @Column(name = "address_id")
  private String addressId;

  /**
   * Default constructor, required for Hibernate.
   */
  public ScreeningAddressEntity() {
    // default
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getScreeningId() {
    return screeningId;
  }

  public void setScreeningId(String screeningId) {
    this.screeningId = screeningId;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

}
