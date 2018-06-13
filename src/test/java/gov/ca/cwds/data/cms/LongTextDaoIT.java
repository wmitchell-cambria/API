package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.fixture.LongTextEntityBuilder;

/**
 * 
 * @author CWDS API Team
 */
public class LongTextDaoIT implements DaoTestTemplate {

  private static SessionFactory sessionFactory;
  private static LongTextDao longTextDao;
  private Session session;

  /*
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AaoDyiJq27";

  /**
   * 
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    longTextDao = new LongTextDao(sessionFactory);
  }

  /**
   * 
   */
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Override
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @Override
  @After
  public void teardown() {
    session.getTransaction().rollback();
  }


  @Override
  @Test
  public void testFind() throws Exception {
    LongText found = longTextDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    LongText found = longTextDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   */
  @Override
  @Test
  public void testCreate() throws Exception {
    LongText longText = new LongTextEntityBuilder().setId("ABc1234567").build();
    LongText create = longTextDao.create(longText);
    assertThat(longText, is(create));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    LongText longText = new LongTextEntityBuilder().build();
    longTextDao.create(longText);
  }

  /**
   * Delete JUnit test
   */
  @Override
  @Test
  public void testDelete() throws Exception {
    LongText deleted = longTextDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    LongText deleted = longTextDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {

    LongText longText = new LongTextEntityBuilder().setCountySpecificCode("19").build();
    LongText updated = longTextDao.update(longText);
    assertThat(longText, is(updated));

  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    LongText longText = new LongTextEntityBuilder().setId("KK8uh6G4r3").build();
    longTextDao.update(longText);
  }

  /*
   * Named Query JUnit test
   */
  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
