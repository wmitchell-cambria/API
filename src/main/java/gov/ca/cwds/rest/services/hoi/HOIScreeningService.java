package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningList;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Screening History Of Involvement
 *
 * @author CWDS API Team
 */
public class HOIScreeningService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  @Inject
  ScreeningDao screeningDao;

  @Inject
  HOIScreeningFactory hoiScreeningFactory;

  public HOIScreeningService() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String primaryKey) {
    Set<HOIScreening> screenings = new HashSet<>();
    // SessionFactory from postgres DB and need to open session in order to execute.
    try (Session session = screeningDao.getSessionFactory().openSession()) {
      ManagedSessionContext.bind(session);
      for (gov.ca.cwds.data.persistence.ns.Screening persistedScreening : screeningDao
          .findHoiScreeningsByScreeningId(primaryKey)) {
        screenings.add(hoiScreeningFactory.buildHOIScreening(persistedScreening));
      }
    }
    return new HOIScreeningList(screenings);
  }

  @Override
  public Response create(InvolvementHistory request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, InvolvementHistory request) {
    throw new NotImplementedException("update not implemented");
  }

}
