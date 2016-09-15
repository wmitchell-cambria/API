package gov.ca.cwds.rest;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtility {
  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtility.class);
  private static SessionFactory sessionFactory;

  public static synchronized SessionFactory getSessionFactory(SessionFactory factory) {
    if (sessionFactory == null) {
      sessionFactory = factory;
      return sessionFactory;
    } else {
      return sessionFactory;
    }
  }
}
