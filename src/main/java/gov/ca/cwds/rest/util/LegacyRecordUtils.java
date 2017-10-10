package gov.ca.cwds.rest.util;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;
import org.apache.commons.lang3.StringUtils;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

public class LegacyRecordUtils {

  public static CmsRecordDescriptor createLegacyDescriptor(String legacyId,
      LegacyTable legacyTable) {
    final CmsRecordDescriptor ret = new CmsRecordDescriptor();

    if (!StringUtils.isBlank(legacyId)) {
      ret.setId(legacyId.trim());

      final String cleanLegacyId = legacyId.trim();
      if (cleanLegacyId.length() == CMS_ID_LEN) {
        ret.setUiId(CmsKeyIdGenerator.getUIIdentifierFromKey(cleanLegacyId));
      } else {
        ret.setUiId(cleanLegacyId);
      }

      if (legacyTable != null) {
        ret.setTableName(legacyTable.getName());
        ret.setTableDescription(legacyTable.getDescription());
      }
    }

    return ret;
  }

}


