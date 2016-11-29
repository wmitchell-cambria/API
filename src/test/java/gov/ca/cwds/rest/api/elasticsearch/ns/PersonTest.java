package gov.ca.cwds.rest.api.elasticsearch.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class PersonTest {

  private String date_of_birth = "1973-11-22";
  private String first_name = "John";
  private String last_name = "Doe";
  private String gender = "Male";
  private String ssn = "123456789";

  private String lastUpdatedId = "z";


  /*
   * Constructor test
   */
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(Person.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void domainPersonConstructorTest() throws Exception {
    gov.ca.cwds.rest.api.domain.Person domain =
        new gov.ca.cwds.rest.api.domain.Person(first_name, last_name, gender, date_of_birth, ssn,
            null);

    gov.ca.cwds.rest.api.elasticsearch.ns.Person indexed =
        new gov.ca.cwds.rest.api.elasticsearch.ns.Person(domain, "z");
    assertThat(indexed.getFirst_name(), is(equalTo(first_name)));
    assertThat(indexed.getLast_name(), is(equalTo(last_name)));
    assertThat(indexed.getGender(), is(equalTo(gender)));
    assertThat(indexed.getDate_of_birth(), is(equalTo(date_of_birth)));
    assertThat(indexed.getSsn(), is(equalTo(ssn)));
    assertThat(indexed.getUpdated_at(), is(equalTo(lastUpdatedId)));
  }
}
