package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class StaffPersonDao extends CmsCrudsDaoImpl<StaffPerson> {

  public StaffPersonDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
