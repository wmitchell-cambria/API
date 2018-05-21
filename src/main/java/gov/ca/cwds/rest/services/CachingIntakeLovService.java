package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.IntakeLovCodeCache;

/**
 * Intake code cache Implementation
 * 
 * @author CWDS API Team
 *
 */
public class CachingIntakeLovService extends IntakeLovService implements IntakeLovCodeCache {

  private transient LoadingCache<CacheKey, Object> intakeLovCodeCache;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default no-arg constructor.
   */
  @SuppressWarnings("unused")
  private CachingIntakeLovService() {
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
  public CachingIntakeLovService(IntakeLovDao intakeLovDao, long secondsToRefreshCache,
      boolean preloadCache) {
    super(intakeLovDao);

    final IntakeCodeCacheLoader cacheLoader = new IntakeCodeCacheLoader(this);
    intakeLovCodeCache = CacheBuilder.newBuilder()
        .refreshAfterWrite(secondsToRefreshCache, TimeUnit.SECONDS).build(cacheLoader);

    if (preloadCache) {
      try {
        Map<CacheKey, Object> intakeCode = cacheLoader.loadAll();
        intakeLovCodeCache.putAll(intakeCode);
      } catch (Exception e) {
        LOGGER.error("Error loading intake codes", e);
        throw new ServiceException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, IntakeLov> getAllLegacySystemCodesForMeta(String metaId) {
    Map<String, IntakeLov> intakeLov = new HashMap<>();
    if (!StringUtils.isBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      return (Map<String, IntakeLov>) getFromCache(cacheKey);
    }
    return intakeLov;
  }

  @Override
  public IntakeLov getLegacySystemCodeForIntakeCode(String metaId, String intakeCode) {

    if (StringUtils.isNotBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      @SuppressWarnings("unchecked")
      Map<String, IntakeLov> intakeLovMap = (Map<String, IntakeLov>) getFromCache(cacheKey);
      if (intakeLovMap != null) {
        return intakeLovMap.get(intakeCode);
      }
    }
    return null;
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
      obj = intakeLovCodeCache.get(cacheEntryKey);
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

    /**
     * Loads all system code cache entries.
     * 
     * @return All system code cache entries.
     * @throws Exception on disconnect, NPE, etc.
     */
    public Map<CacheKey, Object> loadAll() {
      LOGGER.info("Loading all system code cache...");

      Map<CacheKey, Object> intakeCodeMap = new HashMap<>();
      Map<String, Map<String, IntakeLov>> intakeLovMap = new HashMap<>();
      List<IntakeLov> intakeLovList = intakeLovService.findAllIntakeLov();
      if (intakeLovList != null) {
        for (IntakeLov intakeLov : intakeLovList) {
          if (intakeLovMap.containsKey(intakeLov.getLegacyCategoryId())) {
            Map<String, IntakeLov> codeMap = intakeLovMap.get(intakeLov.getLegacyCategoryId());
            codeMap.put(intakeLov.getIntakeCode(), intakeLov);
            intakeLovMap.put(intakeLov.getLegacyCategoryId(), codeMap);
          } else {
            Map<String, IntakeLov> codeMap = new HashMap<>();
            codeMap.put(intakeLov.getIntakeCode(), intakeLov);
            intakeLovMap.put(intakeLov.getLegacyCategoryId(), codeMap);
          }
        }
      }

      for (Entry<String, Map<String, IntakeLov>> entry : intakeLovMap.entrySet()) {
        CacheKey cacheKey = CacheKey.createForMeta(entry.getKey());
        intakeCodeMap.put(cacheKey, entry.getValue());
      }
      return intakeCodeMap;
    }

    @Override
    public Object load(CacheKey key) throws Exception {
      return null;
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
