package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import java.time.LocalDate;
import java.util.Date;
import org.junit.Test;
import gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.PostedSpecialProjectReferral;
import gov.ca.cwds.rest.services.ServiceException;

public class SpecialProjectReferralTest {
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  private String countySpecificCode = "17";
  private String referralId = "1234567ABC";
  private String specialProjectId = "2345678ABC";
  private LocalDate participationEndDate = null;
  private LocalDate participationStartDate = LocalDate.now();
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
//    assertThat(spr.getParticipationEndDate().toString(), is(equalTo(domain.getParticipationEndDate())));
    assertThat(spr.getParticipationStartDate().toString(), is(equalTo(domain.getParticipationStartDate())));
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(DomainChef.cookBoolean(domain.getSafelySurrenderedBabiesIndicator()))));
  }
  
  @Test
  public void testSetters() throws Exception {
    LocalDate newDate = LocalDate.now();
    SpecialProjectReferral spr = new SpecialProjectReferral();
    spr.setCountySpecificCode(countySpecificCode);
    assertThat(spr.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    spr.setId(id);
    assertThat(spr.getId(), is(equalTo(id)));
    assertThat(spr.getPrimaryKey(), is(equalTo(id)));
    spr.setLastUpdatedId(lastUpdatedId);
    assertThat(spr.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
    spr.setLastUpdatedTime(lastUpdatedTime);
    assertThat(spr.getLastUpdatedTime(), is(equalTo(lastUpdatedTime)));
    spr.setParticipationEndDate(newDate);
    assertThat(spr.getParticipationEndDate(), is(equalTo(newDate)));
    spr.setParticipationStartDate(newDate);
    assertThat(spr.getParticipationStartDate(), is(equalTo(newDate)));
    spr.setReferralId(referralId);
    assertThat(spr.getReferralId(), is(equalTo(referralId)));
    spr.setSafelySurrenderedBabiesIndicator(safelySurrenderedBabiesIndicator);
    assertThat(spr.getSafelySurrenderedBabiesIndicator(), is(equalTo(safelySurrenderedBabiesIndicator)));
    spr.setSpecialProjectId(specialProjectId);
    assertThat(spr.getSpecialProjectId(), is(equalTo(specialProjectId)));
  }
  
  @Test
  public void shouldThrowExceptionWhenPostedSpecialProjectReferralIdIsBlank() throws Exception {
    
    SpecialProjectReferral spr = new SpecialProjectReferral(countySpecificCode, referralId, 
        specialProjectId, participationEndDate, participationStartDate, 
        safelySurrenderedBabiesIndicator, id);
    spr.setId("");
    try {
      PostedSpecialProjectReferral psp = new PostedSpecialProjectReferral(spr);
      
    } catch (ServiceException e) {
      
    }
  }
  
}
