package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.fixture.investigation.ScreeningSummaryEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Screening Summary
 * 
 * @author CWDS API Team
 */
public class ScreeningSummaryService
    implements TypedCrudsService<String, ScreeningSummary, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningSummaryService.class);

  private DeliveredServiceDao deliveredServiceDao;

  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   */
  @Inject
  public ScreeningSummaryService(DeliveredServiceDao deliveredServiceDao) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {

    ScreeningSummary serialized = new ScreeningSummaryEntityBuilder().build();
    return serialized;
  }


  @Override
  public Response create(ScreeningSummary request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, ScreeningSummary request) {
    throw new NotImplementedException("update not implemented");
  }

}
