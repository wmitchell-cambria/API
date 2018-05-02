package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
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
public class CmsReferralTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private CmsReferral validCmsReferral = validCmsReferral();

  TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();
  
  private MessageBuilder messageBuilder;
  private Validator validator;

  @Before
  public void setup() {
    messageBuilder = new MessageBuilder();
  }

  @Test
  public void equalsHashCodeWork() throws Exception {
    Referral referral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    assertThat(referral.hashCode(), is(not(0)));
  }

  /**
   * @throws Exception required for test compilation
   */
  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    Referral referral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"), Referral.class);
    Set<Client> client =
        MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/valid/clientCmsReferral.json"),
            new TypeReference<Set<Client>>() {});
    Reporter reporter = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"), Reporter.class);
    Set<ReferralClient> referralClient = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
        new TypeReference<Set<ReferralClient>>() {});
    Set<CrossReport> crossReport = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
        new TypeReference<Set<CrossReport>>() {});
    Set<Allegation> allegation = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
        new TypeReference<Set<Allegation>>() {});

    CmsReferral cmsReferral =
        new CmsReferral(referral, client, allegation, crossReport, referralClient, reporter);

    assertThat(cmsReferral.getReferral(), is(equalTo(referral)));
    assertThat(cmsReferral.getAllegation(), is(equalTo(allegation)));
    assertThat(cmsReferral.getCrossReport(), is(equalTo(crossReport)));
    assertThat(cmsReferral.getReferralClient(), is(equalTo(referralClient)));
    assertThat(cmsReferral.getReporter(), is(equalTo(reporter)));
    assertThat(cmsReferral.getClient(), is(equalTo(client)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class));

    assertThat(MAPPER.writeValueAsString(validCmsReferral()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    CmsReferral referral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);
    CmsReferral validReferral = validCmsReferral();
    assertThat(referral, is(equalTo(validReferral)));
  }

  /*
   * Successful Tests
   */
  @Test
  public void successfulWithValid() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void successfulWithOptionalsNotIncluded() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferralWithOptionalsNotIncluded.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  // failure when Referral is invalid, missing, or null
  @Test
  public void failureWhenReferralNull() throws Exception {

    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReferral.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referral may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failureWhenReferralIsEmpty() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralEmpty.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referral.referralName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failureWhenReferralIsInvalid() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidReferral.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referral.referralResponseType may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * failure when Client is null, missing, or invalid
   */
  @Test
  public void failureWhenClientNull() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullClient.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("client may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failureWhenClientIsEmpty() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenClientEmpty.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("client[].commonFirstName may not be empty")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failureWhenClientIsInvalid() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralInvalidClient.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("client[].birthCity may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * failure when ReferralClient is null, missing, or invalid
   */
  @Test
  public void failureWhenReferralClientNull() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReferralClient.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("referralClient may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  @Test
  public void failureWhenReporterIsEmpty() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReporterEmpty.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("reporter.employerName may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
  }

  /*
   * failure when Reporter is null, missing, or invalid
   */
  @Test
  public void failureWhenAllegationNull() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullAllegation.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    for (ErrorMessage message : validationErrors) {
//      System.out.println(message.getMessage());
      if (message.getMessage().equals("allegation may not be null")) {
        theErrorDetected = true;
      }
    }
    assertThat(theErrorDetected, is(true));
 }

  /*
   * cross report test - cross report is not required for minimal referral data
   */
  @Test
  public void successWhenCrossReportNull() throws Exception {
    CmsReferral toCreate = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferralNullCrossReport.json"),
        CmsReferral.class);
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void SuccessWhenCmsReferralEqualsNullCmsReferral() throws Exception {
    CmsReferral validReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);
    assertThat(validReferral.equals(null), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void SuccessWhenCmsReferralEqualsNullReferralClient() throws Exception {
    CmsReferral validReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/valid/cmsReferral.json"), CmsReferral.class);

    CmsReferral invalidReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullReferralClient.json"),
        CmsReferral.class);

    assertThat(validReferral.equals(invalidReferral), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void SucessWhenCmsReferralAndEmptyReferralClient() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralClientEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void SucessWhenCmsReferralAndEmptyAllegation() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenAllegationEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void SucessWhenCmsReferralAndEmptyReporter() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReporterEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndEmptyReferral() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();
    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenReferralEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));
  }

  @Test
  public void SucessWhenCmsReferralAndEmptyCrossReport() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralEmptyCrossReport.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndEmptyClient() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();

    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralWhenClientEmpty.json"),
        CmsReferral.class);

    assertThat(validCmsReferral.equals(invalidCmsReferral), is(equalTo(Boolean.FALSE)));

  }

  @Test
  public void SucessWhenCmsReferralAndNullAllegation() throws Exception {
    CmsReferral validCmsReferral = this.validCmsReferral();
    CmsReferral invalidCmsReferral = MAPPER.readValue(
        fixture("fixtures/domain/cms/CmsReferral/invalid/cmsReferralNullAllegation.json"),
        CmsReferral.class);

    assertThat(invalidCmsReferral.equals(validCmsReferral), is(equalTo(Boolean.FALSE)));
  }

  /*
   * Utilities
   */
  private CmsReferral validCmsReferral() {

    try {
      Referral referral = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/referralCmsReferral.json"),
          Referral.class);
      Set<Client> client =
          MAPPER.readValue(fixture("fixtures/domain/cms/CmsReferral/valid/clientCmsReferral.json"),
              new TypeReference<Set<Client>>() {});
      Reporter reporter = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/reporterCmsReferral.json"),
          Reporter.class);
      Set<ReferralClient> referralClient = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/referralClientCmsReferral.json"),
          new TypeReference<Set<ReferralClient>>() {});
      Set<CrossReport> crossReport = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/crossReportCmsReferral.json"),
          new TypeReference<Set<CrossReport>>() {});
      Set<Allegation> allegation = MAPPER.readValue(
          fixture("fixtures/domain/cms/CmsReferral/valid/allegationCmsReferral.json"),
          new TypeReference<Set<Allegation>>() {});

      return new CmsReferral(referral, client, allegation, crossReport, referralClient, reporter);

    } catch (JsonParseException e) {
      e.printStackTrace();
      return null;
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

  }
}
