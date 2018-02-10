package gov.ca.cwds.rest.api.domain.hoi;

import static gov.ca.cwds.fixture.ParticipantEntityBuilder.DEFAULT_PERSON_ID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

public class HOIPersonTest {

  HOIPerson target;
  private String id = DEFAULT_PERSON_ID;
  private String firstName = "Barney";
  private String lastName = "Dino";
  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor(id, "jhdgfkhaj-hohj-jkj", new DateTime(),
          LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());

  @Before
  public void setup() throws Exception {
    target = new HOIPerson();
  }

  @Test
  public void type() throws Exception {
    assertThat(HOIPerson.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    HOIPerson target = new HOIPerson();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    HOIPerson target = new HOIPerson();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    HOIPerson target = new HOIPerson();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    HOIPerson target = new HOIPerson();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    HOIPerson target = new HOIPerson();
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    HOIPerson target = new HOIPerson();
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    HOIPerson target = new HOIPerson();
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    HOIPerson target = new HOIPerson();
    LegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setLegacyDescriptor_Args__LegacyDescriptor() throws Exception {
    HOIPerson target = new HOIPerson();
    LegacyDescriptor legacyDescriptor = mock(LegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void constructorTest() throws Exception {
    HOIPerson domain = new HOIPerson(id, firstName, lastName, legacyDescriptor);
    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

}
