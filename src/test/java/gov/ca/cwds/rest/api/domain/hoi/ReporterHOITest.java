package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

public class ReporterHOITest {

  ReporterHOI target;

  @Before
  public void setup() throws Exception {
    target = new ReporterHOI();
  }

  @Test
  public void type() throws Exception {
    assertThat(ReporterHOI.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ReporterHOI target = new ReporterHOI();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ReporterHOI target = new ReporterHOI();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ReporterHOI target = new ReporterHOI();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    ReporterHOI target = new ReporterHOI();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    ReporterHOI target = new ReporterHOI();
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    ReporterHOI target = new ReporterHOI();
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    ReporterHOI target = new ReporterHOI();
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ReporterHOI target = new ReporterHOI();
    LegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setLegacyDescriptor_Args__LegacyDescriptor() throws Exception {
    ReporterHOI target = new ReporterHOI();
    LegacyDescriptor legacyDescriptor = mock(LegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
