package gov.ca.cwds.rest.services.cms.xa;

import javax.validation.Validator;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.services.cms.AddressService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;

public class XaNsReferralService extends ReferralService {

  @Inject
  public XaNsReferralService(ReferralDao referralDao, NonLACountyTriggers nonLaTriggers,
      LACountyTrigger laCountyTrigger, TriggerTablesDao triggerTablesDao,
      StaffPersonDao staffpersonDao, AssignmentService assignmentService, Validator validator,
      CmsDocumentService cmsDocumentService, DrmsDocumentService drmsDocumentService,
      DrmsDocumentTemplateService drmsDocumentTemplateService, AddressService addressService,
      LongTextService longTextService, RIReferral riReferral) {
    super(referralDao, nonLaTriggers, laCountyTrigger, triggerTablesDao, staffpersonDao,
        assignmentService, validator, cmsDocumentService, drmsDocumentService,
        drmsDocumentTemplateService, addressService, longTextService, riReferral);
  }

}
