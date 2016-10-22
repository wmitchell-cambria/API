package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Allegation;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class AllegationDao extends CmsCrudsDaoImpl<Allegation> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public AllegationDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
