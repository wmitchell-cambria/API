package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.CrossReportEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

@SuppressWarnings("javadoc")
public class CrossReportResourceTest {

  private CrossReportResource resource;
  private CrossReport crossReport;
  private TypedResourceDelegate<String, CrossReport> service;
  private String referralId = "2345678ABC";
  private String cmsId = "1234567ABC";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new CrossReportResource(service);
  }

  @Test
  public void callCrossReportServiceOnCreate() throws Exception {
    crossReport = new CrossReportEntityBuilder().build();
    resource.create(crossReport);
    verify(service).create(crossReport);
  }

  @Test
  public void callCrossReportServiceOnUpdate() throws Exception {
    crossReport = new CrossReportEntityBuilder().build();
    resource.update(referralId, cmsId, crossReport);
    verify(service).update(cmsId, crossReport);
  }
}
