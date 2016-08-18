package gov.ca.cwds.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.views.SwaggerView;
import io.swagger.annotations.Api;

@Api(value = "swagger", hidden = true)
@Path(value = "swagger")
@Produces(MediaType.TEXT_HTML)
public class SwaggerResource {
	private SwaggerConfiguration swaggerConfiguration;

	public SwaggerResource(SwaggerConfiguration swaggerConfiguration) {
		super();
		this.swaggerConfiguration = swaggerConfiguration;
	}

	@GET
	public SwaggerView get() {
		return new SwaggerView(swaggerConfiguration);
	}

}
