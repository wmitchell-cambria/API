package gov.ca.cwds.rest.api.domain.es;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.validation.Date;
import gov.ca.cwds.rest.validation.PastDate;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Person search request for ElasticSearch.
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class PersonSearchRequest extends DomainObject implements Request {

  @ApiModelProperty(example = "Bart")
  @JsonProperty("first_name")
  private String firstName;

  @ApiModelProperty(example = "Simpson")
  @JsonProperty("last_name")
  private String lastName;

  @Date
  @PastDate()
  @ApiModelProperty(example = "2008-09-01")
  @JsonProperty("birth_date")
  private String birthDate;

  /**
   * Default, no-param ctor. Required by frameworks.
   */
  PersonSearchRequest() {}

  /**
   * JSON DropWizard Constructor.
   * 
   * @param firstName the first name to search
   * @param lastName the last name to search
   * @param birthDate the birth date to search
   */
  @JsonCreator
  public PersonSearchRequest(@JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName, @JsonProperty("birth_date") String birthDate) {
    super();
    this.firstName = firstName != null ? firstName.trim() : "";
    this.lastName = lastName != null ? lastName.trim() : "";
    this.birthDate = birthDate != null ? birthDate.trim() : "";
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PersonSearchRequest other = (PersonSearchRequest) obj;
    if (birthDate == null) {
      if (other.birthDate != null)
        return false;
    } else if (!birthDate.equals(other.birthDate))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    return true;
  }


}
