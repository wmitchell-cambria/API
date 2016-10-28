package gov.ca.cwds.rest.resources.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.legacy.StaffPerson;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class StaffPersonResourceTest {

  private static final String ROOT_RESOURCE = "/staffpersons/";
  private static final String FOUND_RESOURCE = "/staffpersons/abc";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new StaffPersonResource(resourceDelegate)).build();

  @Before
  public void setup() throws Exception {}

  /*
   * Get Tests
   */
  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get();
    verify(resourceDelegate).get("abc");
  }

  /*
   * Create Tests
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    StaffPerson staffperson = new StaffPerson("abc", "01/01/1969", "Bart", "Student", "Simpson",
        "Q", "", new BigDecimal(9165551212L), 124, "01/01/1969", "", true, "abcdefghij",
        "description", "id", "03", false, "1234567890", "emailaddy");
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(staffperson, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(staffperson));
  }

  @Test
  public void createValidatesEntity() throws Exception {
    StaffPerson staffperson = new StaffPerson("abc", "01/01/1969", "Bart", "Student", "Simpson",
        "Q", "", new BigDecimal(9165551212L), 124, "01/01/1969", "", true, "abcdefghij",
        "description", "id", "WILL_NOT_VALIDATE", false, "1234567890", "emailaddy");
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(staffperson, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  /*
   * Delete Tests
   */
  @Test
  public void deleteDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .delete();
    verify(resourceDelegate).delete("abc");
  }

  /*
   * Update Tests
   */
  @Test
  public void udpateDelegatesToResourceDelegate() throws Exception {
    StaffPerson staffperson = new StaffPerson("abc", "01/01/1969", "Bart", "Student", "Simpson",
        "Q", "", new BigDecimal(9165551212L), 124, "01/01/1969", "", true, "abcdefghij",
        "description", "id", "03", false, "1234567890", "emailaddy");
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(staffperson, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq("abc"), eq(staffperson));
  }

  @Test
  public void udpateValidatesEntity() throws Exception {
    StaffPerson staffperson = new StaffPerson("abc", "01/01/1969", "Bart", "Student", "Simpson",
        "Q", "", new BigDecimal(9165551212L), 124, "01/01/1969", "", true, "abcdefghij",
        "description", "id", "WILL_NOT_VALIDATE", false, "1234567890", "emailaddy");
    int status = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(staffperson, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }
}
