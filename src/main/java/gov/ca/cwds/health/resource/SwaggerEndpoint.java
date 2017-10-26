package gov.ca.cwds.health.resource;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import gov.ca.cwds.health.resource.PingableServer;
import javax.ws.rs.client.Client;

public class SwaggerEndpoint extends PingableServer {
  @Inject
  SwaggerEndpoint(@Named("swaggerClient") Client client,
      @Named("swagger-url") String url,
      @Named ("http-media") String mediaType){
    super(client, url, mediaType);
  }
}
