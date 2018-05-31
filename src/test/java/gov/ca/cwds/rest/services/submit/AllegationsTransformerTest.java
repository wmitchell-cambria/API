package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.fixture.AllegationResourceBuilder;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.AllegationIntake;

/***
 * 
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class AllegationsTransformerTest {

  AllegationIntake allegationIntake;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {

    Set<String> types = Stream.of("General neglect").collect(Collectors.toSet());
    allegationIntake = new AllegationIntake();
    allegationIntake.setId("1");
    allegationIntake.setLegacyId("");
    allegationIntake.setLegacySourceTable("");
    allegationIntake.setNonProtectingParent("U");
    allegationIntake.setTypes(types);
    allegationIntake.setCounty("34");
    allegationIntake.setVictimPersonId(new Long(5432));
  }

  @Test
  public void transformConvertsAllegationsIntakeToAllegations() {
    Allegation allegation = new AllegationResourceBuilder().createAllegation();
    Set<AllegationIntake> nsAllegations = Stream.of(allegationIntake).collect(Collectors.toSet());
    Set<Allegation> expected = Stream.of(allegation).collect(Collectors.toSet());
    Set<Allegation> actual = new AllegationsTransformer().transform(nsAllegations);

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsAllegationsIntakeToAllegationsWhenTypesIsEmpty() {
    allegationIntake.setTypes(new HashSet<String>());
    Set<AllegationIntake> nsAllegations = Stream.of(allegationIntake).collect(Collectors.toSet());
    Set<Allegation> expected = new HashSet<Allegation>();
    Set<Allegation> actual = new AllegationsTransformer().transform(nsAllegations);

    assertEquals(actual, expected);
  }

  @Test
  public void transformConvertsAllegationsIntakeToAllegationsWhenDescriptionIsEmpty() {
    allegationIntake.setTypes(Stream.of("").collect(Collectors.toSet()));
    Allegation allegation =
        new AllegationResourceBuilder().setInjuryHarmType(null).createAllegation();
    Set<AllegationIntake> nsAllegations = Stream.of(allegationIntake).collect(Collectors.toSet());
    Set<Allegation> expected = Stream.of(allegation).collect(Collectors.toSet());
    Set<Allegation> actual = new AllegationsTransformer().transform(nsAllegations);

    assertEquals(actual, expected);
  }

}
