package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Reporter;

public class ReporterServiceImpl implements ReporterService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReporterServiceImpl.class);

	private CrudsService<gov.ca.cwds.rest.api.domain.Reporter, Reporter> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public ReporterServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.Reporter, Reporter> crudsService) {
		this.crudsService = crudsService;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.Reporter find(Serializable primaryKey) {
		return (gov.ca.cwds.rest.api.domain.Reporter) crudsService.find(primaryKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.Reporter delete(String id) {
		return (gov.ca.cwds.rest.api.domain.Reporter) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String create(gov.ca.cwds.rest.api.domain.Reporter object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.Reporter object) {
		return crudsService.update(object);
	}
}