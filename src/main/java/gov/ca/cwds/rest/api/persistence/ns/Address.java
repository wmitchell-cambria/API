package gov.ca.cwds.rest.api.persistence.ns;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * {@link PersistentObject} representing an Address
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "address")
public class Address extends PersistentObject {

  // @SequenceGenerator(name = "people", sequenceName = "seq_address_id", allocationSize = 1,
  // initialValue=1)
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "people")
  @Id
  @Column(name = "address_id")
  private Long id;

  @Column(name = "street_address")
  private String streetAddress;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private Integer zip;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Address() {
    super();
  }

  public static int count = 1;

  public Address(Long id, String streetAddress, String city, String state) {
    super();
    this.id = id;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
  }

  /**
   * Constructor
   * 
   * @param address The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Address(gov.ca.cwds.rest.api.domain.Address address, Long lastUpdatedId) {
    super(lastUpdatedId);
    this.id = (long) count++;
    this.streetAddress = address.getStreet_address();
    this.city = address.getCity();
    this.state = address.getState();
    this.zip = address.getZip();
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getId();
  }


  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }


  /**
   * @return the streetAddress
   */
  public String getStreetAddress() {
    return streetAddress;
  }


  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }


  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the zip
   */
  public Integer getZip() {
    return zip;
  }



}
