package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gov.ca.cwds.fixture.ScreeningDashboardResourceBuilder;
import io.dropwizard.jackson.Jackson;

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
  public void testConstrutor() throws Exception {
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
//	System.out.println(MAPPER.writeValueAsString(emptyScreeningDashboardList));
  }
  
  @Test
  public void testSerializeToJSON() throws Exception {
	ScreeningDashboardList screeningDashboardList = new ScreeningDashboardList(screeningDashboardArray);
//	System.out.println(MAPPER.writeValueAsString(screeningDashboardList));
  }
  
}
