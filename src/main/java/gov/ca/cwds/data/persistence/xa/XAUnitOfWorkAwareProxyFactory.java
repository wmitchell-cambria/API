package gov.ca.cwds.data.persistence.xa;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

/**
 * A factory for creating proxies for components that use Hibernate data access objects outside
 * Jersey resources using two-phase commit, XA transactions.
 * 
 * <p>
 * A created proxy will be aware of the {@link XAUnitOfWork} annotation on the original class
 * methods and will open an XA transaction around them.
 * </p>
 */
public class XAUnitOfWorkAwareProxyFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkAwareProxyFactory.class);

  private final ImmutableMap<String, SessionFactory> sessionFactories;

  public XAUnitOfWorkAwareProxyFactory() {
    sessionFactories = ImmutableMap.of();
  }

  public XAUnitOfWorkAwareProxyFactory(Pair<String, SessionFactory>... pairs) {
    final ImmutableMap.Builder<String, SessionFactory> builder =
        ImmutableMap.<String, SessionFactory>builder();

    for (Pair<String, SessionFactory> p : pairs) {
      builder.put(p.getLeft(), p.getRight());
    }

    sessionFactories = builder.build();
  }

  /**
   * Creates a new <b>@XAUnitOfWork</b> aware proxy of a class with the default constructor.
   *
   * @param clazz the specified class definition
   * @param <T> the type of the class
   * @return a new proxy
   */
  public <T> T create(Class<T> clazz) {
    return create(clazz, new Class<?>[] {}, new Object[] {});
  }

  /**
   * Creates a new <b>@XAUnitOfWork</b> aware proxy of a class with an one-parameter constructor.
   *
   * @param clazz the specified class definition
   * @param constructorParamType the type of the constructor parameter
   * @param constructorArguments the argument passed to the constructor
   * @param <T> the type of the class
   * @return a new proxy
   */
  public <T> T create(Class<T> clazz, Class<?> constructorParamType, Object constructorArguments) {
    return create(clazz, new Class<?>[] {constructorParamType},
        new Object[] {constructorArguments});
  }

  /**
   * Creates a new <b>@XAUnitOfWork</b> aware proxy of a class with a complex constructor.
   *
   * @param clazz the specified class definition
   * @param constructorParamTypes the types of the constructor parameters
   * @param constructorArguments the arguments passed to the constructor
   * @param <T> the type of the class
   * @return a new proxy
   */
  @SuppressWarnings("unchecked")
  public <T> T create(Class<T> clazz, Class<?>[] constructorParamTypes,
      Object[] constructorArguments) {
    final ProxyFactory factory = new ProxyFactory();
    factory.setSuperclass(clazz);

    try {
      final Proxy proxy = (Proxy) (constructorParamTypes.length == 0
          ? factory.createClass().getConstructor().newInstance()
          : factory.create(constructorParamTypes, constructorArguments));
      proxy.setHandler((self, overridden, proceed, args) -> {
        final XAUnitOfWork xaUnitOfWork = overridden.getAnnotation(XAUnitOfWork.class);
        final XAUnitOfWorkAspect aspect = newAspect();

        try {
          // Begin XA transaction.
          aspect.beforeStart(xaUnitOfWork);

          // Call the method.
          final Object result = proceed.invoke(self, args);

          // Commit XA transaction.
          aspect.afterEnd();
          return result;
        } catch (InvocationTargetException e) {
          aspect.onError();
          throw e.getCause();
        } catch (Exception e) {
          aspect.onError();
          throw e;
        } finally {
          aspect.onFinish();
        }
      });
      return (T) proxy;
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
        | InvocationTargetException e) {
      throw new IllegalStateException("Unable to create a proxy for the class '" + clazz + "'", e);
    }
  }

  /**
   * @return a new aspect
   */
  public XAUnitOfWorkAspect newAspect() {
    return new XAUnitOfWorkAspect();
  }

}
