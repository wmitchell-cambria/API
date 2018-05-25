package gov.ca.cwds.data.persistence.xa;

import java.io.Serializable;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;
import gov.ca.cwds.rest.filters.RequestExecutionContextRegistry;

public class ReentrantXAUnitOfWorkAspectFactory
    implements RequestExecutionContextCallback, XAUnitOfWorkAspectFactory {

  private static final long serialVersionUID = 1L;

  private final ImmutableMap<String, SessionFactory> sessionFactories;

  private final ThreadLocal<XAUnitOfWorkAspect> local = new ThreadLocal<>();

  public ReentrantXAUnitOfWorkAspectFactory(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
    RequestExecutionContextRegistry.registerCallback(this);
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

  protected XAUnitOfWorkAspect make(ImmutableMap<String, SessionFactory> someSessionFactories) {
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
