package gov.ca.cwds.rest.business.rules.doctool;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;

/**
 * 
 * @author CWDS API Team
 */
public class R06224DontAllowBlanksInReferralStartDateAndTimeTest {

  private MessageBuilder messageBuilder;
  private Validator validator;


  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @SuppressWarnings("javadoc")
  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    messageBuilder = new MessageBuilder();
  }

  /**
   * <blockquote>
   * 
   * <pre>
   * BUSINESS RULE: "R - 06224 " - Do not allow blanks in Referral Start Date and Time
   * 
   * </blockquote>
   * </pre>
   * 
   * @throws Exception on general error
   */
  @Test
  public void shouldNotAllowBlankReceivedDate() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    Referral referral = new ReferralResourceBuilder().setReceivedDate("").build();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(
        errorContainsMessage(validationErrors, "receivedDate must be in the format of yyyy-MM-dd"),
        is(Boolean.TRUE));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void shouldNotAllowBlankReceivedTime() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    Referral referral = new ReferralResourceBuilder().setReceivedTime("").build();
    messageBuilder.addDomainValidationError(validator.validate(referral));
    List<ErrorMessage> validationErrors = messageBuilder.getMessages();
    assertThat(
        errorContainsMessage(validationErrors, "receivedTime must be in the format of HH:mm:ss"),
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
