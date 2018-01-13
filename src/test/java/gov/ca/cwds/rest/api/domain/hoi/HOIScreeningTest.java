package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

public class HOIScreeningTest {

  HOIScreening target;

  @Before
  public void setup() throws Exception {
    target = new HOIScreening();
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
  public void getReporter_Args__() throws Exception {
    HOIReporter actual = target.getReporter();
    HOIReporter expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReporter_Args__Reporter() throws Exception {
    HOIReporter reporter = mock(HOIReporter.class);
    target.setReporter(reporter);
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    HOISocialWorker actual = target.getAssignedSocialWorker();
    HOISocialWorker expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAssignedSocialWorker_Args__SocialWorker() throws Exception {
    HOISocialWorker assignedSocialWorker = mock(HOISocialWorker.class);
    target.setAssignedSocialWorker(assignedSocialWorker);
  }

}
