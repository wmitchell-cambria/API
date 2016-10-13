package gov.ca.cwds.rest.resources.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.junit.Before;
import org.junit.Test;

public class EmptyBodyFeatureTest {

  private ResourceInfo resourceInfoWithAnnotatedMethod = mock(ResourceInfo.class);
  private ResourceInfo resourceInfoWithoutAnnotatedMethod = mock(ResourceInfo.class);
  private Method annotatedMethod;
  private Method notAnnotatedMethod;
  private FeatureContext context = mock(FeatureContext.class);

  @Before
  public void setup() throws Exception {
    annotatedMethod = EmptyBodyFeatureTest.class.getMethod("annotatedMethod");
    notAnnotatedMethod = EmptyBodyFeatureTest.class.getMethod("nonAnnotatedMethod");

    when(resourceInfoWithAnnotatedMethod.getResourceMethod()).thenReturn(annotatedMethod);
    when(resourceInfoWithoutAnnotatedMethod.getResourceMethod()).thenReturn(notAnnotatedMethod);
  }

  @Test
  public void configureRegistersFilterWhenAnnotationExists() throws Exception {
    EmptyBodyFeature emptyBodyFeature = new EmptyBodyFeature();
    emptyBodyFeature.configure(resourceInfoWithAnnotatedMethod, context);
    verify(context, times(1)).register(EmptyBodyFilter.class);
  }

  @Test
  public void configureDoesNotRegisterFilterWhenAnnotationDoesNotExist() throws Exception {
    EmptyBodyFeature emptyBodyFeature = new EmptyBodyFeature();
    emptyBodyFeature.configure(resourceInfoWithoutAnnotatedMethod, context);
    verify(context, times(0)).register(EmptyBodyFilter.class);
  }

  @EmptyBody
  public void annotatedMethod() {

  }

  public void nonAnnotatedMethod() {

  }

}
