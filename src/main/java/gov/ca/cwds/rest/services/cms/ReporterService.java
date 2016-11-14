package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.legacy.PostedReporter;
import gov.ca.cwds.rest.api.persistence.cms.Reporter;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.ReporterDao;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

/**
 * Business layer object to work on {@link Reporter}
 * 
 * @author CWDS API Team
 */
public class ReporterService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReporterService.class);

  private ReporterDao reporterDao;

  /**
   * Constructor
   * 
   * @param reporterDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.cms.Reporter} objects.
   */
  public ReporterService(ReporterDao reporterDao) {
    this.reporterDao = reporterDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.Reporter find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    gov.ca.cwds.rest.api.persistence.cms.Reporter persistedReporter = reporterDao.find(primaryKey);
    if (persistedReporter != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.Reporter(persistedReporter);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.Reporter delete(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    gov.ca.cwds.rest.api.persistence.cms.Reporter persistedReporter =
        reporterDao.delete(primaryKey);
    if (persistedReporter != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.Reporter(persistedReporter);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedReporter create(Request request) {
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.Reporter);

    gov.ca.cwds.rest.api.domain.legacy.Reporter reporter =
        ((gov.ca.cwds.rest.api.domain.legacy.Reporter) request);

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now
      Reporter managed = new Reporter(IdGenerator.randomString(10), reporter, "q1p");

      managed = reporterDao.create(managed);
      return new PostedReporter(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Reporter already exists : {}", reporter);
      throw new ServiceException(e);
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.Reporter update(Serializable primaryKey,
      Request request) {
    assert (primaryKey instanceof String);
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.Reporter);
    gov.ca.cwds.rest.api.domain.legacy.Reporter reporter =
        ((gov.ca.cwds.rest.api.domain.legacy.Reporter) request);


    try {
      Reporter managed = new Reporter((String) primaryKey, reporter, "q1p");

      managed = reporterDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.legacy.Reporter(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Reporter not found : {}", reporter);
      throw new ServiceException(e);
    }
  }

}

