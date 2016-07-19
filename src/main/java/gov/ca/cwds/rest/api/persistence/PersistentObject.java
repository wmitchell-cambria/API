package gov.ca.cwds.rest.api.persistence;

/**
 * Base class for objects in the persistence layer
 * 
 * @author CWDS API Team 
 */
public abstract class PersistentObject {
	private String id;
	
	/**
	 * Constructor
	 * 
	 * @param id The id of the object
	 */
	public PersistentObject(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	//TODO : make abstract with a copy method while things are stubbed.  Once the real db implementation is
	//       done we may not need it.
	public abstract PersistentObject copy(String id, Object from);
}
