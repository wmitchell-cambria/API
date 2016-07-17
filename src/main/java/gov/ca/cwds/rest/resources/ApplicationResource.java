package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_APPLICATION;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Api(RESOURCE_APPLICATION)
@Path(RESOURCE_APPLICATION)
@Produces(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
/**
 * A resource providing a basic information about the CWDS API
 * 
 * @author CWDS API Team
 */
public interface ApplicationResource {
	
	@ApiOperation("Get the application name")
	@GET
	/**
	 * Get the name of the API
	 * 
	 * @return The name of the API
	 */
	public String getApplicationName();
}