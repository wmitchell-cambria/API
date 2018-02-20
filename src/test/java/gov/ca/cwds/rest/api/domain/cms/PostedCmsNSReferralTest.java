package gov.ca.cwds.rest.api.domain.cms;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.PersonEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.rest.api.domain.PostedPerson;

public class PostedCmsNSReferralTest {
  private String id = "1234567ABC";
  private gov.ca.cwds.data.persistence.cms.Referral referral;
  private PostedReferral postedReferral;
  private gov.ca.cwds.data.persistence.ns.Person person;
  private PostedPerson postedPerson;
  
  @Before
  public void setup() {
	referral = new ReferralEntityBuilder().setId(id).build();
	postedReferral = new PostedReferral(referral);
	person = new PersonEntityBuilder().build();
	postedPerson = new PostedPerson(person);
  }
  
  @Test
  public void shouldConstructObject() throws Exception {
	PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson);
	assertThat(postedCmsNSReferral.getReferral(), is(equalTo(postedReferral)));
	assertThat(postedCmsNSReferral.getPerson(), is(equalTo(postedPerson)));	
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson);	  
    assertTrue(postedCmsNSReferral.equals(postedCmsNSReferral));
  }
  
}
