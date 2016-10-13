package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.views.SwaggerView;

public class SwaggerResourceTest {
  private static SwaggerConfiguration swaggerConfiguration = mock(SwaggerConfiguration.class);
  private static UriInfo uriInfo = mock(UriInfo.class);
  private static UriBuilder uriBuilder = mock(UriBuilder.class);

  @Before
  public void setup() throws Exception {
    when(swaggerConfiguration.getTemplateName()).thenReturn("SwaggerResourceTest.template");
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
    when(uriBuilder.build()).thenReturn(new URI("some_uri"));
  }

  @Test
  public void getReturnsSwaggerViewWithCorrectUri() throws Exception {
    SwaggerResource swaggerResource = new SwaggerResource(swaggerConfiguration);
    SwaggerView view = swaggerResource.get(uriInfo);
    assertThat(view.getJsonUrl(), is("some_uri"));
  }

  @Test
  public void getReturnsSwaggerViewWithCorrectTemplateName() throws Exception {
    SwaggerResource swaggerResource = new SwaggerResource(swaggerConfiguration);
    SwaggerView view = swaggerResource.get(uriInfo);
    assertThat(view.getTemplateName(), is("/gov/ca/cwds/rest/views/SwaggerResourceTest.template"));
  }
}
