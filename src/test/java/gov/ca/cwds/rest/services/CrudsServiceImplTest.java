package gov.ca.cwds.rest.services;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.legacy.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import io.dropwizard.jackson.Jackson;

public class CrudsServiceImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	private CrudsServiceImpl<StaffPerson, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson> crudsServiceImpl;
	private CrudsDao<gov.ca.cwds.rest.api.persistence.legacy.StaffPerson> crudsDao;
	
	
	private gov.ca.cwds.rest.api.domain.legacy.StaffPerson nonExistentStaffPersonToUpdate = new gov.ca.cwds.rest.api.domain.legacy.StaffPerson("notexists","1973-11-22",null,null,null,null,null,null,new Integer(33),"1973-11-22",null,null,null,null,null,null,null,null,null);
	private gov.ca.cwds.rest.api.persistence.legacy.StaffPerson nonExistingPersistentStaffPerson = new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(nonExistentStaffPersonToUpdate, "ABC");
	
	private gov.ca.cwds.rest.api.domain.legacy.StaffPerson existingStaffPersonToCreate= new gov.ca.cwds.rest.api.domain.legacy.StaffPerson("exists","1973-11-22",null,null,null,null,null,null,new Integer(33),"1973-11-22",null,null,null,null,null,null,null,null,null);
	private gov.ca.cwds.rest.api.persistence.legacy.StaffPerson existingPersistentStaffPerson = new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(existingStaffPersonToCreate, "ABC");
	 
	private gov.ca.cwds.rest.api.domain.legacy.StaffPerson toCreate;
	private gov.ca.cwds.rest.api.persistence.legacy.StaffPerson toCreatePersistent;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		crudsDao = mock(CrudsDao.class);
		toCreate = MAPPER.readValue(fixture("fixtures/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
		toCreatePersistent = new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(toCreate, "ABC");
		
		when(crudsDao.create(existingPersistentStaffPerson)).thenThrow(new EntityExistsException());
		when(crudsDao.update(nonExistingPersistentStaffPerson)).thenThrow(new EntityNotFoundException());
		when(crudsDao.create(eq(toCreatePersistent))).thenReturn(toCreatePersistent);
		when(crudsDao.find("1")).thenReturn(toCreatePersistent);
		when(crudsDao.find("null")).thenThrow(new EntityNotFoundException());
		when(crudsDao.delete("1")).thenReturn(toCreatePersistent);
		crudsServiceImpl = new CrudsServiceImpl<StaffPerson, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson>(crudsDao, StaffPerson.class, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson.class);
	}

	@Test
	public void findDelegatesToDao() throws Exception {
		crudsServiceImpl.find("1");
		verify(crudsDao, times(1)).find("1");

	}
	
	public void findReturnsNullWhenNotFound() {
		crudsServiceImpl.find("null");
		assertThat(crudsServiceImpl.find("null"), is(nullValue()));
	}

	@Test
	public void deleteDelegatesToDao() {
		crudsServiceImpl.delete("1");
		verify(crudsDao, times(1)).delete("1");
	}

	@Test
	public void deleteReturnsNullWhenNotFound() {
		crudsServiceImpl.delete("null");
		assertThat(crudsServiceImpl.delete("null"), is(nullValue()));
	}
	
	@Test
	public void createDelegatesToDao() {
		crudsServiceImpl.create(toCreate);
		verify(crudsDao, times(1)).create(toCreatePersistent);
	}

	@Test
	public void createThrowsServiceExceptionWhenExists() {
		thrown.expect(ServiceException.class);
		crudsServiceImpl.create(existingStaffPersonToCreate);
	}

	
	@Test
	public void updateDelegatesToDao() {
		crudsServiceImpl.create(toCreate);
		verify(crudsDao, times(1)).create(toCreatePersistent);
	}

	@Test
	public void updateThrowsServiceExceptionWhenNotFound() {
		thrown.expect(ServiceException.class);
		crudsServiceImpl.update(nonExistentStaffPersonToUpdate);
	}
}
