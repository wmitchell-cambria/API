package gov.ca.cwds.rest.jdbi.legacy;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class StaffPersonDaoIT {
	private SessionFactory sessionFactory;
	private StaffPersonDao staffPersonDao;
	
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
		gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(
				"q1p", null, "External Interface", "external interface", "SCXCIN7", " ",
				"      ", BigDecimal.valueOf(9165672100L), 0, "1999-10-06", "    ", 
				false, "MIZN02k00E", "  ", "    ", "99", false, 
				"3XPCP92q38", null);
		StaffPerson expected = new StaffPerson(staffPerson, "q38");
		StaffPerson found = staffPersonDao.find(id);
		assertThat(found, is(equalTo(expected)));
		//assert found.equals(expected);
	}
	
	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(
				"q1k", null, "External Interface", "external interface", "SCXCIN7", " ",
				"      ", BigDecimal.valueOf(9165672100L), 0, "1999-10-06", "    ", 
				false, "MIZN02k00E", "  ", "    ", "99", false, 
				"3XPCP92q38", null);
		StaffPerson expected = new StaffPerson(staffPerson, "q38");
		StaffPerson create = staffPersonDao.create(expected);
		assertThat(expected, is(create));
		//assert expected.equals(create);
		
	}
	
	@Test
	public void testCreateExistingEntityExpection() {
		gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(
				"q1p", null, "External Interface", "external interface", "SCXCIN7", " ",
				"      ", BigDecimal.valueOf(9165672100L), 0, "1999-10-06", "    ", 
				false, "MIZN02k00E", "  ", "    ", "99", false, 
				"3XPCP92q38", null);
		StaffPerson expected = new StaffPerson(staffPerson, "q38");
		try{
            staffPersonDao.create(expected);
		} catch(EntityExistsException entityExistsExp){
			assertThat(entityExistsExp, IsInstanceOf.instanceOf(EntityExistsException.class));
			assertThat(entityExistsExp.getMessage(), IsNull.nullValue());
		}
	}
	
	@Test
	public void testDelete() {
		String id = "q1u";
		gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(
				"q1u", null, "External Interface", "external interface", "SCXCFP8", " ",
				"      ", BigDecimal.valueOf(9165672100L), 0, "1999-10-06", "    ", 
				 false, "MIZN02k00E", "  ", "    ", "99", false, 
				"3XPCP92q38", null);
		StaffPerson expected = new StaffPerson(staffPerson, "q38");
		StaffPerson delete = staffPersonDao.delete(id);
		assertThat(expected, is(delete));
		//assert expected.equals(delete);
	}
	
	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(
				"q1p", null, "External Interface", "external interface", "SCXCIN7", " ",
				"      ", BigDecimal.valueOf(9165672100L), 0, "1999-10-07", "    ", 
				false, "MIZN02k00E", "  ", "    ", "99", false, 
				"3XPCP92q38", null);
		StaffPerson expected = new StaffPerson(staffPerson, "q38");
		StaffPerson update = staffPersonDao.update(expected);
		assertThat(expected, is(update));
		//assert expected.equals(update);
	}
	
	@Test
	public void testUpdateEntityNotFoundException() {
		gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(
				"q1a", null, "External Interface", "external interface", "SCXCIN7", " ",
				"      ", BigDecimal.valueOf(9165672100L), 0, "1999-10-06", "    ", 
				false, "MIZN02k00E", "  ", "    ", "99", false, 
				"3XPCP92j17", null);
		StaffPerson expected = new StaffPerson(staffPerson, "j17");
		try{
            staffPersonDao.update(expected);
            fail("testUpdateEntityNotFoundException() should throw EntityNotFoundException");
		} catch(EntityNotFoundException entityNotFoundException){
			assertThat(entityNotFoundException, IsInstanceOf.instanceOf(EntityNotFoundException.class));
			assertThat(entityNotFoundException.getMessage(), IsNull.nullValue());
		}
	}
}
