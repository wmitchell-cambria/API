package gov.ca.cwds.rest.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.persistence.CrudsDao;
import gov.ca.cwds.rest.api.persistence.Referral;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CrudsServiceImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private CrudsServiceImpl<Referral> crudsServiceImpl;
	private CrudsDao<Referral> crudsDao;
	
	private Referral nonExistentReferralToUpdate = new Referral("notexists","name", new Date());
	private Referral existintReferralToCreate = new Referral("exists","name", new Date());

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		crudsDao = mock(CrudsDao.class);
		when(crudsDao.create(existintReferralToCreate)).thenThrow(new EntityExistsException());
		when(crudsDao.update(nonExistentReferralToUpdate)).thenThrow(new EntityNotFoundException());
		crudsServiceImpl = new CrudsServiceImpl<>(crudsDao);
	}

	@Test
	public void findDelegatesToDao() {
		crudsServiceImpl.find("1");
		verify(crudsDao, times(1)).find("1");

	}

	@Test
	public void deleteDelegatesToDao() {
		crudsServiceImpl.delete("1");
		verify(crudsDao, times(1)).delete("1");
	}

	@Test
	public void createDelegatesToDao() {
		Referral toCreate = new Referral("1", "name", new Date());
		crudsServiceImpl.create(toCreate);
		verify(crudsDao, times(1)).create(toCreate);
	}

	@Test
	public void createThrowsServiceExceptionWhenExists() {
		Referral toCreate = new Referral("1", "name", new Date());
		crudsServiceImpl.create(toCreate);
		verify(crudsDao, times(1)).create(toCreate);
	}

	
	@Test
	public void updateDelegatesToDao() {
		thrown.expect(ServiceException.class);
		crudsServiceImpl.create(existintReferralToCreate);
	}

	@Test
	public void updateThrowsServiceExceptionWhenNotFound() {
		thrown.expect(ServiceException.class);
		crudsServiceImpl.update(nonExistentReferralToUpdate);
	}
}
