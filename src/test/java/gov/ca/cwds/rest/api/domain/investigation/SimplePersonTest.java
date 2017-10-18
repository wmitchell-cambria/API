package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class SimplePersonTest {

  private String lastName = "Smith";
  private String firstName = "John";

  @Before
  public void setup() {}

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimplePerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimplePerson domain = new SimplePerson(lastName, firstName);
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
  }

}
