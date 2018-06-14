package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.cms.PostedReporter;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;

/**
 * Business layer object to work on {@link Reporter}
 * 
 * @author CWDS API Team
 */
public class ReporterService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Reporter, gov.ca.cwds.rest.api.domain.cms.Reporter> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReporterService.class);

  private ReporterDao reporterDao;

  // Used to implicitly check for referential Integrity. Better to find way to make explicit
  private RIReporter riReporter;

  /**
   * Constructor
   * 
   * @param reporterDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Reporter}
   *        objects.
   * @param riReporter see {@link RIReporter}
   */
  @Inject
  public ReporterService(ReporterDao reporterDao, RIReporter riReporter) {
    this.reporterDao = reporterDao;
    this.riReporter = riReporter;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Reporter find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Reporter persistedReporter = reporterDao.find(primaryKey);
    if (persistedReporter != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Reporter(persistedReporter);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Reporter delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Reporter persistedReporter = reporterDao.delete(primaryKey);
    if (persistedReporter != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Reporter(persistedReporter);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedReporter create(gov.ca.cwds.rest.api.domain.cms.Reporter request) {
    gov.ca.cwds.rest.api.domain.cms.Reporter reporter = request;
    try {
      Reporter managed = new Reporter(reporter, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      managed = reporterDao.create(managed);
      return new PostedReporter(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Reporter already exists : {}", reporter);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Reporter update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Reporter request) {
    gov.ca.cwds.rest.api.domain.cms.Reporter reporter = request;

    try {
      Reporter managed = new Reporter(reporter, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      managed = reporterDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Reporter(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Reporter not found : {}", reporter);
      throw new ServiceException(e);
    }
  }

  /**
   * @return the riReporter
   */
  public RIReporter getRiReporter() {
    return riReporter;
  }

}
