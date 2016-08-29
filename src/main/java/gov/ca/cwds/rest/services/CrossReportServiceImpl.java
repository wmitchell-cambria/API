package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;

public class CrossReportServiceImpl implements CrossReportService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrossReportServiceImpl.class);

	private CrudsService<gov.ca.cwds.rest.api.domain.CrossReport, CrossReport> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public CrossReportServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.CrossReport, CrossReport> crudsService) {
		this.crudsService = crudsService;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.CrossReport find(Serializable primaryKey) {
		return (gov.ca.cwds.rest.api.domain.CrossReport) crudsService.find(primaryKey);
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.CrossReport delete(Serializable id) {
		return (gov.ca.cwds.rest.api.domain.CrossReport) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(gov.ca.cwds.rest.api.domain.CrossReport object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.CrossReport object) {
		return crudsService.update(object);
	}


}
