package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.persistence.legacy.Referral;

/**
 * Implementation of {@link ReferralService} backed by a DAO layer.
 * 
 * @author CDWS API Team
 */
public class ReferralServiceImpl implements ReferralService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralServiceImpl.class);

	private CrudsService<gov.ca.cwds.rest.api.domain.Referral, Referral> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public ReferralServiceImpl(CrudsService<gov.ca.cwds.rest.api.domain.Referral, Referral> crudsService) {
		this.crudsService = crudsService;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.Referral find(Serializable primaryKey) {
		return (gov.ca.cwds.rest.api.domain.Referral) crudsService.find(primaryKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public gov.ca.cwds.rest.api.domain.Referral delete(Serializable id) {
		return (gov.ca.cwds.rest.api.domain.Referral) crudsService.delete(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(gov.ca.cwds.rest.api.domain.Referral object) {
		return crudsService.create(object);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(gov.ca.cwds.rest.api.domain.Referral object) {
		return crudsService.update(object);
	}
}
