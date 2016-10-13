package gov.ca.cwds.rest.jdbi;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * An implementation of {@link CrudsDao} backed by a {@link HashMap}. Used for development purposes.
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link PersistentObject} to perform CRUDS operations on
 */
public final class HashMapDaoImpl<T extends PersistentObject> implements CrudsDao<T> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CrudsDaoImpl.class);

  HashMap<String, T> dummyData;

  public HashMapDaoImpl(HashMap<String, T> dummyData) {
    this.dummyData = dummyData;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public T find(Serializable primaryKey) {
    return dummyData.get(primaryKey);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.CrudsDao#delete(java.io.Serializable)
   */
  @Override
  public T delete(Serializable id) {
    return dummyData.remove(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.CrudsDao#create(gov.ca.cwds.rest.api.persistence.
   * PersistentObject)
   */
  @Override
  public T create(T object) {
    T existing = dummyData.get(object.getPrimaryKey());
    if (existing != null) {
      throw new EntityExistsException();
    }
    dummyData.put(object.getPrimaryKey().toString(), object);
    return object;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.CrudsDao#update(gov.ca.cwds.rest.api.persistence.
   * PersistentObject)
   */
  @Override
  public T update(T object) {
    T existing = dummyData.get(object.getPrimaryKey());
    if (existing == null) {
      throw new EntityNotFoundException();
    }
    dummyData.put(object.getPrimaryKey().toString(), object);
    return object;
  }

  @Override
  public SessionFactory getSessionFactory() {
    // TODO Auto-generated method stub
    return null;
  }
}
