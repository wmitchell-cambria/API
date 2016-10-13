package gov.ca.cwds.rest.resources.serialize;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ApiResponse;
import io.dropwizard.jackson.Jackson;

public class ApiResponseSerializerTest {
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void testSerialization() throws Exception {
    String expected =
        "{\"id\":\"1\",\"Address\":{\"street_address\":\"street\",\"city\":\"city\",\"state\":\"state\",\"zip\":95672}}";
    ApiResponse<Address> addressApiResponse =
        new ApiResponse<Address>("1", new Address("street", "city", "state", 95672));
    String serialized = MAPPER.writeValueAsString(addressApiResponse);
    assertThat(serialized, is(expected));
  }
}
