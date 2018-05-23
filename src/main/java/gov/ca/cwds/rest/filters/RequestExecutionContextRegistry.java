package gov.ca.cwds.rest.filters;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Request execution context registry based on ThreadLocal.
 * 
 * @author CWDS API Team
 */
public class RequestExecutionContextRegistry implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private static final ThreadLocal<RequestExecutionContext> pegged = new ThreadLocal<>();

  private static final Map<String, RequestExecutionContextCallback> registeredCallbacks =
      new LinkedHashMap<>();

  // Callback registry for stop and start.

  public static synchronized void registerCallback(RequestExecutionContextCallback callback) {
    registeredCallbacks.put(callback.callbackKey(), callback);
  }

  /**
   * Register RequestExecutionContext on the current thread with ThreadLocal.
   * 
   * @param ctx request context for this thread
   */
  static void register(RequestExecutionContext ctx) {
    pegged.set(ctx);
  }

  /**
   * Remove RequestExecutionContext from ThreadLocal
   */
  static void remove() {
    pegged.remove();
  }

  /**
   * Get RequestExecutionContext from ThreadLocal
   */
  static RequestExecutionContext get() {
    return pegged.get();
  }

}
