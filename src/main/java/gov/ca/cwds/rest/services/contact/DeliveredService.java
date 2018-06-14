package gov.ca.cwds.rest.services.contact;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}.
 * 
 * @author CWDS API Team
 */
public class DeliveredService
    implements TypedCrudsService<String, DeliveredServiceDomain, DeliveredServiceDomain> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveredService.class);

  private DeliveredServiceDao deliveredServiceDao;

  /**
   * @param deliveredServiceDao {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   */
  @Inject
  public DeliveredService(DeliveredServiceDao deliveredServiceDao) {
    super();
    this.deliveredServiceDao = deliveredServiceDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.contact.DeliveredServiceDomain create(
      DeliveredServiceDomain request) {
    gov.ca.cwds.rest.api.contact.DeliveredServiceDomain deliveredServiceDomain = request;

    try {
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity managed =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(
              CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
              deliveredServiceDomain, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = deliveredServiceDao.create(managed);
      return new gov.ca.cwds.rest.api.contact.DeliveredServiceDomain(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("deliveredServiceEntity already exists : {}", deliveredServiceDomain);
      throw new ServiceException(e);
    }
  }

  @Override
  public gov.ca.cwds.rest.api.contact.DeliveredServiceDomain delete(String id) {
    throw new NotImplementedException("delete not implement");
  }

  @Override
  public gov.ca.cwds.rest.api.contact.DeliveredServiceDomain find(String id) {
    throw new NotImplementedException("find not implement");
  }

  @Override
  public gov.ca.cwds.rest.api.contact.DeliveredServiceDomain update(String id,
      gov.ca.cwds.rest.api.contact.DeliveredServiceDomain arg1) {
    throw new NotImplementedException("update not implement");
  }

}
