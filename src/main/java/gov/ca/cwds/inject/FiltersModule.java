package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;

/**
 * DI for Filter classes
 * 
 * @author CWDS API Team
 */
public class FiltersModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(RequestResponseLoggingFilter.class);
  }

}
