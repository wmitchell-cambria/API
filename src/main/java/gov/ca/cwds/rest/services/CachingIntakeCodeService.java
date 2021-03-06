package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.submit.IntakeCodeConveter;

/**
 * Intake code cache Implementation
 * 
 * @author CWDS API Team
 *
 */
public class CachingIntakeCodeService extends IntakeLovService implements IntakeCodeCache {

  private transient LoadingCache<CacheKey, Object> intakeCodeCache;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default no-arg constructor.
   */
  @SuppressWarnings("unused")
  private CachingIntakeCodeService() {
    // no-opt
  }

  /**
   * Construct the object.
   * 
   * @param intakeLovDao Intake Lov Dao
   * @param secondsToRefreshCache Seconds after which cache entries will be invalidated for refresh.
   */
  @Inject
  public CachingIntakeCodeService(IntakeLovDao intakeLovDao, long secondsToRefreshCache) {
    super(intakeLovDao);

    final IntakeCodeCacheLoader cacheLoader = new IntakeCodeCacheLoader(this);
    intakeCodeCache = CacheBuilder.newBuilder()
        .refreshAfterWrite(secondsToRefreshCache, TimeUnit.SECONDS).build(cacheLoader);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    List<IntakeLov> intakeLov = new ArrayList<>();
    if (StringUtils.isNotBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      intakeLov = (List<IntakeLov>) getFromCache(cacheKey);
    }
    return intakeLov;
  }

  @Override
  public Short getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    Short sysId = null;
    List<IntakeLov> intakeLovs = getAllLegacySystemCodesForMeta(metaId);
    Optional<IntakeLov> intakeLovOptional = intakeLovs.stream()
        .filter(intakeLov -> intakeLov.getIntakeCode().equals(intakeCode)).findFirst();
    if (intakeLovOptional.isPresent()) {
      sysId = intakeLovOptional.get().getLegacySystemCodeId().shortValue();
    }

    return sysId;
  }

  @Override
  public Short getLegacySystemCodeForRaceAndEthnicity(String metaId, String intakeCode) {
    Short sysId = null;
    IntakeCodeConveter intakeCodeConveter =
        StringUtils.isNotBlank(intakeCode) ? IntakeCodeConveter.findLegacyDescription(intakeCode)
            : null;
    if (intakeCodeConveter != null && StringUtils.isNotBlank(intakeCodeConveter.getLegacyValue())
        && StringUtils.isNotBlank(metaId)) {
      sysId = SystemCodeCache.global().getSystemCodeId(intakeCodeConveter.getLegacyValue(), metaId);
    }
    return sysId;
  }

  @Override
  public IntakeLov getIntakeLov(Number legacySystemCodeId) {
    if (legacySystemCodeId == null || Integer.valueOf("0").equals(legacySystemCodeId.intValue())) {
      return null;
    }
    return (IntakeLov) getFromCache(CacheKey.createForSystemCode(legacySystemCodeId));
  }

  @Override
  public String getIntakeCodeForLegacySystemCode(Number systemCodeId) {
    String intakeCode = null;
    final IntakeLov intakeLov = getIntakeLov(systemCodeId);
    if (intakeLov != null) {
      intakeCode = intakeLov.getIntakeCode();
    }
    return intakeCode;
  }

  /**
   * Get cached object identified by given cache key.
   * 
   * @param cacheEntryKey Cache key
   * @return Cached object if found, otherwise null.
   */
  private Object getFromCache(CacheKey cacheEntryKey) {
    Object obj = null;
    try {
      obj = intakeCodeCache.get(cacheEntryKey);
    } catch (Exception e) {
      LOGGER.warn("getFromCache -> Unable to load object for key: " + cacheEntryKey, e);
    }

    return obj;
  }

  /**
   * =============================================================================== <br>
   * Cache loader for intake codes. <br>
   * ===============================================================================
   */
  private static class IntakeCodeCacheLoader extends CacheLoader<CacheKey, Object> {

    private IntakeLovService intakeLovService;

    /**
     * Construct the object
     * 
     * @param intakeLovService
     */
    IntakeCodeCacheLoader(IntakeLovService intakeLovService) {
      this.intakeLovService = intakeLovService;
    }

    @Override
    public Object load(CacheKey key) throws Exception {
      Object objectToCache = null;
      if (CacheKey.META_ID_TYPE.equals(key.getType())) {
        List<IntakeLov> intakeCodeList = intakeLovService.loadAllLegacyMetaIds(key.getValue());
        objectToCache = intakeCodeList;
      } else if (CacheKey.SYSTEM_CODE_ID_TYPE.equals(key.getType())) {
        // Add intakeLov objects keyed by Leagcy System Code ID.
        IntakeLov intakeLov = intakeLovService.loadLegacySystemCode(key.getValue());
        objectToCache = intakeLov;
      }
      return objectToCache;
    }

  }

  /**
   * =============================================================================== <br>
   * Cache key. <br>
   * ===============================================================================
   */
  private static class CacheKey extends ApiObjectIdentity {

    private static final long serialVersionUID = 1L;

    private static final String META_ID_TYPE = "META_ID";
    private static final String SYSTEM_CODE_ID_TYPE = "SYSTEM_CODE_ID";

    private Serializable value;
    private String type;

    private CacheKey(String type, Serializable value) {
      this.type = type;
      this.value = value;
    }

    public Serializable getValue() {
      return value;
    }

    public String getType() {
      return type;
    }

    private static CacheKey createForMeta(Serializable value) {
      return new CacheKey(META_ID_TYPE, value);
    }

    private static CacheKey createForSystemCode(Serializable value) {
      return new CacheKey(SYSTEM_CODE_ID_TYPE, value);
    }
  }

}
