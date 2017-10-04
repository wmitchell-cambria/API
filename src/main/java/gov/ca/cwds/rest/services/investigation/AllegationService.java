package gov.ca.cwds.rest.services.investigation;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.AllegationDao;
import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation People
 * 
 * @author CWDS API Team
 */
public class AllegationService implements TypedCrudsService<String, Allegation, Response> {

  private AllegationDao allegationDao;
  private Allegation validAllegation = new AllegationEntityBuilder().build();

  /**
   * @param allegationDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.Allegation} objects
   */
  @Inject
  public AllegationService(AllegationDao allegationDao) {
    super();
    this.allegationDao = allegationDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validAllegation;
  }

  @Override
  public Response create(Allegation request) {
    return validAllegation;
  }

  @Override
  public Response delete(String primaryKey) {
    return validAllegation;
  }

  @Override
  public Response update(String primaryKey, Allegation request) {
    return validAllegation;
  }
}
