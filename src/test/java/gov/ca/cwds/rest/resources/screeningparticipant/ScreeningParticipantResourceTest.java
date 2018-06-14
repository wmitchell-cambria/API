package gov.ca.cwds.rest.resources.screeningparticipant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;

/**
 * @author CWDS API Team
 *
 */
public class ScreeningParticipantResourceTest {

  ScreeningParticipantResource screeningParticipantResource;
  TypedResourceDelegate<String, ParticipantIntakeApi> typedResourceDelegate;
  ParticipantIntakeApi participantIntakeApi;

  /**
   * 
   */
  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    typedResourceDelegate = mock(TypedResourceDelegate.class);
    screeningParticipantResource = new ScreeningParticipantResource(typedResourceDelegate);
  }

  /**
   * 
   */
  @Test
  public void whenCreateIsInvoledThenWeShouldCallService() {
    screeningParticipantResource.create("12", participantIntakeApi);
    verify(typedResourceDelegate).create(participantIntakeApi);
  }

}
