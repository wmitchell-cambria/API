package gov.ca.cwds.rest.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.jdbi.CrudsDao;

/**
 * An implementation of {@link CrudsService} delegating work to a
 * {@link CrudsDao}
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link DomainObject} that maps to the presistent object to perform cruds on
 * @param <P> The {@link PersistentObject} the service performs CRUDs operations on
 *            
 */
public class CrudsServiceImpl<T extends DomainObject, P extends PersistentObject> implements CrudsService<T, P> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CrudsServiceImpl.class);

	private CrudsDao<P> crudsDao;
	private Class<T> domainObjectClass;
	private Class<P> persistentObjectClass;
	private Constructor<P> persistentObjectConstructor;
	private Constructor<T> domainObjectConstructor;

	public CrudsServiceImpl(CrudsDao<P> crudsDao, Class<T> domainObjectClass, Class<P> persistentObjectClass) {
		this.crudsDao = crudsDao;
		this.domainObjectClass = domainObjectClass;
		this.persistentObjectClass = persistentObjectClass;
	}

	@Override
	public T find(String id) {
		return constructDomainObject(crudsDao.find(id));
	}

	@Override
	public T delete(String id) {
		return constructDomainObject(crudsDao.delete(id));
	}

	@Override
	public String create(T object) {
		try {
			P persistentObject = constructPersistentObject(object);
			persistentObject = crudsDao.create(persistentObject);
			return persistentObject.getPrimaryKey();
		} catch (EntityExistsException e) {
			throw new ServiceException(e);
		} 
	}

	@Override
	public String update(T object) {
		try {
			P persistentObject = constructPersistentObject(object);
			persistentObject = crudsDao.update(persistentObject);
			return persistentObject.getPrimaryKey();
		} catch (EntityNotFoundException e) {
			throw new ServiceException(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Constructor retrievePersistentObjectConstructor() {
		if( persistentObjectConstructor == null ) {
			for( Constructor found : persistentObjectClass.getConstructors()) {
				Class[] params = found.getParameterTypes();
				if( params.length == 2 && params[0].equals(domainObjectClass)) {
					persistentObjectConstructor = found;
					break;
				}
			}
		}
		return persistentObjectConstructor;
	}
	
	private P constructPersistentObject(T object) {
		@SuppressWarnings("unchecked")
		Constructor<P> constructor = retrievePersistentObjectConstructor();
		try {
			P persistentObject = constructor.newInstance(object, retrieveLastUpdatedId() );
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Constructor retrieveDomainObjectConstructor() {
		if( domainObjectConstructor == null ) {
			for( Constructor found : domainObjectClass.getConstructors()) {
				Class[] params = found.getParameterTypes();
				if( params.length == 1 && params[0].equals(persistentObjectClass)) {
					persistentObjectConstructor = found;
					break;
				}
			}
		}
		return persistentObjectConstructor;
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
	
	private String retrieveLastUpdatedId() {
		return "ABC";
	}

}
