package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Allegation;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class AllegationDao extends CrudsDaoImpl<Allegation> {

  SessionFactory sessionFactory;

  public AllegationDao(SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }


}
