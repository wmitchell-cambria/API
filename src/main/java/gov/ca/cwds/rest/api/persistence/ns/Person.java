package gov.ca.cwds.rest.api.persistence.ns;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Person
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "person")
public class Person extends PersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_id")
  @SequenceGenerator(name = "seq_person_id", sequenceName = "seq_person_id", allocationSize = 50)
  @Column(name = "person_id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "date_of_birth")
  @Type(type = "date")
  private Date dateOfBirth;

  @Column(name = "ssn")
  private String ssn;

  @Column(name = "person_address_id")
  private Long personAddressId;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Person() {
    super();
  }


  public Person(String firstName, String lastName, String gender, Date dateOfBirth, String ssn,
      Long personAddressId) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.personAddressId = personAddressId;
  }


  /**
   * Constructor
   * 
   * @param staffPerson The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Person(gov.ca.cwds.rest.api.domain.Person person, Long lastUpdatedId) {
    super(lastUpdatedId);
    this.firstName = person.getFirst_name();
    this.lastName = person.getLast_name();
    this.gender = person.getGender();
    this.dateOfBirth = DomainObject.uncookDateString(person.getDate_of_birth());
    this.ssn = person.getSsn();
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
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }


  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }


  /**
   * @return the dateOfBirth
   */
  public Date getDateOfBirth() {
    return dateOfBirth;
  }


  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }


  /**
   * @return the personAddressId
   */
  public Long getPersonAddressId() {
    return personAddressId;
  }

  public void setPersonAddressId(Long personAddressId) {
    this.personAddressId = personAddressId;
  }


}
