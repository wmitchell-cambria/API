package gov.ca.cwds.inject;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
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

  protected ConnectionProvider buildConnectionProvider(DataSource dataSource,
      Map<String, String> properties) {
    final DatasourceConnectionProviderImpl connectionProvider =
        new DatasourceConnectionProviderImpl();
    connectionProvider.setDataSource(dataSource);
    connectionProvider.configure(properties);
    return connectionProvider;
  }

  @Override
  protected void configure(Configuration configuration, ServiceRegistry registry) {
    configuration.setInterceptor(new ApiHibernateInterceptor());
  }

}
