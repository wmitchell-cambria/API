package gov.ca.cwds.rest.util;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

/**
 * @author CWDS API Team
 */
public class CmsRecordUtils {

  private CmsRecordUtils() {
    // static methods only
  }

  /**
   * @param cmsId - CMS Id
   * @param cmsPhysicalTableName - CMS physical table name
   * @return - CmsRecordDescriptor
   */
  public static CmsRecordDescriptor createLegacyDescriptor(String cmsId,
      LegacyTable cmsPhysicalTableName) {
    final CmsRecordDescriptor cmsRecordDescriptor = new CmsRecordDescriptor();

    if (!StringUtils.isBlank(cmsId)) {
      final String cmsUiId = cmsId.trim();
      cmsRecordDescriptor.setId(cmsUiId);

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
