package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

public class CaseTest {

  @Test
  public void type() throws Exception {
    assertThat(Case.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    Case target = new Case();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    Case target = new Case();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    Case target = new Case();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    Case target = new Case();
    Date actual = target.getStartDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartDate_Args__Date() throws Exception {
    Case target = new Case();
    Date startDate = mock(Date.class);
    target.setStartDate(startDate);
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    Case target = new Case();
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndDate_Args__Date() throws Exception {
    Case target = new Case();
    Date endDate = mock(Date.class);
    target.setEndDate(endDate);
  }

  @Test
  public void getCounty_Args__() throws Exception {
    Case target = new Case();
    SystemCodeDescriptor actual = target.getCounty();
    SystemCodeDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCounty_Args__SystemCodeDescriptor() throws Exception {
    Case target = new Case();
    SystemCodeDescriptor county = mock(SystemCodeDescriptor.class);
    target.setCounty(county);
  }

  @Test
  public void getServiceComponent_Args__() throws Exception {
    Case target = new Case();
    SystemCodeDescriptor actual = target.getServiceComponent();
    SystemCodeDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setServiceComponent_Args__SystemCodeDescriptor() throws Exception {
    Case target = new Case();
    SystemCodeDescriptor serviceComponent = mock(SystemCodeDescriptor.class);
    target.setServiceComponent(serviceComponent);
  }

  @Test
  public void getFocusChild_Args__() throws Exception {
    Case target = new Case();
    Victim actual = target.getFocusChild();
    Victim expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFocusChild_Args__Victim() throws Exception {
    Case target = new Case();
    Victim focusChild = mock(Victim.class);
    target.setFocusChild(focusChild);
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    Case target = new Case();
    SocialWorker actual = target.getAssignedSocialWorker();
    SocialWorker expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAssignedSocialWorker_Args__SocialWorker() throws Exception {
    Case target = new Case();
    SocialWorker assignedSocialWorker = mock(SocialWorker.class);
    target.setAssignedSocialWorker(assignedSocialWorker);
  }

  @Test
  public void getParents_Args__() throws Exception {
    Case target = new Case();
    List<RelatedPerson> actual = target.getParents();
    List<RelatedPerson> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setParents_Args__List() throws Exception {
    Case target = new Case();
    List<RelatedPerson> parents = new ArrayList<RelatedPerson>();
    target.setParents(parents);
  }

  @Test
  public void getAccessLimitation_Args__() throws Exception {
    Case target = new Case();
    AccessLimitation actual = target.getAccessLimitation();
    AccessLimitation expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAccessLimitation_Args__AccessLimitation() throws Exception {
    Case target = new Case();
    AccessLimitation accessLimitation = mock(AccessLimitation.class);
    target.setAccessLimitation(accessLimitation);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    Case target = new Case();
    LegacyDescriptor actual = target.getLegacyDescriptor();
    LegacyDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyDescriptor_Args__LegacyDescriptor() throws Exception {
    Case target = new Case();
    LegacyDescriptor legacyDescriptor = mock(LegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void main_Args__StringArray() throws Exception {
    String[] args = new String[] {};
    Case.main(args);
  }

}
