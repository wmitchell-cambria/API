package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

class InvolvementHistoryData {

  private String screeningId = null;

  private Collection<String> clientIds = new HashSet<>();

  private List<HOIScreening> hoiScreenings = new ArrayList<>();

  private List<HOICase> hoiCases = new ArrayList<>();

  private List<HOIReferral> hoiReferrals = new ArrayList<>();

  InvolvementHistoryData(String screeningId) {
    this.screeningId = screeningId;
  }

  InvolvementHistoryData(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  String getScreeningId() {
    return screeningId;
  }

  Collection<String> getClientIds() {
    return clientIds;
  }

  void setClientIds(Collection<String> clientIds) {
    this.clientIds = clientIds;
  }

  List<HOIScreening> getHoiScreenings() {
    return hoiScreenings;
  }

  void setHoiScreenings(List<HOIScreening> hoiScreenings) {
    this.hoiScreenings = hoiScreenings;
  }

  List<HOICase> getHoiCases() {
    return hoiCases;
  }

  void setHoiCases(List<HOICase> hoiCases) {
    this.hoiCases = hoiCases;
  }

  List<HOIReferral> getHoiReferrals() {
    return hoiReferrals;
  }

  void setHoiReferrals(List<HOIReferral> hoiReferrals) {
    this.hoiReferrals = hoiReferrals;
  }
}
