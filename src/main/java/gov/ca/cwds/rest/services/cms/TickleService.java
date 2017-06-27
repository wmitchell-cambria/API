package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Tickle;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedTickle;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link Tickle}
 * 
 * @author CWDS API Team
 */
public class TickleService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TickleService.class);

  private TickleDao tickleDao;
  private StaffPersonIdRetriever staffPersonIdRetriever;


  /**
   * @param tickleDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Tickle}
   *        objects.
   * @param staffPersonIdRetriever the staffPersonIdRetriever
   */
  @Inject
  public TickleService(TickleDao tickleDao, StaffPersonIdRetriever staffPersonIdRetriever) {
    this.tickleDao = tickleDao;
    this.staffPersonIdRetriever = staffPersonIdRetriever;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Tickle;

    gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
        (gov.ca.cwds.rest.api.domain.cms.Tickle) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      gov.ca.cwds.data.persistence.cms.Tickle managed = new gov.ca.cwds.data.persistence.cms.Tickle(
          CmsKeyIdGenerator.generate(lastUpdatedId), tickle, lastUpdatedId);
      managed = tickleDao.create(managed);
      return new PostedTickle(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("tickle already exists : {}", tickle);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle delete(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Tickle persistedTickle = tickleDao.delete(primaryKey);
    if (persistedTickle != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(persistedTickle);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle find(Serializable primaryKey) {
    assert primaryKey instanceof String;

    gov.ca.cwds.data.persistence.cms.Tickle persistedTickle = tickleDao.find(primaryKey);
    if (persistedTickle != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(persistedTickle);
    }

    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Tickle update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.Tickle;

    gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
        (gov.ca.cwds.rest.api.domain.cms.Tickle) request;

    try {
      String lastUpdatedId = staffPersonIdRetriever.getStaffPersonId();
      Tickle managed = new Tickle((String) primaryKey, tickle, lastUpdatedId);
      managed = tickleDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Tickle(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("tickle not found : {}", tickle);
      throw new ServiceException(e);
    }
  }

}