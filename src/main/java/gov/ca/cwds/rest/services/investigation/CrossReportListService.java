package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.CrossReportDao;
import gov.ca.cwds.fixture.investigation.CrossReportListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.CrossReportList;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation Cross Reports
 * 
 * @author CWDS API Team
 */
public class CrossReportListService
    implements TypedCrudsService<String, CrossReportList, Response> {

  private CrossReportList crossReportListStub = new CrossReportListEntityBuilder().build();

  /**
   * @param crossReportDao {@link Dao} handling
   */
  @Inject
  public CrossReportListService(CrossReportDao crossReportDao) {
    super();
  }

  @Override
  public Response find(String primaryKey) {
    return crossReportListStub;
  }

  @Override
  public Response create(CrossReportList request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, CrossReportList request) {
    throw new NotImplementedException("update not implemented");
  }
}
