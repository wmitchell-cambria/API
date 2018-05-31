package gov.ca.cwds.rest.services.submit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentAgencyTransformerTest {


  /**
   * 
   */
  @Test
  public void testConveterHappenSuccesfully() {
    GovernmentAgencyIntake governmentAgencyIntake =
        new GovernmentAgencyIntake("12", "Ad4ATcY00E", "LAW_ENFORCEMENT");
    GovernmentAgency governmentAgency =
        new GovernmentAgencyTransformer().transform(governmentAgencyIntake);
    assertThat(governmentAgency.getId(), is(equalTo("Ad4ATcY00E")));
    assertThat(governmentAgency.getType(), is(equalTo("LAW_ENFORCEMENT")));
  }

}
