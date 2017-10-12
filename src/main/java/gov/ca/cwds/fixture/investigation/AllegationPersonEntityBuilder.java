package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.AllegationPerson;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

@SuppressWarnings("javadoc")
public class AllegationPersonEntityBuilder {

  protected String firstName = "Joanna";
  protected String lastName = "Kenneson";
  protected String middleName = "";
  protected String suffixTitle = "";
  protected String dateOfBirth = "2010-10-31";
  private CmsRecordDescriptor legacyDescriptor =
      new CmsRecordDescriptor("1234567ABC", "001-2000-3399-415790", "CLIENT_T", "Client");

  public AllegationPerson build() {
    return new AllegationPerson(firstName, lastName, middleName, suffixTitle, dateOfBirth,
        legacyDescriptor);
  }

  public AllegationPersonEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public AllegationPersonEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public AllegationPersonEntityBuilder setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public AllegationPersonEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public AllegationPersonEntityBuilder setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public AllegationPersonEntityBuilder setLegacyDescriptor(CmsRecordDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

}
