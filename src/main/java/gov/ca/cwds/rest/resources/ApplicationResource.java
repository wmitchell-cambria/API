package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.APPLICATION;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Api(APPLICATION)
@Path(APPLICATION)
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
