package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.services.RelationshipsService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClientRelationshipResourceTest {

    private final static RelationshipsService relationshipsService = mock(RelationshipsService.class);


    @ClassRule
    public final static ResourceTestRule resources = ResourceTestRule.builder()
        .addResource(new ClientRelationshipResource(relationshipsService))
            .build();

    @Test
    public void shouldCallGetRelationsipsByClientIdResourceEndpoint(){
        resources.target("/clients/1/relationships").request().accept(MediaType.APPLICATION_JSON).get();
        verify(relationshipsService).find("1");
    }

    @Test
    public void shouldCallGetReleationshipsByClientIdsResourceEndpoint(){
        List clientIds = Arrays.asList("1zxcydd");
        resources.target("/clients/relationships").queryParam("clientIds", "1zxcydd").request().accept(MediaType.APPLICATION_JSON).get();
        verify(relationshipsService).findForIds(clientIds);
    }
}