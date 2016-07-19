package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * @author CWDS API Team
 *
 * @param <T>	The {@link PersistentObject} the service implements CRUDS for
 */
public interface CrudsService<T> extends Service {
	/**
	 * Find object by id
	 * 
	 * @param id
	 *            The id of the object to find.
	 * 
	 * @return The found object, null otherwise
	 */
	public T find(String id);

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
	 * @return The created object with a newly populated id
	 */
	public T create(T object);

	/**
	 * Update object
	 * 
	 * @param object
	 *            The object to be updated
	 * 
	 * @return The updated object
	 */
	public T update(T object);
}
