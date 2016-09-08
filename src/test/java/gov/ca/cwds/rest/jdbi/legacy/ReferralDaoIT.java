package gov.ca.cwds.rest.jdbi.legacy;

import gov.ca.cwds.rest.api.persistence.legacy.Referral;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class ReferralDaoIT {
	private SessionFactory sessionFactory;
	private ReferralDao referralDao;
	
	@Before
	public void setup() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		sessionFactory.getCurrentSession().beginTransaction();
		referralDao = new ReferralDao(sessionFactory);
	}
	
	@After
	public void tearndown() {
		sessionFactory.close();
	}
	
	@Test
	public void testFind() {
		String id = "AbiQCgu0Hj";
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbiQCgu0Hj", " ", false, false, "D5YRVOm0Hj",(short) 122, " ", "2000-03-03", (short) 409, null, 
				null, "L3H7sSC0Hj", null, false, false, (short) 1118, " ", false, "N", null, "Verification (R3)", 
				" ", "2000-01-31", "14:44:00", (short) 1520, (short) 0, "2000-01-31", "14:46:00", null, null, " ", " ",
				" ", null, null, "0Hj", "0Hj", "51", false, false, false, false, null, "C", null, null,
				null, "2000-03-03");
		Referral expected = new Referral(referral, "0Hj");
		Referral found = referralDao.find(id);
		assertThat(found.getId(), is(equalTo(expected.getId())));
	}
	
	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbiQCgu0Ht", " ", false, false, "D5YRVOm0Ht",(short) 122, " ", "2000-03-03", (short) 409, null, 
				null, "L3H7sSC0Ht", null, false, false, (short) 1118, " ", false, "N", null, "Verification (R3)", 
				" ", "2000-01-31", "14:44:00", (short) 1520, (short) 0, "2000-01-31", "14:46:00", null, null, " ", " ",
				" ", null, null, "0Ht", "0Ht", "51", false, false, false, false, null, "C", null, null,
				null, "2000-03-03");
		Referral expected = new Referral(referral, "0Ht");
		Referral create = referralDao.create(expected);
		assertThat(expected, is(create));
		
	}
	
	@Test
	public void testCreateExistingEntityExpection() {
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbiQCgu0Hj", " ", false, false, "D5YRVOm0Hj",(short) 122, " ", "2000-03-03", (short) 409, null, 
				null, "L3H7sSC0Hj", null, false, false, (short) 1118, " ", false, "N", null, "Verification (R3)", 
				" ", "2000-01-31", "14:44:00", (short) 1520, (short) 0, "2000-01-31", "14:46:00", null, null, " ", " ",
				" ", null, null, "0Hj", "0Hj", "51", false, false, false, false, null, "C", null, null,
				null, "2000-03-03");
		
		Referral expected = new Referral(referral, "0Hj");
		try{
		    referralDao.create(expected);
		} catch(EntityExistsException entityExistsExp){
			assertThat(entityExistsExp, IsInstanceOf.instanceOf(EntityExistsException.class));
			assertThat(entityExistsExp.getMessage(), IsNull.nullValue());
		}
	}
	
	@Test
	public void testDelete() {
		String id = "AbiQCgu0Hj";
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbiQCgu0Hj", " ", false, false, "D5YRVOm0Hj",(short) 122, " ", "2000-03-03", (short) 409, null, 
				null, "L3H7sSC0Hj", null, false, false, (short) 1118, " ", false, "N", null, "Verification (R3)", 
				" ", "2000-01-31", "14:44:00", (short) 1520, (short) 0, "2000-01-31", "14:46:00", null, null, " ", " ",
				" ", null, null, "0Hj", "0Hj", "51", false, false, false, false, null, "C", null, null,
				null, "2000-03-03");
		Referral expected = new Referral(referral, "0Hj");
		Referral delete = referralDao.delete(id);
		assertThat(expected.getId(), is(delete.getId()));
	}
	
	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbiQCgu0Hj", " ", false, false, "D5YRVOm0Hj",(short) 122, " ", "2000-03-03", (short) 409, null, 
				null, "L3H7sSC0Hj", null, false, false, (short) 1118, " ", false, "N", null, "Verification (R3)", 
				" ", "2000-01-31", "14:44:00", (short) 1520, (short) 0, "2000-01-30", "14:46:00", null, null, " ", " ",
				" ", null, null, "0Hj", "0Hj", "51", false, false, false, false, null, "C", null, null,
				null, "2000-03-03");
		Referral expected = new Referral(referral, "0Hj");
		Referral update = referralDao.update(expected);
		assertThat(expected, is(update));
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbiQCgu0Hl", " ", false, false, "D5YRVOm0Hl",(short) 122, " ", "2000-03-03", (short) 409, null, 
				null, "L3H7sSC0Hl", null, false, false, (short) 1118, " ", false, "N", null, "Verification (R3)", 
				" ", "2000-01-31", "14:44:00", (short) 1520, (short) 0, "2000-01-31", "14:46:00", null, null, " ", " ",
				" ", null, null, "0Hl", "0Hl", "51", false, false, false, false, null, "C", null, null,
				null, "2000-03-03");
		
		Referral expected = new Referral(referral, "0Hl");
		try{
		    referralDao.update(expected);
		    fail("testUpdateEntityNotFoundException() should throw EntityNotFoundException");
		} catch(EntityNotFoundException entityNotFoundException){
			assertThat(entityNotFoundException, IsInstanceOf.instanceOf(EntityNotFoundException.class));
			assertThat(entityNotFoundException.getMessage(), IsNull.nullValue());
		}
	}
}
