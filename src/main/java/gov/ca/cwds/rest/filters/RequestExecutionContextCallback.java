package gov.ca.cwds.rest.filters;

import java.io.Serializable;

import gov.ca.cwds.data.std.ApiMarker;

public interface RequestExecutionContextCallback extends ApiMarker {

  Serializable key();

  void startRequest(RequestExecutionContext ctx);

  void endRequest(RequestExecutionContext ctx);

}
