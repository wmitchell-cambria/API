package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.APPLICATION;
import static gov.ca.cwds.rest.core.MediaType.APPLICATION_JSON_V1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Api(APPLICATION)
@Path(APPLICATION)
@Produces(APPLICATION_JSON_V1)
public class ApplicationResource {
	
	private String applicationName;
	
	public ApplicationResource(String applicationName) {
		this.applicationName = applicationName;
		
	}
	
	@ApiOperation("Get the application name")
	@GET
	public String getApplicationName() {
		return applicationName;
	}
}
