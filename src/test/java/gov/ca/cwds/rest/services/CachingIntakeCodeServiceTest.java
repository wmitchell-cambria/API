package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import gov.ca.cwds.data.ns.IntakeLovDao;

/**
 * @author CWDS API Team
 *
 */
public class CachingIntakeCodeServiceTest {

  private static CachingIntakeCodeService cachingIntakeCodeService;

  /**
   * 
   */
  @BeforeClass
  public static void setupClass() {
    IntakeLovDao intakeLovDao = mock(IntakeLovDao.class);
    cachingIntakeCodeService = new CachingIntakeCodeService(intakeLovDao, 1, false);
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
    IntakeLovDao intakeLovDao = null;
    CachingIntakeCodeService target = new CachingIntakeCodeService(intakeLovDao, 1, false);
    assertThat(target, notNullValue());
  }

}
