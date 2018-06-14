package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * @author CWDS API Team
 */
public class CachingIntakeCodeServiceTest extends Doofenshmirtz<IntakeLov> {

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private CachingIntakeCodeService target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    long secondsToRefreshCache = 15L;
    target = new CachingIntakeCodeService(intakeLovDao, secondsToRefreshCache);
  }

  @Test
  public void type() throws Exception {
    assertThat(CachingIntakeCodeService.class, notNullValue());
  }

  @Test
  public void instantiation() {
    CachingIntakeCodeService target = new CachingIntakeCodeService(intakeLovDao, 1);
    assertThat(target, notNullValue());
  }

  @Test
  public void testToGetValidLegacyId() {
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    IntakeLov intakeLov1 = new IntakeLov(1253L, "lang_tpc", "English", "07", false, "LANG_TPC", "",
        null, "language", "English", "English");
    List<IntakeLov> lovList = Arrays.asList(intakeLov, intakeLov1);
    when(intakeLovDao.findByLegacyMetaId(any(String.class))).thenReturn(lovList);
    target = new CachingIntakeCodeService(intakeLovDao, 1500);
    Short actualLovCode =
        target.getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "Cambodian");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode, is(equalTo((short) 1251)));
  }

  @Test
  public void testToWhenCacheNull() {
    when(intakeLovDao.findByLegacyMetaId(null)).thenReturn(new ArrayList<>());
    target = new CachingIntakeCodeService(intakeLovDao, 1500);
    Short actualLovCode =
        target.getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.LANGUAGE_CODE, "112kfjn");
    Assert.assertNull(actualLovCode);
  }

  @Test
  public void testToGetAllLegacySystemCodesForMeta() {
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    IntakeLov intakeLov1 = new IntakeLov(1253L, "lang_tpc", "English", "07", false, "LANG_TPC", "",
        null, "language", "English", "English");
    List<IntakeLov> lovList = new ArrayList<>(Arrays.asList(intakeLov, intakeLov1));
    when(intakeLovDao.findByLegacyMetaId(SystemCodeCategoryId.LANGUAGE_CODE)).thenReturn(lovList);
    target = new CachingIntakeCodeService(intakeLovDao, 1500);
    when(intakeLovDao.findByLegacyMetaId(SystemCodeCategoryId.LANGUAGE_CODE)).thenReturn(lovList);
    List<IntakeLov> actualLovCode =
        target.getAllLegacySystemCodesForMeta(SystemCodeCategoryId.LANGUAGE_CODE);
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(2)));
  }

  @Test
  public void testToGetAllLegacySystemCodesForMetaEmpty() {
    when(intakeLovDao.findAll()).thenReturn(new ArrayList<>());
    target = new CachingIntakeCodeService(intakeLovDao, 1500);
    List<IntakeLov> actualLovCode = target.getAllLegacySystemCodesForMeta("");
    Assert.assertNotNull(actualLovCode);
    assertThat(actualLovCode.size(), is(equalTo(0)));
  }

  @Test
  public void getAllLegacySystemCodesForMeta_A$String() throws Exception {
    final String metaId = "LANG_TPC";
    final List<IntakeLov> actual = target.getAllLegacySystemCodesForMeta(metaId);
    final List<IntakeLov> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacySystemCodeForIntakeCode_A$String$String() throws Exception {
    final String metaId = "LANG_TPC";
    String intakeCode = null;
    Short actual = target.getLegacySystemCodeForIntakeCode(metaId, intakeCode);
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacySystemCodeForRaceAndEthnicity_A$String$String() throws Exception {
    final String metaId = "LANG_TPC";
    String intakeCode = null;
    Short actual = target.getLegacySystemCodeForRaceAndEthnicity(metaId, intakeCode);
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
