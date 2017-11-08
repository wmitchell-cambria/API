package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.data.dao.investigation.CrossReportDao;
import gov.ca.cwds.fixture.investigation.CrossReportEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

@SuppressWarnings("javadoc")
public class CrossReportServiceTest {
  private static final String DEFAULT_KEY = "1234567ABC";

  private CrossReport crossReportStub;

  private CrossReportService crossReportService;
  private CrossReportDao crossReportDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    this.crossReportDao = mock(CrossReportDao.class);
    crossReportService = new CrossReportService(crossReportDao);
    crossReportStub = new CrossReportEntityBuilder().build();
  }

  @Test
  public void testFindReturnsCrossReportStub() {
    Response response = crossReportService.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(crossReportStub)));
  }

  @Test
  public void testCreateReturnsCrossReportStub() {
    Response response = crossReportService.create(crossReportStub);
    assertThat(response, is(equalTo(crossReportStub)));
  }

  @Test
  public void testDeleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    crossReportService.delete(DEFAULT_KEY);
  }

  @Test
  public void testUpdateThrowsNotImplementedException() throws Exception {
    Response response = crossReportService.update(DEFAULT_KEY, crossReportStub);
    assertThat(response, is(equalTo(crossReportStub)));
  }

}
