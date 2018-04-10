package gov.ca.cwds.rest.api.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.fixture.ScreeningDashboardResourceBuilder;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ScreeningDashboardListTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private ScreeningDashboard sd1;
  private ScreeningDashboard sd2;
  private List<ScreeningDashboard> screeningDashboardArray = new ArrayList<>();

  @Before
  public void setup() {
    MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
	sd1 = new ScreeningDashboardResourceBuilder().build();
	sd2 = new ScreeningDashboardResourceBuilder().build();
	screeningDashboardArray.add(sd1);
	screeningDashboardArray.add(sd2);
  }
  
  @Test
  public void testConstrutorAndGetter() throws Exception {
	ScreeningDashboardList screeningDashboardList = new ScreeningDashboardList(screeningDashboardArray);
	assertThat(screeningDashboardList.getScreeningDashboard(), is(equalTo(screeningDashboardArray)));
  }
  
  @Test
  public void testDefaultConstructor() throws Exception {
	ScreeningDashboardList empty = new ScreeningDashboardList();
	assertThat(empty.getScreeningDashboard(), is(equalTo(null)));
  }

  @Test
  public void testWhenEmpty() throws Exception {
	List<ScreeningDashboard> empty = new ArrayList<>();
	ScreeningDashboardList emptyScreeningDashboardList = new ScreeningDashboardList(empty);
	assertThat(emptyScreeningDashboardList.getScreeningDashboard(), is(empty));
  }
  
  @Test
  public void testSerializeToJSON() throws Exception {
	TypeReference<List<ScreeningDashboard>> typeReference = new TypeReference<List<ScreeningDashboard>>() {};
	ScreeningDashboardList screeningDashboardList = new ScreeningDashboardList(screeningDashboardArray);
	String expected = MAPPER.writeValueAsString(screeningDashboardList);
    List<ScreeningDashboard> sdlList = MAPPER.readValue(fixture("fixtures/domain/screening/valid/screeningDashboardList.json"), typeReference);
    String sdl = MAPPER.writeValueAsString(sdlList);
    assertThat(sdl, is(expected));
  }

  @Test
  public void testDeserializationFromJSON() throws Exception {
	TypeReference<List<ScreeningDashboard>> typeReference = new TypeReference<List<ScreeningDashboard>>() {};
	
	ScreeningDashboardList expectedScreeningDashboardList = new ScreeningDashboardList(screeningDashboardArray);
    List<ScreeningDashboard> sdlList = MAPPER.readValue(fixture("fixtures/domain/screening/valid/screeningDashboardList.json"), typeReference);

    ScreeningDashboardList screeningDashboardList = new ScreeningDashboardList(sdlList);
     assertThat(screeningDashboardList, is(expectedScreeningDashboardList));
  }
 
  @Test
  public void testEquaslHashCodeWorks() throws Exception {
	EqualsVerifier.forClass(ScreeningDashboard.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
  }

}
