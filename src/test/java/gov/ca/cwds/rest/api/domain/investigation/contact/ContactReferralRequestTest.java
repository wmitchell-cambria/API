package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.fixture.contacts.ContactRequestBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ContactReferralRequestTest {

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    ContactRequest contact = new ContactRequestBuilder().build();
    ContactReferralRequest domain = new ContactReferralRequest("123", contact);
    assertThat(domain.getContactRequest(), is(equalTo(contact)));

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ContactReferralRequest.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

}
