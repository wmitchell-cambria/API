package gov.ca.cwds.rest.jdbi.legacy;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.legacy.Allegation;

public class AllegationDaoIT {
	private SessionFactory sessionFactory;
	private AllegationDao allegationDao;

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
		gov.ca.cwds.rest.api.domain.legacy.Allegation allegation = new gov.ca.cwds.rest.api.domain.legacy.Allegation("Aaeae9r0F4",
				null, (short)2, "M", "  ", null, (short)0, (short)2180, "  ", null, false, "N", false, "AHooKwN0F4", null, "8mu1E710F4",
				"19", false, null);
		Allegation expected = new Allegation(allegation, "0F4");
		Allegation found = allegationDao.find(id);
		assertThat(found, is(equalTo(expected)));
	}

	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.legacy.Allegation allegation = new gov.ca.cwds.rest.api.domain.legacy.Allegation("Aaeae9r0F6",
				null, (short)2, "M", "  ", null, (short)0, (short)2180, "  ", null, false, "N", false, "AHooKwN0F6", null, "8mu1E710F4",
				"19", false, null);
		Allegation expected = new Allegation(allegation, "0F6");
		Allegation create = allegationDao.create(expected);
		assertThat(expected, is(create));
	}

	@Test
	public void testCreateExistingEntityExpection() {
		gov.ca.cwds.rest.api.domain.legacy.Allegation allegation = new gov.ca.cwds.rest.api.domain.legacy.Allegation("Aaeae9r0F4",
				null, (short)2, "M", "  ", null, (short)0, (short)2180, "  ", null, false, "N", false, "AHooKwN0F4", null, "8mu1E710F4",
				"19", false, null);

		Allegation expected = new Allegation(allegation, "0F4");
		try {
			allegationDao.create(expected);
		} catch (EntityExistsException entityExistsExp) {
			assertThat(entityExistsExp, IsInstanceOf.instanceOf(EntityExistsException.class));
			assertThat(entityExistsExp.getMessage(), IsNull.nullValue());
		};
	}

	@Test
	public void testDelete() {
		String id = "Aaeae9r0F4";
		gov.ca.cwds.rest.api.domain.legacy.Allegation allegation = new gov.ca.cwds.rest.api.domain.legacy.Allegation("Aaeae9r0F4",
				null, (short)2, "M", "  ", null, (short)0, (short)2180, "  ", null, false, "N", false, "AHooKwN0F4", null, "8mu1E710F4",
				"19", false, null);
		Allegation expected = new Allegation(allegation, "0F4");
		Allegation delete = allegationDao.delete(id);
		assertThat(expected, is(delete));
		// assert expected.equals(delete);
	}

	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.legacy.Allegation allegation = new gov.ca.cwds.rest.api.domain.legacy.Allegation("Aaeae9r0F4",
				null, (short)2, "M", "  ", null, (short)0, (short)2180, "  ", null, false, "N", false, "AHooKwN0F4", null, "8mu1E710F4",
				"20", false, null);
		Allegation expected = new Allegation(allegation, "0F4");
		Allegation update = allegationDao.update(expected);
		assertThat(expected, is(update));
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		gov.ca.cwds.rest.api.domain.legacy.Allegation allegation = new gov.ca.cwds.rest.api.domain.legacy.Allegation("Aaeae9r0F7",
				null, (short)2, "M", "  ", null, (short)0, (short)2180, "  ", null, false, "N", false, "AHooKwN0F7", null, "8mu1E710F7",
				"20", false, null);
		Allegation expected = new Allegation(allegation, "0F7");
		try {
			allegationDao.update(expected);
			fail("testUpdateEntityNotFoundException() should throw EntityNotFoundException");
		} catch (EntityNotFoundException entityNotFoundException) {
			assertThat(entityNotFoundException, IsInstanceOf.instanceOf(EntityNotFoundException.class));
			assertThat(entityNotFoundException.getMessage(), IsNull.nullValue());
		}
	}

}
