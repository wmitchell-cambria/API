package gov.ca.cwds.rest.api.domain;

import java.util.Map;

import gov.ca.cwds.data.persistence.cms.DeferredRegistry;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * @author CWDS API Team
 *
 */
public interface IntakeCodeCache extends ApiMarker {

  /**
   * Register this intakes Code cache instance for system-wide use.
   */
  default void register() {
    DeferredRegistry.<IntakeCodeCache>register(IntakeCodeCache.class, this);
  }

  /**
   * Globally available, singleton intakes code cache.
   * 
   * @return singleton intakes code cache
   */
  static IntakeCodeCache global() {
    return DeferredRegistry.<IntakeCodeCache>unwrap(IntakeCodeCache.class);
  }

  /**
   * @param metaId - metaId
   * @return All legacy system codes for the metaId.
   */
  Map<String, IntakeLov> getAllLegacySystemCodesForMeta(String metaId);

  /**
   * @param metaId - metaId
   * @param intakeCode - intakeCode
   * @return
   */
  IntakeLov getLegacySystemCodeForIntakeCode(String metaId, String intakeCode);

}
