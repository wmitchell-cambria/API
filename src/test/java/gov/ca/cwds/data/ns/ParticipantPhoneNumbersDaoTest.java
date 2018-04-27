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

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ParticipantPhoneNumbersDaoTest extends Doofenshmirtz<ParticipantPhoneNumbers> {

  ParticipantPhoneNumbersDao target;
  Query<ParticipantPhoneNumbers> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new ParticipantPhoneNumbersDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(ParticipantPhoneNumbersDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findByParticipantId_A$String() throws Exception {
    String participantId = "10";
    Set<ParticipantPhoneNumbers> actual = target.findByParticipantId(participantId);
    Set<ParticipantPhoneNumbers> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_A$ParticipantPhoneNumbers() throws Exception {
    ParticipantPhoneNumbers object = new ParticipantPhoneNumbers();
    ParticipantPhoneNumbers actual = target.create(object);
    ParticipantPhoneNumbers expected = new ParticipantPhoneNumbers();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void update_A$ParticipantPhoneNumbers() throws Exception {
    final PhoneNumbers phoneNumbers = new PhoneNumbers("10", "9164579983", "cell");
    final ParticipantEntity participantEntity = new ParticipantEntity();
    participantEntity.setId("10");

    final ParticipantPhoneNumbers phones =
        new ParticipantPhoneNumbers(participantEntity, phoneNumbers);
    phones.setId("10");
    q = queryInator(phones);

    final ParticipantPhoneNumbers actual = target.update(phones);
    final ParticipantPhoneNumbers expected = new ParticipantPhoneNumbers(phones);
    assertThat(actual, is(equalTo(expected)));
  }

}
