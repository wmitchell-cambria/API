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
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

/**
 * Test Class for DocTool Rule : R - 00846 - Badge Number specification
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class R00846ReporterBadgeNumberTest {

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private MessageBuilder messageBuilder;
  private Validator validator;


  @Before
  public void setup() throws Exception {
     messageBuilder = new MessageBuilder();
  }


  /**
   * <blockquote>
   * 
   * <pre>
   * 
   * DocTool Rule : "R - 00846" - Badge Number specification
   * 
   * BADGE NUMBER may only be specified if the REPORTER is associated with LAW ENFORCEMENT.
   * Access Logic: If Law Enforcement is entered, then Badge Number is enabled, else disabled.
   * </pre>
   * 
   * </blockquote>
   * 
   * @OnlyIf(property = "badgeNumber", ifProperty = "lawEnforcementId")
   * @throws Exception general error
   */
  @Test
  public void testBadgeNumberIsSpecifiedButLawEnforcementIdIsEmptyFails() throws Exception {
    Reporter toCreate = new ReporterResourceBuilder().setBadgeNumber("ABCD").setLawEnforcementId("").build();
         validator = Validation.buildDefaultValidatorFactory().getValidator();
        messageBuilder.addDomainValidationError(validator.validate(toCreate));
        Boolean theErrorDetected = false;

        List<ErrorMessage> validationErrors = messageBuilder.getMessages();
        for (ErrorMessage message : validationErrors) {
//          System.out.println(message.getMessage());
          if (message.getMessage().equals("badgeNumber can only be set if lawEnforcementId is set")) {
            theErrorDetected = true;
          }
        }
        assertThat(theErrorDetected, is(true));
  }

  @Test
  public void testBadgeNumberIsEmptyButLawEnforcementIdIsSpecifiedSuccess() throws Exception {
    Reporter toCreate = new ReporterResourceBuilder().setBadgeNumber("").setLawEnforcementId("lawid").build();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
   messageBuilder.addDomainValidationError(validator.validate(toCreate));
   assertThat(messageBuilder.getMessages().isEmpty(), is(true));
  }

}
