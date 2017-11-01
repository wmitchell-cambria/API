package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.NotImplementedException;
import com.google.inject.Inject;
import gov.ca.cwds.data.dao.investigation.SafetyAlertsDao;
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

  private Set<String> alerts = new HashSet<>();

  private static SafetyAlerts safetyAlerts = new SafetyAlertsEntityBuilder().build();

  private SafetyAlertsDao safetyAlertsDao;

  /**
   * @param safetyAlertsDao - safety alert data access object
   */
  @Inject
  public SafetyAlertsService(SafetyAlertsDao safetyAlertsDao) {
    super();
    this.safetyAlertsDao = safetyAlertsDao;
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
    alerts.add("6401");
    alerts.add("6402");
    safetyAlerts.setAlerts(alerts);
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
