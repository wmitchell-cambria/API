package gov.ca.cwds.rest.services.hoi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * @author CWDS API Team
 *
 */
public class HOIUsingClientIdService
    implements TypedCrudsService<String, InvolvementHistory, Response> {

  @Inject
  InvolvementHistoryService involvementHistoryService;

  /**
   * 
   */
  public HOIUsingClientIdService() {
    super();
  }

  @Override
  public Response find(String arg0) {
    return null;
  }

  /**
   * @param clientIds - all clients Id's to get hoi
   * @return the clients history
   */
  @UnitOfWork(value = "ns")
  public Response findByClientIds(List<String> clientIds) {
    Set<String> clientIdSet = new HashSet<>(clientIds);
    return involvementHistoryService.findInvolvementHistoryByClientIds(clientIdSet);

  }

  @Override
  public Response create(InvolvementHistory arg0) {
    return null;
  }

  @Override
  public Response delete(String arg0) {
    return null;
  }

  @Override
  public Response update(String arg0, InvolvementHistory arg1) {
    return null;
  }

}
