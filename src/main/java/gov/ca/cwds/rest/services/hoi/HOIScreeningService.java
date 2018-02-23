package gov.ca.cwds.rest.services.hoi;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * Business layer object to work on Screening History Of Involvement
 *
 * @author CWDS API Team
 */
public class HOIScreeningService
    extends SimpleResourceService<HOIRequest, HOIScreening, HOIScreeningResponse> {

  @Inject
  ScreeningDao screeningDao;

  @Inject
  HOIScreeningFactory hoiScreeningFactory;

  @Inject
  AuthorizationService authorizationService;

  /**
   * Construct the object
   */
  public HOIScreeningService() {
    super();
  }

  /**
   * @param hoiScreeningRequest HOI Screening Request containing a list of Client Id-s
   * @return list of HOI Screenings
   */
  @Override
  protected HOIScreeningResponse handleFind(HOIRequest hoiScreeningRequest) {
    Set<HOIScreening> screenings =
        new TreeSet<>((s1, s2) -> s2.getStartDate().compareTo(s1.getStartDate()));

    Set<String> clientIds = hoiScreeningRequest.getClientIds();
    // authorizationService.ensureClientAccessAuthorized(clientIds);

    for (ScreeningEntity screeningEntity : screeningDao.findScreeningsByClientIds(clientIds)) {
      // authorizationService.ensureScreeningAccessAuthorized(screeningEntity);
      screenings.add(hoiScreeningFactory.buildHOIScreening(screeningEntity));
    }

    return new HOIScreeningResponse(screenings);
  }

  @Override
  protected HOIScreeningResponse handleRequest(HOIScreening hoiScreening) {
    LOGGER.info("HOIScreeningService handle request not implemented");
    throw new NotImplementedException("handle request not implemented");
  }
}
