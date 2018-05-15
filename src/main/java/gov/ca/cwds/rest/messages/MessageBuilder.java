package gov.ca.cwds.rest.messages;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueDetailsCreator;
import gov.ca.cwds.rest.exception.IssueType;

/**
 * Facility to relay errors from persistence and service layers back to a domain response.
 * 
 * <p>
 * This implementation is <strong>NOT thread safe!</strong> Construct an instance as needed per
 * request.
 * </p>
 * 
 * @author CWDS API Team
 */
public class MessageBuilder {

  private List<ErrorMessage> messages = new ArrayList<>();
  private Set<IssueDetails> issues = new LinkedHashSet<>();

  /**
   * messageBuilder - messageBuilder
   */
  public MessageBuilder() {
    // default constructor
  }

  public void merge(MessageBuilder messageBuilder) {
    this.messages.addAll(messageBuilder.messages);
    this.issues.addAll(messageBuilder.issues);
  }

  /**
   * @param message - message
   */
  public void addError(String message) {
    addError(message, ErrorMessage.ErrorType.VALIDATION);
  }

  /**
   * @param message - message
   * @param type - type
   */
  public void addError(String message, ErrorType type) {
    messages.add(new ErrorMessage(type, message, ""));

    final IssueDetails issue = new IssueDetails();
    this.issues.add(issue);
    issue.setUserMessage(message);

    if (ErrorType.DATA_ACCESS == type) {
      issue.setType(IssueType.DATA_ACCESS_EXCEPTION);
    } else if (ErrorType.BUSINESS == type) {
      issue.setType(IssueType.BUSINESS_VALIDATION);
    } else if (ErrorType.VALIDATION == type) {
      issue.setType(IssueType.CONSTRAINT_VALIDATION);
    } else if (ErrorType.CLIENT_CONTRACT == type) {
      issue.setType(IssueType.JSON_PROCESSING_EXCEPTION);
    } else {
      issue.setType(IssueType.UNEXPECTED_EXCEPTION);
    }
  }

  /**
   * @param errors - errors
   */
  public void addDomainValidationError(Set<ConstraintViolation<DomainObject>> errors) {
    if (!errors.isEmpty()) {
      errors.forEach(error -> {
        final String message = error.getPropertyPath() + " " + error.getMessage();
        messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
      });

      errors.forEach(error -> issues.add(IssueDetailsCreator.create(error, null)));
    }
  }

  /**
   * @return the error message
   */
  public List<ErrorMessage> getMessages() {
    return messages == null ? new ArrayList<>() : messages;
  }

  public void addMessageAndLog(String message, org.slf4j.Logger logger) {
    addError(message);
    logger.error(message);
  }

  public void addMessageAndLog(String message, Exception exception, org.slf4j.Logger logger) {
    addError(message);
    logger.error(message, exception.getMessage());
  }

  public Set<IssueDetails> getIssues() {
    return issues;
  }
}
