package gov.ca.cwds.rest.services;

import java.io.Serializable;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * Interface for business {@link Service} which perform CRUDS operations
 * 
 * @author CWDS API Team
 *
 * @param <T>	The {@link DomainObject} the service implements CRUDS for
 * @param <P> The {@link PersistentObject} the service performs CRUDs operations on
 * 
 */
public interface CrudsService<T extends DomainObject, P extends PersistentObject> extends Service {
	
	/**
	 * Find object by primaryKey
	 * 
	 * @param primaryKey
	 *            The primaryKey of the object to find.
	 * 
	 * @return The found object, null otherwise
	 */
	public T find(Serializable primaryKey);

	/**
	 * Delete object by id
	 * 
	 * @param id
	 *            The id of the object to delete.
	 * 
	 * @return The deleted object, null if not found
	 */
	public T delete(String id);

	/**
	 * Create object
	 * 
	 * @param object
	 *            The object to be created
	 * 
	 * @return The id of the newly created object
	 */
	public String create(T object);

	/**
	 * Update object
	 * 
	 * @param object
	 *            The object to be updated
	 * 
	 * @return The id of the updated object
	 */
	public String update(T object);
}
