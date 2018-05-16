package gov.ca.cwds.rest.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import org.junit.Before;
import org.junit.Test;

public class ScreeningRelationshipResourceTest {

  ScreeningRelationshipResource relationsshipResource;
  ResourceDelegate resourceDelegate;
  ScreeningRelationship relationship;

  @Before
  public void setup() {
    resourceDelegate = mock(ResourceDelegate.class);
    relationsshipResource = new ScreeningRelationshipResource(resourceDelegate);
  }

  @Test
  public void whenCreateIsInvoledThenWeShouldCallService(){
    relationsshipResource.create(relationship);
    verify(resourceDelegate).create(relationship);
  }
}