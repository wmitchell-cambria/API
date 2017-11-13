package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.fixture.investigation.CrossReportEntityBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class CrossReportListTest {
  private ObjectMapper MAPPER = new ObjectMapper();
  private CrossReport crossReport;
  private Set<CrossReport> crossReports = new HashSet<>();

  @Before
  public void setup() {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
    crossReport = new CrossReportEntityBuilder().build();
    crossReports.add(crossReport);
  }

  @Test
  public void shouldCreateObjectWithDefaultConstrutor() {
    CrossReportList crossReportList = new CrossReportList();
    assertNotNull(crossReportList);
  }

  @Test
  public void domainConstructorTest() {
    CrossReportList crossReportList = new CrossReportList(crossReports);
    assertThat(crossReportList.getCrossReports(), is(equalTo(crossReports)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReportList.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  // @Test
  // @Ignore
  // public void testSerializedOutput()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // CrossReportList crossReportList = new CrossReportListEntityBuilder().build();
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(crossReportList);
  // System.out.println(expected);
  // }

}
