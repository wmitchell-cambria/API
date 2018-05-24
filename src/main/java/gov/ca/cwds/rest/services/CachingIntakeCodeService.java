package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
   * @param preloadCache If true then preload all system code cache
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
  public Map<String, IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    Map<String, IntakeLov> intakeLov = new HashMap<>();
    if (StringUtils.isNotBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      intakeLov = (Map<String, IntakeLov>) getFromCache(cacheKey);
    }
    return intakeLov;
  }

  @Override
  public IntakeLov getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {
    IntakeLov intakeLov = new IntakeLov();
    Map<String, IntakeLov> intakeLovMap = getAllLegacySystemCodesForMeta(metaId);
    if (intakeLovMap != null) {
      intakeLov = intakeLovMap.get(intakeCode);
    }
    return intakeLov;

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
        Map<String, IntakeLov> intakeCodeList =
            intakeLovService.loadAllLegacyMetaIds(key.getValue());
        objectToCache = intakeCodeList;
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
  }

}
