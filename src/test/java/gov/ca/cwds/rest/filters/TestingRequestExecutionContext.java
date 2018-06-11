package gov.ca.cwds.rest.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.rest.messages.MessageBuilder;

/**
 * Used for unit tests only.
 */
public class TestingRequestExecutionContext implements RequestExecutionContext {

  /**
   * Context parameters
   */
  private Map<Parameter, Object> contextParameters = new HashMap<>();

  /**
   * Private constructor
   * 
   * @param userId frame user id
   */
  public TestingRequestExecutionContext(String userId) {
    PerryUserIdentity userIdentity = new PerryUserIdentity();
    userIdentity.setUser(userId);

    put(Parameter.REQUEST_START_TIME, new Date());
    put(Parameter.USER_IDENTITY, userIdentity);
    put(Parameter.SEQUENCE_EXTERNAL_TABLE, new Integer(0));
    put(Parameter.MESSAGE_BUILDER, new MessageBuilder());
    put(Parameter.RESOURCE_READ_ONLY, false);

    RequestExecutionContextRegistry.register(this);
  }

  @Override
  public void put(Parameter parameter, Object value) {
    contextParameters.put(parameter, value);
  }

  @Override
  public Object get(Parameter parameter) {
    return contextParameters.get(parameter);
  }

  @Override
  public String getUserId() {
    String userId = null;
    PerryUserIdentity userIdentity = (PerryUserIdentity) get(Parameter.USER_IDENTITY);
    if (userIdentity != null) {
      userId = userIdentity.getUser();
    }
    return userId;
  }

  @Override
  public Date getRequestStartTime() {
    return (Date) get(Parameter.REQUEST_START_TIME);
  }

  @Override
  public String getStaffId() {
    String staffId = null;
    PerryUserIdentity userIdentity = (PerryUserIdentity) get(Parameter.USER_IDENTITY);
    if (userIdentity != null) {
      staffId = userIdentity.getUser();
    }
    return staffId;
  }

  @Override
  public MessageBuilder getMessageBuilder() {
    return (MessageBuilder) contextParameters.get(Parameter.MESSAGE_BUILDER);
  }

  @Override
  public boolean isResourceReadOnly() {
    return false;
  }

}
