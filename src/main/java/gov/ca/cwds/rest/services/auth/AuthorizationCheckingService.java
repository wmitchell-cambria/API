package gov.ca.cwds.rest.services.auth;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.auth.AuthorizationCheckingRequest;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationCheckingResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.security.annotations.Authorize;

/**
 * Service to check that logged in staff person is authorized to access entities
 * 
 * @author CWDS API Team
 */
public class AuthorizationCheckingService extends
    SimpleResourceService<String, AuthorizationCheckingRequest, AuthorizationCheckingResponse> {

  /**
   * Constructor
   */
  public AuthorizationCheckingService() {
    // No-op
  }

  /**
   * Check if logged in user is authorized to access requested entity
   */
  @Override
  protected AuthorizationCheckingResponse handleRequest(AuthorizationCheckingRequest request) {
    if (request != null && StringUtils.isNotBlank(request.getId())) {
      authorizeClientAccess(request.getId());
    }
    return new AuthorizationCheckingResponse();
  }

  /**
   * Check if logged in user is authorized to access client identified by given clientId
   * 
   * @param clientId The client id
   */
  public void authorizeClientAccess(@Authorize("client:read:clientId") String clientId) {
    // Authorizer annotation does the work
  }

  /**
   * This method in not implemented
   */
  @Override
  protected AuthorizationCheckingResponse handleFind(String arg0) {
    throw new UnsupportedOperationException();
  }
}
