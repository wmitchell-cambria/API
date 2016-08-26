package gov.ca.cwds.rest.jdbi;

import java.util.HashMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * An implementation of {@link CrudsDao} backed by a {@link HashMap}.  Used for development purposes.
 * 
 * @author CWDS API Team
 *
 * @param <T>	The {@link PersistentObject} to perform CRUDS operations on
 */
public final class HashMapDaoImpl<T extends PersistentObject> implements CrudsDao<T> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrudsDaoImpl.class);
	
	HashMap<String, T> dummyData;
	
	public HashMapDaoImpl(HashMap<String, T> dummyData) {
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
		T existing = dummyData.get(object.getPrimaryKey()) ;
		if( existing != null ) {
			throw new EntityExistsException();
		}
		dummyData.put(object.getPrimaryKey(), object);
		return object;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.CrudsDao#update(gov.ca.cwds.rest.api.persistence.PersistentObject)
	 */
	@Override
	public T update(T object) {
		T existing = dummyData.get(object.getPrimaryKey()) ;
		if( existing == null ) {
			throw new EntityNotFoundException();
		}
		dummyData.put(object.getPrimaryKey(), object);
		return object;
	}
}