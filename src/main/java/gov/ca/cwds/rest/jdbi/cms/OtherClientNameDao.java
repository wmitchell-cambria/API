package gov.ca.cwds.rest.jdbi.cms;

import gov.ca.cwds.rest.api.persistence.cms.OtherClientName;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

import org.hibernate.SessionFactory;

/**
 * Other Client Name DAO
 * 
 * @author CWDS API Team
 */

public class OtherClientNameDao extends BaseDaoImpl<OtherClientName> {

  public OtherClientNameDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
