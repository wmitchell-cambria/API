package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.ReferralResource;
import gov.ca.cwds.rest.validation.IfThenNot;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class R00807ReferralResponseTypeCantBeEvaluateOutTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REFERRAL + "/";

  private static final ReferralResource mockedReferralResource = mock(ReferralResource.class);

  /**
   * 
   */
  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources =
      ResourceTestRule.builder().addResource(mockedReferralResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  /**
   * 
   */
  @Before
  public void setup() {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    Referral validReferral = new ReferralResourceBuilder().build();
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Referral.class));

    when(mockedReferralResource.create(eq(validReferral)))
        .thenReturn(Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }

  /**
   * Test when application petition indicator is true and referral response type is 1519, using the
   * ifThenNotValidator
   * 
   * @see IfThenNot
   * 
   * @throws Exception - Exception
   */
  @Test
  public void failsWhenPetitionIndicatorTrueAndResponeType1519() throws Exception {
    Referral toCreate = new ReferralResourceBuilder().setApplicationForPetitionIndicator(true)
        .setReferralResponseType((short) 1519).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(
        response.readEntity(String.class)
            .indexOf("is not valid since applicationForPetitionIndicator is set to true"),
        is(greaterThanOrEqualTo(0)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void successWhenPetitionIndicatorFalseAndResponeType1519() throws Exception {
    Referral toCreate = new ReferralResourceBuilder().setApplicationForPetitionIndicator(false)
        .setReferralResponseType((short) 1519).build();
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    String message = response.readEntity(String.class);
    System.out.print(message);
    assertThat(response.getStatus(), is(equalTo(204)));
  }

}
