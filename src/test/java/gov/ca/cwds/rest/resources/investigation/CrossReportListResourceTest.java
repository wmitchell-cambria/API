package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.investigation.CrossReportList;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

@SuppressWarnings("javadoc")
public class CrossReportListResourceTest {

  private CrossReportListResource resource;
  private TypedResourceDelegate<String, CrossReportList> service;
  private String cmsId = "1234567ABC";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new CrossReportListResource(service);
  }

  @Test
  public void callCrossReportListServiceOnFind() throws Exception {
    resource.find(cmsId);
    verify(service).get(cmsId);
  }

}
