package gov.ca.cwds.rest.jdbi.ns;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.ns.StaffPersonNS;
import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

public class StaffPersonNSDao extends CrudsDaoImpl<StaffPersonNS> {

  public StaffPersonNSDao(SessionFactory sessionFactory) {

    super(sessionFactory);
  }

}
