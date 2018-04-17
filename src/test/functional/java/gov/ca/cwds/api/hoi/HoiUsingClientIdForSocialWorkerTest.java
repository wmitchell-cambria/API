package gov.ca.cwds.api.hoi;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.api.FunctionalTest;

public class HoiUsingClientIdForSocialWorkerTest extends FunctionalTest {

  String clientsHoi;

  /**
   * 
   */
  @Before
  public void setup() {
    clientsHoi = getResourceUrlFor("clients/history_of_involvements");
  }

  @Test
  public void return201ForNoCondition() {

  }

}
