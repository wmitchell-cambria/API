package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import gov.ca.cwds.fixture.GovernmentAgencyResourceBuilder;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;

/**
 * @author CWDS API Team
 *
 */
public class GovernmentAgencyTransformerTest {

  private GovernmentAgencyIntake governmentAgencyIntake;

  /**
   * 
   */
  @Test
  public void testConveterHappenSuccesfully() {
    governmentAgencyIntake = new GovernmentAgencyIntake("12", "Ad4ATcY00E", "LAW_ENFORCEMENT");
    GovernmentAgency governmentAgency = new GovernmentAgencyResourceBuilder().build();
    Set<GovernmentAgencyIntake> nsGovernmetAgencyIntake =
        Stream.of(governmentAgencyIntake).collect(Collectors.toSet());
    Set<GovernmentAgency> expected = Stream.of(governmentAgency).collect(Collectors.toSet());
    Set<GovernmentAgency> actual =
        new GovernmentAgencyTransformer().transform(nsGovernmetAgencyIntake);
    assertEquals(actual, expected);
  }

}
