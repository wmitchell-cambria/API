package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class SimplePersonWithRolesTest {

  private Set<String> roles = null;
  private String lastName = "Smith";
  private String firstName = "John";
  private String RestrictedSensitivityIndicator = "R";
  private String sealedSensitivityIndicator = "S";

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimplePerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimplePersonWithRoles domain =
        new SimplePersonWithRoles(roles, lastName, firstName, RestrictedSensitivityIndicator);
    assertThat(domain.getRoles(), is(equalTo(roles)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getSensitivityIndicator(), is(equalTo(RestrictedSensitivityIndicator)));
  }

  @Test
  public void testGetSealedTrue() {
    SimplePersonWithRoles domain =
        new SimplePersonWithRoles(roles, lastName, firstName, RestrictedSensitivityIndicator);
    assertThat(domain.getSealed(), is(equalTo(Boolean.TRUE)));

  }

  @Test
  public void testGetSealedFalse() {
    SimplePersonWithRoles domain =
        new SimplePersonWithRoles(roles, lastName, firstName, sealedSensitivityIndicator);
    assertThat(domain.getSealed(), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void testBlankGetSealedEqualsFalse() {
    SimplePersonWithRoles domain = new SimplePersonWithRoles(roles, lastName, firstName, "");
    assertThat(domain.getSealed(), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void testGetSenstiveTrue() {
    SimplePersonWithRoles domain =
        new SimplePersonWithRoles(roles, lastName, firstName, sealedSensitivityIndicator);
    assertThat(domain.getSensitive(), is(equalTo(Boolean.TRUE)));

  }

  @Test
  public void testGetSensitiveFalse() {
    SimplePersonWithRoles domain =
        new SimplePersonWithRoles(roles, lastName, firstName, RestrictedSensitivityIndicator);
    assertThat(domain.getSensitive(), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void testBlankGetSensitiveEqualsFalse() {
    SimplePersonWithRoles domain = new SimplePersonWithRoles(roles, lastName, firstName, "");
    assertThat(domain.getSensitive(), is(equalTo(Boolean.FALSE)));
  }

}
