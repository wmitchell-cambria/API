package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;

/**
 * @author CWDS API Team
 *
 */
public class CachingIntakeCodeServiceTest {

  private CachingIntakeCodeService cachingIntakeCodeService;
  private static IntakeLovDao intakeLovDao;

  /**
   * 
   */
  @BeforeClass
  public static void setupClass() {
    intakeLovDao = mock(IntakeLovDao.class);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(CachingIntakeCodeService.class, notNullValue());
  }

  /**
   * 
   */
  @Test
  public void instantiation() {

    CachingIntakeCodeService target = new CachingIntakeCodeService(intakeLovDao, 1);
    assertThat(target, notNullValue());

  }

  /**
   * 
   */
  @Ignore
  @Test
  public void testToGetValidLegacyId() {
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    IntakeLov intakeLov1 = new IntakeLov(1253L, "lang_tpc", "English", "07", false, "LANG_TPC", "",
        null, "language", "English", "English");
    List<IntakeLov> lovList = Arrays.asList(intakeLov, intakeLov1);

    when(intakeLovDao.findAll()).thenReturn(lovList);
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    IntakeLov actualLovCode = cachingIntakeCodeService
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "Cambodian");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.getLegacySystemCodeId(), is(equalTo(1251L)));
  }

  /**
   * 
   */
  @Test
  public void testToWhenCacheNull() {
    when(intakeLovDao.findByLegacyMetaId(null)).thenReturn(new ArrayList<>());
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    IntakeLov actualLovCode = cachingIntakeCodeService
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "112kfjn");
    Assert.assertNull(actualLovCode);
  }

  /**
   * 
   */
  @Test
  public void testToGetAllLegacySystemCodesForMeta() {
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    IntakeLov intakeLov1 = new IntakeLov(1253L, "lang_tpc", "English", "07", false, "LANG_TPC", "",
        null, "language", "English", "English");
    List<IntakeLov> lovList = new ArrayList<>(Arrays.asList(intakeLov));

    // when(intakeLovDao.findByLegacyMetaId(SystemCodeCategoryId.LANGUAGE_CODE)).thenReturn(lovList);
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    when(intakeLovDao.findByLegacyMetaId(SystemCodeCategoryId.LANGUAGE_CODE)).thenReturn(lovList);
    List<IntakeLov> actualLovCode =
        cachingIntakeCodeService.getAllLegacySystemCodesForMeta(SystemCodeCategoryId.LANGUAGE_CODE);
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(1)));
  }

  /**
   * 
   */
  @Test
  public void testToGetAllLegacySystemCodesForMetaEmpty() {
    when(intakeLovDao.findAll()).thenReturn(new ArrayList<>());
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1500);
    List<IntakeLov> actualLovCode = cachingIntakeCodeService.getAllLegacySystemCodesForMeta("");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(0)));
  }

}
