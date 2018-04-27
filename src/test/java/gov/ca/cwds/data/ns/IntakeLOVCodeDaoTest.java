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

import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class IntakeLOVCodeDaoTest extends Doofenshmirtz<IntakeLOVCodeEntity> {

  IntakeLOVCodeDao target;
  Query<IntakeLOVCodeEntity> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new IntakeLOVCodeDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLOVCodeDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findIntakeLOVCodesByIntakeCodes_A$Set() throws Exception {
    Set<String> intakeCodes = new HashSet<>();
    Map<String, IntakeLOVCodeEntity> actual = target.findIntakeLOVCodesByIntakeCodes(intakeCodes);
    Map<String, IntakeLOVCodeEntity> expected = new HashMap<>();
    assertThat(actual, is(equalTo(expected)));
  }

}
