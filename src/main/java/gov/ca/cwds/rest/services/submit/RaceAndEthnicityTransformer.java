package gov.ca.cwds.rest.services.submit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Transforms the Intake race and ethnicity from the screening into a legacy supported values
 * {@link RaceAndEthnicity} for an valid participants.
 * 
 * @author CWDS API Team
 *
 */
public class RaceAndEthnicityTransformer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RaceAndEthnicityTransformer.class);

  private static final short UNABLE_TO_DETERMINE = (short) 6351; // Intake calls as 'Abandoned'

  static class IntakeRace {
    private String race;

    @JsonProperty("race_detail")
    private String raceDetail;

    public String getRace() {
      return race;
    }

    public String getRaceDetail() {
      return raceDetail;
    }
  }

  static class IntakeEthnicity {
    @JsonProperty("hispanic_latino_origin")
    private String hispanicLatinoOrigin;

    @JsonProperty("ethnicity_detail")
    private List<String> ethnicityDetail;

    public String getHispanicLatinoOrigin() {
      return hispanicLatinoOrigin;
    }

    public List<String> getEthnicityDetail() {
      return ethnicityDetail;
    }
  }

  /**
   * 
   */
  public RaceAndEthnicityTransformer() {
    // no-opt
  }

  /**
   * @param participantsIntake - participantsIntake
   * @param nsCodeToNsLovMap - nsCodeToNsLovMap
   * @return the raceAndEthnicity
   */
  public RaceAndEthnicity transform(ParticipantIntakeApi participantsIntake,
      Map<String, IntakeLov> nsCodeToNsLovMap) {
    List<IntakeRace> intakeRace = null;
    IntakeEthnicity intakeEthnicity = null;
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity();
    List<Short> raceCodes = new ArrayList<>();
    List<Short> hispanicCodes = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    if (participantsIntake != null) {
      try {
        intakeRace = raceJsonBuilder(participantsIntake, intakeRace, mapper);
        intakeEthnicity = ethnicityJsonBuilder(participantsIntake, intakeEthnicity, mapper);
      } catch (IOException e) {
        LOGGER.error("Unable to parse the race and Ethnicity", e);
        throw new ServiceException(e);
      }
      if (intakeRace != null) {
        buildRace(nsCodeToNsLovMap, intakeRace, raceAndEthnicity, raceCodes);
      }
      if (intakeEthnicity != null) {
        buildEthnicity(nsCodeToNsLovMap, intakeEthnicity, raceAndEthnicity, hispanicCodes);
      }
    }
    return raceAndEthnicity;
  }

  private List<IntakeRace> raceJsonBuilder(ParticipantIntakeApi participantsIntake,
      List<IntakeRace> intakeRace, ObjectMapper mapper) throws IOException {
    if (StringUtils.isNotBlank(participantsIntake.getRaces())) {
      intakeRace =
          mapper.readValue(participantsIntake.getRaces(), new TypeReference<List<IntakeRace>>() {});
    }
    return intakeRace;
  }

  private IntakeEthnicity ethnicityJsonBuilder(ParticipantIntakeApi participantsIntake,
      IntakeEthnicity intakeEthnicity, ObjectMapper mapper) throws IOException {
    if (StringUtils.isNotBlank(participantsIntake.getEthnicity())) {
      intakeEthnicity = mapper.readValue(participantsIntake.getEthnicity(), IntakeEthnicity.class);
    }
    return intakeEthnicity;
  }

  private void buildRace(Map<String, IntakeLov> nsCodeToNsLovMap, List<IntakeRace> intakeRace,
      RaceAndEthnicity raceAndEthnicity, List<Short> raceCodes) {
    for (IntakeRace detail : intakeRace) {
      if (StringUtils.isNotBlank(detail.getRaceDetail())) {
        raceCodes
            .add(nsCodeToNsLovMap.get(detail.getRaceDetail()).getLegacySystemCodeId().shortValue());
      } else {
        raceCodes.add(nsCodeToNsLovMap.get(detail.getRace()).getLegacySystemCodeId().shortValue());
      }
    }
    raceAndEthnicity.setRaceCode(raceCodes);
    if (raceCodes.contains(UNABLE_TO_DETERMINE)) {
      raceAndEthnicity.setUnableToDetermineCode("A");
    }
  }

  private void buildEthnicity(Map<String, IntakeLov> nsCodeToNsLovMap,
      IntakeEthnicity intakeEthnicity, RaceAndEthnicity raceAndEthnicity,
      List<Short> hispanicCodes) {
    if (StringUtils.isBlank(intakeEthnicity.getHispanicLatinoOrigin())) {
      raceAndEthnicity.setHispanicOriginCode("X");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Yes")) {
      hispanicCodes.add(nsCodeToNsLovMap.get(intakeEthnicity.getEthnicityDetail().get(0))
          .getLegacySystemCodeId().shortValue());
      raceAndEthnicity.setHispanicCode(hispanicCodes);
      raceAndEthnicity.setHispanicOriginCode("Y");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("No")) {
      raceAndEthnicity.setHispanicOriginCode("N");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Unknown")) {
      raceAndEthnicity.setHispanicOriginCode("U");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Abandoned")) {
      raceAndEthnicity.setHispanicOriginCode("Z");
      raceAndEthnicity.setHispanicUnableToDetermineCode("A");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Declined to answer")) {
      raceAndEthnicity.setHispanicOriginCode("D");
    }
  }

}
