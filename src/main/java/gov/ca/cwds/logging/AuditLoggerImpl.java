package gov.ca.cwds.logging;

import java.util.Date;
import java.util.UUID;

import org.slf4j.MDC;
import org.slf4j.ext.EventData;
import org.slf4j.ext.EventLogger;

public class AuditLoggerImpl implements AuditLogger {

  private static final String USER_ID = "userid";
  private static final String SESSION_ID = "sessionid";
  private static final String REQUEST_ID = "requestid";
  private static final String TAG = "tag";
  private static final String DATA = "data";
  private static final String UNIQUE_ID = "uniqueId";

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#audit(java.lang.String, java.lang.String)
   */
  @Override
  public void audit(String eventType, String data) {
    MDC.put(TAG, "AUDIT");

    EventData eventData = new EventData();
    eventData.setEventDateTime(new Date());
    eventData.setEventType(eventType);
    eventData.setEventId(MDC.get(UNIQUE_ID));
    eventData.put(DATA, data);
    EventLogger.logEvent(eventData);

    MDC.remove(TAG);
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#buildMDC(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  @Override
  public String buildMDC(String userid, String sessionId, String requestId) {
    MDC.put(USER_ID, userid);
    MDC.put(SESSION_ID, sessionId);
    MDC.put(REQUEST_ID, requestId);
    String uniqueId = UUID.randomUUID().toString();
    MDC.put(UNIQUE_ID, uniqueId);

    return uniqueId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#teardownMDC()
   */
  @Override
  public void teardownMDC() {
    MDC.clear();
  }



}

