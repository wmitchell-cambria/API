package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;

public class StaffPersonDaoIT {
  private SessionFactory sessionFactory;
  private StaffPersonDao staffPersonDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    staffPersonDao = new StaffPersonDao(sessionFactory);
  }

  @After
  public void tearndown() {
    sessionFactory.close();
  }

  @Test
  public void testFind() {
    String id = "q1p";
    StaffPerson found = staffPersonDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() {
    StaffPerson staffPerson = new StaffPerson("q1k", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    StaffPerson created = staffPersonDao.create(staffPerson);
    assertThat(created, is(staffPerson));
  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    StaffPerson staffPerson = new StaffPerson("q1p", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    staffPersonDao.create(staffPerson);
  }

  @Test
  public void testDelete() {
    String id = "q1p";
    StaffPerson deleted = staffPersonDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() {
    StaffPerson staffPerson = new StaffPerson("q1p", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    StaffPerson updated = staffPersonDao.update(staffPerson);
    assertThat(updated, is(staffPerson));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);
    StaffPerson staffPerson = new StaffPerson("q1q", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "99", "N", "3XPCP92q38", null);
    staffPersonDao.update(staffPerson);
  }
}
