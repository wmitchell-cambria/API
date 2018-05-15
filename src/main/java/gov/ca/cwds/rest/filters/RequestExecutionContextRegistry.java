package gov.ca.cwds.rest.filters;

/**
 * Request execution context registry based on ThreadLocal.
 * 
 * @author CWDS API Team
 */
public class RequestExecutionContextRegistry {

  private static final ThreadLocal<RequestExecutionContext> pegged = new ThreadLocal<>();

  // Create callback registry for stop and start.

  /**
   * Register RequestExecutionContext on the current thread with ThreadLocal.
   * 
   * @param requestExecutionContext request context for this thread
   */
  static void register(RequestExecutionContext requestExecutionContext) {
    pegged.set(requestExecutionContext);
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
