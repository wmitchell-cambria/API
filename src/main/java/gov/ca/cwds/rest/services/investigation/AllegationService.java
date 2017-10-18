package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.data.dao.investigation.AllegationsDao;
import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation Allegation
 * 
 * @author CWDS API Team
 */

public class AllegationService implements TypedCrudsService<String, Allegation, Response> {
  private AllegationsDao allegationDao;
  private Allegation validAllegation = new AllegationEntityBuilder().build();

  @Override
  public Response create(Allegation arg0) {
    return validAllegation;
  }

  @Override
  public Response delete(String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response find(String arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response update(String investigationId, Allegation request) {
    return validAllegation;

  }

  /**
   * populating allegations details
   * 
   * @param persistedAllegations - list of persisted allegations
   * @return list of allegations
   */
  public Set<Allegation> populateAllegations(
      Set<gov.ca.cwds.data.persistence.cms.Allegation> persistedAllegations) {
    Set<Allegation> allegations = new HashSet<>();
    for (gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation : persistedAllegations) {
      allegations.add(new Allegation(persistedAllegation));

    }
    return allegations;
  }

}
