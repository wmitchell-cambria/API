package gov.ca.cwds.rest.services.submit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

/**
 * Business layer object to transform NS {@link ParticipantIntakeApi } to {@link Participant }
 * 
 * @author CWDS API Team
 */
public class ParticipantsTransformer {

  /**
   * @param participantsIntake - participantsIntake
   * @param nsCodeToNsLovMap - nsCodeToNsLovMap
   * @return the participants
   */
  public Set<Participant> transform(Set<ParticipantIntakeApi> participantsIntake,
      Map<String, IntakeLov> nsCodeToNsLovMap) {
    Set<Participant> participants = new HashSet<>();


    Boolean clientStaffPersonAdded = Boolean.FALSE;
    Boolean reporterConfidentialWaiver = Boolean.FALSE;
    String reporterEmployerName = "Employer Name";
    AddressTransformer addressTransformer = new AddressTransformer();
    RaceAndEthnicityTransformer raceAndEthnicityTransformer = new RaceAndEthnicityTransformer();

    for (ParticipantIntakeApi p : participantsIntake) {
      Set<Address> addresses = new HashSet<>();
      for (AddressIntakeApi addressIntake : p.getAddresses()) {
        addresses.add(addressTransformer.transform(addressIntake, nsCodeToNsLovMap));
      }
      addresses = Collections.unmodifiableSet(addresses);
      String gender = StringUtils.isNotBlank(p.getGender())
          ? (Gender.findByNsDescription(p.getGender().toLowerCase())).getCmsDescription()
          : "";
      Long pid = Long.valueOf(p.getId());
      p.getSensitive();
      p.getSealed();
      String sensitivityIndicator = "N";
      RaceAndEthnicity raceAndEthnicity =
          raceAndEthnicityTransformer.transform(p, nsCodeToNsLovMap);
      String dob = DomainChef.cookDate(p.getDateOfBirth());
      String ssn = StringUtils.isNotBlank(p.getSsn()) ? p.getSsn().replaceAll("-", "") : "";
      Short russian = 1271;
      Short english = 1253;

      Participant participant = new Participant(pid, p.getLegacySourceTable(), p.getLegacyId(),
          p.getLegacyDescriptor(), p.getFirstName(), p.getMiddleName(), p.getLastName(),
          p.getNameSuffix(), gender, ssn, dob, english, russian,
          Integer.parseInt(p.getScreeningId()), reporterConfidentialWaiver, reporterEmployerName,
          clientStaffPersonAdded, sensitivityIndicator, p.getApproximateAge(),
          p.getApproximateAgeUnits(), p.getRoles(), addresses, raceAndEthnicity);
      participants.add(participant);
    }
    return participants;
  }

}
