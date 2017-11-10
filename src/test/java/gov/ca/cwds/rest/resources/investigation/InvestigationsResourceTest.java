package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

public class InvestigationsResourceTest {

  private InvestigationsResource resource;
  private Investigation investigation;
  private TypedResourceDelegate<String, Investigation> service;
  private String referralId = "1";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new InvestigationsResource(service);

  }

  @Test
  public void callInvestigationSerivceOnFind() throws Exception {
    resource.find(referralId);
    verify(service).get(referralId);
  }
}
