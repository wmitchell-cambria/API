package gov.ca.cwds.rest.services.legacy;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Implementation of {@link StaffPersonService} backed by a DAO layer.
 * 
 * @author CDWS API Team
 */
public class StaffPersonServiceImpl implements StaffPersonService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StaffPersonServiceImpl.class);

	private CrudsService<gov.ca.cwds.rest.api.domain.legacy.StaffPerson, StaffPerson> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public StaffPersonServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.legacy.StaffPerson, StaffPerson> crudsService) {
		this.crudsService = crudsService;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.legacy.StaffPerson find(Serializable primaryKey) {
		return (gov.ca.cwds.rest.api.domain.legacy.StaffPerson) crudsService.find(primaryKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.legacy.StaffPerson delete(Serializable id) {
		return (gov.ca.cwds.rest.api.domain.legacy.StaffPerson) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(gov.ca.cwds.rest.api.domain.legacy.StaffPerson object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.legacy.StaffPerson object) {
		return crudsService.update(object);
	}
}