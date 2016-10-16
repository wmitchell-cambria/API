package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_APPLICATION;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.swagger.annotations.Api;


/**
 * A resource providing a basic information about the CWDS API
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_APPLICATION, hidden = true)
@Path(RESOURCE_APPLICATION)
public class ApplicationResource {

  private String applicationName;

  /**
   * Constructor
   * 
   * @param applicationName The name of the application
   */
  public ApplicationResource(String applicationName) {
    this.applicationName = applicationName;

  }

  /**
   * Get the name of the application
   */
  @GET
  public String getApplicationName() {
    return applicationName;
  }
}
