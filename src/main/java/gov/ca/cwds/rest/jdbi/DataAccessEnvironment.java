package gov.ca.cwds.rest.jdbi;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * Container for {@link Dao} classes
 * 
 * @author CWDS API Team
 */
public class DataAccessEnvironment {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessEnvironment.class);

	@SuppressWarnings("rawtypes")
	private static final Map<Class<? extends PersistentObject>, CrudsDao> daos = new HashMap<>();

	/**
	 * Registers a {@link Dao} to the environment
	 * 
	 * @param clazz
	 *            The {@link PersistentObject} the dao performs operations on
	 * 
	 * @param dao
	 *            The dao implementation
	 */
	public static void register(Class<? extends PersistentObject> clazz, @SuppressWarnings("rawtypes") CrudsDao dao) {
		LOGGER.debug("DAO registration:{} - {}", clazz.getName(), dao.getClass().getName());
		daos.put(clazz, dao);
	}

	/**
	 * Get a registered {@link Dao} based on the given class.
	 * 
	 * @param clazz
	 *            The implemented dao interface.
	 * 
	 * @return The dao implementation
	 */
	@SuppressWarnings("rawtypes")
	public static CrudsDao get(Class<? extends PersistentObject> clazz) {
		return daos.get(clazz);
	}
}
