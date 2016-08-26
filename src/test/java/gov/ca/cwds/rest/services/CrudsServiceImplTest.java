package gov.ca.cwds.rest.services;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.rest.api.domain.StaffPerson;
import gov.ca.cwds.rest.jdbi.CrudsDao;

public class CrudsServiceImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private CrudsServiceImpl<StaffPerson, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson> crudsServiceImpl;
	private CrudsDao<gov.ca.cwds.rest.api.persistence.legacy.StaffPerson> crudsDao;
	
	
	private gov.ca.cwds.rest.api.domain.StaffPerson nonExistentStaffPersonToUpdate = new gov.ca.cwds.rest.api.domain.StaffPerson("notexists","1973-11-22",null,null,null,null,null,null,new Integer(33),"1973-11-22",null,null,null,null,null,null,null,null,null);
	private gov.ca.cwds.rest.api.persistence.legacy.StaffPerson nonExistingPersistentStaffPerson = new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(nonExistentStaffPersonToUpdate, "ABC");
	
	private gov.ca.cwds.rest.api.domain.StaffPerson existingStaffPersonToCreate= new gov.ca.cwds.rest.api.domain.StaffPerson("exists","1973-11-22",null,null,null,null,null,null,new Integer(33),"1973-11-22",null,null,null,null,null,null,null,null,null);
	private gov.ca.cwds.rest.api.persistence.legacy.StaffPerson existingPersistentStaffPerson = new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(existingStaffPersonToCreate, "ABC");
	 
	private gov.ca.cwds.rest.api.domain.StaffPerson toCreate = new gov.ca.cwds.rest.api.domain.StaffPerson("create","1973-11-22",null,null,null,null,null,null,new Integer(33),"1973-11-22",null,null,null,null,null,null,null,null,null);
	private gov.ca.cwds.rest.api.persistence.legacy.StaffPerson toCreatePersistent = new gov.ca.cwds.rest.api.persistence.legacy.StaffPerson(toCreate, "ABC");

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsDao = mock(CrudsDao.class);
		when(crudsDao.create(existingPersistentStaffPerson)).thenThrow(new EntityExistsException());
		when(crudsDao.update(nonExistingPersistentStaffPerson)).thenThrow(new EntityNotFoundException());
		when(crudsDao.create(eq(toCreatePersistent))).thenReturn(toCreatePersistent);
		when(crudsDao.find("1")).thenReturn(toCreatePersistent);
		when(crudsDao.find("null")).thenReturn(null);
		when(crudsDao.delete("1")).thenReturn(toCreatePersistent);
		crudsServiceImpl = new CrudsServiceImpl<StaffPerson, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson>(crudsDao, StaffPerson.class, gov.ca.cwds.rest.api.persistence.legacy.StaffPerson.class);
	}

	@Test
	public void findDelegatesToDao() {
		crudsServiceImpl.find("1");
		verify(crudsDao, times(1)).find("1");

	}
	
	@Test
	public void findThrowsEntityNotFoundWhenNotFound() {
        thrown.expect(ServiceException.class);
        thrown.expectCause(Is.isA(EntityNotFoundException.class));
		crudsServiceImpl.find("null");
	}

	@Test
	public void deleteDelegatesToDao() {
		crudsServiceImpl.delete("1");
		verify(crudsDao, times(1)).delete("1");
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
