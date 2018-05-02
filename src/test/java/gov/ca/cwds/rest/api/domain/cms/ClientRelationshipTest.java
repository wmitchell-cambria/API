package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
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
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ClientRelationshipTest {
  @SuppressWarnings("javadoc")
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private ClientRelationship validClientRelationship = validClientRelationship();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private String endDate = "2017-01-07";
  private String startDate = "2017-01-07";
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();
  }

  @Test
  public void persistentObjectConstructorTest() throws Exception {

    ClientRelationship domain = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, sameHomeCode, startDate);

    gov.ca.cwds.data.persistence.cms.ClientRelationship pt =
        new gov.ca.cwds.data.persistence.cms.ClientRelationship(id, domain, "lastUpdatedId",
            new Date());

    assertThat(domain.getAbsentParentCode(), is(equalTo(pt.getAbsentParentCode())));
    assertThat(domain.getClientRelationshipType(), is(equalTo(pt.getClientRelationshipType())));
    assertThat(domain.getSecondaryClientId(), is(equalTo(pt.getSecondaryClientId())));
    assertThat(domain.getPrimaryClientId(), is(equalTo(pt.getPrimaryClientId())));
    assertThat(domain.getEndDate(), is(equalTo(df.format(pt.getEndDate()))));
    assertThat(domain.getSameHomeCode(), is(equalTo(pt.getSameHomeCode())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(pt.getStartDate()))));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ClientRelationship domain = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, sameHomeCode, startDate);
    assertThat(domain.getAbsentParentCode(), is(equalTo(absentParentCode)));
    assertThat(domain.getClientRelationshipType(), is(equalTo(clientRelationshipType)));
    assertThat(domain.getSecondaryClientId(), is(equalTo(secondaryClientId)));
    assertThat(domain.getPrimaryClientId(), is(equalTo(primaryClientId)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getSameHomeCode(), is(equalTo(sameHomeCode)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ClientRelationship.class).suppress(Warning.NONFINAL_FIELDS)
        .withRedefinedSubclass(PostedClientRelationship.class).verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        null, secondaryClientId, primaryClientId, sameHomeCode, null);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * absentParentCode Tests
   */
  @Test
  public void failsWhenAbsentParentCodeNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(null, clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("absentParentCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenAbsentParentCodeEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship("", clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("absentParentCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenAbsentParentCodeTooLong() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship("AB", clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("absentParentCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * clientRelationshipType Tests
   */
  @Test
  public void failsWhenClientRelationshipTypeNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, null, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("clientRelationshipType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenClientRelationshipTypeEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, null, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("clientRelationshipType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * endDate Tests
   */
  @Test
  public void successWhenEndDateEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        "", secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenEndDateNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        null, secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenEndDateWrongFormat() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        "09-09-2012", secondaryClientId, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("endDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * secondaryClientId Tests
   */
  @Test
  public void failsWhenSecondaryClientIdNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, null, primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("secondaryClientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenSecondaryClientIdEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, "", primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("secondaryClientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenSecondaryClientIdTooLong() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, "123456789012", primaryClientId, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("secondaryClientId size must be between 1 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * primaryClientId Tests
   */
  @Test
  public void failsWhenPrimaryClientIdNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, null, sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryClientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenPrimaryClientIdEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, "", sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryClientId may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenPrimaryClientIdTooLong() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, "123456789012", sameHomeCode, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("primaryClientId size must be between 1 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * sameHomeCode Tests
   */
  @Test
  public void failsWhenSameHomeCodeMissing() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, "", startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sameHomeCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenSameHomeCodeNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, null, startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sameHomeCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenSameHomeCodeEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, "", startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sameHomeCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenSameHomeCodeTooLong() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, "AB", startDate);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("sameHomeCode size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * startDate Tests
   */
  @Test
  public void successWhenStartDateEmpty() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, sameHomeCode, "");
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenStartDateNull() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, sameHomeCode, null);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenStartDateWrongFormat() throws Exception {
    ClientRelationship clientRelationship = new ClientRelationship(absentParentCode, clientRelationshipType,
        endDate, secondaryClientId, primaryClientId, sameHomeCode, "12-12-2000");
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(clientRelationship));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("startDate must be in the format of yyyy-MM-dd")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * Utils
   */
  private ClientRelationship validClientRelationship() {
    return new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, sameHomeCode, startDate);
  }

}
