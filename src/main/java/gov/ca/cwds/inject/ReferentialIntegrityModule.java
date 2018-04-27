package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ServiceBackedResourceDelegate;
import gov.ca.cwds.rest.resources.cms.AllegationResource;
import gov.ca.cwds.rest.services.AddressService;

/**
 * Registers CWDS API referential integrity classes with Guice for dependency injection.
 * 
 * @author CWDS API Team
 */
public class ReferentialIntegrityModule extends AbstractModule {

  /**
   * Default, no-op constructor.
   */
  public ReferentialIntegrityModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    bind(AllegationResource.class);
  }

  @Provides
  @AddressServiceBackedResource
  public ResourceDelegate addressServiceBackedResource(Injector injector) {
    return new ServiceBackedResourceDelegate(injector.getInstance(AddressService.class));
  }

}
