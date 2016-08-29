package gov.ca.cwds.rest.services;

import java.io.Serializable;

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
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.ReferralClient find(Serializable primaryKey) {
		//TODO : extract this stuff to common area
		if( !(primaryKey instanceof String) ) {
			throw new ServiceException("Unable to read primarykey as string");
		}
		String primaryKeyString = (String)primaryKey;
		String referralId = null;
		String clientId = null;
		
		for( String keyValueString: primaryKeyString.split(",")) {
			String[] keyValuePair = keyValueString.split("=");
			String key = keyValuePair[0];
			if( "referralId".equals(key) ) {
				referralId = keyValuePair[1];
			} else if( "clientId".equals(key) ) { 
				clientId = keyValuePair[1];
			}
		}
		ReferralClient.PrimaryKey primaryKeyObject = new ReferralClient.PrimaryKey(referralId, clientId);
		return (gov.ca.cwds.rest.api.domain.ReferralClient) crudsService.find(primaryKeyObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.ReferralClient delete(Serializable primaryKey) {
		//TODO : extract this stuff to common area
		if( !(primaryKey instanceof String) ) {
			throw new ServiceException("Unable to read primarykey as string");
		}
		String primaryKeyString = (String)primaryKey;
		String referralId = null;
		String clientId = null;
		
		for( String keyValueString: primaryKeyString.split(",")) {
			String[] keyValuePair = keyValueString.split("=");
			String key = keyValuePair[0];
			if( "referralId".equals(key) ) {
				referralId = keyValuePair[1];
			} else if( "clientId".equals(key) ) { 
				clientId = keyValuePair[1];
			}
		}
		ReferralClient.PrimaryKey primaryKeyObject = new ReferralClient.PrimaryKey(referralId, clientId);
		return (gov.ca.cwds.rest.api.domain.ReferralClient) crudsService.delete(primaryKeyObject);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(gov.ca.cwds.rest.api.domain.ReferralClient object) {
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
