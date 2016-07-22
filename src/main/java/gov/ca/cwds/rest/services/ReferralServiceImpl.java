package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ReferralService} backed by a DAO layer.
 * 
 * @author CDWS API Team
 */
public class ReferralServiceImpl implements ReferralService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralServiceImpl.class);

	private CrudsService<Referral> crudsService;

	/**
	 * Constructor
	 * 
	 * @param crudsService
	 *            The {@link CrudsService} used by this service
	 */
	public ReferralServiceImpl(CrudsService<Referral> crudsService) {
		this.crudsService = crudsService;
	}

	@Override
	public ReferralSummary findReferralSummary(String id) {
		Referral referral = this.find(id);
		if (referral != null) {
			return new ReferralSummary(referral.getId(),
					referral.getReferralName(), referral.getReceivedDate());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.lang.String)
	 */
	@Override
	public Referral find(String id) {
		return (Referral) crudsService.find(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.lang.String)
	 */
	@Override
	public Referral delete(String id) {
		return (Referral) crudsService.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.
	 * persistence.PersistentObject)
	 */
	@Override
	public Referral create(Referral object) {
		return (Referral) crudsService.create(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.
	 * persistence.PersistentObject)
	 */
	@Override
	public Referral update(Referral object) {
		return (Referral) crudsService.update(object);
	}

}
