package gov.ca.cwds.inject;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import gov.ca.cwds.data.ApiHibernateInterceptor;
import io.dropwizard.hibernate.SessionFactoryFactory;

/**
 * Configure Hibernate with a custom interceptor before constructing a session factory.
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class ApiSessionFactoryFactory extends SessionFactoryFactory {

  @Override
  protected void configure(Configuration configuration, ServiceRegistry registry) {
    configuration.setInterceptor(new ApiHibernateInterceptor());
  }

}
