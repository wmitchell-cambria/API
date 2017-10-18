package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.api.domain.investigation.AllegationList;

@SuppressWarnings("javadoc")
public class AllegationListEntityBuilder {

  private Allegation allegation = new AllegationEntityBuilder().build();
  private Set<Allegation> allegations = new HashSet<>();

  public AllegationList build() {
    allegations.add(allegation);
    return new AllegationList(allegations);
  }

  public AllegationListEntityBuilder setAllegations(Set<Allegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public Set<Allegation> getAllegations() {
    return allegations;
  }

  public AllegationListEntityBuilder addAllegation(Allegation allegation) {
    this.allegations.add(allegation);
    return this;
  }

}
