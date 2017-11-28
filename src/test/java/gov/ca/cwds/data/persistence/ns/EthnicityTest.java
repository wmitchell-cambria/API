package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class EthnicityTest {

  private Long id = (long) 1234;
  private String ethnicityType = "White";
  private String subEthnicity = "Europe";
  private Set<PersonEthnicity> personEthnicity = new HashSet<>();
  private String staffId = "0XA";

  @Test
  public void testConstructor() throws Exception {
    Ethnicity ethnicity = new Ethnicity(id, ethnicityType, subEthnicity);
    assertThat(ethnicity.getId(), is(equalTo(id)));
    assertThat(ethnicity.getEthnicityType(), is(equalTo(ethnicityType)));
    assertThat(ethnicity.getSubEthnicity(), is(equalTo(subEthnicity)));
    assertThat(ethnicity.getPrimaryKey(), is(equalTo(id)));
    assertThat(ethnicity.getPersonEthnicity(), is(equalTo(personEthnicity)));
  }

  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.Ethnicity domain =
        new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicityType, subEthnicity);
    Ethnicity ethnicity = new Ethnicity(domain, staffId, staffId);
    assertThat(ethnicity.getId(), is(equalTo(null)));
    assertThat(ethnicity.getEthnicityType(), is(equalTo(ethnicityType)));
    assertThat(ethnicity.getSubEthnicity(), is(equalTo(subEthnicity)));
    assertThat(ethnicity.getPrimaryKey(), is(equalTo(null)));
    assertThat(ethnicity.getPersonEthnicity(), is(equalTo(personEthnicity)));
    assertThat(ethnicity.getLastUpdatedId(), is(equalTo(staffId)));
    assertThat(ethnicity.getCreateUserId(), is(equalTo(staffId)));
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Ethnicity.class.newInstance(), is(notNullValue()));
  }
}
