package gov.ca.cwds.rest.filters;

import java.util.Date;

/**
 * Request execution context. Binds the current HTTP/REST request to the current thread and exposes
 * common information to all services on that thread without polluting method parameters.
 * 
 * @author CWDS API Team
 */
public interface RequestExecutionContext {

  /**
   * Known request execution parameters.
   */
  public enum Parameter {
    USER_IDENTITY, REQUEST_START_TIME, SEQUENCE_EXTERNAL_TABLE
  }

  /**
   * Store an arbitrary request execution parameter, per enum {@link Parameter}.
   * 
   * @param parameter Parameter
   * @param value Parameter value
   */
  void put(Parameter parameter, Object value);

  /**
   * Retrieve an arbitrary request execution parameter, per enum {@link Parameter}.
   * 
   * @param parameter Parameter
   * @return The parameter value
   */
  Object get(Parameter parameter);

  /**
   * Get user id, if stored.
   * 
   * @return The user id
   */
  String getUserId();

  /**
   * Get staff id, if stored.
   * 
   * @return The staff id
   */
  String getStaffId();

  /**
   * Get request start time, if stored
   * 
   * @return The request start time
   */
  Date getRequestStartTime();

  /**
   * Get instance of RequestExecutionContext from registry
   * 
   * @return RequestExecutionContext instance
   */
  static RequestExecutionContext instance() {
    return RequestExecutionContextRegistry.get();
  }

}
