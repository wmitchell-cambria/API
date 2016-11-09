package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.DomainObject;

public class ServiceRegistry {

  private static final GenericRegistry<DomainObject, Service> registry = new GenericRegistry<>();

  public static final void register(Class<? extends DomainObject> clazz, Service v) {
    registry.register(clazz, v);
  }

  public static final Service get(Class<? extends DomainObject> clazz) {
    return registry.get(clazz);
  }

}
