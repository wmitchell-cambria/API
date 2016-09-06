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
		assertThat(found, is(equalTo(expected)));
		//assert found.equals(expected);
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
		//assert expected.equals(create);
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
		String id = "AbysgeR0Ea";
		gov.ca.cwds.rest.api.domain.legacy.Referral referral = new gov.ca.cwds.rest.api.domain.legacy.Referral(
				"AbysgeR0Ea", " ", true, false, "RR6Suhv0Ea",(short) 122, " ", "1999-07-19", (short) 5375, null, 
				null, "19gPWOR0Ea", null, false, false, (short) 1080, " ", false, "N", null, "The No Name Referral", 
				" ", "1999-07-16", "14:38:00", (short) 1519, (short) 1526, "1999-07-16", "14:40:00", "6k11P0t0Ea", null, " ", " ",
				" ", null, null, "0Ea", "0Ec", "13", false, false, false, false, null, "C", null, null,
				null, "1999-07-19");
		Referral expected = new Referral(referral, "0EU");
		Referral delete = referralDao.delete(id);
		//assert expected.equals(delete);
		assertThat(expected, is(delete));
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
		//assert expected.equals(update);
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
