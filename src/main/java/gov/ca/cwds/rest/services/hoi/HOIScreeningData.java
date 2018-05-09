package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class HOIScreeningData {

  private Collection<String> clientIds;

  private Set<ScreeningEntity> screeningEntities = new HashSet<>();

  private Map<String, IntakeLOVCodeEntity> countyIntakeLOVCodeEntityMap;

  private Map<String, LegacyDescriptorEntity> participantLegacyDescriptors;

  private Collection<String> assigneeStaffIds;

  private Map<String, StaffPerson> staffPersonMap;

  HOIScreeningData(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  Collection<String> getClientIds() {
    return clientIds;
  }

  void setClientIds(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  Set<ScreeningEntity> getScreeningEntities() {
    return screeningEntities;
  }

  Map<String, IntakeLOVCodeEntity> getCountyIntakeLOVCodeEntityMap() {
    return countyIntakeLOVCodeEntityMap;
  }

  void setCountyIntakeLOVCodeEntityMap(
      Map<String, IntakeLOVCodeEntity> countyIntakeLOVCodeEntityMap) {
    this.countyIntakeLOVCodeEntityMap = countyIntakeLOVCodeEntityMap;
  }

  Map<String, LegacyDescriptorEntity> getParticipantLegacyDescriptors() {
    return participantLegacyDescriptors;
  }

  void setParticipantLegacyDescriptors(
      Map<String, LegacyDescriptorEntity> participantLegacyDescriptors) {
    this.participantLegacyDescriptors = participantLegacyDescriptors;
  }

  Collection<String> getAssigneeStaffIds() {
    return assigneeStaffIds;
  }

  void setAssigneeStaffIds(Collection<String> assigneeStaffIds) {
    this.assigneeStaffIds = assigneeStaffIds;
  }

  Map<String, StaffPerson> getStaffPersonMap() {
    return staffPersonMap;
  }

  void setStaffPersonMap(
      Map<String, StaffPerson> staffPersonMap) {
    this.staffPersonMap = staffPersonMap;
  }
}
