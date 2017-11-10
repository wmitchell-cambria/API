package gov.ca.cwds.rest.resources.investigation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

@SuppressWarnings("javadoc")
public class RelationshipListResourceTest {
  private RelationshipListResource resource;
  private RelationshipList contact;
  private TypedResourceDelegate<String, RelationshipList> service;
  private String referralId = "1234567ABC";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    service = mock(TypedResourceDelegate.class);
    resource = new RelationshipListResource(service);
  }

  @Test
  public void callRelationshipServiceOnFind() throws Exception {
    resource.find(referralId);
    verify(service).get(referralId);
  }
}
