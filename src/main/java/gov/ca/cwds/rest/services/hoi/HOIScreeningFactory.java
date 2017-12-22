package gov.ca.cwds.rest.services.hoi;

import com.google.inject.Inject;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCode;
import gov.ca.cwds.data.persistence.ns.Screening;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;

/**
 * @author CWDS API Team
 */
public final class HOIScreeningFactory {

  @Inject
  ScreeningDao screeningDao;

  @Inject
  HOIPersonFactory hoiPersonFactory;

  /**
   * @param persistedScreening ns Screening
   * @return HOIScreening
   */
  public HOIScreening buildHOIScreening(Screening persistedScreening) {
    HOIScreening result = new HOIScreening(persistedScreening);

    if (persistedScreening.getIncidentCounty() != null) {
      IntakeLOVCode code = screeningDao
          .findIntakeLOVCodeByIntakeCode(persistedScreening.getIncidentCounty());
      if (code != null) {
        result.setCounty(
            new SystemCodeDescriptor(code.getLgSysId().shortValue(), code.getIntakeDisplay()));
      }
    }

    if (persistedScreening.getParticipants() != null) {
      for (gov.ca.cwds.data.persistence.ns.Participant persistedParticipant : persistedScreening
          .getParticipants()) {
        HOIPerson participant = hoiPersonFactory.buildHOIPerson(persistedParticipant);
        result.getAllPeople().add(participant);

        if (result.getReporter() == null) {
          result.setReporter(hoiPersonFactory
              .buidHOIReporter(persistedParticipant, participant.getLegacyDescriptor()));
        }
      }
    }

    if (persistedScreening.getAssigneeStaffId() != null) {
      result.setAssignedSocialWorker(
          hoiPersonFactory.buildHOISocialWorker(persistedScreening.getAssigneeStaffId()));
    }

    return result;
  }

}
