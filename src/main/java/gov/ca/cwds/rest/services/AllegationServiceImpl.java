package gov.ca.cwds.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Allegation;

/**
 * Implementation of {@link AllegationService} backed by a DAO layer.
 * 
 * @author CDWS API Team
 */
public class AllegationServiceImpl implements AllegationService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AllegationServiceImpl.class);

	private CrudsService<gov.ca.cwds.rest.api.domain.Allegation, Allegation> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public AllegationServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.Allegation, Allegation> crudsService) {
		this.crudsService = crudsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.Allegation find(String id) {
		return (gov.ca.cwds.rest.api.domain.Allegation) crudsService.find(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.Allegation delete(String id) {
		return (gov.ca.cwds.rest.api.domain.Allegation) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String create(gov.ca.cwds.rest.api.domain.Allegation object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.Allegation object) {
		return crudsService.update(object);
	}


}