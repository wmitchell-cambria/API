package gov.ca.cwds.data.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;

import java.util.Map;
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

import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.fixture.CaseEntityBuilder;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class CaseDaoIT {

  private static SessionFactory sessionFactory;
  private static CaseDao caseDao;
  private Session session;

  public String clientId = "K9epuNg0BN";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * 
   */
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    caseDao = new CaseDao(sessionFactory);
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

  @Test
  public void testFind() throws Exception {
    final String id = "AadfKnG07n";
    CmsCase found = caseDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    final String id = "ZZZZZZ999";
    CmsCase found = caseDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testCreate() throws Exception {
    CmsCase cmsCase = new CaseEntityBuilder().setId("AaOcpgX0kk").build();
    CmsCase create = caseDao.create(cmsCase);
    assertThat(cmsCase, is(create));
  }


  @Test(expected = EntityExistsException.class)
  public void testCreateEntityExistsException() {
    CmsCase cmsCase = new CaseEntityBuilder().setId("AadfKnG07n").build();
    caseDao.create(cmsCase);
  }

  @Test
  public void testDelete() throws Exception {
    CmsCase deleted = caseDao.delete("AawSLHm057");
    assertThat(deleted.getId(), is("AawSLHm057"));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    CmsCase deleted = caseDao.delete("9999999ZZZ");
    assertThat(deleted, is(nullValue()));
  }

  @Test
  public void testUpdate() throws Exception {
    CmsCase cmsCase = new CaseEntityBuilder().setAlertText("Alerts Text 1").build();
    CmsCase updated = caseDao.update(cmsCase);
    assertThat(updated, is(cmsCase));
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdateEntityNotFoundException() throws Exception {
    CmsCase CmsCase = new CaseEntityBuilder().setId("AbNNjTK0P2").build();
    caseDao.update(CmsCase);
  }

  /**
   * Test to find the cases by clientId
   * 
   */
  @Test
  public void testFindClientId() {
    Map<String, CmsCase> cmsCases = caseDao.findByClientIds(Arrays.asList(clientId));
    assertThat(cmsCases, notNullValue());
    assertThat(cmsCases.size(), greaterThanOrEqualTo(1));
  }

}
