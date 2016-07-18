package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;

import java.util.Date;

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

	@Override
	public ReferralSummary findReferralSummary(String id) {
		if( "1".equals(id) ) {
			return null;
		} else {
			return new ReferralSummary(id, "Test Referral Name", new Date());
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.Service#find(java.lang.String)
	 */
	@Override
	public Referral find(String id) {
		if( "1".equals(id) ) {
			return null;
		} else {
			return new Referral(id, "Test Referral Name", new Date());
		}
	}

	
}
