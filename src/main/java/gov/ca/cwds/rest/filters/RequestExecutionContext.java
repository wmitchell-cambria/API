package gov.ca.cwds.rest.filters;

import java.util.Date;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.rest.messages.MessageBuilder;

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

    /**
     * Type: String. Current user's user id or staff id.
     */
    USER_IDENTITY,

    /**
     * Type: Date. The request's start time.
     */
    REQUEST_START_TIME,

    SEQUENCE_EXTERNAL_TABLE,

    /**
     * Type: {@link MessageBuilder}. Default error/warning message builder.
     */
    MESSAGE_BUILDER,

    /**
     * Type: Boolean. Is the current REST endpoint read-only?
     */
    RESOURCE_READ_ONLY
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
   * Get logged in user's identity
   * 
   * @return Logged in user's identity
   */
  PerryUserIdentity getUserIdentity();

  /**
   * Get the message builder for warnings and errors for this request.
   * 
   * @return message builder for warnings and errors
   */
  MessageBuilder getMessageBuilder();

  /**
   * Is the current REST endpoint read-only?
   * 
   * @return true if current operation is read-only
   */
  boolean isResourceReadOnly();

  /**
   * Get instance of RequestExecutionContext from registry.
   * 
   * @return RequestExecutionContext instance
   */
  static RequestExecutionContext instance() {
    return RequestExecutionContextRegistry.get();
  }

}
