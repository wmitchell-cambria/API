package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import gov.ca.cwds.fixture.StaffPersonResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class StaffPersonTest {

 
  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();


  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private StaffPerson validStaffPerson = validStaffPerson();

  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private String id = "a";
  private String endDate = "";
  private String firstName = "b";
  private String jobTitle = "c";
  private String lastName = "d";
  private String middleInitial = "e";
  private String namePrefix = "f";
  private BigDecimal phoneNumber = new BigDecimal(1);
  private Integer phoneExt = 2;
  private String startDate = "2016-10-31";
  private String nameSuffix = "g";
  private Boolean telecommuterIndicator = Boolean.TRUE;
  private String cwsOffice = "h";
  private String availabilityAndLocationDescription = "i";
  private String ssrsLicensingWorkerId = "j";
  private String countyCode = "k";
  private Boolean dutyWorkerIndicator = Boolean.FALSE;
  private String cwsOfficeAddress = "l";
  private String emailAddress = "m";
  // private String twitterName = "n";

  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();
  }

  /*
   * Constructor Tests
   */
  @Test
  public void persistentObjectConstructorTest() throws Exception {
    StaffPerson domain = new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial,
        namePrefix, phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
        availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator,
        cwsOfficeAddress, emailAddress);
    gov.ca.cwds.data.persistence.cms.StaffPerson persistent =
        new gov.ca.cwds.data.persistence.cms.StaffPerson(id, domain, "lastUpdatedId", new Date());

    assertThat(domain.getEndDate(),
        is(equalTo(persistent.getEndDate() == null ? "" : df.format(persistent.getEndDate()))));
    assertThat(domain.getFirstName(), is(equalTo(persistent.getFirstName())));
    assertThat(domain.getJobTitle(), is(equalTo(persistent.getJobTitle())));
    assertThat(domain.getLastName(), is(equalTo(persistent.getLastName())));
    assertThat(domain.getMiddleInitial(), is(equalTo(persistent.getMiddleInitial())));
    assertThat(domain.getNamePrefix(), is(equalTo(persistent.getNamePrefix())));
    assertThat(domain.getPhoneNumber(), is(equalTo(persistent.getPhoneNumber())));
    assertThat(domain.getPhoneExt(), is(equalTo(persistent.getPhoneExt())));
    assertThat(domain.getStartDate(), is(equalTo(df.format(persistent.getStartDate()))));
    assertThat(domain.getNameSuffix(), is(equalTo(persistent.getNameSuffix())));
    assertThat(domain.getTelecommuterIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getTelecommuterIndicator()))));
    assertThat(domain.getCwsOffice(), is(equalTo(persistent.getCwsOffice())));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(persistent.getAvailabilityAndLocationDescription())));
    assertThat(domain.getSsrsLicensingWorkerId(),
        is(equalTo(persistent.getSsrsLicensingWorkerId())));
    assertThat(domain.getCountyCode(), is(equalTo(persistent.getCountyCode())));
    assertThat(domain.getDutyWorkerIndicator(),
        is(equalTo(DomainChef.uncookBooleanString(persistent.getDutyWorkerIndicator()))));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(persistent.getCwsOfficeAddress())));
    assertThat(domain.getEmailAddress(), is(equalTo(persistent.getEmailAddress())));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    StaffPerson domain = new StaffPerson(endDate, firstName, jobTitle, lastName, middleInitial,
        namePrefix, phoneNumber, phoneExt, startDate, nameSuffix, telecommuterIndicator, cwsOffice,
        availabilityAndLocationDescription, ssrsLicensingWorkerId, countyCode, dutyWorkerIndicator,
        cwsOfficeAddress, emailAddress);

    // assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getJobTitle(), is(equalTo(jobTitle)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getMiddleInitial(), is(equalTo(middleInitial)));
    assertThat(domain.getNamePrefix(), is(equalTo(namePrefix)));
    assertThat(domain.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(domain.getPhoneExt(), is(equalTo(phoneExt)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
    assertThat(domain.getNameSuffix(), is(equalTo(nameSuffix)));
    assertThat(domain.getTelecommuterIndicator(), is(equalTo(telecommuterIndicator)));
    assertThat(domain.getCwsOffice(), is(equalTo(cwsOffice)));
    assertThat(domain.getAvailabilityAndLocationDescription(),
        is(equalTo(availabilityAndLocationDescription)));
    assertThat(domain.getSsrsLicensingWorkerId(), is(equalTo(ssrsLicensingWorkerId)));
    assertThat(domain.getCountyCode(), is(equalTo(countyCode)));
    assertThat(domain.getDutyWorkerIndicator(), is(equalTo(dutyWorkerIndicator)));
    assertThat(domain.getCwsOfficeAddress(), is(equalTo(cwsOfficeAddress)));
    assertThat(domain.getEmailAddress(), is(equalTo(emailAddress)));
    // assertThat(domain.getTwitterName(), is(equalTo(twitterName)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class));

    assertThat(MAPPER.writeValueAsString(validStaffPerson()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"),
        StaffPerson.class), is(equalTo(validStaffPerson())));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(StaffPerson.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/valid.json"), StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  // test of columns that are not mandatory and cannot be null
  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/optionalsNotIncluded.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenEndDateWrongFormat() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/endDatewrongFormat.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
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
   * firstName Tests
   */
  @Test
  public void failsWhenFirstNameMissing() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNameMissing.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenFirstNameNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNameNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenFirstNameEmpty() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNamEmpty.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenFirstNameTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/firstNameTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("firstName size must be between 1 and 20")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * jobTitle Tests
   */
  @Test
  public void successWhenJobTitleMissing() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/jobTitleMissing.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenJobTitleNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/jobTitleNull.json"), StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenJobTitleTooLong() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/jobTitleTooLong.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("jobTitle size must be between 0 and 30")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * lastName Tests
   */
  @Test
  public void failsWhenLastNameMissing() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameMissing.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLastNameNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameNull.json"), StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLastNameEmpty() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameEmpty.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenLastNameTooLong() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/lastNameTooLong.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("lastName size must be between 1 and 25")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * middleInitial Tests
   */
  @Test
  public void successWhenMiddleInitialNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/middleInitialNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }


  @Test
  public void failsWhenMiddleInitialTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/middleInitialTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("middleInitial size must be 1")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * namePrefix Tests
   */
  @Test
  public void successWhenNamePrefixMissing() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/namePrefixMissing.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenNamePrefixNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/namePrefixNull.json"), StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenNamePrefixTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/namePrefixTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("namePrefix size must be between 0 and 6")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * phoneNumber Tests
   */
  @Test
  public void failsWhenPhoneNumberMissing() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneNumberMissing.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("phoneNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
 }

  @Test
  public void failsWhenPhoneNumberNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneNumberNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("phoneNumber may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * phoneExt Tests
   */
  @Test
  public void testWhenPhoneExtMissingFails() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneExtMissing.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
      System.out.println(message.getMessage());
      if (message.getMessage().equals("phoneExt may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenPhoneExtNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/phoneExtNull.json"), StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("phoneExt may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }
  

  @Test
  public void testWhenPhoneExtZeroSucess() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/phoneExtZero.json"), StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  /*
   * startDate Tests
   */
  @Test
  public void failsWhenStartDateNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/startDateNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("startDate may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenStartDateWrongFormat() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/startDateWrongFormat.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
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

  // /*
  // * nameSuffix Tests
  // */
  @Test
  public void failsWhenNameSuffixTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/nameSuffixTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("nameSuffix size must be between 0 and 4")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
 }

  /*
   * telecommuterIndicator Tests
   */
  @Test
  public void failsWhenTelecommuterIndicatorNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/telecommuterIndicatorNull.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("telecommuterIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * cwsOffice Tests
   */
  @Test
  public void failsWhenCwsOfficeNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOffice may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCwsOfficeTooShort() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeTooShort.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOffice size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCwsOfficeTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOffice size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * availabilityAndLocationDescription Tests
   */
  @Test
  public void testAvailabilityAndLocationDescriptionEmptySuccess() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(fixture(
        "fixtures/domain/legacy/StaffPerson/valid/availabilityAndLocationDescriptionEmpty.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenAvailabilityAndLocationDescriptionNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(fixture(
        "fixtures/domain/legacy/StaffPerson/invalid/availabilityAndLocationDescriptionNull.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("availabilityAndLocationDescription may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
   }

  /*
   * ssrsLicensingWorkerId Tests
   */
  @Test
  public void failsWhenSsrsLicensingWorkerIdNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/ssrsLicensingWorkerIdNull.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("ssrsLicensingWorkerId may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testSsrsLicensingWorkerIdEmptySuccess() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/valid/ssrsLicensingWorkerIdEmpty.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenSsrsLicensingWorkerIdTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/ssrsLicensingWorkerIdTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("ssrsLicensingWorkerId size must be between 0 and 4")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * countyCode Tests
   */
  @Test
  public void failsWhenCountyCodeNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countyCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountyCodeEmpty() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeEmpty.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countyCode may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCountyCodeTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/countyCodeTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("countyCode size must be between 1 and 2")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * dutyWorkerIndicator Tests
   */
  @Test
  public void failsWhenDutyWorkerIndicatorNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/dutyWorkerIndicatorNull.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dutyWorkerIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenDutyWorkerIndicatorEmpty() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/dutyWorkerIndicatorEmpty.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("dutyWorkerIndicator may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * cwsOfficeAddress Tests
   */
  @Test
  public void failsWhenCwsOfficeAddressNull() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressNull.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOfficeAddress may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCwsOfficeAddressEmpty() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressEmpty.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOfficeAddress may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCwsOfficeAddressTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOfficeAddress size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failsWhenCwsOfficeAddressTooShort() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/cwsOfficeAddressTooShort.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("cwsOfficeAddress size must be between 10 and 10")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * emailAddress Tests
   */
  @Test
  public void successWhenEmailAddressEmpty() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/emailAddressEmpty.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successWhenEmailAddressNull() throws Exception {
    StaffPerson staffPerson =
        MAPPER.readValue(fixture("fixtures/domain/legacy/StaffPerson/valid/emailAddressNull.json"),
            StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void failsWhenEmailAddressTooLong() throws Exception {
    StaffPerson staffPerson = MAPPER.readValue(
        fixture("fixtures/domain/legacy/StaffPerson/invalid/emailAddressTooLong.json"),
        StaffPerson.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(staffPerson));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("emailAddress size must be between 0 and 50")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * Utils
   */
  private StaffPerson validStaffPerson() {
    return new StaffPerson("2016-10-31", "John", "CEO", "Doe", "C", "Mr",
        new BigDecimal(9165551212L), 22, "2016-10-31", "III", true, "MIZN02k11B", "abc", "def",
        "99", false, "3XPCP92b24", "john.doe@anyco.com");
  }
}
