package gov.ca.cwds.rest.jdbi.ns;

import gov.ca.cwds.rest.api.persistence.ns.Screening;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

import org.hibernate.SessionFactory;

/**
 * Screening DAO
 * 
 * @author CWDS API Team
 */
public class ScreeningDao extends CrudsDaoImpl<Screening> {

  public ScreeningDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
