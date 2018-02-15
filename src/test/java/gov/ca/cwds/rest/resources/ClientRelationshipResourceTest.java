package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClientRelationshipResourceTest {

    @SuppressWarnings("unchecked")
    private final static TypedResourceDelegate<String, Relationship> typedResourceDelegate =
            mock(TypedResourceDelegate.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ClientRelationshipResource(typedResourceDelegate))
            .build();
    @Test
    public void shouldCallGetResourceEndpoint(){
        Relationship relationship = new Relationship();

        resources.target("/clients/1/relationships").request().accept(MediaType.APPLICATION_JSON).get();
        verify(typedResourceDelegate).get("1");
    }

}