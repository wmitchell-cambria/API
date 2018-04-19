package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.ns.IntakeLOVCodeDao;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.Response;
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
  IntakeLOVCodeDao intakeLOVCodeDao;

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
    /*
     * NOTE: When we want to enable authorizations for screening history, we can add following line
     * of code back at this spot:<br/>
     * authorizationService&#46;ensureClientAccessAuthorized&#40;clientIds&#41;&#59;
     */

    Set<ScreeningEntity> screeningEntities = screeningDao.findScreeningsByClientIds(clientIds);
    Set<String> counties = screeningEntities.stream().map(ScreeningEntity::getIncidentCounty)
        .collect(Collectors.toSet());
    Map<String, IntakeLOVCodeEntity> countyIntakeLOVCodeEntityMap = intakeLOVCodeDao
        .findIntakeLOVCodesByIntakeCodes(counties);
    for (ScreeningEntity screeningEntity : screeningEntities) {
      /*
       * NOTE: When we want to enable authorizations for screening history, we can add following
       * line of code back at this spot:<br/>
       * authorizationService&#46;ensureScreeningAccessAuthorized&#40;screeningEntity&#41;&#59;
       */
      screenings.add(hoiScreeningFactory.buildHOIScreening(screeningEntity,
          countyIntakeLOVCodeEntityMap.get(screeningEntity.getIncidentCounty())));
    }

    return new HOIScreeningResponse(screenings);
  }

  /**
   * @param clientIds - clientIds
   * @return the list of screenings using clientIds
   */
  public Response findHoiScreeningsByClientIds(List<String> clientIds) {
    HOIRequest hoiRequest = new HOIRequest();
    hoiRequest.setClientIds(new HashSet<>(clientIds));
    return handleFind(hoiRequest);
  }

  @Override
  protected HOIScreeningResponse handleRequest(HOIScreening hoiScreening) {
    LOGGER.info("HOIScreeningService handle request not implemented");
    throw new NotImplementedException("handle request not implemented");
  }
}
