package gov.ca.cwds.rest.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic registry container associates arbitrary class type K with an instance of type V.
 * 
 * @author CWDS API Team
 */
public class GenericRegistry<K, V> {
  private static final Logger LOGGER = LoggerFactory.getLogger(GenericRegistry.class);

  private final Map<Class<? extends K>, V> registry = new ConcurrentHashMap<>();

  /**
   * Registers a V (value) implementation object to the environment.
   * 
   * @param clazz The K (key) class
   * @param v The V (value) implementation
   */
  public void register(Class<? extends K> clazz, V v) {
    LOGGER.debug("register:{} - {}", clazz.getName(), v.getClass().getName());
    registry.put(clazz, v);
  }

  /**
   * Get registered by associated return class.
   * 
   * @param clazz The K (key) class
   * @return v The V (value) implementation
   */
  public V get(Class<? extends K> clazz) {
    return registry.get(clazz);
  }

  /**
   * Wipe the slate clean. Clear all key/value pairs.
   * 
   * <p>Underlying map implementation is synchronized but could change.</p>
   */
  public synchronized void clear() {
    registry.clear();
  }
}
