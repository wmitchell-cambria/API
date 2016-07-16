package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;

/**
 * This business service provides transactional methods for manipulating
 * {@link Referral}
 * 
 * @author CWDS API Team
 */
public interface ReferralService {

	/**
	 * Finds a {@link ReferralSummary} based on the given id.
	 * 
	 * @param id
	 *            The id of the {@link Referral} to be summarized
	 * 
	 * @return The {@link ReferralSummary} if found, null if not found.
	 */
	public ReferralSummary findReferralSummary(String id);

}
