package gov.ca.cwds.health.resource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

public class PingableServerTest {

  String url = "someserver.com";
  String mediaType = "app/json";

  PingableServer pingable;
  Response wsResponse;
  Client wsClient;

  @Before
  public void setup(){
    wsResponse = mock(Response.class);
    when(wsResponse.getStatus()).thenReturn(200);
    WebTarget webtarget = mock(WebTarget.class);
    Builder wsBuilder = mock(Builder.class);
    when(wsBuilder.get()).thenReturn(wsResponse);

    when(webtarget.request(mediaType)).thenReturn(wsBuilder);
    wsClient = mock(Client.class);
    when(wsClient.target(url)).thenReturn(webtarget);

    pingable = new PingableServer(wsClient, url, mediaType);
  }

  @Test
  public void shouldReturnTrueWhenA200StatusIsReceived(){
    assertTrue("Expected a valid ping", pingable.ping());
  }

  @Test
  public void shouldReturnTrueWhenA302StatusIsReceived(){
    when(wsResponse.getStatus()).thenReturn(302);
    assertTrue("Expected a valid ping", pingable.ping());
  }


    @Test
  public void shouldReturnfalseWhenA500StatusIsReceived(){
    when(wsResponse.getStatus()).thenReturn(500);
    PingableServer pingable = new PingableServer(wsClient, url, mediaType);
    assertFalse("Expected a invalid ping", pingable.ping());
  }

  @Test
  public void shouldContainMessageWhenUnsuccessful(){
    int statusCode = 500;
    String message = "Status:" + statusCode;
    when(wsResponse.getStatus()).thenReturn(statusCode);
    PingableServer pingable = new PingableServer(wsClient, url, mediaType);

    pingable.ping();

    assertEquals("Expected error message to contain the status code",
        message, pingable.getMessage());
  }
}