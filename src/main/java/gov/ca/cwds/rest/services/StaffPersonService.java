package gov.ca.cwds.rest.services;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.legacy.PostedStaffPerson;
import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.StaffPersonDao;
import gov.ca.cwds.rest.util.IdGenerator;

/**
 * Business layer object to work on {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
public class StaffPersonService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonService.class);

  private StaffPersonDao staffPersonDao;

  /**
   * Constructor
   * 
   * @param staffPersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.cms.StaffPerson} objects.
   */
  public StaffPersonService(StaffPersonDao staffPersonDao) {
    this.staffPersonDao = staffPersonDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.StaffPerson find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    gov.ca.cwds.rest.api.persistence.cms.StaffPerson persistedStaffPerson =
        staffPersonDao.find(primaryKey);
    if (persistedStaffPerson != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(persistedStaffPerson);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.legacy.StaffPerson delete(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    gov.ca.cwds.rest.api.persistence.cms.StaffPerson persistedStaffPerson =
        staffPersonDao.delete(primaryKey);
    if (persistedStaffPerson != null) {
      return new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(persistedStaffPerson);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedStaffPerson create(Request request) {
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.StaffPerson);

    gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson =
        ((gov.ca.cwds.rest.api.domain.legacy.StaffPerson) request);

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now
      StaffPerson managed = new StaffPerson(IdGenerator.randomString(3), staffPerson, "q1p");

      managed = staffPersonDao.create(managed);
      return new PostedStaffPerson(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("StaffPerson already exists : {}", staffPerson);
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
  public gov.ca.cwds.rest.api.domain.legacy.StaffPerson update(Serializable primaryKey,
      Request request) {
    assert (primaryKey instanceof String);
    assert (request instanceof gov.ca.cwds.rest.api.domain.legacy.StaffPerson);
    gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson =
        ((gov.ca.cwds.rest.api.domain.legacy.StaffPerson) request);


    try {
      StaffPerson managed = new StaffPerson((String) primaryKey, staffPerson, "q1p");

      managed = staffPersonDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.legacy.StaffPerson(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("StaffPerson not found : {}", staffPerson);
      throw new ServiceException(e);
    }
  }

}

