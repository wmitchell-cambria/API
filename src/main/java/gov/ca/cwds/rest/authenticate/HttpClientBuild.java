package gov.ca.cwds.rest.authenticate;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class HttpClientBuild {

  RequestConfig requestConfig =
      RequestConfig.custom().setRedirectsEnabled(false).setCookieSpec(CookieSpecs.STANDARD).build();

  HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
  HttpClientContext httpContext = HttpClientContext.create();

  ArrayList<NameValuePair> postParams = new ArrayList<>();

}
