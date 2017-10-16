package gov.ca.cwds.rest.util;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

/**
 * @author CWDS API Team
 *
 */
public class CmsRecordUtils {

  /**
   * @param CmsId - CMS Id
   * @param cmsPhysicalTableName - CMS physical table name
   * @return - CmsRecordDescriptor
   */
  public static CmsRecordDescriptor createLegacyDescriptor(String CmsId,
      LegacyTable cmsPhysicalTableName) {
    final CmsRecordDescriptor cmsRecordDescriptor = new CmsRecordDescriptor();

    if (!StringUtils.isBlank(CmsId)) {
      cmsRecordDescriptor.setId(CmsId.trim());

      final String cmsUiId = CmsId.trim();
      if (cmsUiId.length() == CMS_ID_LEN) {
        cmsRecordDescriptor.setUiId(CmsKeyIdGenerator.getUIIdentifierFromKey(cmsUiId));
      } else {
        cmsRecordDescriptor.setUiId(cmsUiId);
      }

      if (cmsPhysicalTableName != null) {
        cmsRecordDescriptor.setTableName(cmsPhysicalTableName.getName());
        cmsRecordDescriptor.setTableDescription(cmsPhysicalTableName.getDescription());
      }
    }

    return cmsRecordDescriptor;
  }

}


