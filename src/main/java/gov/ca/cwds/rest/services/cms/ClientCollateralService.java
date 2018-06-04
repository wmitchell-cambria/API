package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientCollateralDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.ClientCollateral;
import gov.ca.cwds.rest.api.domain.cms.PostedClientCollateral;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientCollateral;

/**
 * Business layer object to work on {@link ClientCollateral}.
 * 
 * @author CWDS API Team
 */
public class ClientCollateralService
    implements TypedCrudsService<String, ClientCollateral, ClientCollateral> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientCollateralService.class);

  private ClientCollateralDao clientCollateralDao;
  // Used to implicitly check for referential Integrity. Better to find way to make explicit
  private RIClientCollateral ri;

  /**
   * Constructor
   * 
   * @param clientCollateralDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientCollateral} objects.
   * @param ri referential integrity checker
   */
  @Inject
  public ClientCollateralService(ClientCollateralDao clientCollateralDao, RIClientCollateral ri) {
    this.clientCollateralDao = clientCollateralDao;
    this.ri = ri;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.PostedClientCollateral find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.ClientCollateral persistedClientCollateral =
        clientCollateralDao.find(primaryKey);
    if (persistedClientCollateral != null) {
      return new gov.ca.cwds.rest.api.domain.cms.PostedClientCollateral(persistedClientCollateral);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientCollateral delete(String primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedClientCollateral create(ClientCollateral request) {
    ClientCollateral clientCollateral = request;

    try {
      gov.ca.cwds.data.persistence.cms.ClientCollateral managed =
          new gov.ca.cwds.data.persistence.cms.ClientCollateral(
              CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
              clientCollateral, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = clientCollateralDao.create(managed);
      return new PostedClientCollateral(managed);
    } catch (EntityExistsException e) {
      LOGGER.warn("ClientCollateral already exists : {}", clientCollateral);
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
  public ClientCollateral update(String primaryKey, ClientCollateral request) {
    throw new NotImplementedException("Update is not implemented");
  }

  /**
   * @return the ri
   */
  public RIClientCollateral getRi() {
    return ri;
  }

}
