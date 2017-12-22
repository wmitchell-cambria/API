package gov.ca.cwds.fixture.investigation;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.services.ServiceException;
import io.dropwizard.jackson.Jackson;

public class InvolvementHistoryResourceBuilder {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  public InvolvementHistory build() {
    InvolvementHistory historyOfInvolvement = null;
    try {
      historyOfInvolvement = MAPPER.readValue(
          fixture("gov/ca/cwds/rest/services/hoi/involvementhistory/valid/valid.json"),
          InvolvementHistory.class);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
    return historyOfInvolvement;
  }

}
