package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;

/**
 * Business layer object to work on {@link CrossReport}.
 * 
 * @author CWDS API Team
 */
public class CrossReportService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.CrossReport, gov.ca.cwds.rest.api.domain.cms.CrossReport> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrossReportService.class);

  private CrossReportDao crossReportDao;

  // Used to implicitly check for referential Integrity. Better to find way to make explicit
  private RICrossReport riCrossReport;

  /**
   * Constructor
   * 
   * @param crossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.CrossReport} objects.
   * @param riCrossReport the ri for cross report
   */
  @Inject
  public CrossReportService(CrossReportDao crossReportDao, RICrossReport riCrossReport) {
    this.crossReportDao = crossReportDao;
    this.riCrossReport = riCrossReport;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.CrossReport persistedCrossReport =
        crossReportDao.find(primaryKey);
    if (persistedCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(persistedCrossReport);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.CrossReport persistedCrossReport =
        crossReportDao.delete(primaryKey);
    if (persistedCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(persistedCrossReport);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport create(
      gov.ca.cwds.rest.api.domain.cms.CrossReport request) {
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport = request;
    try {
      CrossReport managed = new CrossReport(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
          crossReport, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      managed = crossReportDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("CrossReport already exists : {}", crossReport);
      throw new ServiceException("CrossReport already exists : {}" + crossReport, e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.CrossReport request) {
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport = request;

    try {
      CrossReport managed = new CrossReport(crossReport.getThirdId(), crossReport,
          RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      managed = crossReportDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("CrossReport not found : {}", crossReport);
      throw new ServiceException(e);
    }
  }

  /**
   * @return the riCrossReport
   */
  public RICrossReport getRiCrossReport() {
    return riCrossReport;
  }

}
