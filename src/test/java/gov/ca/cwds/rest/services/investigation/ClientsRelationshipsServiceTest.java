package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.fixture.investigation.RelationshipListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

@SuppressWarnings("javadoc")
public class ClientsRelationshipsServiceTest {
  private static final String DEFAULT_KEY = "1234567ABC";
  private RelationshipList relationshipListStub;
  private ClientsRelationshipsService relationshipService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");

    ClientRelationshipDao clientRelationshipDao = mock(ClientRelationshipDao.class);
    ClientDao clientDao = mock(ClientDao.class);
    relationshipService = new ClientsRelationshipsService(clientRelationshipDao, clientDao);
    relationshipListStub = new RelationshipListEntityBuilder().build();
  }

  @Test
  public void testFindReturnsRelationshipListStub() {
    Response response = relationshipService.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(relationshipListStub)));

  }

  @Test
  public void createThrowsNotImplementedException() {
    thrown.expect(NotImplementedException.class);
    relationshipService.create(relationshipListStub);
  }

  @Test
  public void deleteThrowsNotImplementedException() {
    thrown.expect(NotImplementedException.class);
    relationshipService.delete(DEFAULT_KEY);
  }

  @Test
  public void updateThrowsNotImplementedException() {
    thrown.expect(NotImplementedException.class);
    relationshipService.update(DEFAULT_KEY, relationshipListStub);
  }

}
