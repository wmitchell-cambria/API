package gov.ca.cwds.rest.jdbi.cms;

import java.util.List;

import org.hibernate.SessionFactory;

import gov.ca.cwds.rest.api.persistence.cms.Client;
import gov.ca.cwds.rest.jdbi.CmsCrudsDaoImpl;

public class ClientDao extends CmsCrudsDaoImpl<Client> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  public ClientDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }


  @SuppressWarnings("unchecked")
  public List<Client> findAllClient() {
    List<Client> clientList = this.getSessionFactory().getCurrentSession()
        .createSQLQuery("SELECT * FROM cwsint.client_t").list();
    return clientList;
  }

}
