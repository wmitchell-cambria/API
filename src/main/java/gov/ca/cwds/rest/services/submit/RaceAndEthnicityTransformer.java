package gov.ca.cwds.rest.services.submit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
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

  private static final String DECLINED_TO_ANSWER = "D";
  private static final String UNKNOWN = "U";
  private static final String NO = "N";
  private static final String YES = "Y";
  private static final String DEFAULT_VALUE = "X";
  private static final short UNABLE_TO_DETERMINE = (short) 6351; // Intake calls as 'Abandoned'

  static class IntakeRace {
    @JsonProperty("race")
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
   * @return the raceAndEthnicity
   */
  public RaceAndEthnicity transform(ParticipantIntakeApi participantsIntake) {
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
        buildRace(intakeRace, raceAndEthnicity, raceCodes);
      }
      if (intakeEthnicity != null) {
        buildEthnicity(intakeEthnicity, raceAndEthnicity, hispanicCodes);
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

  private void buildRace(List<IntakeRace> intakeRace, RaceAndEthnicity raceAndEthnicity,
      List<Short> raceCodes) {
    for (IntakeRace detail : intakeRace) {
      if (StringUtils.isNotBlank(detail.getRaceDetail())) {
        raceCodes.add(IntakeCodeCache.global().getLegacySystemCodeForRaceAndEthnicity(
            SystemCodeCategoryId.ETHNICITY, detail.getRaceDetail()));
      } else {
        validateGetRace(raceCodes, detail);
      }
    }
    raceAndEthnicity.setRaceCode(raceCodes);
    if (raceCodes.contains(UNABLE_TO_DETERMINE)) {
      raceAndEthnicity.setUnableToDetermineCode("A");
    }
  }

  private void validateGetRace(List<Short> raceCodes, IntakeRace detail) {
    if ("Abandoned".contains(detail.getRace())) {
      raceCodes.add(IntakeCodeCache.global().getLegacySystemCodeForRaceAndEthnicity("DSP_RSNC",
          detail.getRace()));
    } else {
      raceCodes.add(IntakeCodeCache.global().getLegacySystemCodeForRaceAndEthnicity(
          SystemCodeCategoryId.ETHNICITY, detail.getRace()));
    }
  }

  private void buildEthnicity(IntakeEthnicity intakeEthnicity, RaceAndEthnicity raceAndEthnicity,
      List<Short> hispanicCodes) {
    if (StringUtils.isBlank(intakeEthnicity.getHispanicLatinoOrigin())) {
      raceAndEthnicity.setHispanicOriginCode(DEFAULT_VALUE);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Yes")) {
      setHispanicCodeForYes(intakeEthnicity, raceAndEthnicity, hispanicCodes);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("No")) {
      raceAndEthnicity.setHispanicOriginCode(NO);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Unknown")) {
      raceAndEthnicity.setHispanicOriginCode(UNKNOWN);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Abandoned")) {
      setHispanicOriginForAbandoned(intakeEthnicity, raceAndEthnicity, hispanicCodes);
      raceAndEthnicity.setHispanicUnableToDetermineCode("A");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Declined to answer")) {
      raceAndEthnicity.setHispanicOriginCode(DECLINED_TO_ANSWER);
    }
  }

  private void setHispanicCodeForYes(IntakeEthnicity intakeEthnicity,
      RaceAndEthnicity raceAndEthnicity, List<Short> hispanicCodes) {
    raceAndEthnicity.setHispanicOriginCode(YES);
    if (!intakeEthnicity.ethnicityDetail.isEmpty()) {
      hispanicCodes.add(IntakeCodeCache.global().getLegacySystemCodeForRaceAndEthnicity(
          SystemCodeCategoryId.ETHNICITY, intakeEthnicity.getEthnicityDetail().get(0)));
      raceAndEthnicity.setHispanicCode(hispanicCodes);
    }
  }

  private void setHispanicOriginForAbandoned(IntakeEthnicity intakeEthnicity,
      RaceAndEthnicity raceAndEthnicity, List<Short> hispanicCodes) {
    hispanicCodes.add(IntakeCodeCache.global().getLegacySystemCodeForRaceAndEthnicity("DSP_RSNC",
        intakeEthnicity.getHispanicLatinoOrigin()));
    raceAndEthnicity.setHispanicCode(hispanicCodes);
    raceAndEthnicity.setHispanicOriginCode("Z");
  }

}
