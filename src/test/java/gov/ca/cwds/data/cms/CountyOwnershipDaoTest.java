package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

public class CountyOwnershipDaoTest {

  CountyOwnershipDao target;

  @Before
  public void setup() throws Exception {
    final SessionFactory sessionFactory = mock(SessionFactory.class);
    target = new CountyOwnershipDao(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(CountyOwnershipDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  // @Test
  // public void update_Args__CountyOwnership() throws Exception {
  // CountyOwnership object = new CountyOwnership();
  // CountyOwnership actual = target.update(object);
  // CountyOwnership expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

}
