package gov.ca.cwds.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;

public class ReferralClientServiceImpl implements ReferralClientService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralClientServiceImpl.class);

	private CrudsService<ReferralClient> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public ReferralClientServiceImpl(CrudsService<ReferralClient> crudsService) {
		this.crudsService = crudsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.lang.String)
	 */
	@Override
	public ReferralClient find(String id) {
		return (ReferralClient) crudsService.find(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public ReferralClient delete(String id) {
		return (ReferralClient) crudsService.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.
	 * persistence.PersistentObject)
	 */
	@Override
	public ReferralClient create(ReferralClient object) {
		return (ReferralClient) crudsService.create(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.
	 * persistence.PersistentObject)
	 */
	@Override
	public ReferralClient update(ReferralClient object) {
		return (ReferralClient) crudsService.update(object);
	}
}
