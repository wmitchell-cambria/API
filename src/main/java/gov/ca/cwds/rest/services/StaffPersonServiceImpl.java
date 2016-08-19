package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link StaffPersonService} backed by a DAO layer.
 * 
 * @author CDWS API Team
 */
public class StaffPersonServiceImpl implements StaffPersonService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StaffPersonServiceImpl.class);

	private CrudsService<StaffPerson> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public StaffPersonServiceImpl(CrudsService<StaffPerson> crudsService) {
		this.crudsService = crudsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.lang.String)
	 */
	@Override
	public StaffPerson find(String id) {
		return (StaffPerson) crudsService.find(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public StaffPerson delete(String id) {
		return (StaffPerson) crudsService.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.
	 * persistence.PersistentObject)
	 */
	@Override
	public StaffPerson create(StaffPerson object) {
		return (StaffPerson) crudsService.create(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.
	 * persistence.PersistentObject)
	 */
	@Override
	public StaffPerson update(StaffPerson object) {
		return (StaffPerson) crudsService.update(object);
	}
}