package gov.ca.cwds.fixture.contacts;

import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class LastUpdatedByEntityBuilder {

  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();
  private String id = "0X5";
  private String firstName = "John";
  private String middleInitial = "Q";
  private String lastName = "Public";
  private String suffixTitle = "MS";
  private String prefixTitle = "Mr.";

  public LastUpdatedBy build() {
    return new LastUpdatedBy(legacyDescriptor, firstName, middleInitial, lastName, suffixTitle,
        prefixTitle);
  }

  public LastUpdatedByEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public LastUpdatedByEntityBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public LastUpdatedByEntityBuilder setMiddleInitial(String middleInitial) {
    this.middleInitial = middleInitial;
    return this;
  }

  public LastUpdatedByEntityBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public LastUpdatedByEntityBuilder setSuffixTitle(String suffixTitle) {
    this.suffixTitle = suffixTitle;
    return this;
  }

  public LastUpdatedByEntityBuilder setPrefixTitle(String prefixTitle) {
    this.prefixTitle = prefixTitle;
    return this;
  }
}
