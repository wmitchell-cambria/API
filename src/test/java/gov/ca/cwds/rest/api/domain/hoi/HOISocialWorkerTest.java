package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOISocialWorkerTest {

  HOISocialWorker target;
  private String id = "jhdgfkhaj";
  private String firstName = "Barney";
  private String lastName = "Dino";
  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj", new DateTime(),
          LegacyTable.STAFF_PERSON.getName(), LegacyTable.STAFF_PERSON.getDescription());

  @Before
  public void setup() throws Exception {
    target = new HOISocialWorker();
  }

  @Test
  public void type() throws Exception {
    assertThat(HOISocialWorker.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    LegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setLegacyDescriptor_Args__LegacyDescriptor() throws Exception {
    HOISocialWorker target = new HOISocialWorker();
    LegacyDescriptor legacyDescriptor = mock(LegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    HOISocialWorker empty = new HOISocialWorker();
    assertNotNull(empty);
  }


  @Test
  public void equalsHashCodeWork() {
    HOISocialWorker empty = new HOISocialWorker();
    assertThat(empty.hashCode(), is(not(0)));
  }

  @Test
  public void constructorTest() throws Exception {
    HOISocialWorker domain = new HOISocialWorker(id, firstName, lastName, legacyDescriptor);
    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

}
