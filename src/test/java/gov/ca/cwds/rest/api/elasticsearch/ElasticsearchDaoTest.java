package gov.ca.cwds.rest.api.elasticsearch;

import static org.hamcrest.Matchers.startsWith;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.elasticsearch.db.ElasticsearchDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ElasticsearchDaoTest {

  private ElasticsearchDao esdao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    esdao = new ElasticsearchDao("localhost", "9300", "elasticsearch");
  }

  @Test
  public void testIndexNameEmptyFails() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Elasticsearch Index Name must be provided"));
    esdao.setIndexName("");
  }

  @Test
  public void testIndexTypeEmptyFails() throws Exception {
    thrown.expect(ApiException.class);
    thrown.expectMessage(startsWith("Elasticsearch Index Type must be provided"));
    esdao.setIndexType("");
  }
}
