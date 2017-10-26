package gov.ca.cwds.health.resource;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS API Team
 *
 */
public class DB2Database implements Pingable {
  private SessionFactory sessionFactory;
  private String message;

  @Inject
  DB2Database(@CmsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public boolean ping() {
    boolean connectionOK = true;
    try {
      Session session = sessionFactory.openSession();
      Query query = session.createNativeQuery("values 1");
      if (query.list().get(0) == null) {
        connectionOK = false;
        message = "Unable to retrieve test querry";
      }
    } catch (Exception e) {
      connectionOK = false;
      message = "Exception occurred while connecting to DB: " + e.getMessage();
    }
    return connectionOK;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
