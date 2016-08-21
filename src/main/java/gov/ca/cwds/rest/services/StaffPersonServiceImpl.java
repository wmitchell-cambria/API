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

	private CrudsService<gov.ca.cwds.rest.api.domain.StaffPerson, StaffPerson> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public StaffPersonServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.StaffPerson, StaffPerson> crudsService) {
		this.crudsService = crudsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.StaffPerson find(String id) {
		return (gov.ca.cwds.rest.api.domain.StaffPerson) crudsService.find(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.StaffPerson delete(String id) {
		return (gov.ca.cwds.rest.api.domain.StaffPerson) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String create(gov.ca.cwds.rest.api.domain.StaffPerson object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.StaffPerson object) {
		return crudsService.update(object);
	}
}