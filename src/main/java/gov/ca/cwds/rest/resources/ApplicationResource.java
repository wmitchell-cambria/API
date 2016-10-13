package gov.ca.cwds.rest.resources;

import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;


/**
 * A resource providing a basic information about the CWDS API
 * 
 * @author CWDS API Team
 */
public interface ApplicationResource extends Resource {

  @ApiOperation("Get the application name")
  @GET
  /**
   * Get the name of the API
   * 
   * @return The name of the API
   */
  public String getApplicationName();
}
