package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.joda.time.DateTime;
import org.junit.*;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOIAllegationTest {

  private String id = "ABC1234567";
  private SystemCodeDescriptor type = new SystemCodeDescriptor((short) 123, "descrption");
  private SystemCodeDescriptor disposition = new SystemCodeDescriptor((short) 123, "descrption");
  private HOIVictim victim = new HOIVictim(id, "Kak", "Man",
      new LegacyDescriptor("Abc1234589", null, new DateTime(), null, null));
  private HOIPerpetrator perpetrator = new HOIPerpetrator("Abc1234580", "Kak1", "Man2",
      new LegacyDescriptor(id, null, new DateTime(), null, null));
  private LegacyDescriptor legacyDescriptor =
      new LegacyDescriptor("Abc1209877", null, new DateTime(), null, null);

  HOIAllegation target;

  @Before
  public void setup() throws Exception {
    target = new HOIAllegation();
  }

  @Test
  public void type() throws Exception {
    assertThat(HOIAllegation.class, notNullValue());
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
    SystemCodeDescriptor actual = target.getType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDescription_Args__String() throws Exception {
    SystemCodeDescriptor type = mock(SystemCodeDescriptor.class);
    target.setType(type);
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
    HOIVictim actual = target.getVictim();
    HOIVictim expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVictim_Args__Victim() throws Exception {
    HOIVictim victim = mock(HOIVictim.class);
    target.setVictim(victim);
  }

  @Test
  public void getPerpetrator_Args__() throws Exception {
    HOIPerpetrator actual = target.getPerpetrator();
    HOIPerpetrator expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPerpetrator_Args__Perpetrator() throws Exception {
    HOIPerpetrator perpetrator = mock(HOIPerpetrator.class);
    target.setPerpetrator(perpetrator);
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    HOIAllegation empty = new HOIAllegation();
    assertNotNull(empty);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    HOIAllegation domain =
        new HOIAllegation(id, type, disposition, victim, perpetrator, legacyDescriptor);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getDisposition(), is(equalTo(disposition)));
    assertThat(domain.getVictim(), is(equalTo(victim)));
    assertThat(domain.getPerpetrator(), is(equalTo(perpetrator)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
  }

}
