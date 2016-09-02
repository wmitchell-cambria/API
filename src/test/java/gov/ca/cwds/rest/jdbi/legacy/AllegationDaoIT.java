package gov.ca.cwds.rest.jdbi.legacy;


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
		gov.ca.cwds.rest.api.domain.Allegation allegation = new gov.ca.cwds.rest.api.domain.Allegation(
				"Aaeae9r0F4", null, 2, "M", "  ", null,
				0, 2180, "  ", null, false, 
				"N", false, "AHooKwN0F4", null, "8mu1E710F4", "19", 
				false, null);
		Allegation expected = new Allegation (allegation, "0F4");
		Allegation found = allegationDao.find(id);
		assert found.equals(expected);
	}

	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.Allegation allegation = new gov.ca.cwds.rest.api.domain.Allegation(
				"Aaeae9r0F6", null, 2, "M", "  ", null,
				0, 2180, "  ", null, false, 
				"N", false, "AHooKwN0F6", null, "8mu1E710F4", "19", 
				false, null);
		Allegation expected = new Allegation (allegation, "0F6");
		Allegation create = allegationDao.create(expected);
		assert expected.equals(create);
		//allegationDao.delete("Aaeae9r0F6");
		
	}
	
	@Test
	public void testCreateExistingEntityExpection() {
		boolean gotException = false;
		gov.ca.cwds.rest.api.domain.Allegation allegation = new gov.ca.cwds.rest.api.domain.Allegation(
				"Aaeae9r0F4", null, 2, "M", "  ", null,
				0, 2180, "  ", null, false, 
				"N", false, "AHooKwN0F4", null, "8mu1E710F4", "19", 
				false, null);
		
		Allegation expected = new Allegation (allegation, "0F4");
		try{
		Allegation create = allegationDao.create(expected);
		} catch(EntityExistsException entityExistsExp){
			gotException = true;
		}
		assert gotException;
	}
	
	@Test
	public void testDelete() {
		gov.ca.cwds.rest.api.domain.Allegation allegation = new gov.ca.cwds.rest.api.domain.Allegation(
				"Aaeae9r0F4", null, 2, "M", "  ", null,
				0, 2180, "  ", null, false, 
				"N", false, "AHooKwN0F4", null, "8mu1E710F4", "19", 
				false, null);
		
		Allegation expected = new Allegation (allegation, "0F4");
		Allegation deleted = allegationDao.delete("Aaeae9r0F4");
		assert expected.equals(deleted);
	}
	
	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.Allegation allegation = new gov.ca.cwds.rest.api.domain.Allegation(
				"Aaeae9r0F4", null, 2, "M", "  ", null,
				0, 2180, "  ", null, false, 
				"N", false, "AHooKwN0F4", null, "8mu1E710F4", "20", 
				false, null);
		Allegation expected = new Allegation (allegation, "0F4");
		Allegation update = allegationDao.update(expected);
		assert expected.equals(update);
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		boolean gotException = false;
		gov.ca.cwds.rest.api.domain.Allegation allegation = new gov.ca.cwds.rest.api.domain.Allegation(
				"Aaeae9r0F7", null, 2, "M", "  ", null,
				0, 2180, "  ", null, false, 
				"N", false, "AHooKwN0F7", null, "8mu1E710F7", "20", 
				false, null);
		
		Allegation expected = new Allegation (allegation, "0F7");
		try{
		Allegation update = allegationDao.update(expected) ;
		} catch(EntityNotFoundException entityNotFoundException){
			gotException = true;
		}
		assert gotException;
	}

}
