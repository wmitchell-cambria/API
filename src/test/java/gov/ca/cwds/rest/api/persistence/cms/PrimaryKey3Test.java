package gov.ca.cwds.rest.api.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class PrimaryKey3Test {

  private String keySegment1 = "ABC1234567";
  private String keySegment2 = "1234567ABC";
  private String keySegment3 = "2345678BCD";
  private String toString = "keySegmentOne=" + keySegment1.trim() + ",keySegmentTwo="
      + keySegment2.trim() + ",keySegmentThree=" + keySegment3.trim();

  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(PrimaryKey3.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void persistentConstructorTest() throws Exception {
    PrimaryKey3 pk = new PrimaryKey3(keySegment1, keySegment2, keySegment3);
    assertThat(pk.getKeySegmentOne(), is(equalTo(keySegment1)));
    assertThat(pk.getKeySegmentTwo(), is(equalTo(keySegment2)));
    assertThat(pk.getKeySegmentThree(), is(equalTo(keySegment3)));

  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(PrimaryKey3.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void toStringTest() throws Exception {
    PrimaryKey3 pk = new PrimaryKey3(keySegment1, keySegment2, keySegment3);
    assertThat(pk.toString(), is(equalTo(toString)));
  }

}
