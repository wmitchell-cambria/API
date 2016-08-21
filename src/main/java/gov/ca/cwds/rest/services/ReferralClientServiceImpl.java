package gov.ca.cwds.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;

public class ReferralClientServiceImpl implements ReferralClientService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralClientServiceImpl.class);

	private CrudsService<gov.ca.cwds.rest.api.domain.ReferralClient, ReferralClient> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public ReferralClientServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.ReferralClient, ReferralClient> crudsService) {
		this.crudsService = crudsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.ReferralClient find(String id) {
		return (gov.ca.cwds.rest.api.domain.ReferralClient) crudsService.find(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.ReferralClient delete(String id) {
		return (gov.ca.cwds.rest.api.domain.ReferralClient) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String create(gov.ca.cwds.rest.api.domain.ReferralClient object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.ReferralClient object) {
		return crudsService.update(object);
	}
}
