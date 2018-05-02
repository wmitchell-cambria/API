package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.fixture.LongTextResourceBuilder;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * @author CWDS API Team
 *
 */
public class LongTextTest {

  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private LongText validLongText = validLongText();

  private String id = "ABC1234567";
  private String countySpecificCode = "57";
  private String textDescription = "Child Education level is below average";

  private MessageBuilder messageBuilder;
  private Validator validator;
  
  /**
   * 
   */
  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();
  }

  /*
   * Constructor Tests
   */
  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {

    LongText domain = new LongText(countySpecificCode, textDescription);

    gov.ca.cwds.data.persistence.cms.LongText persistent =
        new gov.ca.cwds.data.persistence.cms.LongText(id, domain, "lastUpdatedId");

    LongText totest = new LongText(persistent);
    assertThat(totest.getCountySpecificCode(), is(equalTo(persistent.getCountySpecificCode())));
    assertThat(totest.getTextDescription(), is(equalTo(persistent.getTextDescription())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    LongText domain = new LongText(countySpecificCode, textDescription);

    assertThat(domain.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(domain.getTextDescription(), is(equalTo(textDescription)));
  }

  /**
   * 
   */
  @Test
  public void equalsHashCodeWork() {
    // EqualsVerifier.forClass(LongText.class).suppress(Warning.NONFINAL_FIELDS).verify();
    LongText domain = new LongText(countySpecificCode, textDescription);
    assertThat(domain.hashCode(), is(not(0)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class));

    assertThat(MAPPER.writeValueAsString(validLongText), is(equalTo(expected)));
  }

  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"),
        LongText.class), is(equalTo(validLongText)));
  }

  /*
   * Successful Tests
   */
  /**
   * @throws Exception test standard
   * 
   */
  @Test
  public void successfulWithValid() throws Exception {
    LongText longText = MAPPER
        .readValue(fixture("fixtures/domain/legacy/LongText/valid/valid.json"), LongText.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(longText));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * countySpecificCode Tests
   */
  @Test
  public void failsWhenCountySpecificCodeNull() throws Exception {
    LongText longText = new LongTextResourceBuilder().setCountySpecificCode(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(longText));
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
    LongText longText = new LongTextResourceBuilder().setCountySpecificCode("123").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(longText));
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
   * textDescription Tests
   */
  @Test
  public void failsWhenTextDescriptionNull() throws Exception {
    LongText longText = new LongTextResourceBuilder().setTextDescription(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(longText));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("textDescription may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenTextDescriptionEmpty() throws Exception {
    LongText longText = new LongTextResourceBuilder().setTextDescription("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(longText));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("textDescription may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * Utilis
   */
  private LongText validLongText() {
    return new LongText("57", "Child Education level is below average");
  }

}
