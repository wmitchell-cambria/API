package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

/**
 * Business layer object to work on Screening History Of Involvement
 *
 * @author CWDS API Team
 */
public class HOIScreeningService extends SimpleResourceService<String, HOIScreening, HOIScreeningResponse> {

  @Inject
  ScreeningDao screeningDao;

  @Inject
  HOIScreeningFactory hoiScreeningFactory;

  public HOIScreeningService() {
    super();
  }

  /**
   *
   * @param primaryKey - screening Id
   * @return list of HOI Screenings
   */
  @Override
  protected HOIScreeningResponse handleFind(String primaryKey) {
    Set<HOIScreening> screenings = new HashSet<>();
    for (ScreeningEntity screeningEntity : screeningDao
        .findHoiScreeningsByScreeningId(primaryKey)) {
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
