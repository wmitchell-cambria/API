package gov.ca.cwds.rest.jdbi.legacy;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNull;

import java.math.BigDecimal;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
//import org.hibernate.mapping.PrimaryKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;
import gov.ca.cwds.rest.api.persistence.legacy.CrossReport.PrimaryKey;

public class CrossReportDaoIT {
	private SessionFactory sessionFactory;
	private CrossReportDao crossreportDao;
	
	@Before
	public void setup() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		sessionFactory.getCurrentSession().beginTransaction();
		crossreportDao = new CrossReportDao(sessionFactory);
	}

	@After
	public void tearndown() {
		sessionFactory.close();
	}


	@Test
	public void testFind() {
		gov.ca.cwds.rest.api.domain.legacy.CrossReport crossreport = new gov.ca.cwds.rest.api.domain.legacy.CrossReport(
				"7wviAIk0AB", (short)2094, false, false, "15:19:00",
				"  ", 0, BigDecimal.valueOf(0L), "1998-12-07"," ", " ","925q4As0AB", 
				null, "0AB", " ", " ", " ", "37",
				false, false, false);
		CrossReport expected = new CrossReport (crossreport, "0AB");
		CrossReport found = crossreportDao.find(new PrimaryKey("925q4As0AB", "7wviAIk0AB"));
		assertThat(found, is(equalTo(expected)));
	}

	@Test
	public void testCreate() {
		gov.ca.cwds.rest.api.domain.legacy.CrossReport crossreport = new gov.ca.cwds.rest.api.domain.legacy.CrossReport(
				"7wviAIk0AC", (short)2094, false, false, "15:19:00",
				"  ", 0, BigDecimal.ZERO, "1998-12-07"," ", " ", "925q4As0AC",
				 null, "0AC", " ", " ", " ", "34",
				false, false,false);
		CrossReport expected = new CrossReport (crossreport, "0AC");
		CrossReport create = crossreportDao.create(expected);
		assertThat(expected, is(create));
		
	}
	
	@Test
	public void testCreateExistingEntityExpection() {
		gov.ca.cwds.rest.api.domain.legacy.CrossReport crossreport = new gov.ca.cwds.rest.api.domain.legacy.CrossReport(
				"CVDUfmj0WS", (short)2096, false, false, null,
				"  ", 0, BigDecimal.ZERO, "2012-01-01"," ", " ", "Aj20cK10WS", 
				"OHCMA5M00E", "0WS", " ", " ", " ", "22",
				true, false,false);
		
		CrossReport expected = new CrossReport (crossreport, "0WS");
		
		try {
			crossreportDao.create(expected);
		} catch (EntityExistsException entityExistsExp) {
			assertThat(entityExistsExp, IsInstanceOf.instanceOf(EntityExistsException.class));
			assertThat(entityExistsExp.getMessage(), IsNull.nullValue());
		};
	}
	
	@Test
	public void testDelete() {
		String thirdId = "FsMh09h00J";
		String referralId = "AAKhYyf00J";
		gov.ca.cwds.rest.api.domain.legacy.CrossReport crossreport = new gov.ca.cwds.rest.api.domain.legacy.CrossReport(
				"FsMh09h00J", (short)2094,  false, true, null,
				"  ", 0, BigDecimal.ZERO, "1997-09-17"," ", " ", "AAKhYyf00J", 
				"FDIp2i900E", "00J", " ", " ", " ", "34",
				true, false,false);
		CrossReport expected = new CrossReport (crossreport, "00J");
		CrossReport.PrimaryKey primaryKey = new CrossReport.PrimaryKey(referralId, thirdId);
		CrossReport delete = crossreportDao.delete(primaryKey);
		assertThat(expected, is(delete));
	}
	
	@Test
	public void testUpdate() {
		gov.ca.cwds.rest.api.domain.legacy.CrossReport crossreport = new gov.ca.cwds.rest.api.domain.legacy.CrossReport(
				"AOUJ46O0FT", (short)2094,  false, true, null,
				"  ", 0, BigDecimal.ZERO, "1998-08-03"," ", " ", "ABY8eW40FT", 
				"OeIxMeI00E", "0FT", " ", " ", " ", "13",
				true, false,false);
		CrossReport expected = new CrossReport (crossreport, "0FT");
		CrossReport update = crossreportDao.update(expected);
		assertThat(expected, is(update));
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		gov.ca.cwds.rest.api.domain.legacy.CrossReport crossreport = new gov.ca.cwds.rest.api.domain.legacy.CrossReport(
				"AOUJ46O00J", (short)2094,  false, true, null,
				"  ", 0, BigDecimal.ZERO, "1998-08-03"," ", " ",
				"ABY8eW400J", "OeIxMeI00E", "00J", " ", " ", " ", "13",
				true, false,false);
		CrossReport expected = new CrossReport (crossreport, "00J");
		try {
			crossreportDao.update(expected);
			fail("testUpdateEntityNotFoundException() should throw EntityNotFoundException");
		} catch (EntityNotFoundException entityNotFoundException) {
			assertThat(entityNotFoundException, IsInstanceOf.instanceOf(EntityNotFoundException.class));
			assertThat(entityNotFoundException.getMessage(), IsNull.nullValue());
		}
		
	}

}
