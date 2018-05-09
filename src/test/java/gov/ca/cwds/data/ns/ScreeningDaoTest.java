package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ScreeningDaoTest extends Doofenshmirtz<ScreeningEntity> {

  ScreeningDao target;
  Query<ScreeningEntity> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new ScreeningDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(ScreeningDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findScreeningsByReferralId_A$String() throws Exception {
    String referralId = "10";
    ScreeningEntity[] actual = target.findScreeningsByReferralId(referralId);
    ScreeningEntity[] expected = new ScreeningEntity[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findScreeningsByClientIds_A$Set() throws Exception {
    Set<String> clientIds = new HashSet<>();
    clientIds.add(DEFAULT_CLIENT_ID);
    Set<ScreeningEntity> actual = target.findScreeningsByClientIds(clientIds);
    Set<ScreeningEntity> expected = new HashSet<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findScreeningsByUserId_A$String() throws Exception {
    String staffId = "10";
    List<ScreeningWrapper> actual = target.findScreeningsByUserId(staffId);
    List<ScreeningWrapper> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
