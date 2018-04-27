package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class LegacyDescriptorDaoTest extends Doofenshmirtz<LegacyDescriptorEntity> {

  LegacyDescriptorDao target;
  Query<LegacyDescriptorEntity> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new LegacyDescriptorDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(LegacyDescriptorDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findParticipantLegacyDescriptor_A$String() throws Exception {
    String participantId = null;
    LegacyDescriptorEntity actual = target.findParticipantLegacyDescriptor(participantId);
    LegacyDescriptorEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findAddressLegacyDescriptor_A$String__null() throws Exception {
    String addressId = null;
    LegacyDescriptorEntity actual = target.findAddressLegacyDescriptor(addressId);
    LegacyDescriptorEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findAddressLegacyDescriptor_A$String__non_null() throws Exception {
    final LegacyDescriptorEntity fixture = new LegacyDescriptorEntity(DEFAULT_CLIENT_ID, "uid",
        "CLIENT_T", "Client", "0X5", "CMS client table", 10L);
    q = queryInator(fixture);
    String addressId = DEFAULT_PARTICIPANT_ID;
    LegacyDescriptorEntity actual = target.findAddressLegacyDescriptor(addressId);
    LegacyDescriptorEntity expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findParticipantLegacyDescriptors_A$Set() throws Exception {
    Set<String> participantIds = new HashSet<>();
    participantIds.add("10");
    Map<String, LegacyDescriptorEntity> actual =
        target.findParticipantLegacyDescriptors(participantIds);
    Map<String, LegacyDescriptorEntity> expected = new HashMap<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
