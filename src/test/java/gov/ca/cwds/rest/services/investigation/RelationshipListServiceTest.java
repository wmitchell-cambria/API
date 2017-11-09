package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.dao.investigation.RelationshipsDao;
import gov.ca.cwds.fixture.investigation.RelationshipListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;

@SuppressWarnings("javadoc")
public class RelationshipListServiceTest {
  private static final String DEFAULT_KEY = "1234567ABC";
  private RelationshipList relationshipListStub;
  private RelationshipListService relationshipListService;
  private RelationshipsDao relationshipDao;
  private ClientDao clientDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");

    this.relationshipDao = mock(RelationshipsDao.class);
    this.clientDao = mock(ClientDao.class);
    relationshipListService = new RelationshipListService(relationshipDao, clientDao);
    relationshipListStub = new RelationshipListEntityBuilder().build();
  }

  @Test
  public void testFindReturnsRelationshipListStub() {
    Response response = relationshipListService.find(DEFAULT_KEY);
    assertThat(response, is(equalTo(relationshipListStub)));

  }

  @Test
  public void createThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    relationshipListService.create(relationshipListStub);
  }

  @Test
  public void deleteThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    relationshipListService.delete(DEFAULT_KEY);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    thrown.expect(NotImplementedException.class);
    relationshipListService.update(DEFAULT_KEY, relationshipListStub);
  }

}
