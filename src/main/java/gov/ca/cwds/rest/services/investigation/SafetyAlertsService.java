package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.fixture.investigation.SafetyAlertsEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.SafetyAlerts;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation Safety Alerts
 * 
 * @author CWDS API Team
 */
public class SafetyAlertsService implements TypedCrudsService<String, SafetyAlerts, Response> {

  private static SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();

  @Inject
  public SafetyAlertsService() {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String primaryKey) {
    return safetyAlerts;
  }

  @Override
  public Response create(SafetyAlerts request) {
    return safetyAlerts;
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, SafetyAlerts request) {
    throw new NotImplementedException("update not implemented");
  }


}
