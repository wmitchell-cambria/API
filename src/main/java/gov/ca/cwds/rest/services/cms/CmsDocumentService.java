package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.api.domain.legacy.CmsDocument;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Business layer object to work on {@link Screening}
 * 
 * @author CWDS API Team
 */
public class CmsDocumentService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentService.class);

  private CmsDocumentDao dao;

  /**
   * 
   * @param dao The {@link Dao} handling {@link gov.ca.cwds.rest.api.persistence.ns.Screening}
   *        objects.
   * @param personService The person service
   */
  public CmsDocumentService(CmsDocumentDao dao) {
    this.dao = dao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public CmsDocument find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    LOGGER.info("primaryKey=" + primaryKey);
    gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      return new CmsDocument(doc);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedScreening create(Request request) {
    // assert (request instanceof ScreeningReference);
    throw new NotImplementedException("Create is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ScreeningResponse update(Serializable primaryKey, Request request) {
    assert (primaryKey instanceof Long);
    // assert (request instanceof ScreeningRequest);
    throw new NotImplementedException("Update is not implemented");
  }

}
