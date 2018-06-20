package gov.ca.cwds.rest.api.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonInclude(Include.ALWAYS)
public class PostedScreeningToReferral extends ScreeningToReferral implements Response {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default, no-op constructor.
   */
  public PostedScreeningToReferral() {
    super();
  }

  /**
   * @param id - screening ID
   * @param referralId - legacy referral Id
   * @param legacySourceTable - legacy source table name
   * @param endedAt - date/time ended
   * @param incidentCounty - county
   * @param incidentDate - date of incident
   * @param locationType - location type
   * @param communicationMethod - communication method
   * @param currentLocationOfChildren - currentLocationOfChildren
   * @param name - name
   * @param reportNarrative - report narrative
   * @param reference - reference
   * @param responseTime - response time frame
   * @param startedAt - started at
   * @param assignee - assigned to county worker
   * @param assigneeStaffId - assigneeStaffId
   * @param additionalInformation - additional information
   * @param screeningDecision - screening decision
   * @param screeningDecisionDetail - detail
   * @param approvalStatus - approvalStatus
   * @param familyAwareness - familyAwareness
   * @param filedWithLawEnforcement - filedWithLawEnforcement
   * @param responsibleAgency - responsibleAgency
   * @param limitedAccessCode - sealed/sensitive
   * @param limitedAccessDescription - description of sensitivity
   * @param limitedAccessAgency - agency that set sensitivity
   * @param limitedAccessDate - date that sensitivity was set
   * @param alerts - alerts
   * @param alertInformation - alertInformation
   * @param address - address of referral
   * @param participants - participants of the referral
   * @param crossReports - cross reports
   * @param allegations - allegations
   * @param reportType - reportType
   */
  public PostedScreeningToReferral(long id, String referralId, String legacySourceTable,
      @Date String endedAt, String incidentCounty, @Date String incidentDate, String locationType,
      Short communicationMethod, String currentLocationOfChildren, String name,
      String reportNarrative, String reference, Short responseTime, @Date String startedAt,
      String assignee, String assigneeStaffId, String additionalInformation,
      String screeningDecision, String screeningDecisionDetail, int approvalStatus,
      boolean familyAwareness, boolean filedWithLawEnforcement, String responsibleAgency,
      String limitedAccessCode, String limitedAccessDescription, String limitedAccessAgency,
      java.util.Date limitedAccessDate, Set<String> alerts, String alertInformation,
      Address address, Set<Participant> participants, Set<ScreeningRelationship> relationships, Set<CrossReport> crossReports,
      Set<Allegation> allegations, String reportType) {
    super(id, legacySourceTable, referralId, endedAt, incidentCounty, incidentDate, locationType,
        communicationMethod, currentLocationOfChildren, name, reportNarrative, reference,
        responseTime, startedAt, assignee, assigneeStaffId, additionalInformation,
        screeningDecision, screeningDecisionDetail, approvalStatus, familyAwareness,
        filedWithLawEnforcement, responsibleAgency, limitedAccessCode, limitedAccessDescription,
        limitedAccessAgency, limitedAccessDate, alerts, alertInformation, address, participants,
        relationships, crossReports, allegations, reportType);
  }

  /**
   * @param id - id
   * @param referral - referral
   * @param resultParticipants - resultParticipants
   * @param resultCrossReports - resultCrossReports
   * @param resultAllegations - resultAllegations
   * @return the postedScreeningToReferral
   */
  public static PostedScreeningToReferral createWithDefaults(String id,
      ScreeningToReferral referral, Set<Participant> resultParticipants,
      Set<CrossReport> resultCrossReports, Set<Allegation> resultAllegations) {

    return new PostedScreeningToReferral(referral.getId(), id, LegacyTable.REFERRAL.getName(),
        referral.getEndedAt(), referral.getIncidentCounty(), referral.getIncidentDate(),
        referral.getLocationType(), referral.getCommunicationMethod(),
        referral.getCurrentLocationOfChildren(), referral.getName(), referral.getReportNarrative(),
        referral.getReference(), referral.getResponseTime(), referral.getStartedAt(),
        referral.getAssignee(), referral.getAssigneeStaffId(), referral.getAdditionalInformation(),
        referral.getScreeningDecision(), referral.getScreeningDecisionDetail(),
        referral.getApprovalStatus(), referral.isFamilyAwareness(),
        referral.isFiledWithLawEnforcement(), referral.getResponsibleAgency(),
        referral.getLimitedAccessCode(), referral.getLimitedAccessDescription(),
        referral.getLimitedAccessAgency(), referral.getLimitedAccessDate(), referral.getAlerts(),
        referral.getAlertInformation(), referral.getAddress(), resultParticipants,
        referral.getRelationships(), resultCrossReports, resultAllegations,
        referral.getReportType());
  }
}
