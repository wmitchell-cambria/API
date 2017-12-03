package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.Allegation;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class SimpleAllegationTest {

  private SimpleAllegation simpleAllegation;
  private String victimId;
  private String perpetratorId;
  private String screeningId;
  private Set<String> allegationTypes = new HashSet<>();
  private String[] persistentAllegationTypes = new String[2];
  private Set<String> emptyAllegationTypes = new HashSet<>();

  @Before
  public void setup() {
    allegationTypes.add("General neglect");
    allegationTypes.add("Physical abuse");
    victimId = "1234567ABC";
    perpetratorId = "2345678ABC";
    screeningId = "3456789ABC";
    persistentAllegationTypes[0] = "General neglect";
    persistentAllegationTypes[1] = "Physical abuse";
  }

  @Test
  public void testConstructor() throws Exception {
    SimpleAllegation constructed = new SimpleAllegation(victimId, perpetratorId, allegationTypes);
    assertThat(constructed.getVictimId(), is(equalTo(victimId)));
    assertThat(constructed.getPerpetratorId(), is(equalTo(perpetratorId)));
    assertThat(constructed.getAllegationTypes(), is(equalTo(allegationTypes)));
  }

  @Test
  public void testConstructorUsingPersistentAllegation() throws Exception {
    Allegation persistent =
        new Allegation("", screeningId, victimId, perpetratorId, "", "", persistentAllegationTypes);
    SimpleAllegation constructed = new SimpleAllegation(persistent);
    Set<String> newAllegationTypes = new HashSet<>();
    Collections.addAll(newAllegationTypes, persistent.getAllegationTypes());
    assertThat(constructed.getAllegationTypes(), is(equalTo(newAllegationTypes)));
    assertThat(constructed.getVictimId(), is(equalTo(persistent.getVictimId())));
    assertThat(constructed.getPerpetratorId(), is(equalTo(persistent.getPerpetratorId())));
  }

  @Test
  public void testConstructorUsingPersistentAllegationWithNullAllegationTypes() throws Exception {
    Allegation persistent = new Allegation("", screeningId, victimId, perpetratorId, "", "", null);
    SimpleAllegation constructed = new SimpleAllegation(persistent);
    assertThat(constructed.getAllegationTypes(), is(equalTo(emptyAllegationTypes)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SimpleAllegation.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
