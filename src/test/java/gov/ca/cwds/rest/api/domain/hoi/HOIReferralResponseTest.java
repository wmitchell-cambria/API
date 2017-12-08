package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class HOIReferralResponseTest {


  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(HOIReferralResponse.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    HOIReferralResponse target = new HOIReferralResponse();
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getHoiReferrals_Args__() throws Exception {
    HOIReferralResponse target = new HOIReferralResponse();
    List<HOIReferral> actual = target.getHoiReferrals();
    List<HOIReferral> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void setHoiReferrals_Args__List() throws Exception {
    List<HOIReferral> hoiReferrals = new ArrayList<HOIReferral>();
    HOIReferralResponse target = new HOIReferralResponse();
    target.setHoiReferrals(hoiReferrals);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void hashCode_Args__() throws Exception {
    HOIReferralResponse target = new HOIReferralResponse();
    int actual = target.hashCode();
    int expected = 630;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void equals_Args__Object() throws Exception {
    HOIReferralResponse target = new HOIReferralResponse();
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
