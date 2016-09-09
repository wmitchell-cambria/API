package gov.ca.cwds.rest.jdbi.legacy;

import gov.ca.cwds.rest.api.persistence.legacy.Reporter;

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


public class ReporterDaoIT {
	private SessionFactory sessionFactory;
	private ReporterDao reporterDao;

	@Before
	public void setup() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		sessionFactory.getCurrentSession().beginTransaction();
		reporterDao = new ReporterDao(sessionFactory);
	}

	@After
	public void tearndown() {
		sessionFactory.close();
	}
	
	@Test
	public void testFind() {
		String id = "AbiQCgu0Hj";
		gov.ca.cwds.rest.api.domain.legacy.Reporter reporter = new gov.ca.cwds.rest.api.domain.legacy.Reporter(
				"  ", "City", (short) 591, (short) 0 , false, null,
				" ", null, false,"Fred", "Reporter", false, 0, BigDecimal.valueOf(0), 
				" ", " ", BigDecimal.valueOf(0L), 0, (short) 1828, "Street", 
				"12345", " ", 95845, "AbiQCgu0Hj", null, (short) 0, "51");
		Reporter expected = new Reporter(reporter, "0Hj");
		Reporter found = reporterDao.find(id);
		assertThat(found.getReferralId(), is(equalTo(expected.getReferralId())));
	}

	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.legacy.Reporter reporter = new gov.ca.cwds.rest.api.domain.legacy.Reporter(
				"  ", "City", (short) 591, (short) 0 , false, null,
				" ", null, false,"Fred", "Reporter", false, 0, BigDecimal.valueOf(0), 
				" ", " ", BigDecimal.valueOf(0L), 0, (short) 1828, "Street", 
				"12345", " ", 95845, "AbiQCgu0Hk", null, (short) 0, "51");
		Reporter expected = new Reporter(reporter, "0Hk");
		Reporter create = reporterDao.create(expected);
		assertThat(expected, is(create));
		
	}
	
	@Test
	public void testCreateExistingEntityExpection() {
		gov.ca.cwds.rest.api.domain.legacy.Reporter reporter = new gov.ca.cwds.rest.api.domain.legacy.Reporter(
				"  ", "City", (short) 591, (short) 0 , false, null,
				" ", null, false,"Fred", "Reporter", false, 0, BigDecimal.valueOf(0), 
				" ", " ", BigDecimal.valueOf(0L), 0, (short) 1828, "Street", 
				"12345", " ", 95845, "AbiQCgu0Hj", null, (short) 0, "51");
		
		Reporter expected = new Reporter(reporter, "0Kj");
		try{
            reporterDao.create(expected);
		} catch(EntityExistsException entityExistsExp){
			assertThat(entityExistsExp, IsInstanceOf.instanceOf(EntityExistsException.class));
			assertThat(entityExistsExp.getMessage(), IsNull.nullValue());
		}
	}
	
	@Test
	public void testDelete() {
		String id = "AbiQCgu0Hj";
		gov.ca.cwds.rest.api.domain.legacy.Reporter reporter = new gov.ca.cwds.rest.api.domain.legacy.Reporter(
				"  ", "City", (short) 591, (short) 0 , false, null,
				" ", null, false,"Fred", "Reporter", false, 0, BigDecimal.valueOf(0), 
				" ", " ", BigDecimal.valueOf(0L), 0, (short) 1828, "Street", 
				"12345", " ", 95845, "AbiQCgu0Hj", null, (short) 0, "51");
		Reporter expected = new Reporter(reporter, "0Hj");
		Reporter delete = reporterDao.delete(id);
		assertThat(expected.getReferralId(), is(delete.getReferralId()));
	}
	
	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.legacy.Reporter reporter = new gov.ca.cwds.rest.api.domain.legacy.Reporter(
				"  ", "City", (short) 591, (short) 0 , false, null,
				" ", null, false,"Fred", "Reporter", false, 0, BigDecimal.valueOf(0), 
				" ", " ", BigDecimal.valueOf(0), 0, (short) 1828, "Street", 
				"12345", " ", 94536, "AbiQCgu0Hj", null, (short) 0, "51");
		Reporter expected = new Reporter(reporter, "OHj");
		Reporter update = reporterDao.update(expected);
		assertThat(expected, is(update));
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		gov.ca.cwds.rest.api.domain.legacy.Reporter reporter = new gov.ca.cwds.rest.api.domain.legacy.Reporter(
				"  ", "City", (short) 591, (short) 0 , false, null,
				" ", null, false,"Fred", "Reporter", false, 0, BigDecimal.valueOf(0), 
				" ", " ", BigDecimal.valueOf(0), 0, (short) 1828, "Street", 
				"12345", " ", 95845, "AbiQCgu0Hz", null, (short) 0, "51");
		
		Reporter expected = new Reporter(reporter, "0Kz");
		try{
	        reporterDao.update(expected) ;
	        fail("testUpdateEntityNotFoundException() should throw EntityNotFoundException");
		} catch(EntityNotFoundException entityNotFoundException){
			assertThat(entityNotFoundException, IsInstanceOf.instanceOf(EntityNotFoundException.class));
			assertThat(entityNotFoundException.getMessage(), IsNull.nullValue());
		}
	}

}
