package gov.ca.cwds.rest.services.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.HistoryOfInvolvement;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on History Of Involvement
 * 
 * @author CWDS API Team
 */
public class HistoryOfInvolvementService
    implements TypedCrudsService<String, HistoryOfInvolvement, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryOfInvolvementService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Inject
  public HistoryOfInvolvementService() {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String primaryKey) {
    // please do not change this code until we are ready to replace stubbed data with data from db2
    try {
      return MAPPER.readValue(
          fixture("gov/ca/cwds/rest/services/investigation/historyOfInvolvement/valid/valid.json"),
          HistoryOfInvolvement.class);
    } catch (Exception e) {
      LOGGER.error("Exception in finding stubbed data for HistoryOfInvolvement {}", e.getMessage());
      throw new ServiceException("Exception In finding stubbed data for HistoryOfInvolvement", e);
    }
  }

  @Override
  public Response create(HistoryOfInvolvement request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }


  @Override
  public Response update(String primaryKey, HistoryOfInvolvement request) {
    throw new NotImplementedException("update not implemented");
  }

}
