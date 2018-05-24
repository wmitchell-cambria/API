package gov.ca.cwds.rest.filters;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Request execution context registry based on ThreadLocal.
 * 
 * @author CWDS API Team
 */
public class RequestExecutionContextRegistry implements ApiMarker {

  private static final class CallbackRegistry implements ApiMarker {

    private static final long serialVersionUID = 1L;

    private final Map<Serializable, RequestExecutionContextCallback> callbacks =
        new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();

    public void register(RequestExecutionContextCallback callback) {
      try {
        lock.lockInterruptibly();
        callbacks.put(callback.key(), callback);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } finally {
        lock.unlock();
      }
    }

    public void startRequest(RequestExecutionContext ctx) {
      callbacks.values().stream().sequential().forEach(c -> c.startRequest(ctx));
    }

    public void endRequest(RequestExecutionContext ctx) {
      callbacks.values().stream().sequential().forEach(c -> c.endRequest(ctx));
    }

  }

  private static final long serialVersionUID = 1L;

  private static final ThreadLocal<RequestExecutionContext> pegged = new ThreadLocal<>();

  private static final CallbackRegistry callbackRegistry = new CallbackRegistry();

  // Callback registry for stop and start.

  public static synchronized void registerCallback(RequestExecutionContextCallback callback) {
    callbackRegistry.register(callback);
  }

  /**
   * Register RequestExecutionContext on the current thread with ThreadLocal.
   * 
   * @param ctx request context for this thread
   */
  static void register(RequestExecutionContext ctx) {
    pegged.set(ctx);
    callbackRegistry.endRequest(ctx);
  }

  /**
   * Remove RequestExecutionContext from ThreadLocal
   */
  static void remove() {
    final RequestExecutionContext ctx = pegged.get();
    pegged.remove();
    callbackRegistry.startRequest(ctx);
  }

  /**
   * Get RequestExecutionContext from ThreadLocal
   */
  static RequestExecutionContext get() {
    return pegged.get();
  }

}
