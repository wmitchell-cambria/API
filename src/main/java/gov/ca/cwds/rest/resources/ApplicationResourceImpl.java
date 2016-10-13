package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_APPLICATION;

import javax.ws.rs.Path;

import io.swagger.annotations.Api;


/**
 * Implementation of {@link ApplicationResource}.
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_APPLICATION, hidden = true)
@Path(RESOURCE_APPLICATION)
public class ApplicationResourceImpl implements ApplicationResource {

  private String applicationName;

  /**
   * Constructor
   * 
   * @param applicationName The name of the application
   */
  public ApplicationResourceImpl(String applicationName) {
    this.applicationName = applicationName;

  }

  /**
   * Get the name of the application
   */
  public String getApplicationName() {
    return applicationName;
  }
}
