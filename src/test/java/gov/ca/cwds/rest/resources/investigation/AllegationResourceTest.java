package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.Allegation;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

@SuppressWarnings("javadoc")
public class AllegationResourceTest {

  private AllegationResource resource;
  private TypedResourceDelegate<String, Allegation> service;
  private Allegation allegation;
  private String referralId = "1234567ABC";
  private String allegationId = "2345678ABC";

  @SuppressWarnings("unchecked")
  @Before
  public void initMocks() {
    service = mock(TypedResourceDelegate.class);
    resource = new AllegationResource(service);
  }

  @Test

  public void callAllegationServiceOnCreate() throws Exception {
    allegation = new AllegationEntityBuilder().build();
    resource.create(allegationId, allegation);
    verify(service).create(allegation);
  }

  @Test
  public void callAllegationServiceOnUpdate() throws Exception {
    allegation = new AllegationEntityBuilder().build();
    resource.update(referralId, allegationId, allegation);
    verify(service).update(allegationId, allegation);
  }

}
