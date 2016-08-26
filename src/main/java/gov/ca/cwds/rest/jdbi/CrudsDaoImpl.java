package gov.ca.cwds.rest.jdbi;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import io.dropwizard.hibernate.AbstractDAO;

import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public CrudsDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#find(java.lang.String)
	 */
	@Override
	public T find(String id) {
		return get(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#delete(java.lang.String)
	 */
	@Override
	public T delete(String id) {
		T object = find(id);
		if( object != null ) {
			currentSession().delete(object);
		} else {
			throw new EntityNotFoundException();
		}
		return object;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#create(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T create(T object) {
		return persist(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#update(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T update(T object) {
		return persist(object);
	}
}
