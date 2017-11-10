package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class InjuryBodyDetailTest {
  private String thirdId = "1234567ABC";
  private Short physicalAbuseBodyPartType = 1234;
  private String primaryInjuryHarmDetailId = "2345678ABC";
  private String secondaryInjuryHarmDetailId = "3456789ABC";
  private String countySpecificCode = "20";

  @Test
  public void testDefaultConstructor() throws Exception {
    InjuryBodyDetail injuryBodyDetail = new InjuryBodyDetail();
    assertThat(injuryBodyDetail, is(notNullValue()));
  }

  @Test
  public void testConstructor() throws Exception {
    InjuryBodyDetail injuryBodyDetail = new InjuryBodyDetail(thirdId, physicalAbuseBodyPartType,
        primaryInjuryHarmDetailId, secondaryInjuryHarmDetailId, countySpecificCode);
    assertThat(injuryBodyDetail.getThirdId(), is(equalTo(thirdId)));
    assertThat(injuryBodyDetail.getPrimaryKey(), is(equalTo(thirdId)));
    assertThat(injuryBodyDetail.getPhysicalAbuseBodyPartType(),
        is(equalTo(physicalAbuseBodyPartType)));
    assertThat(injuryBodyDetail.getPrimaryInjuryHarmDetailId(),
        is(equalTo(primaryInjuryHarmDetailId)));
    assertThat(injuryBodyDetail.getSecondaryInjuryHarmDetailId(),
        is(equalTo(secondaryInjuryHarmDetailId)));
    assertThat(injuryBodyDetail.getCountySpecificCode(), is(equalTo(countySpecificCode)));
  }


}
