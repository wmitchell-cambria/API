package gov.ca.cwds.data.persistence.xa;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;
import gov.ca.cwds.rest.filters.RequestExecutionContextRegistry;

/**
 * Reentrant handler allows for nested {@link XAUnitOfWork} annotations.
 * 
 * <p>
 * Returns a new AOP {@link XAUnitOfWorkAspect} for the first {@link XAUnitOfWork} encountered, or
 * if XA has already started, returns the request's XA aspect and joins the transaction.
 * </p>
 * 
 * @author CWDS API Team
 */
public class ReentrantXAUnitOfWorkAspectFactory
    implements RequestExecutionContextCallback, XAUnitOfWorkAspectFactory {

  private static final long serialVersionUID = 1L;

  private final Map<String, SessionFactory> sessionFactories;

  private final ThreadLocal<XAUnitOfWorkAspect> local = new ThreadLocal<>();

  public ReentrantXAUnitOfWorkAspectFactory(Map<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
    RequestExecutionContextRegistry.registerCallback(this); // Notify this instance when request
                                                            // start or end
  }

  @Override
  public Serializable key() {
    return ReentrantXAUnitOfWorkAspectFactory.class.getName();
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    local.set(null); // clear the current thread
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    local.set(null); // clear the current thread
  }

  protected XAUnitOfWorkAspect make(Map<String, SessionFactory> someSessionFactories) {
    XAUnitOfWorkAspect ret = local.get();
    if (ret == null) {
      ret = new XAUnitOfWorkAspect(someSessionFactories);
      local.set(ret);
    }
    return ret;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAspectFactory#make()
   */
  @Override
  public XAUnitOfWorkAspect make() {
    return make(sessionFactories);
  }

}
