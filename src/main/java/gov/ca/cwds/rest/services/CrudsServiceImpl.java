package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.persistence.CrudsDao;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of {@link CrudsService} delegating work to a {@link CrudsDao}
 * 
 * @author CWDS API Team
 *
 * @param <T>
 */
public class CrudsServiceImpl<T extends PersistentObject> implements CrudsService<T> {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CrudsServiceImpl.class);
	
	private CrudsDao<T> crudsDao;
	
	public CrudsServiceImpl(CrudsDao<T> crudsDao) {
		this.crudsDao = crudsDao;
	}

	@Override
	public T find(String id) {
		return crudsDao.find(id);
	}

	@Override
	public T delete(String id) {
		return crudsDao.delete(id);
	}

	@Override
	public T create(T object) {
		try {
			return crudsDao.create(object);
		} catch (EntityExistsException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public T update(T object) {
		try {
			return crudsDao.update(object);
		} catch (EntityNotFoundException e) {
			throw new ServiceException(e);
		}
	}

}
