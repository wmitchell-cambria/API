package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.hoi.HOIScreeningBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

public class HOIScreeningTest {
  private String id = "224";
  private String newId = "336";
  private String name = "screening name";
  private String newName = "new screening name";
  private String startDate = "2017-11-30";
  private String newStartDate = "2018-01-31";
  private String endDate = "2017-12-10";
  private String newEndDate = "2018-02-01";
  private String decision = "promote to referral";
  private String newDecision = "new decision";
  private String decisionDetail = "drug counseling";
  private String newDecisionDetail = "new decision detail";
  private Integer countyId = 1101;
  private Integer newCountyId = 1102;
  private String countyDescription = "Sacramento";
  private String newCountyDescription = "Merced";
  private Set<HOIPerson> allPeople = new HashSet<>();
  private HOIReporter reporter;
  private HOIReporter newReporter;
  private HOISocialWorker socialWorker;
  private HOISocialWorker newSocialWorker;

  private SystemCodeDescriptor county = new SystemCodeDescriptor(countyId.shortValue(), countyDescription);
  private SystemCodeDescriptor newCounty = new SystemCodeDescriptor(newCountyId.shortValue(), newCountyDescription);
  HOIScreening target;

  @Before
  public void setup() throws Exception {
    target = new HOIScreeningBuilder().createHOIScreening();
  }

  @Test
  public void type() throws Exception {
    assertThat(HOIScreening.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    assertThat(actual, is(equalTo(id)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    target.setId(newId);
    assertThat(target.getId(), is(equalTo(newId)));
  }

  @Test
  public void shouldGetName() throws Exception {
	assertThat(target.getName(), is(equalTo(name)));
  }
  
  @Test
  public void shouldSetName() throws Exception {
	target.setName(newName);
	assertThat(target.getName(), is(equalTo(newName)));
  }
  
  @Test
  public void shouldGetDecision() throws Exception {
	assertThat(target.getDecision(), is(equalTo(decision)));
  }
  
  @Test
  public void shouldSetDecision() throws Exception {
	target.setDecision(newDecision);
	assertThat(target.getDecision(), is(equalTo(newDecision)));
  }
  
  @Test 
  public void shouldGetDecisionDetail() throws Exception {
	assertThat(target.getDecisionDetail(), is(equalTo(decisionDetail)));
  }
  
  @Test
  public void shouldSetDecisionDetail() throws Exception {
	target.setDecisionDetail(newDecisionDetail);
	assertThat(target.getDecisionDetail(), is(equalTo(newDecisionDetail)));
  }
  
  @Test
  public void getStartDate_Args__() throws Exception {
    assertThat(target.getStartDate(), is(equalTo(DomainChef.uncookDateString(startDate))));
  }

  @Test
  public void setStartDate_Args__Date() throws Exception {
    target.setStartDate(DomainChef.uncookDateString(newStartDate));
    assertThat(target.getStartDate(), is(equalTo(DomainChef.uncookDateString(newStartDate))));
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    assertThat(target.getEndDate(), is(equalTo(DomainChef.uncookDateString((endDate)))));
  }

  @Test
  public void setEndDate_Args__Date() throws Exception {
    target.setEndDate(DomainChef.uncookDateString(newEndDate));
    assertThat(target.getEndDate(), is(equalTo(DomainChef.uncookDateString(newEndDate))));
  }

  @Test
  public void getCounty_Args__() throws Exception {
    assertThat(target.getCounty(), is(equalTo(county)));
  }

  @Test
  public void setCounty_Args__SystemCodeDescriptor() throws Exception {
    target.setCounty(newCounty);
    assertThat(target.getCounty(), is(equalTo(newCounty)));
  }

  @Test
  public void getReporter_Args__() throws Exception {
    assertThat(target.getReporter(), is(equalTo(reporter)));
  }

  @Test
  public void setReporter_Args__Reporter() throws Exception {
    target.setReporter(newReporter);
    assertThat(target.getReporter(), is(equalTo(newReporter)));
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    assertThat(target.getAssignedSocialWorker(), is(equalTo(socialWorker)));
  }

  @Test
  public void setAssignedSocialWorker_Args__SocialWorker() throws Exception {
    target.setAssignedSocialWorker(socialWorker);
    assertThat(target.getAssignedSocialWorker(), is(equalTo(newSocialWorker)));
  }

}
