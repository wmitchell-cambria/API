package gov.ca.cwds.rest.jdbi;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * An implementation of {@link CrudsDao}.  Class is final and is expected that other {@link Dao} will contain this implementation and delegate.
 * 
 * @author CWDS API Team
 *
 * @param <T>	The {@link PersistentObject} to perform CRUDS operations on
 */
public class CrudsDaoImpl<T extends PersistentObject> extends AbstractDAO<T> implements CrudsDao<T>{
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrudsDaoImpl.class);
	
	/**
	 * 
	 * @param sessionFactory	the session factory
	 */
	public CrudsDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.jdbi.CrudsDao#find(java.io.Serializable)
	 */
	@Override
	public T find(Serializable primaryKey) {
		T object = get(primaryKey);
		currentSession().clear();
		return object;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#delete(java.io.Serializable)
	 */
	@Override
	public T delete(Serializable id) {
		T object = find(id);
		if( object != null ) {
			currentSession().delete(object);
		} 
		return object;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#create(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T create(T object) {
		T databaseObject = find(object.getPrimaryKey());
		if( databaseObject != null ) {
			throw new EntityExistsException();
		}
		return persist(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#update(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T update(T object) {
		T databaseObject = find(object.getPrimaryKey());
		if( databaseObject == null ) {
			throw new EntityNotFoundException();
		}
		currentSession().clear();
		return persist(object);
	}
}
