package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.fixture.hoi.HOIReferralResourceBuilder;

/**
 * @author CWDS API Team
 */
public class HOIReferralResponseTest {

  @Test
  public void shouldAddHoiReferral() throws Exception {
    List<HOIReferral> referrals = new ArrayList<>();
    List<HOIReferral> hoiReferrals = new ArrayList<>();
    HOIReferral hoiReferral = new HOIReferralResourceBuilder().createHOIReferral();

    referrals.add(hoiReferral);
    hoiReferrals.addAll(referrals);

    HOIReferralResponse target = new HOIReferralResponse();
    target.setHoiReferrals(referrals);

    HOIReferral newHOIReferral = new HOIReferralResourceBuilder().createHOIReferral();

    target.addHoiReferral(newHOIReferral);
    hoiReferrals.add(newHOIReferral);
    List<HOIReferral> actual = target.getHoiReferrals();

    assertThat(actual.size(), is(equalTo(hoiReferrals.size())));
  }

  @Test
  public void shouldNotAddNullHoiReferral() throws Exception {
    List<HOIReferral> referrals = new ArrayList<>();
    List<HOIReferral> hoiReferrals = new ArrayList<>();
    HOIReferral hoiReferral = new HOIReferralResourceBuilder().createHOIReferral();
    referrals.add(hoiReferral);

    HOIReferralResponse target = new HOIReferralResponse();
    target.setHoiReferrals(referrals);

    hoiReferrals.addAll(referrals);

    HOIReferral nullHOIReferral = null;

    target.addHoiReferral(nullHOIReferral);
    List<HOIReferral> actual = target.getHoiReferrals();

    assertThat(actual.size(), is(equalTo(hoiReferrals.size())));
  }
}
