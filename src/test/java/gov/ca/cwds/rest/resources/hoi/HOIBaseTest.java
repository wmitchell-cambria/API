package gov.ca.cwds.rest.resources.hoi;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import java.io.IOException;
import java.util.List;

abstract class HOIBaseTest extends IntakeBaseTest {

  static final String VALID_HOI_JSON = "fixtures/gov/ca/cwds/rest/resources/hoi/history-of-involvements.valid.json";

  InvolvementHistory getExpectedInvolvementHistory() throws IOException {
    return getInvolvementHistory(fixture(VALID_HOI_JSON));
  }

  InvolvementHistory getInvolvementHistory(String hoiJSON) throws IOException {
    return objectMapper.readValue(hoiJSON.getBytes(), InvolvementHistory.class);
  }

  List<HOIScreening> getExpectedHOIScreenings(InvolvementHistory expectedInvolvementHistory)
      throws IOException {
    List<HOIScreening> hoiScreenings = expectedInvolvementHistory.getScreenings();

    String hoiScreening714JSON = fixture(
        "fixtures/gov/ca/cwds/rest/resources/hoi/hoi-screening-714.json");
    HOIScreening hoiScreening714 = objectMapper
        .readValue(hoiScreening714JSON.getBytes(), HOIScreening.class);
    hoiScreenings.add(hoiScreening714);

    return hoiScreenings;
  }

  void assertHOICasesAreSorted(String[] expectedCaseIds, List<HOICase> actualHoiCases) {
    assertNotNull(actualHoiCases);
    assertEquals(expectedCaseIds.length, actualHoiCases.size());
    for (int i = 0; i < expectedCaseIds.length; i++) {
      assertEquals(expectedCaseIds[i], actualHoiCases.get(i).getId());
    }
  }

  void assertHOIReferralsAreSorted(String[] expectedReferralIds,
      List<HOIReferral> actualHoiReferrals) {
    assertNotNull(actualHoiReferrals);
    assertEquals(expectedReferralIds.length, actualHoiReferrals.size());
    for (int i = 0; i < expectedReferralIds.length; i++) {
      assertEquals(expectedReferralIds[i], actualHoiReferrals.get(i).getId());
    }
  }

  void assertHOIScreeningsAreSorted(String[] expectedScreeningIds,
      List<HOIScreening> actualHOIScreenings) {
    assertNotNull(actualHOIScreenings);
    assertEquals(expectedScreeningIds.length, actualHOIScreenings.size());
    for (int i = 0; i < expectedScreeningIds.length; i++) {
      assertEquals(expectedScreeningIds[i], actualHOIScreenings.get(i).getId());
    }
  }
}
