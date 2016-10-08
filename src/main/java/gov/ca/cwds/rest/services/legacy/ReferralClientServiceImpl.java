package gov.ca.cwds.rest.services.legacy;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.ReferralClient;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.util.ServiceUtils;

@Deprecated
public class ReferralClientServiceImpl implements ReferralClientService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralClientServiceImpl.class);
	
	private static final String KEY_REFERRAL_ID = "referralId";
	private static final String KEY_CLIENT_ID = "clientId";

	private CrudsService<gov.ca.cwds.rest.api.domain.legacy.ReferralClient, ReferralClient> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public ReferralClientServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.legacy.ReferralClient, ReferralClient> crudsService) {
		this.crudsService = crudsService;
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.legacy.ReferralClient find(Serializable primaryKey) {
		ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
		return (gov.ca.cwds.rest.api.domain.legacy.ReferralClient) crudsService.find(primaryKeyObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.legacy.ReferralClient delete(Serializable primaryKey) {
		ReferralClient.PrimaryKey primaryKeyObject = extractPrimaryKey(primaryKey);
		return (gov.ca.cwds.rest.api.domain.legacy.ReferralClient) crudsService.delete(primaryKeyObject);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(gov.ca.cwds.rest.api.domain.legacy.ReferralClient object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.legacy.ReferralClient object) {
		return crudsService.update(object);
	}
	
	private ReferralClient.PrimaryKey extractPrimaryKey(Serializable primaryKey) {
		Map<String, String> nameValuePairs = ServiceUtils.extractKeyValuePairs(primaryKey);
		String referralId = nameValuePairs.get(KEY_REFERRAL_ID);
		String clientId = nameValuePairs.get(KEY_CLIENT_ID);
		ReferralClient.PrimaryKey primaryKeyObject = new ReferralClient.PrimaryKey(referralId, clientId);
		return primaryKeyObject;
	}

}
