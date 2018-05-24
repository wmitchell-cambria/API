package gov.ca.cwds.data.persistence.xa;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableMap;

import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;

public class ReentrantXAUnitOfWorkAspectFactory implements RequestExecutionContextCallback {

  private static final long serialVersionUID = 1L;

  private final ImmutableMap<String, SessionFactory> sessionFactories;

  private final ThreadLocal<XAUnitOfWorkAspect> local = new ThreadLocal<>();

  public ReentrantXAUnitOfWorkAspectFactory(ImmutableMap<String, SessionFactory> sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    local.set(null);
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    local.set(null);
  }

  protected XAUnitOfWorkAspect make(ImmutableMap<String, SessionFactory> someSessionFactories) {
    XAUnitOfWorkAspect aspect = local.get();
    if (aspect == null) {
      aspect = new XAUnitOfWorkAspect(someSessionFactories);
      local.set(aspect);
    }
    return aspect;
  }

  public XAUnitOfWorkAspect newAspect() {
    return make(this.sessionFactories);
  }

  /**
   * @param someSessionFactories Hibernate session factories for this transaction
   * @return a new aspect
   */
  public XAUnitOfWorkAspect newAspect(ImmutableMap<String, SessionFactory> someSessionFactories) {
    return make(someSessionFactories);
  }

}
