package gov.ca.cwds.rest.services.intake;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.intake.IntakeReferral;
import gov.ca.cwds.rest.services.legacy.AllegationService;
import gov.ca.cwds.rest.services.legacy.CrossReportService;
import gov.ca.cwds.rest.services.legacy.ReferralClientService;
import gov.ca.cwds.rest.services.legacy.ReferralService;
import gov.ca.cwds.rest.services.legacy.ReporterService;

public class ReferralServiceImpl implements gov.ca.cwds.rest.services.intake.ReferralService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ReferralServiceImpl.class);
	
	private ReferralService referralService;
	private AllegationService allegationService;
	private CrossReportService crossReportService;
	private ReferralClientService referralClientService;
	private ReporterService reporterService;
	
	public ReferralServiceImpl(ReferralService referralService, AllegationService allegationService,
			CrossReportService crossReportService, ReferralClientService referralClientService,
			ReporterService reporterService) {
		super();
		this.referralService = referralService;
		this.allegationService = allegationService;
		this.crossReportService = crossReportService;
		this.referralClientService = referralClientService;
		this.reporterService = reporterService;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.intake.ReferralService#create(gov.ca.cwds.rest.api.domain.intake.IntakeReferral)
	 */
	@Override
	public Map<String, Serializable>  create(IntakeReferral intakeReferral) {
		Map<String, Serializable> objects = new HashMap<>();
		
		objects.put("referral", (Serializable)this.referralService.create(intakeReferral.getReferral()));
		objects.put("allegation", (Serializable)this.allegationService.create(intakeReferral.getAllegation()));
		objects.put("crossReport", (Serializable)this.crossReportService.create(intakeReferral.getCrossReport()));
		objects.put("referralClient", (Serializable)this.referralClientService.create(intakeReferral.getReferralClient()));
		objects.put("reporter", (Serializable)this.reporterService.create(intakeReferral.getReporter()));
		return objects;
	}


}
