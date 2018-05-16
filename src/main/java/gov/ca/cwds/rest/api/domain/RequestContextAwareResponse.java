package gov.ca.cwds.rest.api.domain;

import java.util.List;
import java.util.Optional;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * {@link Response} interface extension that is aware of the current request's
 * {@link RequestExecutionContext}, which is bound to the current thread.
 * 
 * @author CWDS API Team
 */
public interface RequestContextAwareResponse extends Response {

  /**
   * Pull error/warning messages from the current thread's {@link RequestExecutionContext}, if
   * available, or return null.
   * 
   * @return messages in current request context or null
   */
  static List<ErrorMessage> getRequestContextMessages() {
    final Optional<RequestExecutionContext> ctx =
        Optional.<RequestExecutionContext>ofNullable(RequestExecutionContext.instance());
    final List<ErrorMessage> messages =
        ctx.isPresent() ? ctx.get().getMessageBuilder().getMessages() : null;
    return messages;
  }

  @Override
  default boolean hasMessages() {
    final List<ErrorMessage> messages = getRequestContextMessages();
    return messages != null && !messages.isEmpty() ? true : Response.super.hasMessages();
  }

  @Override
  default List<ErrorMessage> getMessages() {
    final List<ErrorMessage> messages = getRequestContextMessages();
    return messages != null && !messages.isEmpty() ? messages : Response.super.getMessages();
  }

}
