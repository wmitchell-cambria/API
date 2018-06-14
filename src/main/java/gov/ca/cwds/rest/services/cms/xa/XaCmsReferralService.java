package gov.ca.cwds.rest.services.cms.xa;

import javax.validation.Validator;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.xa.XaCmsReferralDaoImpl;
import gov.ca.cwds.data.cms.xa.XaCmsStaffPersonDaoImpl;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.xa.XaNonLACountyTriggers;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;

public class XaCmsReferralService extends ReferralService {

  @Inject
  public XaCmsReferralService(XaCmsReferralDaoImpl referralDao, XaNonLACountyTriggers nonLaTriggers,
      LACountyTrigger laCountyTrigger, TriggerTablesDao triggerTablesDao,
      XaCmsStaffPersonDaoImpl staffpersonDao, AssignmentService assignmentService, Validator validator,
      CmsDocumentService cmsDocumentService, DrmsDocumentService drmsDocumentService,
      DrmsDocumentTemplateService drmsDocumentTemplateService, XaCmsAddressService addressService,
      LongTextService longTextService, RIReferral riReferral) {
    super(referralDao, nonLaTriggers, laCountyTrigger, triggerTablesDao, staffpersonDao,
        assignmentService, validator, cmsDocumentService, drmsDocumentService,
        drmsDocumentTemplateService, addressService, longTextService, riReferral);
  }

}
