package gov.ca.cwds.rest.filters;

import java.io.Serializable;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Classes implementing this interface will be notified of key events in the
 * {@link RequestExecutionContext}, such as a request's start and end.
 * 
 * @author CWDS API Team
 * @see RequestExecutionContext
 */
public interface RequestExecutionContextCallback extends ApiMarker {

  Serializable key();

  void startRequest(RequestExecutionContext ctx);

  void endRequest(RequestExecutionContext ctx);

}
