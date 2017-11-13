package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class CrossReportAgencyTest {
  private ObjectMapper MAPPER = new ObjectMapper();

  protected String type;
  protected String name;

  @Before
  public void setup() {
    type = "DEPARTMENT_OF_JUSTICE";
    name = "County Sheriff Dept";
  }

  @Test
  public void testEmptyConstructorSuccess() {
    CrossReportAgency crossReportAgency = new CrossReportAgency();
    assertNotNull(crossReportAgency);
  }

  @Test
  public void testConstructorSuccess() {
    CrossReportAgency crossReportAgency = new CrossReportAgency(type, name);
    assertThat(crossReportAgency.getType(), is(equalTo(type)));
    assertThat(crossReportAgency.getName(), is(equalTo(name)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(CrossReportAgency.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  // @Test
  // @Ignore
  // public void testSerializedInvestigation()
  // throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
  // CrossReportAgency crossReportAgency = new CrossReportAgency(type, name);
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(crossReportAgency);
  // System.out.println(expected);
  // }

}
