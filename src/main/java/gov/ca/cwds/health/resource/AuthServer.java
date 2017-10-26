package gov.ca.cwds.health.resource;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javax.ws.rs.client.Client;

public class AuthServer extends PingableServer {
  @Inject
  AuthServer(@Named("authClient") Client client,
      @Named("auth-url") String url,
      @Named ("json-media") String mediaType){
    super(client, url, mediaType);
  }
}
