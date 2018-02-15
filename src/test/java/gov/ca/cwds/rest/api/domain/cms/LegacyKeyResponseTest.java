package gov.ca.cwds.rest.api.domain.cms;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LegacyKeyResponseTest {
  private String uiIdentifier = "111-222-333-444456";
  private String newUiIdentifier = "222-333-444-555567";

  @Test
  public void shouldConstructObject() throws Exception {
	LegacyKeyResponse legacyKeyResponse = new LegacyKeyResponse(uiIdentifier);
	assertThat(legacyKeyResponse.getUiIdentifier(), is(equalTo(uiIdentifier)));
  }
  
  @Test
  public void shouldChangeValueUsingSetters() throws Exception {
	LegacyKeyResponse legacyKeyResponse = new LegacyKeyResponse(uiIdentifier);
	legacyKeyResponse.setUiIdentifier(newUiIdentifier);
	assertThat(legacyKeyResponse.getUiIdentifier(), is(equalTo(newUiIdentifier)));
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	LegacyKeyResponse legacyKeyResponse = new LegacyKeyResponse(uiIdentifier);	  
    assertTrue(legacyKeyResponse.equals(legacyKeyResponse));
  }
  
}
