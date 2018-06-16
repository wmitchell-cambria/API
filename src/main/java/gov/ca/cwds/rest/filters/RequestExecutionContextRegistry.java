package gov.ca.cwds.rest.filters;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * {@link RequestExecutionContext} registry based on ThreadLocal.
 * 
 * <p>
 * Also registers callbacks for request events, such as request start and end.
 * </p>
 * 
 * @author CWDS API Team
 */
public class RequestExecutionContextRegistry implements ApiMarker {

  /**
   * Register callbacks for request events, such as request start and end.
   * 
   * <p>
   * Callback methods are invoked on the same thread, since some callback implementations may depend
   * on ThreadLocal themselves.
   * </p>
   * 
   * @author CWDS API Team
   */
  static final class CallbackRegistry implements ApiMarker {

    private static final long serialVersionUID = 1L;

    private final Map<Serializable, RequestExecutionContextCallback> callbacks =
        new ConcurrentHashMap<>();

    /**
     * Register a class instance for callbacks.
     * 
     * @param callback instance to call
     */
    public void register(RequestExecutionContextCallback callback) {
      callbacks.putIfAbsent(callback.key(), callback);
    }

    public void startRequest(RequestExecutionContext ctx) {
      callbacks.values().stream().sequential().forEach(c -> c.startRequest(ctx));
    }

    public void endRequest(RequestExecutionContext ctx) {
      callbacks.values().stream().sequential().forEach(c -> c.endRequest(ctx));
    }

  }

  private static final long serialVersionUID = 1L;

  private static final ThreadLocal<RequestExecutionContext> bound = new ThreadLocal<>();

  private static final CallbackRegistry callbackRegistry = new CallbackRegistry();

  public static synchronized void registerCallback(RequestExecutionContextCallback callback) {
    callbackRegistry.register(callback);
  }

  /**
   * Register RequestExecutionContext on the current thread with ThreadLocal.
   * 
   * @param ctx request context for this thread
   */
  static void register(RequestExecutionContext ctx) {
    bound.set(ctx);
    callbackRegistry.startRequest(ctx);
  }

  /**
   * Remove RequestExecutionContext from ThreadLocal
   */
  static void remove() {
    final RequestExecutionContext ctx = bound.get();
    bound.remove();
    callbackRegistry.endRequest(ctx);
  }

  /**
   * Get RequestExecutionContext from ThreadLocal
   */
  static RequestExecutionContext get() {
    return bound.get();
  }

}
