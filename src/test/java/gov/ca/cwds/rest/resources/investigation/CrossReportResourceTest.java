package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.fixture.investigation.CrossReportEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;

@SuppressWarnings("javadoc")
public class CrossReportResourceTest {

  private static final String ROOT_RESOURCE = "/investigations/1234567ABC/cross_report";
  private CrossReportResource resource;
  private CrossReport crossReport;
  private final static TypedResourceDelegate<String, CrossReport> service =
      mock(TypedResourceDelegate.class);
  private String referralId = "2345678ABC";
  private String cmsId = "1234567ABC";

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @ClassRule
  public final static ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new CrossReportResource(service)).build();

  /*
   * Load system code cache
   */
  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();


  @Before
  public void setup() {
    resource = new CrossReportResource(service);
  }

  @Test
  public void callCrossReportServiceOnCreate() throws Exception {
    crossReport = new CrossReportEntityBuilder().build();
    System.out.println(ROOT_RESOURCE);
    int status =
        inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(crossReport, MediaType.APPLICATION_JSON)).getStatus();
    System.out.println(status);
    verify(service).create(eq(crossReport));
  }

  @Test
  public void callCrossReportServiceOnUpdate() throws Exception {
    crossReport = new CrossReportEntityBuilder().build();
    resource.update(referralId, cmsId, crossReport);
    verify(service).update(cmsId, crossReport);
  }
}
