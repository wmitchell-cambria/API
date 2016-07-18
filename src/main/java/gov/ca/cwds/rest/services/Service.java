package gov.ca.cwds.rest.services;

/**
 * Marker for Business Services
 * 
 * @author CWDS API Team 
 */
public interface Service<T> {
	
	/**
	 * Find object by id
	 * 
	 * @param id	The id of the object to find.
	 * 
	 * @return	The found object, null otherwise
	 */
	public T find(String id);

}
