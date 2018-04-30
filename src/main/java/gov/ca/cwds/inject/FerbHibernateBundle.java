package gov.ca.cwds.inject;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

public abstract class FerbHibernateBundle extends HibernateBundle<ApiConfiguration> {

  protected FerbHibernateBundle(ImmutableList<Class<?>> entities,
      SessionFactoryFactory sessionFactoryFactory) {
    super(entities, sessionFactoryFactory);
  }

  @Override
  public abstract String name();

}
