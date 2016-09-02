package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.CrossReport;
import gov.ca.cwds.rest.util.ServiceUtils;

public class CrossReportServiceImpl implements CrossReportService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrossReportServiceImpl.class);
	
	private static final String KEY_REFERRAL_ID = "referralId";
	private static final String KEY_THIRD_ID = "thirdId";

	private CrudsService<gov.ca.cwds.rest.api.domain.legacy.CrossReport, CrossReport> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public CrossReportServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.legacy.CrossReport, CrossReport> crudsService) {
		this.crudsService = crudsService;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.legacy.CrossReport find(Serializable primaryKey) {
		CrossReport.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
		return (gov.ca.cwds.rest.api.domain.legacy.CrossReport) crudsService.find(primaryKeyObject);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.legacy.CrossReport delete(Serializable primaryKey) {
		CrossReport.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
		return (gov.ca.cwds.rest.api.domain.legacy.CrossReport) crudsService.delete(primaryKeyObject);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(gov.ca.cwds.rest.api.domain.legacy.CrossReport object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.legacy.CrossReport object) {
		return crudsService.update(object);
	}
	
	
	private CrossReport.PrimaryKey extractPrimaryKey(Serializable primaryKey) {
		Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
		String referralId = nameValuePairs.get(KEY_REFERRAL_ID);
		String thirdId = nameValuePairs.get(KEY_THIRD_ID);
		CrossReport.PrimaryKey primaryKeyObject = new CrossReport.PrimaryKey(referralId, thirdId);
		return primaryKeyObject;
	}


}
