package gov.ca.cwds.rest.jdbi.legacy;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;
import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient.PrimaryKey;

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



public class ReferralClientDaoIT {
	private SessionFactory sessionFactory;
	private ReferralClientDao referralClientDao;

	@Before
	public void setup() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		sessionFactory.getCurrentSession().beginTransaction();
		referralClientDao = new ReferralClientDao(sessionFactory);
	}

	@After
	public void tearndown() {
		sessionFactory.close();
	}
	
	
	@Test
	public void testFind() {
		gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient = new 
				gov.ca.cwds.rest.api.domain.legacy.ReferralClient(
				"86XV1bG06s", (short) 122, (short) 681, "S", "1998-07-21", false,
				false, "LNuzMKw06s", "AazXkWY06s", "  ", (short) 2, 
				"Y", "43", null, null,null);
		ReferralClient expected = new ReferralClient(referralClient, "06s");
		ReferralClient found = referralClientDao.find(new PrimaryKey("LNuzMKw06s", "AazXkWY06s"));
		assertThat(found, is(equalTo(expected)));
	}
	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient = new 
				gov.ca.cwds.rest.api.domain.legacy.ReferralClient(
				"86XV1bG06k", (short) 122, (short) 681, "S", "1998-07-21", false,
				false, "LNuzMKw06k", "AazXkWY06k", "  ", (short) 2, 
				"Y", "43", null, null,null);
		ReferralClient expected = new ReferralClient(referralClient, "06k");
		ReferralClient create = referralClientDao.create(expected);
		//assert expected.equals(create);
		//allegationDao.delete("Aaeae9r0F6");
		assertThat(expected, is(create));
		
	}
	
	@Test
	public void testCreateExistingEntityExpection() {
		gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient = new 
				gov.ca.cwds.rest.api.domain.legacy.ReferralClient(
				"86XV1bG06s", (short) 122, (short) 681, "S", "1998-07-21", false,
				false, "LNuzMKw06s", "AazXkWY06s", "  ", (short) 2, 
				"Y", "43", null, null,null);
		
		ReferralClient expected = new ReferralClient(referralClient, "06s");
		try{
		     referralClientDao.create(expected);
		} catch(EntityExistsException entityExistsExp){
			assertThat(entityExistsExp, IsInstanceOf.instanceOf(EntityExistsException.class));
			assertThat(entityExistsExp.getMessage(), IsNull.nullValue());
		}
	}
	
	@Test
	public void testDelete() {
	//	String id = "86XV1bG06s";
		gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient = new 
				gov.ca.cwds.rest.api.domain.legacy.ReferralClient(
				"S3123FM0Hj", (short) 122, (short) 680, "I", "2000-02-15", false,
				false, "3YJfEW40Hj", "AbiOD9Y0Hj", "  ", (short) 2, 
				"Y", "51", null, null,null);
		ReferralClient expected = new ReferralClient(referralClient, "OHj");
		ReferralClient delete = referralClientDao.delete(new PrimaryKey("3YJfEW40Hj", "AbiOD9Y0Hj"));
		assertThat(expected, is(delete));
		//assert expected.equals(delete);
	}
	
	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient = new 
				gov.ca.cwds.rest.api.domain.legacy.ReferralClient(
				"86XV1bG06s", (short) 122, (short) 681, "S", "1998-07-22", false,
				false, "LNuzMKw06s", "AazXkWY06s", "  ", (short) 2, 
				"Y", "43", null, null,null);
		ReferralClient expected = new ReferralClient(referralClient, "06s");
		ReferralClient update = referralClientDao.update(expected);
		assertThat(expected, is(update));
		//assert expected.equals(update);
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		gov.ca.cwds.rest.api.domain.legacy.ReferralClient referralClient = new 
				gov.ca.cwds.rest.api.domain.legacy.ReferralClient(
				"86XV1bG06q", (short) 122, (short) 681, "S", "1998-07-21", false,
				false, "LNuzMKw06q", "AazXkWY06q", "  ", (short) 2, 
				"Y", "43", null, null,null);
		
		ReferralClient expected = new ReferralClient(referralClient, "06q");
		try{
		    referralClientDao.update(expected);
		    fail("testUpdateEntityNotFoundException() should throw EntityNotFoundException");
		} catch(EntityNotFoundException entityNotFoundException){
			assertThat(entityNotFoundException, IsInstanceOf.instanceOf(EntityNotFoundException.class));
			assertThat(entityNotFoundException.getMessage(), IsNull.nullValue());
		}
	}


}
