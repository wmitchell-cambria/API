package gov.ca.cwds.rest.services.submit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;

/**
 * @author CWDS API Team
 *
 */
public class RaceAndEthnicityTransformer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RaceAndEthnicityTransformer.class);

  private static final short UNABLE_TO_DETERMINE = (short) 6351;

  class IntakeRace {
    private String race;
    private String raceDetail;

    public String getRace() {
      return race;
    }

    public String getRaceDetail() {
      return raceDetail;
    }
  }

  class IntakeEthnicity {
    private String hispanicLatinoOrigin;
    private String ethnicityDetail;

    public String getHispanicLatinoOrigin() {
      return hispanicLatinoOrigin;
    }

    public String getEthnicityDetail() {
      return ethnicityDetail;
    }
  }

  /**
   * @param participantsIntake - participantsIntake
   * @param nsCodeToNsLovMap - nsCodeToNsLovMap
   * @return the raceAndEthnicity
   */
  public RaceAndEthnicity transform(ParticipantIntakeApi participantsIntake,
      Map<String, IntakeLov> nsCodeToNsLovMap) {
    List<IntakeRace> intakeRace = null;
    List<IntakeEthnicity> intakeEthnicities = null;
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity();
    List<Short> raceCodes = new ArrayList<>();
    List<Short> hispanicCodes = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    if (participantsIntake != null) {
      try {
        intakeRace = mapper.readValue(participantsIntake.getRaces(),
            new TypeReference<List<IntakeRace>>() {});
      } catch (IOException e) {
        LOGGER.error("Unable to parse the race and Ethnicity {}", e.getMessage());
      }
      for (IntakeRace detail : intakeRace) {
        raceCodes.add(
            Short.valueOf(nsCodeToNsLovMap.get(detail.getRaceDetail()).getLegacyLogicalCode()));
      }
      raceAndEthnicity.setRaceCode(raceCodes);
      if (raceCodes.contains(UNABLE_TO_DETERMINE)) {
        raceAndEthnicity.setUnableToDetermineCode("A");
      }

      String ethinicityCode = participantsIntake.getEthnicity().split("\"ethnicity_detail\":")[1]
          .replaceAll("[", "").replaceAll("]", "");
      hispanicCodes.add(Short.valueOf(nsCodeToNsLovMap.get(ethinicityCode).getLegacyLogicalCode()));
      raceAndEthnicity.setHispanicCode(hispanicCodes);
    }
    return raceAndEthnicity;

  }

}
