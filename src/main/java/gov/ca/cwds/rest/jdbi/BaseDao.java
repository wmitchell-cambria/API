package gov.ca.cwds.rest.jdbi;

import java.util.Date;
import java.util.List;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * Interface with some common DAO functions
 * 
 * @author CWDS API Team
 */
public interface BaseDao<T extends PersistentObject> extends Dao {

  public List<T> findAll();

  public List<T> findAllUpdatedAfter(Date datetime);
}
