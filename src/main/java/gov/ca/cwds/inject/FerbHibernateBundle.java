package gov.ca.cwds.inject;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

public abstract class FerbHibernateBundle extends HibernateBundle<ApiConfiguration> {

  public static final String CMS_BUNDLE_TAG = "cms";
  public static final String NS_BUNDLE_TAG = "ns";

  FerbHibernateBundle(ImmutableList<Class<?>> entities,
      SessionFactoryFactory sessionFactoryFactory) {
    super(entities, sessionFactoryFactory);
  }

  @Override
  public abstract String name();

}
