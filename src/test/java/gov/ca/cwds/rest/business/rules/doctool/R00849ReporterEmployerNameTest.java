package gov.ca.cwds.rest.business.rules.doctool;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.fixture.ReporterResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

/**
 * Test Class for DocTool Rule R - 00849 Employer Name specification
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class R00849ReporterEmployerNameTest {


  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private MessageBuilder messageBuilder;
  private Validator validator;
  
  @Before
  public void setup() throws Exception {
    messageBuilder = new MessageBuilder();
  }


  /**
   * <pre>
   * <blockquote>
   * DocTool Rule "R - 00849" Employer Name specification 
   * 
   * If REPORTER is associated with LAW ENFORCEMENT, the EMPLOYER NAME (AGENCY NAME) must not be specified.
   * Access Logic: If REPORTER>LAW_ENFORCEMENT exists, then disable Agency, and if Agency_Name exists, disable Law_Enforcement, else enable
   * 
   * </blockquote>
   * </pre>
   * 
   * @MutuallyExclusive(required = false, properties = {"employerName", "lawEnforcementId"})
   * @throws Exception general error
   */
  @Test
  public void testBothLawEnforcementIdExistsAndEmployerNameExistsFails() throws Exception {
    Reporter toCreate = new ReporterResourceBuilder().setLawEnforcementId("lawid").setEmployerName("name").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    messageBuilder.addDomainValidationError(validator.validate(toCreate));
    Boolean theErrorDetected = false;

   List<ErrorMessage> validationErrors = messageBuilder.getMessages();
   for (ErrorMessage message : validationErrors) {
//     System.out.println(message.getMessage());
     if (message.getMessage().equals("Properties [employerName, lawEnforcementId] are mutually exclusive but multiple values are set")) {
       theErrorDetected = true;
     }
   }
   assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testBothLawEnforcementIdIsEmptyAndEmployerNameIsEmptySuccess() throws Exception {
    Reporter toCreate = new ReporterResourceBuilder().setLawEnforcementId("").setEmployerName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
   messageBuilder.addDomainValidationError(validator.validate(toCreate));
   assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testLawEnforcementIdIsEmptyAndEmployerNameExistsSuccess() throws Exception {
    Reporter toCreate = new ReporterResourceBuilder().setLawEnforcementId("").setEmployerName("name").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
   messageBuilder.addDomainValidationError(validator.validate(toCreate));
   assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

  @Test
  public void testLawEnforcementIdExistsAndEmployerNameIsEmptySuccess() throws Exception {
    Reporter toCreate = new ReporterResourceBuilder().setLawEnforcementId("lawid").setEmployerName("").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
   messageBuilder.addDomainValidationError(validator.validate(toCreate));
   assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }
  }
