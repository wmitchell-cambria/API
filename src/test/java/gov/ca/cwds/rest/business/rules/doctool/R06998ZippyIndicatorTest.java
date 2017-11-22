package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;
import gov.ca.cwds.rest.business.rules.R06998ZippyIndicator;

/**
 * Rule - R06998
 * 
 * @author CWDS API Team
 *
 */
public class R06998ZippyIndicatorTest {
  @Test
  public void testTrueIndicator() {

    assertThat(R06998ZippyIndicator.YES.getCode(), is(notNullValue()));


  }

  @Test
  public void testFalseIndicator() {

    assertThat(R06998ZippyIndicator.NO.getCode(), is(notNullValue()));


  }


}
