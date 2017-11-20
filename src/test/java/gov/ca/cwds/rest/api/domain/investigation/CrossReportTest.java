package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.fixture.investigation.CmsRecordDescriptorEntityBuilder;
import gov.ca.cwds.fixture.investigation.CrossReportAgencyEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class CrossReportTest {
  private ObjectMapper MAPPER = new ObjectMapper();

  private CmsRecordDescriptor legacyDescriptor;
  protected Boolean readOnly;
  protected Date reportedOn;
  protected String communicationMethod;
  protected String county;
  private Set<CrossReportAgency> crossReportAgencies = new HashSet<>();

  @Before
  public void setup() {
    legacyDescriptor = new CmsRecordDescriptorEntityBuilder().build();
    readOnly = false;
    reportedOn = DomainChef.uncookDateString("2017-10-31");
    communicationMethod = "408";
    county = "20";
    CrossReportAgency crossReportAgency1 = new CrossReportAgencyEntityBuilder().build();
    CrossReportAgency crossReportAgency2 =
        new CrossReportAgencyEntityBuilder().setName("County Public Safety").build();
    crossReportAgencies.add(crossReportAgency2);
    crossReportAgencies.add(crossReportAgency1);
  }

  @Test
  public void testEmptyConstructorSuccess() {
    CrossReport crossReport = new CrossReport();
    assertNotNull(crossReport);
  }

  @Test
  public void testConstructorSuccess() {
    CrossReport crossReport = new CrossReport(legacyDescriptor, readOnly, reportedOn,
        communicationMethod, county, crossReportAgencies);
    assertThat(crossReport.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(crossReport.getReadOnly(), is(equalTo(readOnly)));
    assertThat(crossReport.getReportedOn(), is(equalTo(reportedOn)));
    assertThat(crossReport.getCommunicationMethod(), is(equalTo(communicationMethod)));
    assertThat(crossReport.getCounty(), is(equalTo(county)));
    assertThat(crossReport.getCrossReportAgencies(), is(equalTo(crossReportAgencies)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReport.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void testNullAgencies() throws Exception {
    Set<CrossReportAgency> crossReportAgencies = new HashSet<>();
    CrossReport crossReport = new CrossReport(legacyDescriptor, readOnly, reportedOn,
        communicationMethod, county, crossReportAgencies);
    assertThat(crossReport.getCrossReportAgencies(), is(equalTo(crossReportAgencies)));
  }

  // @Test
  // @Ignore
  // public void testSerializedInvestigation()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // CrossReport crossReport = new CrossReport(legacyDescriptor, readOnly, reportedOn,
  // communicationMethod, county, crossReportAgencies);
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(crossReport);
  // System.out.println(expected);
  // }

}
