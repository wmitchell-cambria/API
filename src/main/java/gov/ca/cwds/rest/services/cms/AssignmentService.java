package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.R03731StartTimeSetting;
import gov.ca.cwds.rest.business.rules.R04530AssignmentEndDateValidator;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIAssignment;
import gov.ca.cwds.rest.validation.StartDateTimeValidator;

/**
 * Business layer object serves {@link Assignment}.
 * 
 * @author CWDS API Team
 */
public class AssignmentService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Assignment, gov.ca.cwds.rest.api.domain.cms.Assignment> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentService.class);
  private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
  private static final String TIME_FORMAT_PATTERN = "HH:mm:ss";

  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private ExternalInterfaceTables externalInterfaceTables;
  private RIAssignment riAssignment;
  private CaseLoadDao caseLoadDao;

  private Validator validator;

  /**
   * Constructor
   * 
   * @param assignmentDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.Assignment} objects.
   * @param nonLACountyTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param triggerTablesDao - triggerTablesDao
   * @param validator the validator to use to validate validatable objects
   * @param externalInterfaceTables external interface table
   * @param riAssignment - riAssignment
   * @param caseLoadDao - caseLoadDao
   */
  @Inject
  public AssignmentService(AssignmentDao assignmentDao, NonLACountyTriggers nonLACountyTriggers,
      StaffPersonDao staffpersonDao, TriggerTablesDao triggerTablesDao, Validator validator,
      ExternalInterfaceTables externalInterfaceTables, RIAssignment riAssignment,
      CaseLoadDao caseLoadDao) {
    this.assignmentDao = assignmentDao;
    this.nonLACountyTriggers = nonLACountyTriggers;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.validator = validator;
    this.externalInterfaceTables = externalInterfaceTables;
    this.riAssignment = riAssignment;
    this.caseLoadDao = caseLoadDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.find(primaryKey);
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Assignment persistedAssignment =
        assignmentDao.delete(primaryKey);
    externalInterfaceTables.createExtInterForDelete(primaryKey, "ASGNM_T");
    if (persistedAssignment != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(persistedAssignment);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAssignment create(gov.ca.cwds.rest.api.domain.cms.Assignment request) {
    gov.ca.cwds.rest.api.domain.cms.Assignment assignment = request;

    try {
      Assignment managed = new Assignment(
          CmsKeyIdGenerator.generate(RequestExecutionContext.instance().getStaffId()), assignment,
          RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      this.validateAssignmentEndDate(assignment);
      managed = assignmentDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Assignment ID cannot be null");
      }
      createDownStreamEntity(managed);
      return new PostedAssignment(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("Assignment already exists : {}", assignment);
      throw new ServiceException(e);
    }
  }

  private void createDownStreamEntity(Assignment managed) {
    // checking the staffPerson county code
    StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
    if (staffperson != null
        && !(triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
      nonLACountyTriggers.createAndUpdateReferralCoutyOwnership(managed);
    }
    externalInterfaceTables.createExtInterAssignment(managed, "N");
  }

  /**
   * @param screeningToReferral - screeningToReferral
   * @param referralId - referralId
   * @param referral - referral
   * @param messageBuilder - messageBuilder
   */
  // create a default assignment
  // R - 02473 Default Referral Assignment
  // R - 02160 Assignment - Caseload Access
  public void createDefaultAssignmentForNewReferral(ScreeningToReferral screeningToReferral,
      String referralId, Referral referral, MessageBuilder messageBuilder) {

    String caseLoadId = "";
    String COUNTY_CODE = "00";
    if (staffpersonDao.find(screeningToReferral.getAssigneeStaffId()) == null) {
      String message = "The given assigneeStaffId is not found";
      ServiceException se = new ServiceException(message);
      messageBuilder.addMessageAndLog(message, se, LOGGER);
    } else {
      caseLoadId = assignmentDao.findCaseId(screeningToReferral.getAssigneeStaffId());
      if (caseLoadId == null) {
        String message = "CaseLoad is not found for the staffperson";
        ServiceException se = new ServiceException(message);
        messageBuilder.addMessageAndLog(message, se, LOGGER);
      }
    }

    if (StringUtils.isNotBlank(caseLoadId)) {
      final CaseLoad caseLoad = caseLoadDao.find(caseLoadId);
      COUNTY_CODE = caseLoad.getCountySpecificCode();
    }

    gov.ca.cwds.rest.api.domain.cms.Assignment da = createDefaultAssignmentToCaseLoad(COUNTY_CODE,
        referralId, screeningToReferral.getStartedAt(), caseLoadId, messageBuilder);
    messageBuilder.addDomainValidationError(validator.validate(da));

    if (!new R03731StartTimeSetting(referral, da).isValid()) {
      String message =
          "Referral Recieved Date/Time does not equal Assignment Start Date/Time (R - 03731)";
      ServiceException se = new ServiceException(message);
      messageBuilder.addMessageAndLog(message, se, LOGGER);
    }

    if ("R".equals(da.getEstablishedForCode())
        && ("P".equals(da.getTypeOfAssignmentCode()) || ("S".equals(da.getTypeOfAssignmentCode())))
        || (da.getSecondaryAssignmentRoleType() == 143)) {
      externalInterfaceTables.createExternalInterfaceReferral(referralId, "C");
    }

    try {
      this.create(da);
    } catch (ServiceException e) {
      String message = e.getMessage();
      messageBuilder.addMessageAndLog(message, e, LOGGER);
    }
  }

  /**
   * @param countyCode - county code
   * @param referralId - referral Id
   * @param startDateTime - start date of assignment
   * @param caseLoadId - case load id of assignment
   * @return - default Assignment
   */
  private gov.ca.cwds.rest.api.domain.cms.Assignment createDefaultAssignmentToCaseLoad(
      String countyCode, String referralId, String startDateTime, String caseLoadId,
      MessageBuilder messageBuilder) {
    // #146713651 - BARNEY: Referrals require a default assignment
    // Default Assignment - referrals will be assigned to the '0X5' staff person ID.
    //
    // An assignment is the association between a Staff Person Case Load and the Referral
    //

    // extract and format the assignment start date/time from the passed start date/time
    String dateStarted = StartDateTimeValidator.extractStartDate(startDateTime, messageBuilder);
    String timeStarted = StartDateTimeValidator.extractStartTime(startDateTime, messageBuilder);

    gov.ca.cwds.rest.api.domain.cms.Assignment assignment =
        new gov.ca.cwds.rest.api.domain.cms.Assignment();
    return assignment.createDefaultReferralAssignment(countyCode, referralId, caseLoadId,
        dateStarted, timeStarted);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Assignment update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Assignment request) {
    gov.ca.cwds.rest.api.domain.cms.Assignment assignment = request;

    try {
      Assignment managed =
          new Assignment(primaryKey, assignment, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      this.validateAssignmentEndDate(assignment);
      managed = assignmentDao.update(managed);
      externalInterfaceTables.createExtInterAssignment(managed, "C");
      return new gov.ca.cwds.rest.api.domain.cms.Assignment(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Assignment not found : {}", assignment);
      throw new ServiceException(e);
    }
  }

  private void validateAssignmentEndDate(gov.ca.cwds.rest.api.domain.cms.Assignment assignment) {
    if (!new R04530AssignmentEndDateValidator(assignment).isValid()) {
      throw new ServiceException(
          "Rule : R - 04530 - Assignment End Date and Time must be less than or equal to the current system date and time AND must be greater than or equal to Assignment Start Date and Time");
    }

  }

}
