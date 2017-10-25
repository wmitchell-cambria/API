package gov.ca.cwds.fixture.contacts;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;

public class ContactEntityBuilder {


  private LastUpdatedBy lastUpdatedBy;
  private String startedAt = "2010-04-27T23:30:14.000Z";
  private String endedAt = "2010-04-28T23:30:14.000Z";
  private Integer purpose = 433;
  private Integer communicationMetod = 408;
  private String status = "C";
  private Set<Integer> services = new HashSet();
  private Integer location = 415;
  private String note = "contact description";
  private Set<PostedIndividualDeliveredService> people = new HashSet();
  private CmsRecordDescriptor legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();

  private PostedIndividualDeliveredService person = new PostedIndividualDeliveredService(
      legacyDescriptor, "first", "middle", "last", "phd", "Mr", "teacher");

  public Contact build() {
    people.add(person);
    return new Contact(legacyDescriptor, lastUpdatedBy, startedAt, endedAt, purpose.toString(),
        communicationMetod.toString(), status, services, location.toString(), note, people);
  }

  public ContactEntityBuilder setLocation(Integer location) {
    this.location = location;
    return this;
  }
}
