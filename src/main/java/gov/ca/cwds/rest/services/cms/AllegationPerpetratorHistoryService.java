package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegationPerpetratorHistory;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;

/**
 * Business layer object to work on {@link AllegationPerpetratorHistory}.
 * 
 * @author CWDS API Team
 */
public class AllegationPerpetratorHistoryService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory, gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(AllegationPerpetratorHistoryService.class);

  private AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao;

  // Used to implicitly check for referential Integrity. Better to find way to make explicit
  private RIAllegationPerpetratorHistory riAllegationPerpetratorHistory;

  /**
   * Constructor
   * 
   * @param allegationPerpetratorHistoryDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory} objects.
   * @param riAllegationPerpetratorHistory the riAllegationPerpetratorHistory
   */
  @Inject
  public AllegationPerpetratorHistoryService(
      AllegationPerpetratorHistoryDao allegationPerpetratorHistoryDao,
      RIAllegationPerpetratorHistory riAllegationPerpetratorHistory) {
    this.allegationPerpetratorHistoryDao = allegationPerpetratorHistoryDao;
    this.riAllegationPerpetratorHistory = riAllegationPerpetratorHistory;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistedAllegationPerpetratorHistory =
        allegationPerpetratorHistoryDao.find(primaryKey);
    if (persistedAllegationPerpetratorHistory != null) {
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(
          persistedAllegationPerpetratorHistory);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory persistedAllegationPerpetratorHistory =
        allegationPerpetratorHistoryDao.delete(primaryKey);
    if (persistedAllegationPerpetratorHistory != null) {
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(
          persistedAllegationPerpetratorHistory);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAllegationPerpetratorHistory create(
      gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory request) {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratoryHistory =
        request;

    try {
      AllegationPerpetratorHistory managed = new AllegationPerpetratorHistory(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
          allegationPerpetratoryHistory, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());

      managed = allegationPerpetratorHistoryDao.create(managed);
      return new PostedAllegationPerpetratorHistory(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("AllegationPerpetratorHistory already exists : {}",
          allegationPerpetratoryHistory);
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
  public gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory request) {
    gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory allegationPerpetratorHistory =
        request;

    try {
      AllegationPerpetratorHistory managed = new AllegationPerpetratorHistory(primaryKey,
          allegationPerpetratorHistory, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      managed = allegationPerpetratorHistoryDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.AllegationPerpetratorHistory(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("AllegationPerpetratorHistory not found : {}", allegationPerpetratorHistory);
      throw new ServiceException(e);
    }
  }

  /**
   * @return the riAllegationPerpetratorHistory
   */
  public RIAllegationPerpetratorHistory getRiAllegationPerpetratorHistory() {
    return riAllegationPerpetratorHistory;
  }

}
