package gov.ca.cwds.rest.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.ca.cwds.IntakeBaseTest;
import gov.ca.cwds.rest.ApiApplication;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.services.RelationshipsService;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.ResourceTestRule;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.services.RelationshipsService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.json.JSONException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class ClientRelationshipResourceTest extends IntakeBaseTest {

    private static final RelationshipsService service = mock(RelationshipsService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ClientRelationshipResource(service))
            .build();

    @Before
    public void setup(){
        reset(service);
    }

    @Test
    public void shouldCallGetRelationsipsByClientIdResourceEndpoint(){
        resources.target("/clients/1/relationships").request().accept(MediaType.APPLICATION_JSON).get();
        verify(service).find("1");
    }

    @Test
    public void shouldCallGetReleationshipsByClientIdsResourceEndpoint(){
        List clientIds = Arrays.asList("1zxcydd");
        resources.client()
                .target("/clients/relationships")
                .queryParam("clientIds", "1zxcydd")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        verify(service).findForIds(clientIds);
    }
}
