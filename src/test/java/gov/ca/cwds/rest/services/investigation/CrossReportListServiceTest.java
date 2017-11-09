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
import gov.ca.cwds.fixture.investigation.CrossReportListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.CrossReportList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

@SuppressWarnings("javadoc")
public class CrossReportListServiceTest {
  private static final String DEFAULT_KEY = "1234567ABC";

  private CrossReportList crossReportListStub;

  private CrossReportListService crossReportListService;
  private CrossReportDao crossReportDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    this.crossReportDao = mock(CrossReportDao.class);
    crossReportListService = new CrossReportListService(crossReportDao);
    crossReportListStub = new CrossReportListEntityBuilder().build();
  }

  @Test
  public void testFindReturnsCrossReportListStub() {
    Response response = crossReportListService.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(crossReportListStub)));

  }

  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    crossReportListService.create(crossReportListStub);
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    crossReportListService.delete(DEFAULT_KEY);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    crossReportListService.update(DEFAULT_KEY, crossReportListStub);
  }
}
