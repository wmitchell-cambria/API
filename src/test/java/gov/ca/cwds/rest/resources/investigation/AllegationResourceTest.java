package gov.ca.cwds.rest.resources.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

@SuppressWarnings("javadoc")
public class AllegationResourceTest {

  private static final String ROOT_RESOURCE = "/investigations/1234567ABC/allegations";

  private AllegationResource resource;
  private Allegation allegation;
  private String referralId = "1234567ABC";
  private String allegationId = "2345678ABC";

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("unchecked")
  private final static TypedResourceDelegate<String, Allegation> service =
      mock(TypedResourceDelegate.class);

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new AllegationResource(service)).build();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    resource = new AllegationResource(service);
  }

  @Test
  public void callAllegationServiceOnCreate() throws Exception {
    allegation = new AllegationEntityBuilder().build();
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(allegation, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(204));
  }

  @Test
  public void validationFailsWithInvalidValuesOnCreate() throws Exception {
    allegation = new AllegationEntityBuilder().setAllegationType(null).build();
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(allegation, MediaType.APPLICATION_JSON)).getStatus();
    assertThat(status, is(422));
  }

  @Test
  public void callAllegationServiceOnUpdate() throws Exception {
    allegation = new AllegationEntityBuilder().build();
    resource.update(referralId, allegationId, allegation);
    verify(service).update(allegationId, allegation);
  }

}
