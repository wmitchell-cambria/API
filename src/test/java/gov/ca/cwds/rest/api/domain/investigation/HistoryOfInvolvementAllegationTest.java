package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class HistoryOfInvolvementAllegationTest {
  private String victimLastName = "Smith";
  private String victimFirstName = "Cody";
  private String perpetratorLastName = "Berk";
  private String perpetratorFirstName = "Jeremy";
  private String dispositionDescription = "Substantiated";
  private String allegationDescription = "Physical abuse";

  @Before
  public void setup() {}

  @Test
  public void testDefaultConstructorSuccess() {
    HistoryOfInvolvementAllegation hoiAllegation = new HistoryOfInvolvementAllegation();
    assertNotNull(hoiAllegation);
  }

  @Test
  public void testConstructorSuccess() {
    HistoryOfInvolvementAllegation hoiAllegation =
        new HistoryOfInvolvementAllegation(victimLastName, victimFirstName, perpetratorLastName,
            perpetratorFirstName, dispositionDescription, allegationDescription);
    assertThat(hoiAllegation.getVictimLastName(), is(equalTo(victimLastName)));
    assertThat(hoiAllegation.getVictimFirstName(), is(equalTo(victimFirstName)));
    assertThat(hoiAllegation.getPerpetratorLastName(), is(equalTo(perpetratorLastName)));
    assertThat(hoiAllegation.getPereptratorFistName(), is(equalTo(perpetratorFirstName)));
    assertThat(hoiAllegation.getDispositionDescription(), is(equalTo(dispositionDescription)));
    assertThat(hoiAllegation.getAllegationDescription(), is(equalTo(allegationDescription)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(HistoryOfInvolvement.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
