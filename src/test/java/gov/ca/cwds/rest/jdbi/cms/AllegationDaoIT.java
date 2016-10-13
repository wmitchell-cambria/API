package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.Allegation;

public class AllegationDaoIT {
  private SessionFactory sessionFactory;
  private AllegationDao allegationDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    allegationDao = new AllegationDao(sessionFactory);
  }

  @After
  public void tearndown() {
    sessionFactory.close();
  }

  @Test
  public void testFind() {
    String id = "Aaeae9r0F4";
    Allegation found = allegationDao.find(id);
    assertThat(found.getId(), is(equalTo(id)));
  }

  @Test
  public void testCreate() {
    Allegation allegation = new Allegation("Aaeae9r0F7", (Date) null, (short) 2, "M", "  ",
        (Date) null, (short) 0, (short) 2180, "  ", (Date) null, "N", "N", "N", "AHooKwN0F7", null,
        "8mu1E710F7", "20", "N", null);
    Allegation create = allegationDao.create(allegation);
    assertThat(allegation, is(create));
  }

  @Test
  public void testCreateExistingEntityException() {
    thrown.expect(EntityExistsException.class);
    Allegation allegation = new Allegation("Aaeae9r0F4", (Date) null, (short) 2, "M", "  ",
        (Date) null, (short) 0, (short) 2180, "  ", (Date) null, "N", "N", "N", "AHooKwN0F7", null,
        "8mu1E710F7", "20", "N", null);
    allegationDao.create(allegation);
  }

  @Test
  public void testDelete() {
    String id = "Aaeae9r0F4";
    Allegation deleted = allegationDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() {
    Allegation allegation = new Allegation("Aaeae9r0F4", (Date) null, (short) 2, "M", "  ",
        (Date) null, (short) 0, (short) 2180, "  ", (Date) null, "N", "N", "N", "AHooKwN0F7", null,
        "8mu1E710F7", "20", "N", null);
    Allegation update = allegationDao.update(allegation);
    assertThat(allegation, is(update));
  }

  @Test
  public void testUpdateEntityNotFoundException() {
    thrown.expect(EntityNotFoundException.class);

    Allegation allegation = new Allegation("ZZZZZZZZZZ", (Date) null, (short) 2, "M", "  ",
        (Date) null, (short) 0, (short) 2180, "  ", (Date) null, "N", "N", "N", "AHooKwN0F7", null,
        "8mu1E710F7", "20", "N", null);
    allegationDao.update(allegation);
  }

}
