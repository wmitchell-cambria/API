package gov.ca.cwds.fixture.contacts;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;

public class ContactRequestBuilder {

  private String startedAt = "2010-04-27T23:30:14.000Z";
  private String endedAt = "2010-04-28T23:30:14.000Z";
  private String purpose = "433";
  private String communicationMetod = "408";
  private String status = "C";
  private Set<Integer> services = new HashSet();
  private String location = "415";
  private String note =
      "some text describing the contact of up to 8000 characters can be stored in CMS";
  private Set<PostedIndividualDeliveredService> people = new HashSet();

  private PostedIndividualDeliveredService person = new PostedIndividualDeliveredService("CLIENT_T",
      "1234567ABC", "first", "middle", "last", "phd", "Mr", "teacher");

  public ContactRequest build() {
    people.add(person);
    return new ContactRequest(startedAt, endedAt, purpose.toString(), communicationMetod.toString(),
        status, services, location.toString(), note, people);
  }

  public ContactRequestBuilder setLocation(String location) {
    this.location = location;
    return this;
  }

  public ContactRequestBuilder setStartedAt(String startedAt) {
    this.startedAt = startedAt;
    return this;
  }

}
