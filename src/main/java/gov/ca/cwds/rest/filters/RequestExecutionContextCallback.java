package gov.ca.cwds.rest.filters;

import gov.ca.cwds.data.std.ApiMarker;

public interface RequestExecutionContextCallback extends ApiMarker {

  void startRequest(RequestExecutionContext ctx);

  void endRequest(RequestExecutionContext ctx);

}
