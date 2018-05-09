package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.query.Query;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ParticipantDaoTest extends Doofenshmirtz<ParticipantEntity> {

  ParticipantDao target;
  Query<ParticipantEntity> q;

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new ParticipantDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(ParticipantDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findLegacyIdListByScreeningId_A$String() throws Exception {
    String screeningId = DEFAULT_PARTICIPANT_ID;

    Set<String> actual = target.findLegacyIdListByScreeningId(screeningId);
    Set<String> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
