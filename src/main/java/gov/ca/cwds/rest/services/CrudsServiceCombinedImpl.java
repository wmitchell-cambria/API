package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.jdbi.CrudsDao;

/**
 * An implementation of {@link CrudsService} delegating work to two {@link CrudsDao}
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link DomainObject} that maps to the presistent object to perform cruds on
 * @param <P> The {@link PersistentObject} the service performs CRUDs operations on
 * @param <V> The second {@link PersistentObject} the service performs CRUDs operations on
 * 
 */
public class CrudsServiceCombinedImpl<T extends DomainObject, P extends PersistentObject, V extends PersistentObject>
    implements CrudsService<T, P> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrudsServiceCombinedImpl.class);

  private CrudsDao<P> crudsDao;
  private CrudsDao<V> crudsNSDao;
  private Class<T> domainObjectClass;
  private Class<P> persistentObjectClass;
  private Class<V> persistentObjectNSClass;
  private Constructor<P> persistentObjectConstructor;
  private Constructor<V> persistentObjectNSConstructor;
  private Constructor<T> domainObjectConstructor;

  public CrudsServiceCombinedImpl(CrudsDao<P> crudsDao, Class<T> domainObjectClass,
      Class<P> persistentObjectClass) {
    this.crudsDao = crudsDao;
    this.domainObjectClass = domainObjectClass;
    this.persistentObjectClass = persistentObjectClass;
  }

  public CrudsServiceCombinedImpl(CrudsDao<P> crudsDao, CrudsDao<V> crudsNSDao,
      Class<T> domainObjectClass, Class<P> persistentObjectClass, Class<V> persistentObjectNSClass) {
    this.crudsNSDao = crudsNSDao;
    this.crudsDao = crudsDao;
    this.domainObjectClass = domainObjectClass;
    this.persistentObjectClass = persistentObjectClass;
    this.persistentObjectNSClass = persistentObjectNSClass;
  }

  @Override
  public T find(Serializable primaryKey) {
    SessionFactory sessionFactory = crudsDao.getSessionFactory();
    org.hibernate.Session session = sessionFactory.openSession();
    ManagedSessionContext.bind(session);
    P object = crudsDao.find(primaryKey);
    SessionFactory sessionFactoryNS = crudsNSDao.getSessionFactory();
    org.hibernate.Session sessionNS = sessionFactoryNS.openSession();
    ManagedSessionContext.bind(sessionNS);
    if (crudsNSDao == null) {
      if (object != null) {
        return constructDomainObject(object);
      }
    } else {
      V objectNS = crudsNSDao.find(primaryKey);
      if (object != null && objectNS != null) {
        return constructNSDomainObject(object, objectNS);
      } else if (object != null) {
        return constructDomainObject(object);
      }
    }
    return null;
  }

  @Override
  public T delete(Serializable id) {
    SessionFactory sessionFactory = crudsDao.getSessionFactory();
    org.hibernate.Session session = sessionFactory.openSession();
    ManagedSessionContext.bind(session);
    P object = crudsDao.delete(id);
    session.flush();

    SessionFactory sessionFactoryNS = crudsNSDao.getSessionFactory();
    org.hibernate.Session sessionNS = sessionFactoryNS.openSession();
    ManagedSessionContext.bind(sessionNS);
    V objectNS = crudsNSDao.delete(id);
    sessionNS.flush();

    if (object != null && objectNS != null) {
      return constructNSDomainObject(object, objectNS);
    } else if (object != null) {
      return constructDomainObject(object);
    }
    return null;
  }

  @Override
  public Serializable create(T object) {
    try {
      SessionFactory sessionFactory = crudsDao.getSessionFactory();
      org.hibernate.Session session = sessionFactory.openSession();
      ManagedSessionContext.bind(session);
      P persistentObject = constructPersistentObject(object);
      persistentObject = crudsDao.create(persistentObject);
      session.flush();

      SessionFactory sessionFactoryNS = crudsNSDao.getSessionFactory();
      org.hibernate.Session sessionNS = sessionFactoryNS.openSession();
      ManagedSessionContext.bind(sessionNS);
      V persistentObjectNS = constructPersistentObjectNS(object);
      persistentObjectNS = crudsNSDao.create(persistentObjectNS);
      sessionNS.flush();

      return persistentObjectNS.getPrimaryKey();
    } catch (EntityExistsException e) {
      LOGGER.info("object already exists {}", object);
      throw new ServiceException(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public String update(T object) {
    try {
      SessionFactory sessionFactory = crudsDao.getSessionFactory();
      org.hibernate.Session session = sessionFactory.openSession();
      ManagedSessionContext.bind(session);
      P persistentObject = constructPersistentObject(object);
      persistentObject = (P) session.merge(persistentObject);
      persistentObject = crudsDao.update(persistentObject);
      session.flush();
      SessionFactory sessionFactoryNS = crudsNSDao.getSessionFactory();
      org.hibernate.Session sessionNS = sessionFactoryNS.openSession();
      ManagedSessionContext.bind(sessionNS);
      V persistentObjectNS = constructPersistentObjectNS(object);
      persistentObjectNS = (V) sessionNS.merge(persistentObjectNS);
      persistentObjectNS = crudsNSDao.update(persistentObjectNS);
      sessionNS.flush();
      return persistentObjectNS.getPrimaryKey().toString();
    } catch (EntityNotFoundException e) {
      LOGGER.info("object not found : {}", object);
      throw new ServiceException(e);
    }

  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private Constructor retrievePersistentObjectConstructor() {
    if (persistentObjectConstructor == null) {
      for (Constructor found : persistentObjectClass.getConstructors()) {
        Class[] params = found.getParameterTypes();
        if (params.length == 2 && params[0].equals(domainObjectClass)) {
          persistentObjectConstructor = found;
          break;
        }
      }
    }
    return persistentObjectConstructor;
  }


  @SuppressWarnings({"rawtypes", "unchecked"})
  private Constructor retrievePersistentObjectNSConstructor() {
    if (persistentObjectNSConstructor == null) {
      for (Constructor found : persistentObjectNSClass.getConstructors()) {
        Class[] params = found.getParameterTypes();
        if (params.length == 2 && params[0].equals(domainObjectClass)) {
          persistentObjectNSConstructor = found;
          break;
        }
      }
    }
    return persistentObjectNSConstructor;
  }

  private P constructPersistentObject(T object) {
    @SuppressWarnings("unchecked")
    Constructor<P> constructor = retrievePersistentObjectConstructor();
    try {
      P persistentObject = constructor.newInstance(object, retrieveLastUpdatedId());
      return persistentObject;
    } catch (InstantiationException e) {
      throw new ServiceException(e);
    } catch (IllegalAccessException e) {
      throw new ServiceException(e);
    } catch (IllegalArgumentException e) {
      throw new ServiceException(e);
    } catch (InvocationTargetException e) {
      throw new ServiceException(e);
    }
  }

  private V constructPersistentObjectNS(T object) {
    @SuppressWarnings("unchecked")
    Constructor<V> constructor = retrievePersistentObjectNSConstructor();
    try {
      V persistentObject = constructor.newInstance(object, retrieveLastUpdatedId());
      return persistentObject;
    } catch (InstantiationException e) {
      throw new ServiceException(e);
    } catch (IllegalAccessException e) {
      throw new ServiceException(e);
    } catch (IllegalArgumentException e) {
      throw new ServiceException(e);
    } catch (InvocationTargetException e) {
      throw new ServiceException(e);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private Constructor retrieveDomainObjectConstructor() {
    for (Constructor found : domainObjectClass.getConstructors()) {
      Class[] params = found.getParameterTypes();
      if (params.length == 1 && params[0].equals(persistentObjectClass)) {
        domainObjectConstructor = found;
        break;
      }
    }
    return domainObjectConstructor;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private Constructor retrieveDomainObjectNSConstructor() {
    for (Constructor found : domainObjectClass.getConstructors()) {
      Class[] params = found.getParameterTypes();
      if (params.length == 2 && params[0].equals(persistentObjectClass)) {
        domainObjectConstructor = found;
        break;
      }
    }
    return domainObjectConstructor;
  }

  private T constructDomainObject(P object) {
    @SuppressWarnings("unchecked")
    Constructor<T> constructor = retrieveDomainObjectConstructor();
    try {
      T domainObject = constructor.newInstance(object);
      return domainObject;
    } catch (InstantiationException e) {
      throw new ServiceException(e);
    } catch (IllegalAccessException e) {
      throw new ServiceException(e);
    } catch (IllegalArgumentException e) {
      throw new ServiceException(e);
    } catch (InvocationTargetException e) {
      throw new ServiceException(e);
    }
  }

  private T constructNSDomainObject(P object, V objectNS) {
    @SuppressWarnings("unchecked")
    Constructor<T> constructor = retrieveDomainObjectNSConstructor();
    try {
      T domainObject = constructor.newInstance(object, objectNS);
      return domainObject;
    } catch (InstantiationException e) {
      throw new ServiceException(e);
    } catch (IllegalAccessException e) {
      throw new ServiceException(e);
    } catch (IllegalArgumentException e) {
      throw new ServiceException(e);
    } catch (InvocationTargetException e) {
      throw new ServiceException(e);
    }
  }


  private String retrieveLastUpdatedId() {
    return "ABC";
  }

}
