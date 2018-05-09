package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ParticipantAddressesDaoTest extends Doofenshmirtz<ParticipantAddresses> {

  ParticipantAddressesDao target;
  Query<ParticipantAddresses> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new ParticipantAddressesDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(ParticipantAddressesDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findByParticipantId_A$String() throws Exception {
    String participantId = DEFAULT_PARTICIPANT_ID;
    Set<ParticipantAddresses> actual = target.findByParticipantId(participantId);
    Set<ParticipantAddresses> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
