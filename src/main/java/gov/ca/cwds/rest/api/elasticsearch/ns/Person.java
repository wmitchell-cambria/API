package gov.ca.cwds.rest.api.elasticsearch.ns;


/**
 * {@link NsElasticsearchObject} representing a Person
 * 
 * @author CWDS API Team
 */
public class Person extends NsElasticsearchObject {

  private String id;
  private String first_name;
  private String last_name;
  private String gender;
  private String date_of_birth;
  private String ssn;

  /**
   * Default constructor
   * */
  public Person() {
    super();
  }

  public Person(String id, String firstName, String lastName, String gender, String dateOfBirth,
      String ssn) {
    super();
    this.id = id;
    this.first_name = firstName;
    this.last_name = lastName;
    this.gender = gender;
    this.date_of_birth = dateOfBirth;
    this.ssn = ssn;
  }


  /**
   * Constructor
   * 
   * @param person The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Person(gov.ca.cwds.rest.api.domain.Person person, String lastUpdatedId) {
    super(lastUpdatedId);
    this.first_name = person.getFirst_name();
    this.last_name = person.getLast_name();
    this.gender = person.getGender();
    this.date_of_birth = person.getDate_of_birth();
    this.ssn = person.getSsn();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the first_name
   */
  public String getFirst_name() {
    return first_name;
  }

  /**
   * @param first_name the first_name to set
   */
  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  /**
   * @return the last_name
   */
  public String getLast_name() {
    return last_name;
  }

  /**
   * @param last_name the last_name to set
   */
  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @param gender the gender to set
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * @return the date_of_birth
   */
  public String getDate_of_birth() {
    return date_of_birth;
  }

  /**
   * @param date_of_birth the date_of_birth to set
   */
  public void setDate_of_birth(String date_of_birth) {
    this.date_of_birth = date_of_birth;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @param ssn the ssn to set
   */
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

}
