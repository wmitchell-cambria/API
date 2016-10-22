package gov.ca.cwds.rest.jdbi;

import javax.persistence.EntityExistsException;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.CmsPersistentObject;

/**
 * An implementation of {@link CrudsDao}. This class decorates {@link CrudsDaoImpl} in that the CMS
 * system has identifiers generated at the app level. We need to check on create that the identifier
 * is not already used.
 * 
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link CmsPersistentObject} to perform CRUDS operations on
 */
public class CmsCrudsDaoImpl<T extends CmsPersistentObject> extends CrudsDaoImpl<T> {


  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public CmsCrudsDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.jdbi.CrudsDao#create(gov.ca.cwds.rest.api.persistence.PersistentObject)
   */
  @Override
  public T create(T object) throws EntityExistsException {
    CmsPersistentObject databaseObject = find(object.getPrimaryKey());
    if (databaseObject != null) {
      throw new EntityExistsException();
    }
    return super.create(object);
  }
}
