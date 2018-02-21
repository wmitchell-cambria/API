package gov.ca.cwds.rest.services.hoi;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.security.annotations.Authorize;

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
    authorizeClients(clientIds);

    for (ScreeningEntity screeningEntity : screeningDao.findScreeningsByClientIds(clientIds)) {
      authorizeScreening(screeningEntity);
      screenings.add(hoiScreeningFactory.buildHOIScreening(screeningEntity));
    }

    return new HOIScreeningResponse(screenings);
  }

  @Override
  protected HOIScreeningResponse handleRequest(HOIScreening hoiScreening) {
    LOGGER.info("HOIScreeningService handle request not implemented");
    throw new NotImplementedException("handle request not implemented");
  }

  public void authorizeScreening(
      @Authorize("screening:read:screeningEntity") ScreeningEntity screeningEntity) {
    // Check screening access restriction
    String accessRestriction = screeningEntity.getAccessRestrictions();
    if (StringUtils.isNoneBlank(accessRestriction)) {

    }

    // Check participants
    Set<ParticipantEntity> participants = screeningEntity.getParticipants();
    if (participants != null) {
      for (ParticipantEntity participant : participants) {
        String participantId = participant.getLegacyId();
        if (StringUtils.isNotBlank(participantId)) {
          authorizeClient(participantId);
        }
      }
    }
  }

  private void authorizeClients(Collection<String> clientIds) {
    for (String clientId : clientIds) {
      authorizeClient(clientId);
    }
  }

  public String authorizeClient(@Authorize("client:read:clientId") String clientId) {
    return clientId;
  }
}
