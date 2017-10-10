package gov.ca.cwds.rest.services.investigation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.fixture.investigation.InvestigationEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.jackson.Jackson;

/**
 * Business layer object to work on Investigation
 * 
 * @author CWDS API Team
 */
public class InvestigationService implements TypedCrudsService<String, Investigation, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvestigationService.class);

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private InvestigationDao investigationDao;
  private StaffPersonDao staffPersonDao;
  private AddressDao addressDao;
  private LongTextDao longTextDao;

  private Investigation validInvestigation = new InvestigationEntityBuilder().build();


  /**
   * 
   * @param investigationDao - investigationDao service
   * @param staffPersonDao - staffPersonDao service
   * @param addressDao - addressDao service
   * @param longTextDao - longTextDao service
   */
  @Inject
  public InvestigationService(InvestigationDao investigationDao, StaffPersonDao staffPersonDao,
      AddressDao addressDao, LongTextDao longTextDao) {
    super();
    this.investigationDao = investigationDao;
    this.addressDao = addressDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextDao = longTextDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */

  @Override
  public Response find(String primaryKey) {
    Investigation validInvestigation = null;
    Referral referral = investigationDao.find(primaryKey);

    if (referral == null) {
      throw new ServiceException("Referral/Investigation not found for provided id :" + primaryKey);

      // validInvestigation = this.validInvestigation;

    } else {

      Address address = this.findIncidentAddress(referral.getAllegesAbuseOccurredAtAddressId());
      StaffPerson staffPerson = this.findStaffPersonById(referral.getPrimaryContactStaffPersonId());
      LongText rptNarrativeLongText =
          this.findReportNarrativeLongTextById(referral.getCurrentLocationOfChildren());
      LongText addInfoLongText =
          findAdditionalInfoLongTextById(referral.getResponseRationaleText());

      validInvestigation =
          new Investigation(referral, address, staffPerson, rptNarrativeLongText, addInfoLongText);
    }

    return validInvestigation;
  }



  /**
   * Find the Staff Person who last updated the deliveredServiceEntity persistence object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return The Staff Person who last updated the persistence object
   */
  private StaffPerson findStaffPersonById(String staffPersonId) {
    StaffPerson staffPerson = null;
    staffPerson = staffPersonDao.find(staffPersonId);
    return staffPerson;
  }

  /**
   * finding incident address details
   * 
   * @param allegesAbuseOccurredAtAddressId - allegesAbuseOccurredAtAddressId
   * @return instance of object
   */
  private Address findIncidentAddress(String allegesAbuseOccurredAtAddressId) {
    Address address = null;
    if (StringUtils.isNotBlank(allegesAbuseOccurredAtAddressId)) {
      address = this.addressDao.find(allegesAbuseOccurredAtAddressId);

    }
    return address;
  }

  /**
   * finding LongText instance based on ReportNarrativeLongTextById
   * 
   * @param longTextId - long text id value
   * @return instance of LongText
   */
  private LongText findReportNarrativeLongTextById(String longTextId) {
    LongText longText = null;
    if (StringUtils.isNotBlank(longTextId)) {
      longText = this.longTextDao.find(longTextId);
    }
    return longText;
  }

  /**
   * finding LongText instance based on responseRationaleTextId
   * 
   * @param responseRationaleTextId - long text id value
   * @return instance of LongText
   */
  private LongText findAdditionalInfoLongTextById(String responseRationaleTextId) {
    LongText longText = null;
    if (StringUtils.isNotBlank(responseRationaleTextId)) {
      longText = this.longTextDao.find(responseRationaleTextId);
    }
    return longText;

  }


  @Override
  public Investigation delete(String primaryKey) {
    return validInvestigation;
  }

  @Override
  public Response create(Investigation request) {
    return validInvestigation;
  }

  @Override
  public Response update(String primaryKey, Investigation request) {
    return validInvestigation;
  }

}
