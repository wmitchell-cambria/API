package gov.ca.cwds.rest.api.domain.hoi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class HOICaseResponseTest {


  /**
   * @throws Exception - Exception
   */
  @Test
  public void type() throws Exception {
    assertThat(HOICaseResponse.class, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void instantiation() throws Exception {
    HOICaseResponse target = new HOICaseResponse();
    assertThat(target, notNullValue());
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void getHoiCase_Args__() throws Exception {
    HOICaseResponse target = new HOICaseResponse();
    List<HOICase> actual = target.getHoiCases();
    List<HOICase> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void setHoiCases_Args__List() throws Exception {
    List<HOICase> hoiCases = new ArrayList<HOICase>();
    HOICaseResponse target = new HOICaseResponse();
    target.setHoiCases(hoiCases);
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void hashCode_Args__() throws Exception {
    HOICaseResponse target = new HOICaseResponse();
    int actual = target.hashCode();
    int expected = 630;
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * @throws Exception - Exception
   */
  @Test
  public void equals_Args__Object() throws Exception {
    HOICaseResponse target = new HOICaseResponse();
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
