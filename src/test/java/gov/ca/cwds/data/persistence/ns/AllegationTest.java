package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class AllegationTest {

  private String id = "testid";
  private String screeningId = "12345";
  private String perpetratorId = "23455";
  private String victimId = "34567";
  private String createdAt = "2016-01-01";
  private String updatedAt = "2016-01-01";
  private String[] allegationTypes = new String[1];

  @Test
  public void testConstructor() throws Exception {
    String allegationType = new String("Physical abuse");
    allegationTypes[0] = allegationType;

    Allegation allegation = new Allegation(id, screeningId, perpetratorId, victimId, createdAt,
        updatedAt, allegationTypes);
    assertThat(allegation.getId(), is(equalTo(id)));
    assertThat(allegation.getScreeningId(), is(equalTo(screeningId)));
    assertThat(allegation.getPerpetratorId(), is(equalTo(perpetratorId)));
    assertThat(allegation.getVictimId(), is(equalTo(victimId)));
    assertThat(allegation.getCreatedAt(), is(equalTo(createdAt)));
    assertThat(allegation.getUpdatedAt(), is(equalTo(updatedAt)));
    assertThat(allegation.getAllegationTypes(), is(equalTo(allegationTypes)));
    assertThat(allegation.getPrimaryKey(), is(equalTo(id)));
    assertThat(allegation.getScreening(), is(equalTo(null)));
  }

  @Test
  public void testSetAllegationTypes() throws Exception {
    allegationTypes[0] = "Physical abuse";

    Allegation allegation = new Allegation(id, screeningId, perpetratorId, victimId, createdAt,
        updatedAt, allegationTypes);
    allegationTypes[0] = "modified";
    allegation.setAllegationTypes(allegationTypes);
    assertThat(allegation.getAllegationTypes(), is(equalTo(allegationTypes)));
  }
}
