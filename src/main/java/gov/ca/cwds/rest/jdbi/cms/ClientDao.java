package gov.ca.cwds.rest.jdbi.cms;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Client;
import gov.ca.cwds.rest.jdbi.BaseDaoImpl;

public class ClientDao extends BaseDaoImpl<Client> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public ClientDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
