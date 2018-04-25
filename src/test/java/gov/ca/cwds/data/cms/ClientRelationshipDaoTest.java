package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ClientRelationshipDaoTest extends Doofenshmirtz<ClientRelationship> {

  ClientRelationshipDao target;
  Query<ClientRelationship> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new ClientRelationshipDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(ClientRelationshipDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findByPrimaryClientId_A$String() throws Exception {
    String primaryClientId = DEFAULT_CLIENT_ID;
    ClientRelationship[] actual = target.findByPrimaryClientId(primaryClientId);
    ClientRelationship[] expected = new ClientRelationship[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findBySecondaryClientId_A$String() throws Exception {
    String secondaryClientId = DEFAULT_CLIENT_ID;
    ClientRelationship[] actual = target.findBySecondaryClientId(secondaryClientId);
    ClientRelationship[] expected = new ClientRelationship[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findRelationshipsByClientId_A$String() throws Exception {
    String clientId = DEFAULT_CLIENT_ID;
    List<RelationshipWrapper> actual = target.findRelationshipsByClientId(clientId);
    List<RelationshipWrapper> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
