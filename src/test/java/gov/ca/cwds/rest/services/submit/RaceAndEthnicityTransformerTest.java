package gov.ca.cwds.rest.services.submit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.fixture.ParticipantIntakeApiResourceBuilder;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class RaceAndEthnicityTransformerTest {

  private Map<String, IntakeLov> nsCodeToNsLovMap;

  /**
   * 
   */
  @Before
  public void setup() {
    nsCodeToNsLovMap = new HashMap<>();
  }

  /**
   * Test to check race and ethnicity transformed successfully
   */
  @Test
  public void testSuccesWhenBuildRaceAndEthnicty() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder().build();
    IntakeLov intakeLov1 = new IntakeLov();
    intakeLov1.setLegacySystemCodeId(841L);
    intakeLov1.setIntakeCode("Central American");
    nsCodeToNsLovMap.put(intakeLov1.getIntakeCode(), intakeLov1);

    IntakeLov intakeLov2 = new IntakeLov();
    intakeLov2.setLegacySystemCodeId(3164L);
    intakeLov2.setIntakeCode("Mexican");
    nsCodeToNsLovMap.put(intakeLov2.getIntakeCode(), intakeLov2);

    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getHispanicCode().get(0), is(equalTo((short) 3164)));
    assertThat(actual.getRaceCode().get(0), is(equalTo((short) 841)));
  }

  /**
   * Test when the race code is "Abandoned" then the race code is 6351 and UnableToDetermineCode is
   * set to "A"
   */
  @Test
  public void testWhenRaceCodeIsAbandoned() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces("[\n" + "{\"race\":\"Abandoned\",\"race_detail\":null}]").setEthnicity(null)
        .build();
    IntakeLov intakeLov1 = new IntakeLov();
    intakeLov1.setLegacySystemCodeId(6351L);
    intakeLov1.setIntakeCode("Abandoned");
    nsCodeToNsLovMap.put(intakeLov1.getIntakeCode(), intakeLov1);

    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);

    assertThat(actual.getRaceCode().get(0), is(equalTo((short) 6351)));
    assertThat(actual.getUnableToDetermineCode(), is(equalTo("A")));
  }

  /**
   * Test to validate when hispanic is empty and HispanicOriginCode is set "X"
   */
  @Test
  public void testWhenHispanicIsEmpty() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null)
        .setEthnicity(
            "{\n" + "  \"hispanic_latino_origin\": null,\n" + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getHispanicOriginCode(), is(equalTo("X")));
  }

  /**
   * Test when hispanic is No then hispanicOriginCode is set "N"
   */
  @Test
  public void testWhenHispanicIsNo() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"No\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getHispanicOriginCode(), is(equalTo("N")));
  }

  /**
   * Test when hispanic is Unknown then hispanicOriginCode is set "U"
   */
  @Test
  public void testWhenHispanicIsUnknown() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Unknown\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getHispanicOriginCode(), is(equalTo("U")));
  }

  /**
   * Test when hispanic is Abandoned then hispanicOriginCode is set "Z" and
   * hispanicUnableToDetermineCode "A"
   */
  @Test
  public void testWhenHispanicIsAbandoned() {
    ParticipantIntakeApi participantsIntake = new ParticipantIntakeApiResourceBuilder()
        .setRaces(null).setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Abandoned\",\n"
            + "  \"ethnicity_detail\": []\n" + "}")
        .build();
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getHispanicOriginCode(), is(equalTo("Z")));
    assertThat(actual.getHispanicUnableToDetermineCode(), is(equalTo("A")));
  }

  /**
   * Test when hispanic is Declined to answer then hispanicOriginCode is set "D"
   */
  @Test
  public void testWhenHispanicIsDeclinedToAnswer() {
    ParticipantIntakeApi participantsIntake =
        new ParticipantIntakeApiResourceBuilder().setRaces(null)
            .setEthnicity("{\n" + "  \"hispanic_latino_origin\": \"Declined to answer\",\n"
                + "  \"ethnicity_detail\": []\n" + "}")
            .build();
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getHispanicOriginCode(), is(equalTo("D")));
  }

  /**
   * Test to check service exception when Json mapper failed to parse Json
   */
  @Test(expected = ServiceException.class)
  public void testExceptionWhenJsonInValid() {
    ParticipantIntakeApi participantsIntake =
        new ParticipantIntakeApiResourceBuilder().setRaces("{invalid}").setEthnicity(null).build();
    @SuppressWarnings("unused")
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
  }

  /**
   * Test when participant is null transform will be ignored
   */
  @Test
  public void testWhenParticipantNull() {
    ParticipantIntakeApi participantsIntake = null;
    RaceAndEthnicity actual =
        new RaceAndEthnicityTransformer().transform(participantsIntake, nsCodeToNsLovMap);
    assertThat(actual.getRaceCode(), is(nullValue()));
    assertThat(actual.getHispanicCode(), is(nullValue()));
    assertThat(actual.getUnableToDetermineCode(), is(nullValue()));
    assertThat(actual.getHispanicOriginCode(), is(nullValue()));
    assertThat(actual.getHispanicUnableToDetermineCode(), is(nullValue()));
  }

}
