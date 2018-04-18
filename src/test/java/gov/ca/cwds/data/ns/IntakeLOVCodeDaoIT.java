package gov.ca.cwds.data.ns;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntakeLOVCodeDaoIT {

  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
  }

  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  @Test
  public void testThatFindIntakeLOVCodesByIntakeCodesExist() {
    Query query = session.getNamedQuery(
        "gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity.findIntakeLOVCodesByIntakeCodes");
    Assert.assertNotNull(query);
  }
}
