package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class CombinedHOITest {

  @Test
  public void type() throws Exception {
    assertThat(CombinedHOI.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CombinedHOI target = new CombinedHOI();
    assertThat(target, notNullValue());
  }

  @Test
  public void getCases_Args__() throws Exception {
    CombinedHOI target = new CombinedHOI();
    Set<CaseHOI> actual = target.getCases();
    Set<CaseHOI> expected = Collections.emptySet();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferrals_Args__() throws Exception {
    CombinedHOI target = new CombinedHOI();
    Set<ReferralHOI> actual = target.getReferrals();
    Set<ReferralHOI> expected = Collections.emptySet();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreenings_Args__() throws Exception {
    CombinedHOI target = new CombinedHOI();
    Set<ScreeningHOI> actual = target.getScreenings();
    Set<ScreeningHOI> expected = Collections.emptySet();
    assertThat(actual, is(equalTo(expected)));
  }

}
