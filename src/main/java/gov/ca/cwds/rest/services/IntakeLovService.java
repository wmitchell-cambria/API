package gov.ca.cwds.rest.services;

import java.util.stream.Collectors;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.IntakeLovEntry;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for Intake LOV listings.
 * 
 * @author CWDS API Team
 */
public class IntakeLovService
    extends SimpleResourceService<String, IntakeLovEntry, IntakeLovResponse> implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private transient IntakeLovDao dao;

  /**
   * Constructor
   * 
   * @param dao main DAO
   */
  @Inject
  public IntakeLovService(IntakeLovDao dao) {
    this.dao = dao;
  }

  @Override
  protected IntakeLovResponse handleRequest(IntakeLovEntry req) {
    return new IntakeLovResponse(
        dao.findAll().stream().map(IntakeLovEntry::new).collect(Collectors.toList()));
  }

  @Override
  protected IntakeLovResponse handleFind(String searchForThis) {
    try {
      return handleRequest(new IntakeLovEntry());
    } catch (Exception e) {
      throw new ServiceException("ERROR handling \"find\"", e);
    }
  }

}
