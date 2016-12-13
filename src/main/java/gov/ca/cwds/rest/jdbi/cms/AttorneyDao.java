package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Attorney;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

public class AttorneyDao extends BaseDaoImpl<Attorney> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public AttorneyDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
