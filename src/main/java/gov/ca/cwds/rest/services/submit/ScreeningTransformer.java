package gov.ca.cwds.rest.services.submit;

import java.util.Date;
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

  private static final String LEGACY_SOURCE_TABLE = "";
  private static final String CURRENT_LOCATION_OF_CHILDREN = null;
  private static final String RESPONSIBLE_AGENCY = "C";
  private static final Boolean FAMILY_AWARENESS = Boolean.FALSE;
  private static final Boolean FILED_WITH_LAW_ENFORCEMENT = Boolean.FALSE;
  private static final Short APPROVAL_STATUS =
      SystemCodeCache.global().getSystemCodeId("Request Not Submitted", "APV_STC");


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
    String communicationMethod = screening.getCommunicationMethod();
    Short communicationMethodSysId = StringUtils.isNotBlank(communicationMethod)
        ? nsCodeToNsLovMap.get(communicationMethod).getLegacySystemCodeId().shortValue()
        : null;
    String responseTime = screening.getScreeningDecisionDetail();
    Short responseTimeSysId = StringUtils.isNotBlank(responseTime)
        ? nsCodeToNsLovMap.get(responseTime).getLegacySystemCodeId().shortValue()
        : null;
    String limitedAccessCode = StringUtils.isNotBlank(screening.getAccessRestrictions())
        ? (AccessRestrictions.findByNsDescription(screening.getAccessRestrictions().toLowerCase()))
            .getCmsDescription()
        : "N";
    Date limitedAccessDate = null;
    // this is the field restrictions_date in postgres screening that was missed in creation of
    // Screening Domain object, Dev will add on Monday
    Address address = (screening.getIncidentAddress() != null)
        ? new AddressTransformer().transform(screening.getIncidentAddress(), nsCodeToNsLovMap)
        : null;
    Set<Participant> participants =
        (screening.getParticipantIntakeApis() != null) ? new ParticipantsTransformer()
            .transform(screening.getParticipantIntakeApis(), nsCodeToNsLovMap) : null;

    Set<CrossReport> crossReports =
        (screening.getCrossReports() != null) ? new CrossReportsTransformer()
            .transform(screening.getCrossReports(), nsCodeToNsLovMap, cmsSysIdToNsLovMap) : null;

    return new ScreeningToReferral((long) Integer.parseInt(screening.getId()), LEGACY_SOURCE_TABLE,
        screening.getReferralId(),
        DomainChef.cookISO8601Timestamp(DomainChef.uncookDateString(screening.getEndedAt())),
        screening.getIncidentCounty(), screening.getIncidentDate(), screening.getLocationType(),
        communicationMethodSysId, CURRENT_LOCATION_OF_CHILDREN, screening.getName(),
        screening.getReportNarrative(), screening.getReference(), responseTimeSysId,
        DomainChef.cookISO8601Timestamp(DomainChef.uncookDateString(screening.getStartedAt())),
        screening.getAssignee(), screening.getAssigneeStaffId(),
        screening.getAdditionalInformation(), screening.getScreeningDecision(),
        screening.getScreeningDecisionDetail(), APPROVAL_STATUS, FAMILY_AWARENESS,
        FILED_WITH_LAW_ENFORCEMENT, RESPONSIBLE_AGENCY, limitedAccessCode,
        screening.getRestrictionsRationale(), loggedInStaffCounty, limitedAccessDate,
        screening.getSafetyAlerts(), screening.getSafetyInformation(), address, participants,
        crossReports, allegations);

  }

}
