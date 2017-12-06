package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AllegationHOITest {

  private String id = "ABC1234567";
  private String description = "descrption";
  private SystemCodeDescriptor disposition = new SystemCodeDescriptor((short) 123, "descrption");
  private Victim victim = new Victim(id, "Kak", "Man",
      new LegacyDescriptor("Abc1234589", null, new DateTime(), null, null));
  private Perpetrator perpetrator = new Perpetrator("Abc1234580", "Kak1", "Man2",
      new LegacyDescriptor(id, null, new DateTime(), null, null));
  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor("Abc1209877", null, new DateTime(), null, null);

  AllegationHOI target;

  @Before
  public void setup() throws Exception {
    target = new AllegationHOI();
  }

  @Test
  public void type() throws Exception {
    assertThat(AllegationHOI.class, notNullValue());
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
  public void getDescription_Args__() throws Exception {
    String actual = target.getDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDescription_Args__String() throws Exception {
    String description = null;
    target.setDescription(description);
  }

  @Test
  public void getDisposition_Args__() throws Exception {
    SystemCodeDescriptor actual = target.getDisposition();
    SystemCodeDescriptor expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDisposition_Args__SystemCodeDescriptor() throws Exception {
    SystemCodeDescriptor disposition = mock(SystemCodeDescriptor.class);
    target.setDisposition(disposition);
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
  public void getVictim_Args__() throws Exception {
    Victim actual = target.getVictim();
    Victim expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVictim_Args__Victim() throws Exception {
    Victim victim = mock(Victim.class);
    target.setVictim(victim);
  }

  @Test
  public void getPerpetrator_Args__() throws Exception {
    Perpetrator actual = target.getPerpetrator();
    Perpetrator expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPerpetrator_Args__Perpetrator() throws Exception {
    Perpetrator perpetrator = mock(Perpetrator.class);
    target.setPerpetrator(perpetrator);
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    AllegationHOI empty = new AllegationHOI();
    assertThat(empty.getClass(), is(AllegationHOI.class));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    AllegationHOI domain =
        new AllegationHOI(id, description, disposition, victim, perpetrator, legacyDescriptor);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getDescription(), is(equalTo(description)));
    assertThat(domain.getDisposition(), is(equalTo(disposition)));
    assertThat(domain.getVictim(), is(equalTo(victim)));
    assertThat(domain.getPerpetrator(), is(equalTo(perpetrator)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

}
