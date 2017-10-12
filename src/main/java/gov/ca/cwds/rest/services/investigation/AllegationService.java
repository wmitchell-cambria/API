package gov.ca.cwds.rest.services.investigation;

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
    // TODO Auto-generated method stub
    return null;
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

}
