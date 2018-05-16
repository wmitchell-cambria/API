package gov.ca.cwds.rest.services.submit;

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


    Boolean clientStaffPersonAdded = false;
    Boolean reporterConfidentialWaiver = false;
    String reporterEmployerName = "";

    for (ParticipantIntakeApi p : participantsIntake) {
      Set<Address> addresses = new HashSet<>();
      for (AddressIntakeApi addressIntake : p.getAddresses()) {
        addresses.add(new AddressTransformer().transform(addressIntake, nsCodeToNsLovMap));
      }
      String gender = getGender(p.getGender());
      Long pid = (long) Integer.parseInt(p.getId());
      p.getRaces();
      p.getSensitive();
      p.getSealed();
      String sensitivityIndicator = "N";
      p.getEthnicity();
      p.getRaces();
      RaceAndEthnicity raceAndEthnicity =
          new RaceAndEthnicityTransformer().transform(p, nsCodeToNsLovMap);
      String dob = DomainChef.cookDate(p.getDateOfBirth());
      String ssn = StringUtils.isNotBlank(p.getSsn()) ? p.getSsn().replaceAll("-", "") : "";
      Short farsi = 1254;
      Short english = 1253;


      Participant participant = new Participant(pid, p.getLegacySourceTable(), p.getLegacyId(),
          p.getLegacyDescriptor(), p.getFirstName(), p.getMiddleName(), p.getLastName(),
          p.getNameSuffix(), gender, ssn, dob, english, farsi, Integer.parseInt(p.getScreeningId()),
          reporterConfidentialWaiver, reporterEmployerName, clientStaffPersonAdded,
          sensitivityIndicator, p.getApproximateAge(), p.getApproximateAgeUnits(), p.getRoles(),
          addresses, raceAndEthnicity);
      participants.add(participant);
    }
    return participants;
  }

  private String getGender(String gender) {
    if (StringUtils.isNotBlank(gender)) {
      if (gender.equalsIgnoreCase("male")) {
        return "M";
      } else if (gender.equalsIgnoreCase("female")) {
        return "F";
      } else if (gender.equalsIgnoreCase("intersex")) {
        return "I";
      }
      return "U";
    }
    return "U";
  }

}
