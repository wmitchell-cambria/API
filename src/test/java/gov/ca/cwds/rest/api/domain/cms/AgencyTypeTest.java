package gov.ca.cwds.rest.api.domain.cms;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class AgencyTypeTest {

  @Test
  public void shouldReturnNullWhenNotValidId() {
	Short invalidAgencyId = Short.valueOf("777");
	
	assertThat(AgencyType.getById(invalidAgencyId), is(equalTo(null)));
  }
  
  @Test
  public void shouldReturnNullWhenIdIsNull() {
	Short invalidAgencyId = null;
	assertThat(AgencyType.getById(invalidAgencyId), is(equalTo(null)));
	
  }
  
  @Test
  public void shouldReturnDescription() {
	assertThat(AgencyType.getByName("PUBLIC_DEFENDER_OFFICE").getDescription(), is(equalTo("Public Defender Office")));
  }
  
}
