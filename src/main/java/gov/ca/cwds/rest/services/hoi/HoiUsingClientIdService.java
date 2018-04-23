package gov.ca.cwds.rest.services.hoi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * This is service class to retrieve all referrals, cases and screenings based on the clientId.
 * 
 * @author CWDS API Team
 */
public class HoiUsingClientIdService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  @Inject
  InvolvementHistoryService involvementHistoryService;

  /**
   * Default constructor.
   */
  public HoiUsingClientIdService() {
    super();
  }

  @Override
  public Response find(String primaryKey) {
    throw new NotImplementedException("request get not implemented");
  }

  /**
   * Find the history of involvements for client, cases and screenings using clientIds.
   * 
   * @param clientIds - all clients Id's to get hoi
   * @return the clients history
   */
  @UnitOfWork(value = "ns")
  public Response findByClientIds(List<String> clientIds) {
    Set<String> clientIdSet = new HashSet<>(clientIds);
    return involvementHistoryService.findInvolvementHistoryByClientIds(clientIdSet);
  }

  @Override
  public Response create(InvolvementHistory primaryKey) {
    throw new NotImplementedException("request create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("request delete not implemented");
  }

  @Override
  public Response update(String primaryKey, InvolvementHistory request) {
    throw new NotImplementedException("request update not implemented");
  }

}
