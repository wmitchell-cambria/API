package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentAgencyTest {

  private String id = "12vhdlh00i";
  private String type = "DEPARTMENT_OF_JUSTICE";

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    GovernmentAgency domain = new GovernmentAgency(id, type);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getType(), is(equalTo(type)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(GovernmentAgency.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
