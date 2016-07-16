package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.ReferralSummary;

import java.util.Date;

public class ReferralResourceImpl implements ReferralResource {

	@Override
	public ReferralSummary getReferralSummary(String id) {
		return new ReferralSummary("someid", "Test Referral Name", new Date());
	}

}
