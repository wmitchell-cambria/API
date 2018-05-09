package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.ReferralClientResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ReferralClientTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String referralId = "a";
  private String clientId = "b";
  private String approvalNumber = "c";
  private Short approvalStatusType = 1;
  private Short dispositionClosureReasonType = 2;
  private String dispositionCode = "d";
  private String dispositionDate = "1973-11-22";
  private Boolean selfReportedIndicator = Boolean.TRUE;
  private Boolean staffPersonAddedIndicator = Boolean.TRUE;
  private String dispositionClosureDescription = "e";
  private Short ageNumber = 3;
  private String agePeriodCode = "f";
  private String countySpecificCode = "g";
  private Boolean mentalHealthIssuesIndicator = Boolean.TRUE;
  private Boolean alcoholIndicator = null;
  private Boolean drugIndicator = Boolean.FALSE;

  public ReferralClientTest() throws ParseException {}

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() throws Exception {
    
    messageBuilder = new MessageBuilder();
    
    CrudsDao crudsDao = mock(CrudsDao.class);

    ReferralClient validReferralClient = validReferralClient();

    when(crudsDao.find(any())).thenReturn(mock(Referral.class));

  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class));

    assertThat(MAPPER.writeValueAsString(validReferralClient()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"),
        ReferralClient.class), is(equalTo(validReferralClient())));
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    ReferralClient domain =
        new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
            dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
            referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);
    gov.ca.cwds.data.persistence.cms.ReferralClient persistent =
        new gov.ca.cwds.data.persistence.cms.ReferralClient(domain, "lastUpdatedId", new Date());

    ReferralClient totest = new ReferralClient(persistent);
    assertThat(totest.getReferralId(), is(equalTo(persistent.getReferralId())));
    assertThat(totest.getClientId(), is(equalTo(persistent.getClientId())));
    assertThat(totest.getApprovalNumber(), is(equalTo(persistent.getApprovalNumber())));
    assertThat(totest.getApprovalStatusType(), is(equalTo(persistent.getApprovalStatusType())));
    assertThat(totest.getDispositionClosureReasonType(),
        is(equalTo(persistent.getDispositionClosureReasonType())));
    assertThat(totest.getDispositionCode(), is(equalTo(persistent.getDispositionCode())));
    assertThat(totest.getDispositionDate(),
        is(equalTo(df.format(persistent.getDispositionDate()))));
    assertThat(totest.getSelfReportedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getSelfReportedIndicator()))));
    assertThat(totest.getStaffPersonAddedIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getStaffPersonAddedIndicator()))));
    assertThat(totest.getDispositionClosureDescription(),
        is(equalTo(persistent.getDispositionClosureDescription())));
    assertThat(totest.getAgeNumber(), is(equalTo(persistent.getAgeNumber())));
    assertThat(totest.getAgePeriodCode(), is(equalTo(persistent.getAgePeriodCode())));
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getMentalHealthIssuesIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getMentalHealthIssuesIndicator()))));
    assertThat(totest.getAlcoholIndicator(), is(Boolean.FALSE));
    assertThat(totest.getDrugIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getDrugIndicator()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    ReferralClient referralClient =
        new ReferralClient(approvalNumber, approvalStatusType, dispositionClosureReasonType,
            dispositionCode, dispositionDate, selfReportedIndicator, staffPersonAddedIndicator,
            referralId, clientId, dispositionClosureDescription, ageNumber, agePeriodCode,
            countySpecificCode, mentalHealthIssuesIndicator, alcoholIndicator, drugIndicator);

    assertThat(referralClient.getReferralId(), is(equalTo(referralId)));
    assertThat(referralClient.getClientId(), is(equalTo(clientId)));
    assertThat(referralClient.getApprovalNumber(), is(equalTo(approvalNumber)));
    assertThat(referralClient.getApprovalStatusType(), is(equalTo(approvalStatusType)));
    assertThat(referralClient.getDispositionClosureReasonType(),
        is(equalTo(dispositionClosureReasonType)));
    assertThat(referralClient.getDispositionCode(), is(equalTo(dispositionCode)));
    assertThat(referralClient.getDispositionDate(), is(equalTo(dispositionDate)));
    assertThat(referralClient.getSelfReportedIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getStaffPersonAddedIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getDispositionClosureDescription(),
        is(equalTo(dispositionClosureDescription)));
    assertThat(referralClient.getAgeNumber(), is(equalTo(ageNumber)));
    assertThat(referralClient.getAgePeriodCode(), is(equalTo(agePeriodCode)));
    assertThat(referralClient.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(referralClient.getMentalHealthIssuesIndicator(), is(equalTo(Boolean.TRUE)));
    assertThat(referralClient.getAlcoholIndicator(), is(nullValue()));
    assertThat(referralClient.getDrugIndicator(), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void testCreateWithDefaultCreatesWithValues() {
    Boolean selfReported = true;
    String referralId = "referralId";
    String clientId = "clientId";
    String countyCode = "countyCode";
    Short approvalCode = 1;
    String dispositionCode = "";
    Short ageNumber = 12;
    String agePeriodCode = "Y";

    ReferralClient referralClient =
        ReferralClient.createWithDefault(selfReported, staffPersonAddedIndicator, dispositionCode,
            referralId, clientId, countyCode, approvalCode, ageNumber, agePeriodCode);

    assertEquals("Expected selfReported field to be initialized with values", selfReported,
        referralClient.getSelfReportedIndicator());
    assertEquals("Expected referralId field to be initialized with values", referralId,
        referralClient.getReferralId());
    assertEquals("Expected clientId field to be initialized with values", clientId,
        referralClient.getClientId());
    assertEquals("Expected countyCode field to be initialized with values", countyCode,
        referralClient.getCountySpecificCode());
    assertEquals("Expected approvalCode field to be initialized with values", approvalCode,
        referralClient.getApprovalStatusType());
    assertEquals("Expected ageNumber field to be initialized with values", ageNumber,
        referralClient.getAgeNumber());
    assertEquals("Expected agePeriodCode field to be initialized with values", agePeriodCode,
        referralClient.getAgePeriodCode());
  }

  @Test
  public void testCreateWithDefaultCreatesWithDefaultValues() {
    Boolean selfReported = true;
    String referralId = "referralId";
    String clientId = "clientId";
    String countyCode = "countyCode";
    Short approvalCode = 1;
    Short ageNumber = 12;
    String agePeriodCode = "Y";

    String approvalNumber = "";
    Short dispositionClosureReasonType = 0;
    String dispositionCode = "A";
    String dispositionDate = "";
    Boolean staffPersonAddedIndicator = false;
    String dispositionClosureDescription = "";
    Boolean mentalHealthIssuesIndicator = false;
    Boolean alcoholIndicator = false;
    Boolean drugIndicator = false;

    ReferralClient referralClient =
        ReferralClient.createWithDefault(selfReported, staffPersonAddedIndicator, dispositionCode,
            referralId, clientId, countyCode, approvalCode, ageNumber, agePeriodCode);

    assertEquals("Expected approvalNumber field to be initialized with default values",
        approvalNumber, referralClient.getApprovalNumber());
    assertEquals(
        "Expected dispositionClosureReasonType field to be initialized with default values",
        dispositionClosureReasonType, referralClient.getDispositionClosureReasonType());
    assertEquals("Expected dispositionCode field to be initialized with default values",
        dispositionCode, referralClient.getDispositionCode());
    assertEquals("Expected dispositionDat field to be initialized with default values",
        dispositionDate, referralClient.getDispositionDate());
    assertEquals("Expected staffPersonAddedIndicato field to be initialized with default values",
        staffPersonAddedIndicator, referralClient.getStaffPersonAddedIndicator());
    assertEquals(
        "Expected dispositionClosureDescription field to be initialized with default values",
        dispositionClosureDescription, referralClient.getDispositionClosureDescription());
    assertEquals("Expected mentalHealthIssuesIndicator field to be initialized with default values",
        mentalHealthIssuesIndicator, referralClient.getMentalHealthIssuesIndicator());
    assertEquals("Expected alcoholIndicator field to be initialized with default values",
        alcoholIndicator, referralClient.getAlcoholIndicator());
    assertEquals("Expected drugIndicator field to be initialized with default values",
        drugIndicator, referralClient.getDrugIndicator());
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ReferralClient.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(validReferralClient()));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    ReferralClient referralClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/optionalsNotIncluded.json"),
        ReferralClient.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * approvalNumber Tests
   */
  @Test
  public void successWhenApprovalNumberEmpty() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setApprovalNumber("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenApprovalNumberNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setApprovalNumber(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenApprovalNumberTooLong() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setApprovalNumber("12345678901").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("approvalNumber size must be between 0 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * dispositionClosureReasonType Tests
   */
  @Test
  public void failsWhenDispositionClosureReasonTypeNull() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setDispositionClosureDescription(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dispositionClosureDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenDispositionClosureReasonTypeZero() throws Exception {
    Short dispositionClosureReasonType = (short) 0;

    ReferralClient referralClient = new ReferralClientResourceBuilder().setDispositionClosureReasonType(dispositionClosureReasonType).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * approvalStatusType Tests
   */
  @Test
  public void failsWhenApprovalStatusTypeNull() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setApprovalStatusType(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("approvalStatusType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * dispositionCode Tests
   */
  @Test
  public void failsWhenDispositionCodeNull() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setDispositionCode(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dispositionCode may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenDispositionCodeEmpty() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setDispositionCode("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenDispositionCodeTooLong() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setDispositionCode("zz").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dispositionCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenDispositionCodeInvalid() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setDispositionCode("Z").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dispositionCode must be one of [A, I, S, X, ]")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * dispositionDate Tests
   */
  @Test
  public void successWhenDispositionDateEmpty() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setDispositionDate("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenDispositionDateNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setDispositionDate(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenDispositionDateWrongFormat() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setDispositionDate("06/18/1992").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dispositionDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * selfReportedIndicator Tests
   */
  @Test
  public void failsWhenSelfReportedIndicatorNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setSelfReportedIndicator(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("selfReportedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * staffPersonAddedIndicator Tests
   */

  @Test
  public void failsWhenStaffPersonAddedIndicatorNull() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder().setStaffPersonAddedIndicator(null)
        .buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("staffPersonAddedIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * referralId Tests
   */
  @Test
  public void failsWhenReferralIdNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setReferralId(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  @Test
  public void failsWhenReferralIdTooLong() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setReferralId("12345678901").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenReferralIdAllWhiteSpace() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setReferralId("  ").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * clientId Tests
   */
  @Test
  public void failsWhenClientIdNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setClientId(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("clientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenClientIdEmpty() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setClientId("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("clientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenClientIdTooLong() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setClientId("12345678901").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("clientId size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenClientIdAllWhiteSpace() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setClientId("  ").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("clientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * dispositionClosureDescription Tests
   */
  @Test
  public void failsWhenDispositionClosureDescriptionNull() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder()
        .setDispositionClosureDescription(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dispositionClosureDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void successWhenDispositionClosureDescriptionEmpty() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder()
        .setDispositionClosureDescription("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * ageNumber Tests
   */
  @Test
  public void failsWhenAgeNumberNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setAgeNumber(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("ageNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * agePeriodCode Tests
   */
  @Test
  public void shouldConvertNullValuesToDefaultEmpty() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setAgePeriodCode(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
    assertEquals("", referralClient.getAgePeriodCode());
  }

  @Test
  public void successWhenAgePeriodCodeEmpty() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setAgePeriodCode("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenAgePeriodCodeTooLong() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setAgePeriodCode("YM").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("agePeriodCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  @Test
  public void successWhenAgePeriodCodeAllWhiteSpace() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setAgePeriodCode("  ").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setCountySpecificCode(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountySpecificCodeEmpty() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setCountySpecificCode("").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountySpecificCodeTooLong() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setCountySpecificCode("100").buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countySpecificCode size must be between 1 and 2")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * mentalHealthIssuesIndicator Tests
   */
  @Test
  public void successWhenMentalHealthIssuesIndicatorEmpty() throws Exception {
    ReferralClient referralClient = MAPPER.readValue(
        fixture(
            "fixtures/domain/legacy/ReferralClient/valid/mentalHealthIssuesIndicatorEmpty.json"),
        ReferralClient.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenMentalHealthIssuesIndicatorNull() throws Exception {
    ReferralClient referralClient = new ReferralClientResourceBuilder()
        .setMentalHealthIssuesIndicator(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * alcoholIndicator Tests
   */
  @Test
  public void successWhenAlcoholIndicatorEmpty() throws Exception {
    ReferralClient referralClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/alcoholIndicatorEmpty.json"),
        ReferralClient.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenAlcoholIndicatorNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setAlcoholIndicator(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * drugIndicator Tests
   */
  @Test
  public void successWhenDrugIndicatorEmpty() throws Exception {
    ReferralClient referralClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/drugIndicatorEmpty.json"),
        ReferralClient.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenDrugIndicatorNull() throws Exception {
    ReferralClient referralClient =
        new ReferralClientResourceBuilder().setDrugIndicator(null).buildReferralClient();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(referralClient));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * Utils
   */
  private ReferralClient validReferralClient()
      throws JsonParseException, JsonMappingException, IOException {
    ReferralClient referralClient = MAPPER.readValue(
        fixture("fixtures/domain/legacy/ReferralClient/valid/valid.json"), ReferralClient.class);
    return referralClient;
  }
}
