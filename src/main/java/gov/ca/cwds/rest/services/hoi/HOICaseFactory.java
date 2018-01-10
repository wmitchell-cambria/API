package gov.ca.cwds.rest.services.hoi;

import java.util.List;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIRelatedPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.hoi.HOIVictim;

/**
 * @author CWDS API Team
 *
 */
public class HOICaseFactory {

  /**
   * @param cmscase - cmscase
   * @param county - county
   * @param serviceComponent - serviceComponent
   * @param focusChild - focusChild
   * @param assignedSocialWorker - assignedSocialWorker
   * @param parents - parents
   * @return the built Cases HOI
   */
  public HOICase createHOICase(CmsCase cmscase, SystemCodeDescriptor county,
      SystemCodeDescriptor serviceComponent, HOIVictim focusChild,
      HOISocialWorker assignedSocialWorker, List<HOIRelatedPerson> parents) {
    HOICase hoiCase = new HOICase();
    String cmscaseId = cmscase.getId();
    hoiCase.setId(cmscaseId);
    hoiCase.setStartDate(cmscase.getStartDate());
    hoiCase.setEndDate(cmscase.getEndDate());
    hoiCase.setCounty(county);
    hoiCase.setServiceComponent(serviceComponent);
    hoiCase.setFocusChild(focusChild);
    hoiCase.setAssignedSocialWorker(assignedSocialWorker);
    hoiCase.setAccessLimitation(new AccessLimitation(
        LimitedAccessType.getByValue(cmscase.getLimitedAccessCode()),
        cmscase.getLimitedAccessDate(), cmscase.getLimitedAccessDesc(),
        new SystemCodeDescriptor(cmscase.getLimitedAccessGovernmentEntityType(),
            SystemCodeCache.global()
                .getSystemCodeShortDescription(cmscase.getLimitedAccessGovernmentEntityType()))));

    hoiCase.setParents(parents);
    hoiCase.setLegacyDescriptor(
        new LegacyDescriptor(cmscaseId, CmsKeyIdGenerator.getUIIdentifierFromKey(cmscaseId),
            new DateTime(cmscase.getLastUpdatedTime()), LegacyTable.CASE.getName(),
            LegacyTable.CASE.getDescription()));
    return hoiCase;
  }

}
