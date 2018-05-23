package gov.ca.cwds.rest.filters;

import gov.ca.cwds.data.std.ApiMarker;

public interface RequestExecutionContextCallback extends ApiMarker {

  String callbackKey();

  void requestStarted(RequestExecutionContext ctx);

  void requestEnded(RequestExecutionContext ctx);

}
