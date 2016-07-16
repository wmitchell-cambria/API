package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.ReferralSummary;

import java.util.Date;

public class ReferralServiceImpl implements ReferralService {

	@Override
	public ReferralSummary findReferralSummary(String id) {
		return new ReferralSummary(id, "Test Referral Name", new Date());
	}

}
