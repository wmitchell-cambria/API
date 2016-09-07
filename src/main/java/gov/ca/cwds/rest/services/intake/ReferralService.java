package gov.ca.cwds.rest.services.intake;

import java.io.Serializable;
import java.util.Map;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import gov.ca.cwds.rest.services.Service;

public interface ReferralService extends Service {
	
	public Map<String, Serializable> create(IntakeReferral intakeReferral);

}
