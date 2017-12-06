package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
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
public class SocialWorkerTest {

  SocialWorker target;
  private String id = "jhdgfkhaj";
  private String firstName = "Barney";
  private String lastName = "Dino";
  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj", new DateTime(),
          LegacyTable.STAFF_PERSON.getName(), LegacyTable.STAFF_PERSON.getDescription());

  @Before
  public void setup() throws Exception {
    target = new SocialWorker();
  }

  @Test
  public void type() throws Exception {
    assertThat(SocialWorker.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    SocialWorker target = new SocialWorker();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    SocialWorker target = new SocialWorker();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    SocialWorker target = new SocialWorker();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    SocialWorker target = new SocialWorker();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    SocialWorker target = new SocialWorker();
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    SocialWorker target = new SocialWorker();
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    SocialWorker target = new SocialWorker();
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    SocialWorker target = new SocialWorker();
    LegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setLegacyDescriptor_Args__LegacyDescriptor() throws Exception {
    SocialWorker target = new SocialWorker();
    LegacyDescriptor legacyDescriptor = mock(LegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    SocialWorker empty = new SocialWorker();
    assertThat(empty.getClass(), is(SocialWorker.class));
  }


  @Test
  public void equalsHashCodeWork() {
    SocialWorker empty = new SocialWorker();
    assertThat(empty.hashCode(), is(not(0)));
  }

  @Test
  public void constructorTest() throws Exception {
    SocialWorker domain = new SocialWorker(id, firstName, lastName, legacyDescriptor);
    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getFirstName(), is(equalTo(firstName)));
    assertThat(domain.getLastName(), is(equalTo(lastName)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

}
