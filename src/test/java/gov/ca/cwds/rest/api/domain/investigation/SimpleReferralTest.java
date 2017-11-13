package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.HistoryOfInvolvementAllegationEntityBuilder;
import gov.ca.cwds.fixture.investigation.SimplePersonEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class SimpleReferralTest {

  private ObjectMapper MAPPER = new ObjectMapper();
  private String endDate = "2000-02-01";
  private Set<HistoryOfInvolvementAllegation> allegations = new HashSet<>();
  private SimplePerson assignedSocialWorker;
  private SimpleLegacyDescriptor legacyDescriptor;
  private SimplePerson reporter;
  private LimitedAccess limitedAccess;
  private String responseTime = "immediate";
  private String countyName = "Plumas";
  private String responseTimeId = "1520";
  private String startDate = "1999-02-28";
  private String legacyUiId = "0762-2283-8000-4000739";

  @Before
  public void setup() {
    reporter = new SimplePersonEntityBuilder().setFirstName("Reporter").build();
    assignedSocialWorker = new SimplePersonEntityBuilder().setFirstName("social worker").build();
    legacyDescriptor = new SimpleLegacyDescriptor(legacyUiId);
    HistoryOfInvolvementAllegation allegation =
        new HistoryOfInvolvementAllegationEntityBuilder().build();
    allegations.add(allegation);
    limitedAccess = new LimitedAccess("0", "N'");
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ScreeningSummary.class).suppress(Warning.NONFINAL_FIELDS).verify();

  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SimpleReferral domain = new SimpleReferral(endDate, legacyDescriptor, reporter, countyName,
        responseTimeId, allegations, assignedSocialWorker, limitedAccess, responseTime, startDate);
    assertThat(domain.getEndDate(), is(equalTo(endDate)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(domain.getReporter(), is(equalTo(reporter)));
    assertThat(domain.getCountyName(), is(equalTo(countyName)));
    assertThat(domain.getResponseTimeId(), is(equalTo(responseTimeId)));
    assertThat(domain.getAllegations(), is(equalTo(allegations)));
    assertThat(domain.getAssignedSocialWorker(), is(equalTo(assignedSocialWorker)));
    assertThat(domain.getAccessLimitation(), is(equalTo(limitedAccess)));
    assertThat(domain.getResponseTime(), is(equalTo(responseTime)));
    assertThat(domain.getStartDate(), is(equalTo(startDate)));
  }

  // @Test
  // @Ignore
  // public void testSerializedOutput()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // SimpleReferral smipleReferral = new SimpleReferralEntityBuilder().build();
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(smipleReferral);
  // System.out.println(expected);
  // }

}
