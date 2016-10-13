package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CrossReportTest {

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(CrossReport.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReport.PrimaryKey.class).suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }

}
