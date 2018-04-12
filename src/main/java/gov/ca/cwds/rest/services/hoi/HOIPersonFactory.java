package gov.ca.cwds.rest.services.hoi;

import com.google.inject.Inject;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.StaffPerson;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.resources.StaffPersonResource;
import gov.ca.cwds.rest.util.CmsRecordUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author CWDS API Team
 */
public final class HOIPersonFactory {

  @Inject
  ParticipantDao participantDao;

  @Inject
  StaffPersonResource staffPersonResource;

  /**
   * @param participantEntity ns ParticipantEntity
   * @return HOIPerson
   */
  public HOIPerson buildHOIPerson(ParticipantEntity participantEntity) {
    HOIPerson result = new HOIPerson(participantEntity);
    LegacyDescriptorEntity legacyDescriptorEntity = participantDao.findParticipantLegacyDescriptor(participantEntity.getId());
    if (legacyDescriptorEntity != null) {
      result.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }
    return result;
  }

  /**
   * @param participantEntity ns participant
   * @param legacyDescriptor domain LegacyDescriptor
   * @return HOIReporter instance; can be null if the given participant has no reporter role
   */
  public HOIReporter buidHOIReporter(ParticipantEntity participantEntity,
      LegacyDescriptor legacyDescriptor) {
    Set<String> roles = parseRoles(participantEntity.getRoles());
    HOIReporter.Role reporterRole = findReporterRole(roles);
    if (reporterRole == null) {
      return null;
    }
    return new HOIReporter(reporterRole, participantEntity.getId(),
        participantEntity.getFirstName(), participantEntity.getLastName(),
        legacyDescriptor);
  }

  /**
   * @param assigneeStaffId staff person id
   * @return corresponding instance of HOISocialWorker or null
   */
  public HOISocialWorker buildHOISocialWorker(String assigneeStaffId) {
    StaffPerson staffPerson = (StaffPerson) staffPersonResource.get(assigneeStaffId).getEntity();
    if (staffPerson == null) {
      return null;
    }

    CmsRecordDescriptor cmsRecordDescriptor = CmsRecordUtils
        .createLegacyDescriptor(assigneeStaffId, LegacyTable.STAFF_PERSON);

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(cmsRecordDescriptor.getId(),
        cmsRecordDescriptor.getUiId(), null, cmsRecordDescriptor.getTableName(),
        cmsRecordDescriptor.getTableDescription());

    return new HOISocialWorker(assigneeStaffId, staffPerson.getFirstName(),
        staffPerson.getLastName(), legacyDescriptor);
  }

  /**
   * @param roles string array
   * @return set of roles parsed from the input string
   */
  private Set<String> parseRoles(String[] roles) {
    return new HashSet<>(Arrays.asList(roles));
  }

  /**
   * @param roles set of roles
   * @return the first found HOIReporter.Role or null if there is no reporter role in the given set
   */
  private HOIReporter.Role findReporterRole(Set<String> roles) {
    for (String role : roles) {
      HOIReporter.Role reporterRole = HOIReporter.Role.fromString(role);
      if (reporterRole != null) {
        return reporterRole;
      }
    }
    return null;
  }

}
