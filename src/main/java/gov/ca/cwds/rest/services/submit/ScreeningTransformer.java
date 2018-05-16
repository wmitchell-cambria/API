package gov.ca.cwds.rest.services.submit;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Business layer object to transform an NS {@link Screening} to a {@link ScreeningToReferral}
 * 
 * @author CWDS API Team
 */
public class ScreeningTransformer {

  public ScreeningToReferral transform(Screening screening, String loggedInStaffId,
      String loggedInStaffCounty, Map<String, IntakeLov> nsLovMap,
      Map<String, IntakeLov> cmsSysIdToNsLovMap) {
    screening.setAssigneeStaffId(loggedInStaffId);
    return createScreeningToReferralWithDefaults(screening, loggedInStaffCounty, nsLovMap,
        cmsSysIdToNsLovMap);
  }

  private ScreeningToReferral createScreeningToReferralWithDefaults(Screening screening,
      String loggedInStaffCounty, Map<String, IntakeLov> nsCodeToNsLovMap,
      Map<String, IntakeLov> cmsSysIdToNsLovMap) {
    Set<Allegation> allegations =
        new AllegationsTransformer().transform(screening.getAllegations(), nsCodeToNsLovMap);
    Long id = (long) Integer.parseInt(screening.getId());
    String legacySourceTable = "";
    String referralId = screening.getReferralId();
    String endedAt =
        DomainChef.cookISO8601Timestamp(DomainChef.uncookDateString(screening.getEndedAt()));
    String incidentCounty = screening.getIncidentCounty();
    String incidentDate = screening.getIncidentDate();
    String locationType = screening.getLocationType();
    String communicationMethod = screening.getCommunicationMethod();
    Short communicationMethodSysId = StringUtils.isNotBlank(communicationMethod)
        ? nsCodeToNsLovMap.get(communicationMethod).getLegacySystemCodeId().shortValue()
        : null;
    String currentLocationOfChildren = null; // do not see in intake-api
    String name = screening.getName();
    String reportNarrative = screening.getReportNarrative();
    String reference = screening.getReference();
    String responseTime = screening.getScreeningDecisionDetail();
    Short responseTimeSysId = StringUtils.isNotBlank(responseTime)
        ? nsCodeToNsLovMap.get(responseTime).getLegacySystemCodeId().shortValue()
        : null;
    String startedAt =
        DomainChef.cookISO8601Timestamp(DomainChef.uncookDateString(screening.getStartedAt()));
    String assignee = screening.getAssignee();
    String assigneeStaffId = screening.getAssigneeStaffId();
    String additionalInformation = screening.getAdditionalInformation();
    String screeningDecision = screening.getScreeningDecision();
    String screeningDecisionDetail = screening.getScreeningDecisionDetail();
    Short approvalStatus =
        SystemCodeCache.global().getSystemCodeId("Request Not Submitted", "APV_STC");
    // this is defaulted in intake-api,see referral_representer
    Boolean familyAwareness = false; // this is defaulted in intake-api, see referral_representer
    Boolean filedWithLawEnforcement = false;
    // this is defaulted in intake-api, see referral_representer
    String responsibleAgency = "C"; // this is defaulted in intake-api, see referral_representer
    String limitedAccessCode = setLimitedAccessCode(screening.getAccessRestrictions());
    String limitedAccessDescription = screening.getRestrictionsRationale();
    String limitedAccessAgency = loggedInStaffCounty;
    Date limitedAccessDate = null;// dateToday
    Address address = (screening.getIncidentAddress() != null)
        ? new AddressTransformer().transform(screening.getIncidentAddress(), nsCodeToNsLovMap)
        : null;
    Set<Participant> participants =
        (screening.getParticipantIntakeApis() != null) ? new ParticipantsTransformer()
            .transform(screening.getParticipantIntakeApis(), nsCodeToNsLovMap) : null;

    Set<CrossReport> crossReports =
        (screening.getCrossReports() != null) ? new CrossReportsTransformer()
            .transform(screening.getCrossReports(), nsCodeToNsLovMap, cmsSysIdToNsLovMap) : null;

    Set<String> alerts = new HashSet<>(); // Need to map this field
    String alertInformation = null; // Need to map this field
    return new ScreeningToReferral(id, legacySourceTable, referralId, endedAt, incidentCounty,
        incidentDate, locationType, communicationMethodSysId, currentLocationOfChildren, name,
        reportNarrative, reference, responseTimeSysId, startedAt, assignee, assigneeStaffId,
        additionalInformation, screeningDecision, screeningDecisionDetail, approvalStatus,
        familyAwareness, filedWithLawEnforcement, responsibleAgency, limitedAccessCode,
        limitedAccessDescription, limitedAccessAgency, limitedAccessDate, alerts, alertInformation,
        address, participants, crossReports, allegations);
  }


  private String setLimitedAccessCode(String accessRestrictions) {
    if (accessRestrictions != null) {
      if (accessRestrictions.equalsIgnoreCase("sensitive")) {
        return "S";
      } else if (accessRestrictions.equalsIgnoreCase("sealed")) {
        return "R";
      }
    }
    return "N";
  }

}
