package gov.ca.cwds.logging;

/**
 * Interface for audit logger
 * 
 * @author CWDS API Team
 */
public interface AuditLogger {

  /**
   * Writes given message to the audit log with the given event type
   * 
   * @param eventType The eventType
   * @param data The data to audit
   *
   */
  public void audit(String eventType, String data);

  /**
   * Setup the Mapped Diagnostic Context
   * 
   * @param userid The userid
   * @param sessionId The session id
   * @param requestId The request id
   * 
   * @return A unique id for this request
   */
  public String buildMDC(String userid, String sessionId, String requestId);

  public void teardownMDC();

}
