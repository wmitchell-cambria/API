package gov.ca.cwds.rest.services.hoi;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * @author CWDS API Team
 *
 */
public class ReferralHistoryOfInvolvementService implements CrudsService {

  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("Find is not implemented");
  }

  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public Response create(Request request) {
    throw new NotImplementedException("Create is not implemented");
  }

  @Override
  public Response update(Serializable primaryKey, Request request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
