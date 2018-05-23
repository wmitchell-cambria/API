package gov.ca.cwds.data.cms;

import java.util.Map;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;

/**
 * @author CWDS API Team
 *
 */
public class TestIntakeCodeCache implements IntakeCodeCache {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public TestIntakeCodeCache() {
    register();
  }

  @Override
  public Map<String, IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    return null;
  }

  @Override
  public IntakeLov getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    if ("DSP_RSNC".contains(metaId) && "Abandoned".contains(intakeCode)) {
      final Long sysCode = 6351L;
      return new IntakeLov(sysCode, null, null, null, false, "DSP_RSNC", null, null, null,
          "Abandoned", null);
    }
    if (SystemCodeCategoryId.ETHNICITY.contains(metaId)
        && "Central American".contains(intakeCode)) {
      final Long sysCode = 841L;
      return new IntakeLov(sysCode, null, null, null, false, "ETHNCTYC", null, null, null,
          "Central American", null);
    }
    if (SystemCodeCategoryId.ETHNICITY.contains(metaId) && "Mexican".contains(intakeCode)) {
      final Long sysCode = 3164L;
      return new IntakeLov(sysCode, null, null, null, false, "ETHNCTYC", null, null, null,
          "Mexican", null);
    }
    return null;
  }

}
