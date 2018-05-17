package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOIReporterTest {

  private Role role = Role.MANDATED_REPORTER;
  HOIReporter target;
  private String id = "jhdgfkhaj";
  private String firstName = "Barney";
  private String lastName = "Dino";
  private String nameSuffix = "Jr.";
  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj", new DateTime(),
          LegacyTable.REPORTER.getName(), LegacyTable.REPORTER.getDescription());

  @Before
  public void setup() throws Exception {
    target = new HOIReporter();
  }

  @Test
  public void testRoleMandatedReporter() throws Exception {
    HOIReporter domain =
        new HOIReporter(role, id, firstName, lastName, nameSuffix, legacyDescriptor);
    domain.setRole(Role.MANDATED_REPORTER);
    assertThat(domain.getRole(), is(Role.MANDATED_REPORTER));
  }

  @Test
  public void testRoleSelfReporter() throws Exception {
    HOIReporter domain =
        new HOIReporter(role, id, firstName, lastName, nameSuffix, legacyDescriptor);
    domain.setRole(Role.SELF_REPORTER);
    assertThat(domain.getRole(), is(Role.SELF_REPORTER));
  }

  @Test
  public void testRoleAnonymousReporter() throws Exception {
    HOIReporter domain =
        new HOIReporter(role, id, firstName, lastName, nameSuffix, legacyDescriptor);
    domain.setRole(Role.ANONYMOUS_REPORTER);
    assertThat(domain.getRole(), is(Role.ANONYMOUS_REPORTER));
  }

  @Test
  public void testRoleNonMandatedReporter() throws Exception {
    HOIReporter domain =
        new HOIReporter(role, id, firstName, lastName, nameSuffix, legacyDescriptor);
    domain.setRole(Role.NON_MANDATED_REPORTER);
    assertThat(domain.getRole(), is(Role.NON_MANDATED_REPORTER));
  }

  @Test
  public void testRoleDescription() throws Exception {
    HOIReporter domain =
        new HOIReporter(role, id, firstName, lastName, nameSuffix, legacyDescriptor);
    domain.setRole(Role.NON_MANDATED_REPORTER);
    assertThat(domain.getRole().getDescription(), is("Non-mandated Reporter"));
  }

  @Test
  public void testFromStringDescrptionForNonMandatedReporter() throws Exception {
    Role role = Role.fromString("Non-mandated Reporter");
    assertThat(role, is(equalTo(Role.NON_MANDATED_REPORTER)));
  }

  @Test
  public void testFromStringDescrptionForAnonymousReporter() throws Exception {
    Role role = Role.fromString("Anonymous Reporter");
    assertThat(role, is(equalTo(Role.ANONYMOUS_REPORTER)));
  }

  @Test
  public void testFromStringDescrptionForSelfReporter() throws Exception {
    Role role = Role.fromString("Self Reported");
    assertThat(role, is(equalTo(Role.SELF_REPORTER)));
  }

  @Test
  public void testFromStringDescrptionNull() throws Exception {
    Role role = Role.fromString(null);
    assertThat(role, is(nullValue()));
  }

  @Test
  public void testFromStringDescrptionInValid() throws Exception {
    Role role = Role.fromString("In valid value");
    assertThat(role, is(nullValue()));
  }
}
