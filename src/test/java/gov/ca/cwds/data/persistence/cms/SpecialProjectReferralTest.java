package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import java.util.Date;
import org.junit.Test;
import gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;

public class SpecialProjectReferralTest {
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  private String countySpecificCode = "17";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private Date participationEndDate = null;
  private Date participationStartDate = new Date();
  private String safelySurrenderedBabiesIndicator = "N";
  private String id = "3456789ABC";
  
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SpecialProjectReferral.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testConstructor() throws Exception {
    SpecialProjectReferral spr = new SpecialProjectReferral(countySpecificCode, referralId, 
       specialProjectId, participationEndDate, participationStartDate, 
       safelySurrenderedBabiesIndicator, id);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
    assertThat(spr.getParticipationEndDate(), is(equalTo(participationEndDate)));
    assertThat(spr.getParticipationStartDate(), is(equalTo(participationStartDate)));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
    assertThat(spr.getId(), is(equalTo(id)));
  }
  
  @Test
  public void testConstructorUsingDomainObject() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral domain = new SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral spr = new SpecialProjectReferral(id, domain, lastUpdatedId, lastUpdatedTime);
    assertThat(spr.getId(), is(equalTo(id)));
    assertThat(spr.getCountySpecificCode(), is(equalTo(domain.getCountySpecificCode())));
    assertThat(spr.getReferralId(), is(equalTo(domain.getReferralId())));
    assertThat(spr.getSpecialProjectId(), is(equalTo(domain.getSpecialProjectId())));
    assertThat(spr.getParticipationEndDate(), is(equalTo(DomainChef.uncookDateString(domain.getParticipationEndDate()))));
    assertThat(spr.getParticipationStartDate(), is(equalTo(DomainChef.uncookDateString(domain.getParticipationStartDate()))));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(DomainChef.cookBoolean(domain.getSafelySurrenderedBabiesIndicator()))));
  }
  
  
}
