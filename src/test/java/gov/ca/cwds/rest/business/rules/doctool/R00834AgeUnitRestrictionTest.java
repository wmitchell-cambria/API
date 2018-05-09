package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.ClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Client;
import gov.ca.cwds.rest.business.rules.R00834AgeUnitRestriction;

/**
 * @author CWDS API Team
 *
 */
public class R00834AgeUnitRestrictionTest {

  /**
   * 
   */
  @Test
  public void TestClientDObisNull() {
    Client client = new ClientResourceBuilder().setBirthDate(null).setEstimatedDobCode("Y").build();
    R00834AgeUnitRestriction r00834AgeUnitRestriction = new R00834AgeUnitRestriction(client);
    r00834AgeUnitRestriction.execute();
    assertThat(r00834AgeUnitRestriction.getAge(), is(nullValue()));
    assertThat(r00834AgeUnitRestriction.getAgeUnits(), is(nullValue()));
  }

  /**
   * 
   */
  @Test
  public void TestWhenAgeAndAgeUnitSetYears() {
    Client client = new ClientResourceBuilder().setBirthDate("2005-05-07").setEstimatedDobCode("N")
        .setCreationDate("2018-05-07").build();
    R00834AgeUnitRestriction r00834AgeUnitRestriction = new R00834AgeUnitRestriction(client);
    r00834AgeUnitRestriction.execute();
    assertThat(r00834AgeUnitRestriction.getAge(), is(equalTo("13")));
    assertThat(r00834AgeUnitRestriction.getAgeUnits(), is(equalTo("Y")));
  }

  /**
   * 
   */
  @Test
  public void TestWhenAgeAndAgeUnitSetMonths() {
    Client client = new ClientResourceBuilder().setBirthDate("2017-12-07").setEstimatedDobCode("N")
        .setCreationDate("2018-05-07").build();
    R00834AgeUnitRestriction r00834AgeUnitRestriction = new R00834AgeUnitRestriction(client);
    r00834AgeUnitRestriction.execute();
    assertThat(r00834AgeUnitRestriction.getAge(), is(equalTo("5")));
    assertThat(r00834AgeUnitRestriction.getAgeUnits(), is(equalTo("M")));
  }

  /**
   * 
   */
  @Test
  public void TestWhenAgeAndAgeUnitSetWeeks() {
    Client client = new ClientResourceBuilder().setBirthDate("2018-04-10").setEstimatedDobCode("N")
        .setCreationDate("2018-05-07").build();
    R00834AgeUnitRestriction r00834AgeUnitRestriction = new R00834AgeUnitRestriction(client);
    r00834AgeUnitRestriction.execute();
    assertThat(r00834AgeUnitRestriction.getAge(), is(equalTo("3")));
    assertThat(r00834AgeUnitRestriction.getAgeUnits(), is(equalTo("W")));
  }

  /**
   * 
   */
  @Test
  public void TestWhenAgeAndAgeUnitSetDays() {
    Client client = new ClientResourceBuilder().setBirthDate("2018-05-01").setEstimatedDobCode("N")
        .setCreationDate("2018-05-07").build();
    R00834AgeUnitRestriction r00834AgeUnitRestriction = new R00834AgeUnitRestriction(client);
    r00834AgeUnitRestriction.execute();
    assertThat(r00834AgeUnitRestriction.getAge(), is(equalTo("6")));
    assertThat(r00834AgeUnitRestriction.getAgeUnits(), is(equalTo("D")));
  }

}
