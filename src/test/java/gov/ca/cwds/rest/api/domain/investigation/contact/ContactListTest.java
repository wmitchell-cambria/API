package gov.ca.cwds.rest.api.domain.investigation.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.contacts.ContactListEntityBuilder;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class ContactListTest {
  private ObjectMapper MAPPER = new ObjectMapper();

  @Test
  public void defaultConstructorTest() {
    ContactList contact = new ContactList();
    assertNotNull(contact);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    Set<Integer> services = new HashSet<>();
    final Set<PostedIndividualDeliveredService> people = validPeople();
    LastUpdatedBy lastUpdatedByPerson =
        new LastUpdatedBy("0X5", "Joe", "M", "Friday", "Mr.", "Jr.");
    Contact contact = new Contact("1234567ABC", lastUpdatedByPerson, "2010-04-27T23:30:14.000Z", "",
        "433", "408", "C", services, "415",
        "some text describing the contact of up to 8000 characters can be stored in CMS", people);
    Set<Contact> contacts = new HashSet<>();
    contacts.add(contact);
    ContactList domain = new ContactList(contacts);
    assertThat(domain.getContacts(), is(equalTo(contacts)));

  }


  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ContactList.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private Set<PostedIndividualDeliveredService> validPeople() {
    final Set<PostedIndividualDeliveredService> ret = new HashSet<>();
    ret.add(new PostedIndividualDeliveredService("CLIENT_T", "3456789ABC", "John", "Bob", "Smith",
        "Mr.", "Jr.", ""));
    ret.add(new PostedIndividualDeliveredService("REPTR_T", "4567890ABC ", "Sam", "Bill", "Jones",
        "Mr.", "III", "Reporter"));
    return ret;
  }

  @Test
  @Ignore
  public void testSerilizedOutput()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    ContactList contactList = new ContactListEntityBuilder().build();
    final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(contactList);
    System.out.println(expected);
  }

}
