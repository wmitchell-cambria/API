package gov.ca.cwds.rest.filters;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RequestResponseLoggingFilterTest {

  private RequestResponseLoggingFilter filter;

  private HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
  private HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
  private FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
  private FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);


  @Before
  public void setup() throws Exception {
    // filter = new RequestResponseLoggingFilter();
  }

  @Test
  public void testDoFilter() throws Exception {

    // Mockito.when(mockHttpServletRequest.getRequestURI()).thenReturn("/");
    //
    // BufferedReader br = new BufferedReader(new StringReader("test"));
    // Mockito.when(mockHttpServletRequest.getReader()).thenReturn(br);
    //
    // filter.init(mockFilterConfig);
    // filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
    // filter.destroy();
  }

  // @Test(expected = Exception.class)
  // public void testDoFilterException() throws IOException, ServletException {
  //
  // RequestResponseLoggingFilter filter = new RequestResponseLoggingFilter();
  //
  // HttpServletRequest mockReq = new MockHttpServletRequest();
  // HttpServletResponse mockResp = new MockHttpServletResponse();
  // FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
  // FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);
  //
  // filter.init(mockFilterConfig);
  // filter.doFilter(mockReq, mockResp, mockFilterChain);
  // filter.destroy();
  // }
}
