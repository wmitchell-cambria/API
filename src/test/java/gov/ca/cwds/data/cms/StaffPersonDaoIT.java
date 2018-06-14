package gov.ca.cwds.data.cms;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
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
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;

/**
 * @author CWDS API Team
 */
public class StaffPersonDaoIT implements DaoTestTemplate {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static StaffPersonDao staffPersonDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    staffPersonDao = new StaffPersonDao(sessionFactory);
  }

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
    String id = "q1p";
    StaffPerson found = staffPersonDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    String id = "ZZZ";
    StaffPerson found = staffPersonDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  @Test
  public void testCreate() throws Exception {
    // TODO: id may already exist in database.
    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("ZZp").build();
    StaffPerson created = staffPersonDao.create(staffPerson);
    assertThat(created, is(staffPerson));
  }

  @Override
  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {
    StaffPerson staffPerson = new StaffPersonEntityBuilder().build();
    staffPersonDao.create(staffPerson);
  }

  @Override
  @Test
  public void testDelete() throws Exception {
    String id = "q1p";
    StaffPerson deleted = staffPersonDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    // TODO: depends on an id not created by this unit test.
    String id = "ZZZ";
    StaffPerson deleted = staffPersonDao.delete(id);
    assertThat(deleted, is(nullValue()));
  }

  @Override
  @Test
  public void testUpdate() throws Exception {
    StaffPerson staffPerson = new StaffPersonEntityBuilder().setCountyCode("19").build();
    StaffPerson updated = staffPersonDao.update(staffPerson);
    assertThat(updated, is(staffPerson));
  }

  @Override
  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    // TODO: depends on an id not created by this unit test.
    StaffPerson staffPerson = new StaffPersonEntityBuilder().setId("zzp").build();
    staffPersonDao.update(staffPerson);
  }

  @Override
  public void testFindAllNamedQueryExist() throws Exception {

  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {

  }

}
