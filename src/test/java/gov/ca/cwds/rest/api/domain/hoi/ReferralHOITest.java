package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

public class ReferralHOITest {

  ReferralHOI target;

  @Before
  public void setup() throws Exception {
    target = new ReferralHOI();
  }

  @Test
  public void type() throws Exception {
    assertThat(ReferralHOI.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    Date actual = target.getStartDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartDate_Args__Date() throws Exception {
    Date startDate = mock(Date.class);
    target.setStartDate(startDate);
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndDate_Args__Date() throws Exception {
    Date endDate = mock(Date.class);
    target.setEndDate(endDate);
  }

  @Test
  public void getCounty_Args__() throws Exception {
    SystemCodeDescriptor actual = target.getCounty();
    SystemCodeDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCounty_Args__SystemCodeDescriptor() throws Exception {
    SystemCodeDescriptor county = mock(SystemCodeDescriptor.class);
    target.setCounty(county);
  }

  @Test
  public void getResponseTime_Args__() throws Exception {
    SystemCodeDescriptor actual = target.getResponseTime();
    SystemCodeDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setResponseTime_Args__SystemCodeDescriptor() throws Exception {
    SystemCodeDescriptor responseTime = mock(SystemCodeDescriptor.class);
    target.setResponseTime(responseTime);
  }

  @Test
  public void getReporter_Args__() throws Exception {
    ReporterHOI actual = target.getReporter();
    ReporterHOI expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReporter_Args__Reporter() throws Exception {
    ReporterHOI reporter = mock(ReporterHOI.class);
    target.setReporter(reporter);
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    SocialWorker actual = target.getAssignedSocialWorker();
    SocialWorker expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAssignedSocialWorker_Args__SocialWorker() throws Exception {
    SocialWorker assignedSocialWorker = mock(SocialWorker.class);
    target.setAssignedSocialWorker(assignedSocialWorker);
  }

  @Test
  public void getAllegations_Args__() throws Exception {
    List<AllegationHOI> actual = target.getAllegations();
    List<AllegationHOI> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllegations_Args__List() throws Exception {
    List<AllegationHOI> allegations = new ArrayList<AllegationHOI>();
    target.setAllegations(allegations);
  }

  @Test
  public void getAccessLimitation_Args__() throws Exception {
    AccessLimitation actual = target.getAccessLimitation();
    AccessLimitation expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAccessLimitation_Args__AccessLimitation() throws Exception {
    AccessLimitation accessLimitation = mock(AccessLimitation.class);
    target.setAccessLimitation(accessLimitation);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    LegacyDescriptor actual = target.getLegacyDescriptor();
    LegacyDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyDescriptor_Args__LegacyDescriptor() throws Exception {
    LegacyDescriptor legacyDescriptor = mock(LegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void main_Args__StringArray() throws Exception {
    String[] args = new String[] {};
    ReferralHOI.main(args);
  }

}
