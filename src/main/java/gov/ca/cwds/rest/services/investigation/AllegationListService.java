package gov.ca.cwds.rest.services.investigation;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationDao;
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

  private AllegationList validAllegations = new AllegationListEntityBuilder().build();

  /**
   * @param allegationDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.AllegationList} objects
   */
  @Inject
  public AllegationListService(AllegationDao allegationDao) {
    super();
  }

  @Override
  public Response find(String primaryKey) {
    return validAllegations;
  }

  @Override
  public Response create(AllegationList request) {
    return validAllegations;
  }

  @Override
  public Response delete(String primaryKey) {
    return validAllegations;
  }

  @Override
  public Response update(String primaryKey, AllegationList request) {
    return null;
  }

}
