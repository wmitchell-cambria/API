package gov.ca.cwds.inject;

import org.hibernate.EmptyInterceptor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.persistence.ns.papertrail.PaperTrailInterceptor;
import io.dropwizard.hibernate.SessionFactoryFactory;

/**
 * Configure Hibernate and set a custom interceptor before constructing a session factory.
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 * @see PaperTrailInterceptor
 */
public class FerbSessionFactoryFactory<T extends EmptyInterceptor> extends SessionFactoryFactory {

  private T interceptor;

  /**
   * Default ctor for base class.
   */
  protected FerbSessionFactoryFactory() {
    super();
  }

  /**
   * Preferred constructor. Construct with chosen interceptor.
   * 
   * @param interceptor chosen Hibernate interceptor
   */
  public FerbSessionFactoryFactory(T interceptor) {
    super();
    this.interceptor = interceptor;
  }

  @Override
  protected void configure(Configuration configuration, ServiceRegistry registry) {
    configuration.setInterceptor(interceptor);
  }

}
