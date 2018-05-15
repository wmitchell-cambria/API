package gov.ca.cwds.rest.services.submit;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import gov.ca.cwds.ObjectMapperUtils;
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
    ScreeningToReferral screeningToReferral = createScreeningToReferralWithDefaults(screening,
        loggedInStaffCounty, nsLovMap, cmsSysIdToNsLovMap);
    printScreeningToReferral(screeningToReferral);
    return screeningToReferral;
  }

  private ScreeningToReferral createScreeningToReferralWithDefaults(Screening s,
      String loggedInStaffCounty, Map<String, IntakeLov> nsCodeToNsLovMap,
      Map<String, IntakeLov> cmsSysIdToNsLovMap) {
    Set<Allegation> allegations =
        new AllegationsTransformer().transform(s.getAllegations(), nsCodeToNsLovMap);
    Long id = (long) Integer.parseInt(s.getId());
    String legacySourceTable = null;
    String referralId = s.getReferralId();
    String endedAt = DomainChef.cookISO8601Timestamp(DomainChef.uncookDateString(s.getEndedAt()));
    String incidentCounty = s.getIncidentCounty();
    String incidentDate = s.getIncidentDate();
    String locationType = s.getLocationType();
    String communicationMethod = s.getCommunicationMethod();
    Short communicationMethodSysId = StringUtils.isNotBlank(communicationMethod)
        ? nsCodeToNsLovMap.get(communicationMethod).getLegacySystemCodeId().shortValue()
        : null;
    String currentLocationOfChildren = null; // do not see in intake-api
    String name = s.getName();
    String reportNarrative = s.getReportNarrative();
    String reference = s.getReference();
    String responseTime = s.getScreeningDecisionDetail();
    Short responseTimeSysId = StringUtils.isNotBlank(responseTime)
        ? nsCodeToNsLovMap.get(responseTime).getLegacySystemCodeId().shortValue()
        : null;
    String startedAt =
        DomainChef.cookISO8601Timestamp(DomainChef.uncookDateString(s.getStartedAt()));
    String assignee = s.getAssignee();
    String assigneeStaffId = s.getAssigneeStaffId();
    String additionalInformation = s.getAdditionalInformation();
    String screeningDecision = s.getScreeningDecision();
    String screeningDecisionDetail = s.getScreeningDecisionDetail();
    Short approvalStatus =
        SystemCodeCache.global().getSystemCodeId("Request Not Submitted", "APV_STC");
    // this is defaulted in intake-api,see referral_representer
    Boolean familyAwareness = false; // this is defaulted in intake-api, see referral_representer
    Boolean filedWithLawEnforcement = false;
    // this is defaulted in intake-api, see referral_representer
    String responsibleAgency = "C"; // this is defaulted in intake-api, see referral_representer
    String limitedAccessCode = setLimitedAccessCode(s.getAccessRestrictions());
    String limitedAccessDescription = s.getRestrictionsRationale();
    String limitedAccessAgency = loggedInStaffCounty;
    Date limitedAccessDate = new Date();// dateToday
    Address address = new AddressTransformer().transform(s.getIncidentAddress(), nsCodeToNsLovMap);
    Set<Participant> participants =
        new ParticipantsTransformer().transform(s.getParticipantIntakeApis(), nsCodeToNsLovMap);

    Set<CrossReport> crossReports = new CrossReportsTransformer().transform(s.getCrossReports(),
        nsCodeToNsLovMap, cmsSysIdToNsLovMap);

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

  private void printScreeningToReferral(ScreeningToReferral screeningToReferral) {
    System.out.println("SCREENING TO REFERRAL");
    String json;
    try {
      json = ObjectMapperUtils.createObjectMapper().writeValueAsString(screeningToReferral);
      System.out.println(json);
    } catch (JsonProcessingException e) {
      System.out.println("no json created ");
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
