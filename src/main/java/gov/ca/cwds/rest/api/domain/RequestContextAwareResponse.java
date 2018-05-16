package gov.ca.cwds.rest.api.domain;

import java.util.List;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * {@link Response} interface extension that is aware of the current request's
 * {@link RequestExecutionContext}.
 * 
 * @author CWDS API Team
 */
public interface RequestContextAwareResponse extends Response {

  @Override
  default boolean hasMessages() {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final List<ErrorMessage> messages = ctx.getMessageBuilder().getMessages();
    return messages != null && !messages.isEmpty() ? true : Response.super.hasMessages();
  }

  @Override
  default List<ErrorMessage> getMessages() {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final List<ErrorMessage> messages = ctx.getMessageBuilder().getMessages();
    return messages != null && !messages.isEmpty() ? messages : Response.super.getMessages();
  }

}
