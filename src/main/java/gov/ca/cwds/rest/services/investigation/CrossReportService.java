package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.fixture.investigation.CrossReportEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on investigation Cross Reports
 * 
 * @author CWDS API Team
 */
public class CrossReportService implements TypedCrudsService<String, CrossReport, Response> {

  private static CrossReport crossReportStub = new CrossReportEntityBuilder().build();

  @Inject
  public CrossReportService() {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String primaryKey) {
    throw new NotImplementedException("find not implemented");
  }

  @Override
  public Response create(CrossReport request) {
    return crossReportStub;
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, CrossReport request) {
    return crossReportStub;
  }

}
