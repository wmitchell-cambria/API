package gov.ca.cwds.rest.services.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;
import static org.junit.Assert.assertEquals;


import gov.ca.cwds.IntakeBaseTest;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOICaseServiceTest extends IntakeBaseTest {

  @Test
  public void testHandleFindNonExistingClientId() throws Exception {
    WebTarget target = clientTestRule.withSecurityToken("security/social-worker-only-principal.json")
        .target(RESOURCE_CASE_HISTORY_OF_INVOLVEMENT);

    HOIRequest request = new HOIRequest();
    request.setClientIds(Stream.of("-1").collect(Collectors.toSet()));

    Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON);
    Response response = invocation.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

    String value = IOUtils.toString((InputStream) response.getEntity(), "UTF-8");
    assertEquals("[ ]", value);
  }

  /**
   * Test setup
   * 
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
/*
    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
    caseDao = mock(CaseDao.class);
    clientDao = mock(ClientDao.class);
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao);
    request = new HOIRequest();
    request.setClientIds(Stream.of("123").collect(Collectors.toSet()));
*/
  }

/*
  @Test
  public void testHandleFind() throws Exception {
    CmsCase cmscase1 = new CmsCaseEntityBuilder().setId("TAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
    CmsCase cmscase2 = new CmsCaseEntityBuilder().setId("RAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
    CmsCase[] cases = {cmscase1, cmscase2};
    ClientRelationship relationship = new ClientRelationshipEntityBuilder().build();
    ClientRelationship[] relationships = {relationship};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class))).thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(relationships);
    Client client = new ClientEntityBuilder().build();
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    when(clientDao.find(any(String.class))).thenReturn(client);
    HOICaseResponse response = target.handleFind(request);
    assertThat(response, notNullValue());
  }
*/

/*
  @Test
  public void testNotPermittedHandleFind() throws Exception {
    HOICaseService spy = spy(target);
    doThrow(new AuthorizationException()).when(spy).authorizeClient(anyString());
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(new CmsCase[] {});

    HOICaseResponse response = spy.handleFind(request);
    assertEquals(response.getHoiCases().size(), 0);
  }
*/

/*
  @Test
  public void testHandleFindWhenNoClientIdsProvided() throws Exception {
    HOIRequest emptyRequest = new HOIRequest();
    emptyRequest.setClientIds(new HashSet<String>());
    HOICaseResponse response = target.handleFind(emptyRequest);
    assertThat(response, notNullValue());
  }
*/

/*
  @Test
  public void testFindParentsByPrimaryRelationship() throws Exception {
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).build();
    CmsCase[] cases = {cmscase};
    ClientRelationship relationship =
        new ClientRelationshipEntityBuilder().setClientRelationshipType((short) 245).build();
    ClientRelationship[] relationships = {relationship};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class))).thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(relationships);
    Client client = new ClientEntityBuilder().build();
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    when(clientDao.find(any(String.class))).thenReturn(client);
    HOICaseResponse response = target.handleFind(request);
    assertThat(response, notNullValue());
  }
*/

/*
  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() throws Exception {
    target.handleRequest(new HOICase());
  }
*/
}
