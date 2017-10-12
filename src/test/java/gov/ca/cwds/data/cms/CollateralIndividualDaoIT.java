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

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.fixture.CollateralIndividualEntityBuilder;
import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class CollateralIndividualDaoIT {

  static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static SessionFactory sessionFactory;
  private static CollateralIndividualDao collateralIndividualDao;
  private Session session;

  /**
   * id matches src/main/resources/db.cms/ci-seeds.sql
   */
  private String id = "AarHGUP0Ki";

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
    collateralIndividualDao = new CollateralIndividualDao(sessionFactory);
  }

  /**
   * 
   */
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

  /**
   * Find JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testFind() throws Exception {
    CollateralIndividual found = collateralIndividualDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    CollateralIndividual found = collateralIndividualDao.find("9999999ZZZ");
    assertThat(found, is(nullValue()));
  }

  /**
   * Create JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testCreate() throws Exception {

    CollateralIndividual collateralIndividual =
        new CollateralIndividualEntityBuilder().setId("ABC1234567").build();

    CollateralIndividual create = collateralIndividualDao.create(collateralIndividual);
    assertThat(collateralIndividual, is(create));
  }

  @Test(expected = EntityExistsException.class)
  public void testCreateExistingEntityException() throws Exception {

    CollateralIndividual collateralIndividual = new CollateralIndividualEntityBuilder().build();
    collateralIndividualDao.create(collateralIndividual);

  }

  /**
   * Delete JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testDelete() throws Exception {
    CollateralIndividual deleted = collateralIndividualDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    CollateralIndividual deleted = collateralIndividualDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  /**
   * Update JUnit test
   * 
   * @throws Exception test general
   */
  @Test
  public void testUpdate() throws Exception {

    CollateralIndividual collateralIndividual =
        new CollateralIndividualEntityBuilder().setZipNumber(95833).build();

    CollateralIndividual updated = collateralIndividualDao.update(collateralIndividual);
    assertThat(collateralIndividual, is(updated));

  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {

    CollateralIndividual collateralIndividual =
        new CollateralIndividualEntityBuilder().setId("0ok7yhT54D").build();
    collateralIndividualDao.update(collateralIndividual);
  }

}
