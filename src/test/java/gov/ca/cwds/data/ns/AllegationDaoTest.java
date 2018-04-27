package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.Allegation;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class AllegationDaoTest extends Doofenshmirtz<Allegation> {

  AllegationDao target;
  Query<Allegation> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new AllegationDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(AllegationDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findByVictimId_A$String() throws Exception {
    String id = "10";
    final List<Allegation> actual = target.findByVictimId(id);
    final List<Allegation> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findByPerpetratorId_A$String() throws Exception {
    String id = "10";
    final List<Allegation> actual = target.findByPerpetratorId(id);
    final List<Allegation> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findByVictimOrPerpetratorId_A$String() throws Exception {
    String id = "10";
    final List<Allegation> actual = target.findByVictimOrPerpetratorId(id);
    final List<Allegation> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void deleteByIdList_A$List() throws Exception {
    final List<String> idList = new ArrayList<String>();
    idList.add(DEFAULT_CLIENT_ID);
    target.deleteByIdList(idList);
  }

}
