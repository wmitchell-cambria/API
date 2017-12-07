package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class InvolvementHistoryTest {

  @Test
  public void type() throws Exception {
    assertThat(InvolvementHistory.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    InvolvementHistory target = new InvolvementHistory();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    InvolvementHistory target = new InvolvementHistory();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCases_Args__() throws Exception {
    InvolvementHistory target = new InvolvementHistory();
    Set<HOICase> actual = target.getCases();
    Set<HOICase> expected = Collections.emptySet();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferrals_Args__() throws Exception {
    InvolvementHistory target = new InvolvementHistory();
    Set<HOIReferral> actual = target.getReferrals();
    Set<HOIReferral> expected = Collections.emptySet();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getScreenings_Args__() throws Exception {
    InvolvementHistory target = new InvolvementHistory();
    Set<HOIScreening> actual = target.getScreenings();
    Set<HOIScreening> expected = Collections.emptySet();
    assertThat(actual, is(equalTo(expected)));
  }

}
