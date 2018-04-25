package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.PostedAllegation;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;

/**
 * Business layer object to work on {@link Allegation}
 * 
 * @author CWDS API Team
 */
public class AllegationService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Allegation, gov.ca.cwds.rest.api.domain.cms.Allegation> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AllegationService.class);

  private AllegationDao allegationDao;
  // Used to implicitly check for referential Integrity. Better to find way to make explicit
  @SuppressWarnings("unused")
  private RIAllegation riAllegation;

  /**
   * Constructor
   * 
   * @param allegationDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Allegation} objects.
   * @param riAllegation the ri for allegation
   */
  @Inject
  public AllegationService(AllegationDao allegationDao, RIAllegation riAllegation) {
    this.allegationDao = allegationDao;
    this.riAllegation = riAllegation;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Allegation find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation =
        allegationDao.find(primaryKey);
    if (persistedAllegation != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(persistedAllegation);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Allegation delete(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Allegation persistedAllegation =
        allegationDao.delete(primaryKey);
    if (persistedAllegation != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(persistedAllegation);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAllegation create(gov.ca.cwds.rest.api.domain.cms.Allegation request) {
    gov.ca.cwds.rest.api.domain.cms.Allegation allegation = request;

    try {
      Allegation managed = new Allegation(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
          allegation, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      managed = allegationDao.create(managed);
      return new PostedAllegation(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Allegation already exists : {}", allegation);
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
  public gov.ca.cwds.rest.api.domain.cms.Allegation update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Allegation request) {
    gov.ca.cwds.rest.api.domain.cms.Allegation allegation = request;

    try {
      Allegation managed =
          new Allegation(primaryKey, allegation, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = allegationDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Allegation(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Allegation not found : {}", allegation);
      throw new ServiceException(e);
    }
  }

  /**
   * @return the riAllegation
   */
  public RIAllegation getRiAllegation() {
    return riAllegation;
  }

}
