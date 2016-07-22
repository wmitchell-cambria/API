package gov.ca.cwds.rest.jdbi;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

import java.util.HashMap;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of {@link CrudsDao}.  Class is final and is expected that other {@link Dao} will contain this implementation and delegate.
 * 
 * @author CWDS API Team
 *
 * @param <T>	The {@link PersistentObject} to perform CRUDS operations on
 */
public final class CrudsDaoImpl<T extends PersistentObject> implements CrudsDao<T> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrudsDaoImpl.class);
	
	HashMap<String, T> dummyData;
	
	public CrudsDaoImpl(HashMap<String, T> dummyData) {
		this.dummyData = dummyData;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#find(java.lang.String)
	 */
	@Override
	public T find(String id) {
		return dummyData.get(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#delete(java.lang.String)
	 */
	@Override
	public T delete(String id) {
		return dummyData.remove(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#create(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T create(T object) {
		T existing = dummyData.get(object.getId()) ;
		if( existing != null ) {
			throw new EntityExistsException();
		}
		String id = UUID.randomUUID().toString();
		@SuppressWarnings("unchecked")
		T created = (T)object.copy(id, object);
		dummyData.put(id, created);
		return created;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#update(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T update(T object) {
		T existing = dummyData.get(object.getId()) ;
		if( existing == null ) {
			throw new EntityNotFoundException();
		}
		@SuppressWarnings("unchecked")
		T updated = (T)object.copy(object.getId(), object);
		dummyData.put(updated.getId(), updated);
		return updated;
	}
}
