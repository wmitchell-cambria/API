package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.fixture.CmsAddressResourceBuilder;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ScreeningToReferralService;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("unused")
public class R05360ReferralCityMandatoryTest {

  private static final ObjectMapper MAPPER = ElasticSearchPerson.MAPPER;
  public static final Short STATE_OF_CALIFORNIA_COUNTY_CODE = Short.valueOf("1126");

  private ScreeningToReferralService screeningToReferralService;
  private MessageBuilder messageBuilder;
  private Validator validator;

  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    messageBuilder = new MessageBuilder();
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 05360" - StreetName is set then City is required
   * 
   * IF  streetNumber is set
   * THEN streetName is required
   * 
   * IF streetName is set
   * THEN city is required
   * </blockquote>
   * </pre>
   * 
   * @throws Exception on general error
   */
  @Test
  public void shouldBeValidAddressWithBothStreetNameAndNumber() {
    Address address = new CmsAddressResourceBuilder().setStreetNumber("1234")
        .setStreetName("test street name").setCity("test city").buildCmsAddress();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(address));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(validationErrors.size(), is(equalTo(0)));
  }

  @Test
  public void shouldOneErrorMessageInvalidCounty() {
    Address address = new CmsAddressResourceBuilder()
        .setGovernmentEntityCd(STATE_OF_CALIFORNIA_COUNTY_CODE).buildCmsAddress();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(address));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(validationErrors.size(), is(equalTo(1)));
  }

  @Test
  public void shouldNotBeValidAddressWithStreetNameOnly() {
    Address address = new CmsAddressResourceBuilder().setStreetNumber("1234")
        .setStreetName("test street name").setCity("").buildCmsAddress();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(address));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(errorContainsMessage(validationErrors, "city is required since streetName is set"),
        is(Boolean.TRUE));
  }

  @Test
  public void shouldNotBeValidAddressWithStreetnumberOnly() {
    Address address = new CmsAddressResourceBuilder().setStreetNumber("1234").setStreetName("")
        .setCity("city").buildCmsAddress();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(address));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(
        errorContainsMessage(validationErrors, "streetName is required since streetNumber is set"),
        is(Boolean.TRUE));
  }

  @Test
  public void shouldBeValidReporterWithBothStreetNameAndNumber() {
    Reporter reporter = new ReporterResourceBuilder().setReferralId("1234567ABC")
        .setStreetName("the street name").setCityName("city name").setStreetNumber("1234").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(validationErrors.size(), is(equalTo(0)));
  }

  @Test
  public void shouldNotBeValidReporterWithStreetNameOnly() {
    Reporter reporter = new ReporterResourceBuilder().setReferralId("1234567ABC")
        .setStreetName("the street name").setStreetNumber("").setCityName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    Boolean theErrorDetected = false;
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(
        errorContainsMessage(validationErrors, "cityName is required since streetName is set"),
        is(Boolean.TRUE));
  }

  @Test
  public void shouldNotBeValidReporterWithStreetnumberOnly() {
    Reporter reporter = new ReporterResourceBuilder().setReferralId("1234567ABC").setStreetName("")
        .setStreetNumber("1234").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(reporter));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(
        errorContainsMessage(validationErrors, "streetName is required since streetNumber is set"),
        is(Boolean.TRUE));
  }

  private Boolean errorContainsMessage(List<ErrorMessage> validationErrors, String message) {
    Boolean containsMessage = Boolean.FALSE;
    for (ErrorMessage errorMessage : validationErrors) {
      if (errorMessage.getMessage().equals(message)) {
        return Boolean.TRUE;
      }
    }
    return containsMessage;
  }

}
