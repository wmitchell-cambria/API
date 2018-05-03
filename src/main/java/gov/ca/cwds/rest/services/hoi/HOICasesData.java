package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

class HOICasesData {

  private Collection<String> clientIds;

  private Map<String, Collection<ClientRelationship>> relationshipsByPrimaryClients;
  private Collection<ClientRelationship> allRelationshipsByPrimaryClients;

  private Map<String, Collection<ClientRelationship>> relationshipsBySecondaryClients;
  private Collection<ClientRelationship> allRelationshipsBySecondaryClients;

  private Collection<String> allClientIds;
  private Map<String, Client> allClients;

  private Map<String, CmsCase> cmsCases;

  HOICasesData(Collection<String> clientIds) {
    this.clientIds = clientIds;
    this.allClientIds = new HashSet<>(clientIds);
  }

  Collection<String> getClientIds() {
    return clientIds;
  }

  Map<String, Collection<ClientRelationship>> getRelationshipsByPrimaryClients() {
    return relationshipsByPrimaryClients;
  }

  void setRelationshipsByPrimaryClients(
      Map<String, Collection<ClientRelationship>> relationshipsByPrimaryClients) {
    this.relationshipsByPrimaryClients = relationshipsByPrimaryClients;
  }

  Collection<ClientRelationship> getAllRelationshipsByPrimaryClients() {
    return allRelationshipsByPrimaryClients;
  }

  void setAllRelationshipsByPrimaryClients(
      Collection<ClientRelationship> allRelationshipsByPrimaryClients) {
    this.allRelationshipsByPrimaryClients = allRelationshipsByPrimaryClients;
  }

  Map<String, Collection<ClientRelationship>> getRelationshipsBySecondaryClients() {
    return relationshipsBySecondaryClients;
  }

  void setRelationshipsBySecondaryClients(
      Map<String, Collection<ClientRelationship>> relationshipsBySecondaryClients) {
    this.relationshipsBySecondaryClients = relationshipsBySecondaryClients;
  }

  Collection<ClientRelationship> getAllRelationshipsBySecondaryClients() {
    return allRelationshipsBySecondaryClients;
  }

  void setAllRelationshipsBySecondaryClients(
      Collection<ClientRelationship> allRelationshipsBySecondaryClients) {
    this.allRelationshipsBySecondaryClients = allRelationshipsBySecondaryClients;
  }

  Collection<String> getAllClientIds() {
    return allClientIds;
  }

  Map<String, Client> getAllClients() {
    return allClients;
  }

  void setAllClients(Map<String, Client> allClients) {
    this.allClients = allClients;
  }

  Map<String, CmsCase> getCmsCases() {
    return cmsCases;
  }

  void setCmsCases(
      Map<String, CmsCase> cmsCases) {
    this.cmsCases = cmsCases;
  }
}
