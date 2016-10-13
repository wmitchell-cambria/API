package gov.ca.cwds.rest.resources.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Response filter which empties the response body on successful requests.
 * 
 * @author CWDS API Team
 */
public class EmptyBodyFilter implements ContainerResponseFilter {

  /*
   * (non-Javadoc)
   * 
   * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.
   * ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
   */
  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    if (responseContext.getStatus() >= 200 && responseContext.getStatus() < 300) {
      responseContext.setEntity(null);
    }
  }


}
