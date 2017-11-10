package gov.ca.cwds.fixture.investigation;

import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.investigation.SimpleAllegation;

@SuppressWarnings("javadoc")
public class SimpleAllegationEntityBuilder {

  private String victimId = "1234567ABC";
  private String perpetratorId = "2345678ABC";
  private Set<String> allegationTypes = new HashSet<>();

  public SimpleAllegation build() {
    allegationTypes.add("General neglect");
    allegationTypes.add("Physical abuse");
    return new SimpleAllegation(victimId, perpetratorId, allegationTypes);
  }

  public SimpleAllegationEntityBuilder setVictimId(String victimId) {
    this.victimId = victimId;
    return this;
  }

  public SimpleAllegationEntityBuilder setPerpetratorId(String perpetratorId) {
    this.perpetratorId = perpetratorId;
    return this;
  }

  public SimpleAllegationEntityBuilder setAllegationTypes(Set<String> allegationTypes) {
    this.allegationTypes = allegationTypes;
    return this;
  }

}
