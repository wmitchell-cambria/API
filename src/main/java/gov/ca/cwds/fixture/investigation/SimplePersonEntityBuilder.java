package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.SimplePerson;

@SuppressWarnings("javadoc")
public class SimplePersonEntityBuilder {

  private String lastName = "Smyth";
  private String firstName = "Susan";

  public SimplePerson build() {
    return new SimplePerson(lastName, firstName);
  }

  public SimplePersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public SimplePersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

}
