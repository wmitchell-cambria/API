package gov.ca.cwds.rest.services.hoi;

import com.google.inject.Inject;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import java.util.Map;

/**
 * @author CWDS API Team
 */
public final class HOIScreeningFactory {

  @Inject
  HOIPersonFactory hoiPersonFactory;

  /**
   * @param screeningEntity NS ScreeningEntity
   * @param countyIntakeLOVCodeEntity NS IntakeLOVCodeEntity
   * @return HOIScreening
   */
  HOIScreening buildHOIScreening(ScreeningEntity screeningEntity,
      IntakeLOVCodeEntity countyIntakeLOVCodeEntity,
      Map<String, LegacyDescriptorEntity> participantLegacyDescriptors) {
    HOIScreening result = new HOIScreening(screeningEntity);

    if (countyIntakeLOVCodeEntity != null) {
      result.setCounty(
          new SystemCodeDescriptor(countyIntakeLOVCodeEntity.getLgSysId().shortValue(),
              countyIntakeLOVCodeEntity.getIntakeDisplay()));
    }

    if (screeningEntity.getParticipants() != null) {
      for (ParticipantEntity participantEntity : screeningEntity.getParticipants()) {
        HOIPerson participant = hoiPersonFactory
            .buildHOIPerson(participantEntity, participantLegacyDescriptors);
        result.getAllPeople().add(participant);

        if (result.getReporter() == null) {
          result.setReporter(hoiPersonFactory
              .buidHOIReporter(participantEntity, participant.getLegacyDescriptor()));
        }
      }
    }

    if (screeningEntity.getAssigneeStaffId() != null) {
      result.setAssignedSocialWorker(
          hoiPersonFactory.buildHOISocialWorker(screeningEntity.getAssigneeStaffId()));
    }

    return result;
  }

}
