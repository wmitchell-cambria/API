package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Static slate of singleton {@link Service} implementation instances by {@link DomainObject} class
 * type.
 * 
 * @author CWDS API Team
 * @see GenericRegistry
 */
public final class ServiceRegistry {

  /**
   * Singleton instance of the registry.
   * <p>
   * Key: {@link DomainObject} class
   * <p/>
   * <p>
   * Value: {@link Service} instance
   * <p>
   */
  private static final GenericRegistry<DomainObject, Service> registry = new GenericRegistry<>();

  /**
   * Register a unique instance (singleton) of a {@link Service} implementation with a unique
   * {@link DomainObject} class.
   * 
   * @param clazz Class of type {@link DomainObject}
   * @param v "value", a {@link Service} object instance
   * @see GenericRegistry#register(Class, Object)
   */
  public static final void register(Class<? extends DomainObject> clazz, Service v) {
    registry.register(clazz, v);
  }

  /**
   * Not a factory. Returns a unique instance (singleton) of the {@link Service} interface.
   * 
   * @param clazz Class of type {@link DomainObject}
   * @return singleton {@link Service} instance
   * @see GenericRegistry#get(Class)
   */
  public static final Service get(Class<? extends DomainObject> clazz) {
    return registry.get(clazz);
  }

  /**
   * Clears registry entries.
   * 
   * @see GenericRegistry#clear()
   */
  public static final void clear() {
    registry.clear();
  }
}
