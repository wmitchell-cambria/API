package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * Common services for HOI service implementations.
 * 
 * @author CWDS API Team
 */
public interface HOIBaseService extends SensitiveClientOverride {

  AuthorizationService getAuthorizationService();

  Logger getLogger();

  /**
   * SNAP-49: HOI not shown for client.
   * 
   * <p>
   * Sometimes Cases or Referrals link to clients that the current user is not authorized to view
   * due to sealed/sensitivity restriction, county access privileges, or a short-coming with
   * authorization rules. The client authorizer throws an UnauthorizedException, then skip that
   * client and move on. Don't bomb all History of Involvement because the user is not authorized to
   * view a client's half-sister's foster sibling.
   * </p>
   * 
   * @param clientIds client keys to authorize
   * @return list of client id's that the user is authorized to view
   */
  default List<String> authorizeClients(Collection<String> clientIds) {
    final List<String> ret = new ArrayList<>(clientIds.size());
    for (String clientId : clientIds) {
      try {
        authorizeClient(clientId);
        ret.add(clientId);
      } catch (Exception e) {
        final String msg = String.format("NOT AUTHORIZED TO VIEW CLIENT ID \"%s\"!", clientId);
        RequestExecutionContext.instance().getMessageBuilder().addMessageAndLog(msg, e, getLogger(),
            ErrorType.CLIENT_AUTHORIZATION_WARNING);
      }
    }

    return ret;
  }

  /**
   * Authorizes the user to view the given client else throws UnauthorizedException.
   * 
   * @param clientId client identifier
   */
  default void authorizeClient(String clientId) {
    if (!developmentOnlyClientSensitivityOverride()) {
      getAuthorizationService().ensureClientAccessAuthorized(clientId);
    }
  }


}
