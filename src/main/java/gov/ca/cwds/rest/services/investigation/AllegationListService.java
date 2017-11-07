package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.AllegationsDao;
import gov.ca.cwds.fixture.investigation.AllegationListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.AllegationList;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation Allegations
 * 
 * @author CWDS API Team
 */
public class AllegationListService implements TypedCrudsService<String, AllegationList, Response> {

  private AllegationList validAllegationsList = new AllegationListEntityBuilder().build();

  /**
   * @param allegationDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.AllegationList} objects
   */
  @Inject
  public AllegationListService(AllegationsDao allegationDao) {
    super();
  }

  @Override
  public Response find(String primaryKey) {
    return validAllegationsList;
  }

  @Override
  public Response create(AllegationList request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, AllegationList request) {
    throw new NotImplementedException("update not implemented");
  }

}
