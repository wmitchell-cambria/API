package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;


@SuppressWarnings("javadoc")
public class ContactResourceTest {

  private ContactResource resource;
  private ContactRequest contact;
  private TypedResourceDelegate<String, ContactReferralRequest> service;
  private String referralId = "1";
  private String contactId = "2";


  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new ContactResource(service);
  }

  @Test
  public void callContactServiceOnCreate() throws Exception {
    resource.create("1", contact);
    ContactReferralRequest contactReferralRequest = new ContactReferralRequest("1", contact);
    verify(service).create(contactReferralRequest);
  }

  @Test
  public void callContactServiceServiceOnFind() throws Exception {
    resource.find(referralId, contactId);
    verify(service).get(referralId + ":" + contactId);
  }

  @Test
  public void callContactServiceServiceOnUpdate() throws Exception {
    resource.update(referralId, contactId, contact);
    ContactReferralRequest contactReferralRequest = new ContactReferralRequest(referralId, contact);
    verify(service).update(contactId, contactReferralRequest);
  }

  @Test
  public void callContactServiceServiceOnFindAll() throws Exception {
    resource.findAll(referralId);
    verify(service).get(referralId);
  }
}
