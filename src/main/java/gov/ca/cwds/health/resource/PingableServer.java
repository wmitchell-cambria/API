package gov.ca.cwds.health.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingableServer implements Pingable {
  private static final Logger LOGGER = LoggerFactory.getLogger(PingableServer.class);

  protected Client client;
  protected String url;
  private String message;
  private String mediaType;

  public PingableServer( Client client, String url, String mediaType) {
    this.client = client;
    this.url = url;
    this.mediaType = mediaType;
  }

  @Override
  public boolean ping() {
    WebTarget webTarget = client.target(url);
    Invocation.Builder invocationBuilder =  webTarget.request(mediaType);
    Response response = invocationBuilder.get();

    boolean ok = false;
    if (response.getStatus() == 200){
      ok = true;
      message = "Status is OK";
    } else {

      LOGGER.warn("Unable to ping resource: url: " + url);
      LOGGER.warn("Received status of: " + response.getStatus());
      message = "Status:" + response.getStatus();
    }
    return ok;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
