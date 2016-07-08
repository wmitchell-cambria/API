package gov.ca.cwds.rest.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Api("/application")
@Path("/application")
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
