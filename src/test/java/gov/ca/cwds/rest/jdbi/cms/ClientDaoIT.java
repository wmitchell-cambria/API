package gov.ca.cwds.rest.jdbi.cms;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.Client;

public class ClientDaoIT {
  private SessionFactory sessionFactory;
  private ClientDao clientDao;
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String birthDateString = "1972-08-17";
  private String creationDateString = "2004-08-17";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    sessionFactory = new Configuration().configure().buildSessionFactory();
    sessionFactory.getCurrentSession().beginTransaction();
    clientDao = new ClientDao(sessionFactory);
  }

  @After
  public void tearndown() {
    sessionFactory.close();
  }

  @Test
  public void testFind() {
    String id = "AaiU7IW0Rt";
    Client found = clientDao.find(id);
    assertThat(found.getId(), is(id));
  }

  @Test
  public void testCreate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AbiOD9Y0Hj",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1313, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    Client created = clientDao.create(client);
    assertThat(created, is(client));
  }

  @Test
  public void testCreateExistingEntityException() throws Exception {
    thrown.expect(EntityExistsException.class);
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AaiU7IW0Rt",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1313, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    clientDao.create(client);
  }

  @Test
  public void testDelete() {
    String id = "AaiU7IW0Rt";
    Client deleted = clientDao.delete(id);
    assertThat(deleted.getId(), is(id));
  }

  @Test
  public void testUpdate() throws Exception {
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AaiU7IW0Rt",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1314, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    Client updated = clientDao.update(client);
    assertThat(updated, is(client));
  }

  @Test
  public void testUpdateEntityNotFoundException() throws Exception {
    thrown.expect(EntityNotFoundException.class);
    Date birthDate = df.parse(birthDateString);
    Date creationDate = df.parse(creationDateString);
    Client client = new Client(null, "N", " ", " ", (short) 0, birthDate, "", (short) 0, "N", "N",
        null, " ", "Tumbling", "Waters", " ", null, "N", creationDate, "N", " ", "N", null, "N",
        null, null, " ", (short) 0, null, "Y", null, null, "M", null, null, "U", "AasRx3r0Ha",
        (short) 0, (short) 0, "U", "N", "N", "U", "N", (short) 0, "U", null, " ", (short) 1313, "N",
        "N", " ", "N", (short) 0, (short) 0, (short) 0, (short) 0, "N", "N", "N", "N", "O", " ",
        " ", "N", "N", "U", "N");
    clientDao.update(client);
  }


  @Test
  public void testSelect() throws Exception {
    List<Client> found = clientDao.findAllClient();
    assertThat(found.size(), is(2));
  }
}
