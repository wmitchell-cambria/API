package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Allegation;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class AllegationDao extends CrudsDaoImpl<Allegation> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public AllegationDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
