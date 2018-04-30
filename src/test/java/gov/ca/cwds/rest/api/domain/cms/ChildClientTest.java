package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.fixture.ChildClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.junit.template.DomainTestTemplate;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ChildClientTest implements DomainTestTemplate {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private String lastUpdatedId = "0X5";

  private String victimClientId = "ABC1234567";
  private String adoptableCode = "N";
  private Short adoptedAge = 1234;
  private Boolean afdcFcEligibilityIndicatorVar = false;
  private Boolean allEducationInfoOnFileIndicator = false;
  private Boolean allHealthInfoOnFileIndicator = false;
  private String attemptToAcquireEducInfoDesc = "Eucation";
  private String attemptToAcquireHlthInfoDesc = "Health";
  private String awolAbductedCode = "N";
  private Boolean birthHistoryIndicatorVar = false;
  private Boolean childIndianAncestryIndicator = false;
  private Boolean collegeIndicator = false;
  private String currentCaseId = "ABC1234567";
  private Short deathCircumstancesType = 1234;
  private String disabilityDiagnosedCode = "N";
  private String drmsHePassportDocOld = "Old";
  private String drmsHealthEducPassportDoc = "Document";
  private String drmsVoluntaryPlcmntAgrmntDoc = "Agreement";
  private Boolean fc2EligApplicationIndicatorVar = false;
  private String foodStampsApplicationDate = "2000-01-01";
  private Boolean foodStampsApplicationIndicator = false;
  private String icwaEligibilityCode = "N";
  private Boolean intercountryAdoptDisruptedIndicator = false;
  private Boolean intercountryAdoptDissolvedIndicator = false;
  private Boolean medEligibilityApplicationIndicatorVar = false;
  private Boolean minorNmdParentIndicator = false;
  private Boolean parentalRightsLimitedIndicator = false;
  private Boolean parentalRightsTermintnIndicatorVar = false;
  private Boolean paternityIndividualIndicatorVar = false;
  private Boolean postsecVocIndicator = false;
  private String previouslyAdoptedCode = "N";
  private Boolean safelySurrendedBabiesIndicatorVar = false;
  private Boolean saw1EligApplicationIndicatorVar = false;
  private Integer sawsCaseSerialNumber = 0;
  private String sijsScheduledInterviewDate = "2000-01-01";
  private String siiNextScreeningDueDate = "2000-01-01";
  private Boolean ssiSspApplicationIndicator = false;
  private Boolean tribalAncestryNotifctnIndicatorVar = false;
  private String tribalCustomaryAdoptionDate = "2000-01-01";
  private Boolean tribalCustomaryAdoptionIndicator = false;
  
  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() throws Exception  {
    messageBuilder = new MessageBuilder();

    ChildClient validChildClient = validChildClient();
  }

  @Override
  public void teardown() throws Exception {

  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    ChildClient domain = new ChildClient(victimClientId, adoptableCode, adoptedAge,
        afdcFcEligibilityIndicatorVar, allEducationInfoOnFileIndicator,
        allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc, attemptToAcquireHlthInfoDesc,
        awolAbductedCode, birthHistoryIndicatorVar, childIndianAncestryIndicator, collegeIndicator,
        currentCaseId, deathCircumstancesType, disabilityDiagnosedCode, drmsHePassportDocOld,
        drmsHealthEducPassportDoc, drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar,
        foodStampsApplicationDate, foodStampsApplicationIndicator, icwaEligibilityCode,
        intercountryAdoptDisruptedIndicator, intercountryAdoptDissolvedIndicator,
        medEligibilityApplicationIndicatorVar, minorNmdParentIndicator,
        parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);

    gov.ca.cwds.data.persistence.cms.ChildClient persistent =
        new gov.ca.cwds.data.persistence.cms.ChildClient(victimClientId, domain, lastUpdatedId);

    assertThat(persistent.getVictimClientId(), is(equalTo(victimClientId)));
    assertThat(persistent.getAdoptableCode(), is(equalTo(adoptableCode)));
    assertThat(persistent.getAdoptedAge(), is(equalTo(adoptedAge)));
    assertThat(persistent.getAfdcFcEligibilityIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(afdcFcEligibilityIndicatorVar))));
    assertThat(persistent.getAllEducationInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(allEducationInfoOnFileIndicator))));
    assertThat(persistent.getAllHealthInfoOnFileIndicator(),
        is(equalTo(DomainChef.cookBoolean(allHealthInfoOnFileIndicator))));
    assertThat(persistent.getAttemptToAcquireEducInfoDesc(),
        is(equalTo(attemptToAcquireEducInfoDesc)));
    assertThat(persistent.getAttemptToAcquireHlthInfoDesc(),
        is(equalTo(attemptToAcquireHlthInfoDesc)));
    assertThat(persistent.getAwolAbductedCode(), is(equalTo(awolAbductedCode)));
    assertThat(persistent.getBirthHistoryIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(birthHistoryIndicatorVar))));
    assertThat(persistent.getChildIndianAncestryIndicator(),
        is(equalTo(DomainChef.cookBoolean(childIndianAncestryIndicator))));
    assertThat(persistent.getCollegeIndicator(),
        is(equalTo(DomainChef.cookBoolean(collegeIndicator))));
    assertThat(persistent.getCurrentCaseId(), is(equalTo(currentCaseId)));
    assertThat(persistent.getDeathCircumstancesType(), is(equalTo(deathCircumstancesType)));
    assertThat(persistent.getDisabilityDiagnosedCode(), is(equalTo(disabilityDiagnosedCode)));
    assertThat(persistent.getDrmsHePassportDocOld(), is(equalTo(drmsHePassportDocOld)));
    assertThat(persistent.getDrmsHealthEducPassportDoc(), is(equalTo(drmsHealthEducPassportDoc)));
    assertThat(persistent.getDrmsVoluntaryPlcmntAgrmntDoc(),
        is(equalTo(drmsVoluntaryPlcmntAgrmntDoc)));
    assertThat(persistent.getFc2EligApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(fc2EligApplicationIndicatorVar))));
    assertThat(persistent.getFoodStampsApplicationDate(),
        is(equalTo(DomainChef.uncookDateString(foodStampsApplicationDate))));
    assertThat(persistent.getFoodStampsApplicationIndicator(),
        is(equalTo(DomainChef.cookBoolean(foodStampsApplicationIndicator))));
    assertThat(persistent.getIcwaEligibilityCode(), is(equalTo(icwaEligibilityCode)));
    assertThat(persistent.getIntercountryAdoptDisruptedIndicator(),
        is(equalTo(DomainChef.cookBoolean(intercountryAdoptDisruptedIndicator))));
    assertThat(persistent.getIntercountryAdoptDissolvedIndicator(),
        is(equalTo(DomainChef.cookBoolean(intercountryAdoptDissolvedIndicator))));
    assertThat(persistent.getMedEligibilityApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(medEligibilityApplicationIndicatorVar))));
    assertThat(persistent.getMinorNmdParentIndicator(),
        is(equalTo(DomainChef.cookBoolean(minorNmdParentIndicator))));
    assertThat(persistent.getParentalRightsLimitedIndicator(),
        is(equalTo(DomainChef.cookBoolean(parentalRightsLimitedIndicator))));
    assertThat(persistent.getParentalRightsTermintnIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(parentalRightsTermintnIndicatorVar))));
    assertThat(persistent.getPaternityIndividualIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(paternityIndividualIndicatorVar))));
    assertThat(persistent.getPostsecVocIndicator(),
        is(equalTo(DomainChef.cookBoolean(postsecVocIndicator))));
    assertThat(persistent.getPreviouslyAdoptedCode(), is(equalTo(previouslyAdoptedCode)));
    assertThat(persistent.getSafelySurrendedBabiesIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(safelySurrendedBabiesIndicatorVar))));
    assertThat(persistent.getSaw1EligApplicationIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(saw1EligApplicationIndicatorVar))));
    assertThat(persistent.getSawsCaseSerialNumber(), is(equalTo(sawsCaseSerialNumber)));
    assertThat(persistent.getSijsScheduledInterviewDate(),
        is(equalTo(DomainChef.uncookDateString(sijsScheduledInterviewDate))));
    assertThat(persistent.getSiiNextScreeningDueDate(),
        is(equalTo(DomainChef.uncookDateString(siiNextScreeningDueDate))));
    assertThat(persistent.getSsiSspApplicationIndicator(),
        is(equalTo(DomainChef.cookBoolean(ssiSspApplicationIndicator))));
    assertThat(persistent.getTribalAncestryNotifctnIndicatorVar(),
        is(equalTo(DomainChef.cookBoolean(tribalAncestryNotifctnIndicatorVar))));
    assertThat(persistent.getTribalCustomaryAdoptionDate(),
        is(equalTo(DomainChef.uncookDateString(tribalCustomaryAdoptionDate))));
    assertThat(persistent.getTribalCustomaryAdoptionIndicator(),
        is(equalTo(DomainChef.cookBoolean(tribalCustomaryAdoptionIndicator))));

  }

  @Override
  @Test
  public void testJSONCreatorConstructor() throws Exception {

    ChildClient vchc = validChildClient();

    ChildClient domain = new ChildClient(victimClientId, adoptableCode, adoptedAge,
        afdcFcEligibilityIndicatorVar, allEducationInfoOnFileIndicator,
        allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc, attemptToAcquireHlthInfoDesc,
        awolAbductedCode, birthHistoryIndicatorVar, childIndianAncestryIndicator, collegeIndicator,
        currentCaseId, deathCircumstancesType, disabilityDiagnosedCode, drmsHePassportDocOld,
        drmsHealthEducPassportDoc, drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar,
        foodStampsApplicationDate, foodStampsApplicationIndicator, icwaEligibilityCode,
        intercountryAdoptDisruptedIndicator, intercountryAdoptDissolvedIndicator,
        medEligibilityApplicationIndicatorVar, minorNmdParentIndicator,
        parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);

    assertThat(domain.getVictimClientId(), is(equalTo(vchc.getVictimClientId())));
    assertThat(domain.getAdoptableCode(), is(equalTo(vchc.getAdoptableCode())));
    assertThat(domain.getAdoptedAge(), is(equalTo(vchc.getAdoptedAge())));
    assertThat(domain.getAfdcFcEligibilityIndicatorVar(),
        is(equalTo(vchc.getAfdcFcEligibilityIndicatorVar())));
    assertThat(domain.getAllEducationInfoOnFileIndicator(),
        is(equalTo(vchc.getAllEducationInfoOnFileIndicator())));
    assertThat(domain.getAllHealthInfoOnFileIndicator(),
        is(equalTo(vchc.getAllHealthInfoOnFileIndicator())));
    assertThat(domain.getAttemptToAcquireEducInfoDesc(),
        is(equalTo(vchc.getAttemptToAcquireEducInfoDesc())));
    assertThat(domain.getAttemptToAcquireHlthInfoDesc(),
        is(equalTo(vchc.getAttemptToAcquireHlthInfoDesc())));
    assertThat(domain.getAwolAbductedCode(), is(equalTo(vchc.getAwolAbductedCode())));
    assertThat(domain.getBirthHistoryIndicatorVar(),
        is(equalTo(vchc.getBirthHistoryIndicatorVar())));
    assertThat(domain.getChildIndianAncestryIndicator(),
        is(equalTo(vchc.getChildIndianAncestryIndicator())));
    assertThat(domain.getCollegeIndicator(), is(equalTo(vchc.getCollegeIndicator())));
    assertThat(domain.getCurrentCaseId(), is(equalTo(vchc.getCurrentCaseId())));
    assertThat(domain.getDeathCircumstancesType(), is(equalTo(vchc.getDeathCircumstancesType())));
    assertThat(domain.getDisabilityDiagnosedCode(), is(equalTo(vchc.getDisabilityDiagnosedCode())));
    assertThat(domain.getDrmsHePassportDocOld(), is(equalTo(vchc.getDrmsHePassportDocOld())));
    assertThat(domain.getDrmsHealthEducPassportDoc(),
        is(equalTo(vchc.getDrmsHealthEducPassportDoc())));
    assertThat(domain.getDrmsVoluntaryPlcmntAgrmntDoc(),
        is(equalTo(vchc.getDrmsVoluntaryPlcmntAgrmntDoc())));
    assertThat(domain.getFc2EligApplicationIndicatorVar(),
        is(equalTo(vchc.getFc2EligApplicationIndicatorVar())));
    assertThat(domain.getFoodStampsApplicationDate(),
        is(equalTo(vchc.getFoodStampsApplicationDate())));
    assertThat(domain.getFoodStampsApplicationIndicator(),
        is(equalTo(vchc.getFoodStampsApplicationIndicator())));
    assertThat(domain.getIcwaEligibilityCode(), is(equalTo(vchc.getIcwaEligibilityCode())));
    assertThat(domain.getIntercountryAdoptDisruptedIndicator(),
        is(equalTo(vchc.getIntercountryAdoptDisruptedIndicator())));
    assertThat(domain.getIntercountryAdoptDissolvedIndicator(),
        is(equalTo(vchc.getIntercountryAdoptDissolvedIndicator())));
    assertThat(domain.getMedEligibilityApplicationIndicatorVar(),
        is(equalTo(vchc.getMedEligibilityApplicationIndicatorVar())));
    assertThat(domain.getMinorNmdParentIndicator(), is(equalTo(vchc.getMinorNmdParentIndicator())));
    assertThat(domain.getParentalRightsLimitedIndicator(),
        is(equalTo(vchc.getParentalRightsLimitedIndicator())));
    assertThat(domain.getParentalRightsTermintnIndicatorVar(),
        is(equalTo(vchc.getParentalRightsTermintnIndicatorVar())));
    assertThat(domain.getPaternityIndividualIndicatorVar(),
        is(equalTo(vchc.getPaternityIndividualIndicatorVar())));
    assertThat(domain.getPostsecVocIndicator(), is(equalTo(vchc.getPostsecVocIndicator())));
    assertThat(domain.getPreviouslyAdoptedCode(), is(equalTo(vchc.getPreviouslyAdoptedCode())));
    assertThat(domain.getSafelySurrendedBabiesIndicatorVar(),
        is(equalTo(vchc.getSafelySurrendedBabiesIndicatorVar())));
    assertThat(domain.getSaw1EligApplicationIndicatorVar(),
        is(equalTo(vchc.getSaw1EligApplicationIndicatorVar())));
    assertThat(domain.getSawsCaseSerialNumber(), is(equalTo(vchc.getSawsCaseSerialNumber())));
    assertThat(domain.getSijsScheduledInterviewDate(),
        is(equalTo(vchc.getSijsScheduledInterviewDate())));
    assertThat(domain.getSiiNextScreeningDueDate(), is(equalTo(vchc.getSiiNextScreeningDueDate())));
    assertThat(domain.getSsiSspApplicationIndicator(),
        is(equalTo(vchc.getSsiSspApplicationIndicator())));
    assertThat(domain.getTribalAncestryNotifctnIndicatorVar(),
        is(equalTo(vchc.getTribalAncestryNotifctnIndicatorVar())));
    assertThat(domain.getTribalCustomaryAdoptionDate(),
        is(equalTo(vchc.getTribalCustomaryAdoptionDate())));
    assertThat(domain.getTribalCustomaryAdoptionIndicator(),
        is(equalTo(vchc.getTribalCustomaryAdoptionIndicator())));
  }

  @Test
  public void createWithDefaultsShouldInitializeWithPassedInValues() {
    String clientId = "1";
    ChildClient client = ChildClient.createWithDefaults(clientId);
    assertEquals("Expected victim client id field to have been initialized with value", clientId,
        client.getVictimClientId());
  }

  @Test
  public void createWithDefaultsShouldInitializeWithDefaultValues() {
    String clientId = "1";
    ChildClient client = ChildClient.createWithDefaults(clientId);
    assertEquals("Expected adoptableCode field to have been initialized with default value", "NA",
        client.getAdoptableCode());
    assertEquals("Expected adoptedAge field to have been initialized with default value",
        new Short("0"), client.getAdoptedAge());
    assertEquals(
        "Expected afdcFcEligibilityIndicatorVar field to have been initialized with default value",
        false, client.getAfdcFcEligibilityIndicatorVar());
    assertEquals(
        "Expected allEducationInfoOnFileIndicator field to have been initialized with default value",
        false, client.getAllEducationInfoOnFileIndicator());
    assertEquals(
        "Expected allHealthInfoOnFileIndicator field to have been initialized with default value",
        false, client.getAllHealthInfoOnFileIndicator());
    assertEquals(
        "Expected attemptToAcquireEducInfoDesc field to have been initialized with default value",
        "", client.getAttemptToAcquireEducInfoDesc());
    assertEquals(
        "Expected attemptToAcquireHlthInfoDesc field to have been initialized with default value",
        "", client.getAttemptToAcquireHlthInfoDesc());
    assertEquals("Expected awolAbductedCode field to have been initialized with default value", "",
        client.getAwolAbductedCode());
    assertEquals(
        "Expected birthHistoryIndicatorVar field to have been initialized with default value",
        false, client.getBirthHistoryIndicatorVar());
    assertEquals(
        "Expected childIndianAncestryIndicator field to have been initialized with default value",
        false, client.getChildIndianAncestryIndicator());
    assertEquals("Expected collegeIndicator field to have been initialized with default value",
        false, client.getCollegeIndicator());
    assertEquals("Expected currentCaseId field to have been initialized with default value", "",
        client.getCurrentCaseId());
    assertEquals(
        "Expected deathCircumstancesType field to have been initialized with default value",
        new Short("0"), client.getDeathCircumstancesType());
    assertEquals(
        "Expected disabilityDiagnosedCode field to have been initialized with default value", "D",
        client.getDisabilityDiagnosedCode());
    assertEquals("Expected drmsHePassportDocOld field to have been initialized with default value",
        "", client.getDrmsHePassportDocOld());
    assertEquals(
        "Expected drmsHealthEducPassportDoc field to have been initialized with default value", "",
        client.getDrmsHealthEducPassportDoc());
    assertEquals(
        "Expected drmsVoluntaryPlcmntAgrmntDoc field to have been initialized with default value",
        "", client.getDrmsVoluntaryPlcmntAgrmntDoc());
    assertEquals(
        "Expected fc2EligApplicationIndicatorVar field to have been initialized with default value",
        false, client.getFc2EligApplicationIndicatorVar());
    assertEquals(
        "Expected foodStampsApplicationDate field to have been initialized with default value", "",
        client.getFoodStampsApplicationDate());
    assertEquals(
        "Expected foodStampsApplicationIndicator field to have been initialized with default value",
        false, client.getFoodStampsApplicationIndicator());
    assertEquals("Expected icwaEligibilityCode field to have been initialized with default value",
        "U", client.getIcwaEligibilityCode());
    assertEquals(
        "Expected intercountryAdoptDisruptedIndicator field to have been initialized with default value",
        false, client.getIntercountryAdoptDisruptedIndicator());
    assertEquals(
        "Expected intercountryAdoptDissolvedIndicator field to have been initialized with default value",
        false, client.getIntercountryAdoptDissolvedIndicator());
    assertEquals(
        "Expected medEligibilityApplicationIndicatorVar field to have been initialized with default value",
        false, client.getMedEligibilityApplicationIndicatorVar());
    assertEquals(
        "Expected minorNmdParentIndicator field to have been initialized with default value", false,
        client.getMinorNmdParentIndicator());
    assertEquals(
        "Expected parentalRightsLimitedIndicator field to have been initialized with default value",
        false, client.getParentalRightsLimitedIndicator());
    assertEquals(
        "Expected parentalRightsTermintnIndicatorVar field to have been initialized with default value",
        false, client.getParentalRightsTermintnIndicatorVar());
    assertEquals(
        "Expected paternityIndividualIndicatorVar field to have been initialized with default value",
        false, client.getPaternityIndividualIndicatorVar());
    assertEquals("Expected postsecVocIndicator field to have been initialized with default value",
        false, client.getPostsecVocIndicator());
    assertEquals("Expected previouslyAdoptedCode field to have been initialized with default value",
        "X", client.getPreviouslyAdoptedCode());
    assertEquals(
        "Expected safelySurrendedBabiesIndicatorVar field to have been initialized with default value",
        false, client.getSaw1EligApplicationIndicatorVar());
    assertEquals(
        "Expected saw1EligApplicationIndicatorVar field to have been initialized with default value",
        false, client.getSaw1EligApplicationIndicatorVar());
    assertEquals("Expected sawsCaseSerialNumber field to have been initialized with default value",
        new Integer(0), client.getSawsCaseSerialNumber());
    assertEquals(
        "Expected sijsScheduledInterviewDate field to have been initialized with default value", "",
        client.getSijsScheduledInterviewDate());
    assertEquals(
        "Expected siiNextScreeningDueDate field to have been initialized with default value", "",
        client.getSiiNextScreeningDueDate());
    assertEquals(
        "Expected ssiSspApplicationIndicator field to have been initialized with default value",
        false, client.getSsiSspApplicationIndicator());
    assertEquals(
        "Expected tribalAncestryNotifctnIndicatorVar field to have been initialized with default value",
        false, client.getTribalAncestryNotifctnIndicatorVar());
    assertEquals(
        "Expected tribalCustomaryAdoptionDate field to have been initialized with default value",
        "", client.getTribalCustomaryAdoptionDate());
    assertEquals(
        "Expected tribalCustomaryAdoptionIndicator field to have been initialized with default value",
        false, client.getTribalCustomaryAdoptionIndicator());

  }

  @Override
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(ChildClient.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Override
  @Test
  public void testSerializesToJSON() throws Exception {
    ChildClient validChildClient = validDomainChildClient();
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class));

    assertThat(MAPPER.writeValueAsString(validChildClient), is(equalTo(expected)));
  }

  @Override
  @Test
  public void testDeserializesFromJSON() throws Exception {
    ChildClient validChildClient = validDomainChildClient();
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"),
        ChildClient.class), is(equalTo(validChildClient)));

  }

  @Override
  @Test
  public void testSuccessWithValid() throws Exception {
    ChildClient domain = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * victimClientId test
   */
  @Test
  public void failWhenVictimClientIdNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setVictimClientId(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals("victimClientId may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenVictimClientIdEmpty() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setVictimClientId("").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals("victimClientId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * Adaptable code test
   */
  @Test
  public void testFailAdoptableCodeEmpty() throws Exception {

    ChildClient domain = new ChildClientResourceBuilder().setAdoptableCode("").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("adoptableCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailAdoptableCodeInvalid() throws Exception {

    ChildClient domain = new ChildClientResourceBuilder().setAdoptableCode("Z").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("adoptableCode must be one of [N, Y, NA]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testFailAdoptableCodeNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAdoptableCode(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("adoptableCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testSuccessAdoptableCodeN() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAdoptableCode("N").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));

    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testSuccessAdoptableCodeY() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAdoptableCode("Y").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));

    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testSuccessAdoptableCodeNA() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAdoptableCode("NA").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));

    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * adoptedAge test
   */
  @Test
  public void testSuccessAdoptedAgeValid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAdoptedAge(adoptedAge).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));

    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testFailAdoptedAgeNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAdoptedAge(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      if (message.getMessage().equals("adoptedAge may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  /*
   * afdcFcEligibilityIndicatorVar test
   */
  @Test
  public void failWhenAfdcFcEligibilityIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAfdcFcEligibilityIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("afdcFcEligibilityIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenAfdcFcEligibilityIndicatorVarEmpty() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAfdcFcEligibilityIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("afdcFcEligibilityIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * allEducationInfoOnFileIndicator test
   */
  @Test
  public void failWhenAllEducationInfoOnFileIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAllEducationInfoOnFileIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("allEducationInfoOnFileIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * allHealthInfoOnFileIndicator test
   */
  @Test
  public void failWhenAllHealthInfoOnFileIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAllHealthInfoOnFileIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("allHealthInfoOnFileIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * attemptToAcquireEducInfoDesc test
   */
  @Test
  public void failWhenAttemptToAcquireEducInfoDescNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAttemptToAcquireEducInfoDesc(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("attemptToAcquireEducInfoDesc may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * attemptToAcquireHlthInfoDesc test
   */
  @Test
  public void failWhenAttemptToAcquireHlthInfoDescNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAttemptToAcquireHlthInfoDesc(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("attemptToAcquireHlthInfoDesc may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * awolAbductedCode test
   */
  @Test
  public void testFailAwolAbductedCodeNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setAwolAbductedCode(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("awolAbductedCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * birthHistoryIndicatorVar test
   */
  @Test
  public void failWhenBirthHistoryIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setBirthHistoryIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("birthHistoryIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * childIndianAncestryIndicator test
   */
  @Test
  public void failWhenChildIndianAncestryIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setChildIndianAncestryIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("childIndianAncestryIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failWhenCurrentCaseIdTooLong() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setCurrentCaseId("12345678901").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("currentCaseId size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * deathCircumstancesType test
   */
  @Test
  public void failWhenDeathCircumstancesTypeNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setDeathCircumstancesType(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("deathCircumstancesType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
 }

  @Test
  public void shouldFailWhenInvalidDisabilityDiagnosedCode() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setDisabilityDiagnosedCode("X").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("disabilityDiagnosedCode must be one of [N, Y, D]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }
  
  @Test
  public void shouldFailIfDrmshealtheducpassportDocLengthGreaterThanTen() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setDrmsHealthEducPassportDoc("12345678901").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsHealthEducPassportDoc size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }
  
  @Test
  public void shouldFailIfDrmsVoluntaryPlcmntAgrmntDocLengthGreaterThanTen() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setDrmsVoluntaryPlcmntAgrmntDoc("12345678901").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsVoluntaryPlcmntAgrmntDoc size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }
  
  /*
   * fc2EligApplicationIndicatorVar test
   */
  @Test
  public void failWhenFc2EligApplicationIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setFc2EligApplicationIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("fc2EligApplicationIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * foodStampsApplicationDate test
   */
  @Test
  public void failWhenFoodStampsApplicationDateInvalid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setFoodStampsApplicationDate("2000:01:01").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("foodStampsApplicationDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * foodStampsApplicationIndicator test
   */
  @Test
  public void failWhenFoodStampsApplicationIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setFoodStampsApplicationIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("foodStampsApplicationIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * icwaEligibilityCode test
   */
  @Test
  public void testFailIcwaEligibilityCodeInvalid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setIcwaEligibilityCode("Z").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("icwaEligibilityCode must be one of [Y, N, U, P]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  @Test
  public void testFailIcwaEligibilityCodeNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setIcwaEligibilityCode(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("icwaEligibilityCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }
  
  /*
   * intercountryAdoptDisruptedIndicator test
   */
  @Test
  public void failWhenIntercountryAdoptDisruptedIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setIntercountryAdoptDisruptedIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("intercountryAdoptDisruptedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * intercountryAdoptDissolvedIndicator test
   */
  @Test
  public void failWhenIntercountryAdoptDissolvedIndicatorNull() throws Exception {
   ChildClient domain = new ChildClientResourceBuilder().setIntercountryAdoptDissolvedIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("intercountryAdoptDissolvedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * medEligibilityApplicationIndicatorVar test
   */
  @Test
  public void failWhenMedEligibilityApplicationIndicatorVarNull() throws Exception {
   ChildClient domain = new ChildClientResourceBuilder().setMedEligibilityApplicationIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("medEligibilityApplicationIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * minorNmdParentIndicator test
   */
  @Test
  public void failWhenMinorNmdParentIndicatorNull() throws Exception {
   ChildClient domain = new ChildClientResourceBuilder().setMinorNmdParentIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("minorNmdParentIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * parentalRightsLimitedIndicator test
   */
  @Test
  public void failWhenParentalRightsLimitedIndicatorNull() throws Exception {
   ChildClient domain = new ChildClientResourceBuilder().setParentalRightsLimitedIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("parentalRightsLimitedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }


  /*
   * parentalRightsTermintnIndicatorVar test
   */
  @Test
  public void failWhenParentalRightsTermintnIndicatorVarNull() throws Exception {
      ChildClient domain = new ChildClientResourceBuilder().setParentalRightsTermintnIndicatorVar(null).buildChildClient();
       
       validator = Validation.buildDefaultValidatorFactory().getValidator();
       messageBuilder.addDomainValidationError(validator.validate(domain));
       Boolean theErrorDetected = false;

       List<ErrorMessage> validationErrors = messageBuilder.getMessages();
       for (ErrorMessage message : validationErrors) {
//         System.out.println(message.getMessage());
         if (message.getMessage().equals("parentalRightsTermintnIndicatorVar may not be null")) {
           theErrorDetected = true;
         }
       }
       assertThat(theErrorDetected, is(true));    
   }

  /*
   * paternityIndividualIndicatorVar test
   */
  @Test
  public void failWhenPaternityIndividualIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setPaternityIndividualIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("paternityIndividualIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }
  /*
   * previouslyAdoptedCode test
   */
  @Test
  public void testFailPreviouslyAdoptedCodeInvalid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setPreviouslyAdoptedCode("Z").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("previouslyAdoptedCode must be one of [Y, N, U, X]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  @Test
  public void testFailPreviouslyAdoptedCodeNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setPreviouslyAdoptedCode(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("previouslyAdoptedCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * safelySurrendedBabiesIndicatorVar test
   */
  @Test
  public void failWhenSafelySurrendedBabiesIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setSafelySurrendedBabiesIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("safelySurrendedBabiesIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
   }

  /*
   * saw1EligApplicationIndicatorVar test
   */
  @Test
  public void failWhenSaw1EligApplicationIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setSaw1EligApplicationIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("saw1EligApplicationIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * sawsCaseSerialNumber test
   */
  @Test
  public void failWhenSawsCaseSerialNumberNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setSawsCaseSerialNumber(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sawsCaseSerialNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * sijsScheduledInterviewDate test
   */
  @Test
  public void failWhenSijsScheduledInterviewDateInvalid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setSijsScheduledInterviewDate("2000:01:01").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sijsScheduledInterviewDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * siiNextScreeningDueDate test
   */
  @Test
  public void failWhenSiiNextScreeningDueDateInvalid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setSiiNextScreeningDueDate("2000:01:01").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("siiNextScreeningDueDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * ssiSspApplicationIndicator test
   */
  @Test
  public void failWhenSsiSspApplicationIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setSsiSspApplicationIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("ssiSspApplicationIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }


  /*
   * tribalAncestryNotifctnIndicatorVar test
   */
  @Test
  public void failWhenTribalAncestryNotifctnIndicatorVarNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setTribalAncestryNotifctnIndicatorVar(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("tribalAncestryNotifctnIndicatorVar may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * tribalCustomaryAdoptionDate test
   */
  @Test
  public void failWhenTribalCustomaryAdoptionDateInvalid() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setTribalCustomaryAdoptionDate("10-01-2009").buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("tribalCustomaryAdoptionDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }

  /*
   * tribalCustomaryAdoptionIndicator test
   */
  @Test
  public void failWhenTribalCustomaryAdoptionIndicatorNull() throws Exception {
    ChildClient domain = new ChildClientResourceBuilder().setTribalCustomaryAdoptionIndicator(null).buildChildClient();
    
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(domain));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("tribalCustomaryAdoptionIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));    
  }


  private ChildClient validChildClient()
      throws JsonParseException, JsonMappingException, IOException {
    ChildClient vchc = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ChildClient/valid/valid.json"), ChildClient.class);
    return vchc;
  }

  private ChildClient validDomainChildClient() {
    ChildClient domain = new ChildClient(victimClientId, adoptableCode, adoptedAge,
        afdcFcEligibilityIndicatorVar, allEducationInfoOnFileIndicator,
        allHealthInfoOnFileIndicator, attemptToAcquireEducInfoDesc, attemptToAcquireHlthInfoDesc,
        awolAbductedCode, birthHistoryIndicatorVar, childIndianAncestryIndicator, collegeIndicator,
        currentCaseId, deathCircumstancesType, disabilityDiagnosedCode, drmsHePassportDocOld,
        drmsHealthEducPassportDoc, drmsVoluntaryPlcmntAgrmntDoc, fc2EligApplicationIndicatorVar,
        foodStampsApplicationDate, foodStampsApplicationIndicator, icwaEligibilityCode,
        intercountryAdoptDisruptedIndicator, intercountryAdoptDissolvedIndicator,
        medEligibilityApplicationIndicatorVar, minorNmdParentIndicator,
        parentalRightsLimitedIndicator, parentalRightsTermintnIndicatorVar,
        paternityIndividualIndicatorVar, postsecVocIndicator, previouslyAdoptedCode,
        safelySurrendedBabiesIndicatorVar, saw1EligApplicationIndicatorVar, sawsCaseSerialNumber,
        sijsScheduledInterviewDate, siiNextScreeningDueDate, ssiSspApplicationIndicator,
        tribalAncestryNotifctnIndicatorVar, tribalCustomaryAdoptionDate,
        tribalCustomaryAdoptionIndicator);

    return domain;
  }

  @Override
  public void testSuccessWithOptionalsNotIncluded() throws Exception {
    
  }

}
