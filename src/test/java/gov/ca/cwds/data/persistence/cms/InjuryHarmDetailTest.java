package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.InjuryHarmDetailEntityBuilder;

/**
 * @author CWDS API Team
 *
 */
public class InjuryHarmDetailTest {

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(InjuryHarmDetail.class.newInstance(), is(notNullValue()));
  }

  /**
   * persistent Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testPersistentConstructor() throws Exception {

    InjuryHarmDetail validInjuryHarmDetail = new InjuryHarmDetailEntityBuilder().build();

    InjuryHarmDetail persistent = new InjuryHarmDetail(validInjuryHarmDetail.getThirdId(),
        validInjuryHarmDetail.getInjuryHarmType(),
        validInjuryHarmDetail.getInjuryToBodyDetailIndicator(),
        validInjuryHarmDetail.getAllegationId(), validInjuryHarmDetail.getCountySpecificCode());

    assertThat(persistent.getThirdId(), is(equalTo(validInjuryHarmDetail.getThirdId())));
    assertThat(persistent.getInjuryToBodyDetailIndicator(),
        is(equalTo(validInjuryHarmDetail.getInjuryToBodyDetailIndicator())));
    assertThat(persistent.getAllegationId(), is(equalTo(validInjuryHarmDetail.getAllegationId())));
    assertThat(persistent.getInjuryHarmType(),
        is(equalTo(validInjuryHarmDetail.getInjuryHarmType())));
    assertThat(persistent.getCountySpecificCode(),
        is(equalTo(validInjuryHarmDetail.getCountySpecificCode())));

  }

}
