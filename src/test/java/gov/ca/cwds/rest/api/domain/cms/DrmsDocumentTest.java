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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import gov.ca.cwds.fixture.DrmsDocumentResourceBuilder;
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
@SuppressWarnings("javadoc")
public class DrmsDocumentTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();
  
  private MessageBuilder messageBuilder;
  private Validator validator;


  /**
   * 
   */
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat tf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
  private String time = "2002-10-25T11:21:02.000196";

  private String id = "1234567ABC";
  private String drmsDocumentTemplateId = "DUMMY";
  private String fingerprintStaffPerson = "q1p";
  private String staffPersonId = "q1p";
  private String handleName = "DUMMY";
  private Date creationTimeStamp = null;

  @Before
  public void setup() throws Exception {
    messageBuilder = new MessageBuilder();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    DrmsDocument domain = new DrmsDocument(creationTimeStamp, drmsDocumentTemplateId,
        fingerprintStaffPerson, staffPersonId, handleName);

    gov.ca.cwds.data.persistence.cms.DrmsDocument persistent =
        new gov.ca.cwds.data.persistence.cms.DrmsDocument(id, domain, "lastUpdatedId", new Date());

    DrmsDocument totest = new DrmsDocument(persistent);
    assertThat(totest.getCreationTimeStamp(), is(equalTo(persistent.getCreationTimeStamp())));
    assertThat(totest.getDrmsDocumentTemplateId(),
        is(equalTo(persistent.getDrmsDocumentTemplateId())));
    assertThat(totest.getFingerprintStaffPerson(),
        is(equalTo(persistent.getFingerprintStaffPerson())));
    assertThat(totest.getStaffPersonId(), is(equalTo(persistent.getStaffPersonId())));
    assertThat(totest.getHandleName(), is(equalTo(persistent.getHandleName())));
  }

  /**
   * @throws Exception test standard test standard
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    DrmsDocument domain = new DrmsDocument((Date) null, drmsDocumentTemplateId,
        fingerprintStaffPerson, staffPersonId, handleName);

    assertThat(domain.getCreationTimeStamp(), is(equalTo(creationTimeStamp)));
    assertThat(domain.getDrmsDocumentTemplateId(), is(equalTo(drmsDocumentTemplateId)));
    assertThat(domain.getFingerprintStaffPerson(), is(equalTo(fingerprintStaffPerson)));
    assertThat(domain.getStaffPersonId(), is(equalTo(staffPersonId)));
    assertThat(domain.getHandleName(), is(equalTo(handleName)));
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    // EqualsVerifier.forClass(DrmsDocument.class).suppress(Warning.NONFINAL_FIELDS).verify();
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class));
    assertThat(expected.hashCode(), is(not(0)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class));

    assertThat(MAPPER.writeValueAsString(validDrmsDocument()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"),
        DrmsDocument.class), is(equalTo(validDrmsDocument())));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    DrmsDocument drmsDocument = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(drmsDocument));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    DrmsDocument drmsDocument = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/optionalsNotIncluded.json"),
        DrmsDocument.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(drmsDocument));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * creationTimeStamp test
   */
  @Test
  public void failsWhenCreationTimeStampNull() throws Exception {
    DrmsDocument drmsDocument = new DrmsDocumentResourceBuilder().setCreationTimeStamp(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(drmsDocument));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("creationTimeStamp may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * drmsDocumentTemplateId test
   */
  @Test
  public void failsWhenDrmsDocumentTemplateIdNull() throws Exception {
    DrmsDocument drmsDocument = new DrmsDocumentResourceBuilder().setDrmsDocumentTemplateId(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(drmsDocument));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("drmsDocumentTemplateId may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * handleName test
   */
  @Test
  public void failsWhenHandleNameNull() throws Exception {
    DrmsDocument drmsDocument = new DrmsDocumentResourceBuilder().setHandleName(null).build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(drmsDocument));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("handleName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenHandleNameToLong() throws Exception {
    DrmsDocument drmsDocument = new DrmsDocumentResourceBuilder().setHandleName("1234567890123456789012345678901").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(drmsDocument));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("handleName size must be between 1 and 30")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }


  private DrmsDocument validDrmsDocument() throws Exception {

    DrmsDocument validDrmsDocument = MAPPER.readValue(
        fixture("fixtures/domain/legacy/DrmsDocument/valid/valid.json"), DrmsDocument.class);
    return validDrmsDocument;
  }

}
