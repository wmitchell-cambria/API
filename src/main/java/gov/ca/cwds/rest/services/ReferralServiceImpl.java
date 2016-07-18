package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;

import java.util.HashMap;

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
	
	HashMap<String, Referral> dummyData = new HashMap<String, Referral>();
	
	@Override
	public ReferralSummary findReferralSummary(String id) {
		Referral referral = dummyData.get(id);
		if( referral != null ) {
			return new ReferralSummary(referral.getId(), referral.getReferralName(), referral.getReceivedDate());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.Service#find(java.lang.String)
	 */
	@Override
	public Referral find(String id) {
		return dummyData.get(id);
	}

	@Override
	public Referral delete(String id) {
		return dummyData.remove(id);
	}

	
}
