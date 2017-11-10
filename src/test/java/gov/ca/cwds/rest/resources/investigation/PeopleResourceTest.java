package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.investigation.People;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

@SuppressWarnings("javadoc")
public class PeopleResourceTest {

  private PeopleResource resource;
  private People people;
  private TypedResourceDelegate<String, People> service;
  private String referralId = "1234567ABC";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new PeopleResource(service);
  }

  @Test
  public void callPeopleServiceOnFind() throws Exception {
    resource.find(referralId);
    verify(service).get(referralId);
  }
}
