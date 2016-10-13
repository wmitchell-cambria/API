package gov.ca.cwds.rest.setup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.junit.Before;
import org.junit.Test;

import io.dropwizard.setup.Environment;

public class ApiEnvironmentTest {
  private ApiEnvironment apiEnvironment;
  private Environment environment;
  private Validator validator;

  @Before
  public void setup() {
    environment = mock(Environment.class);
    apiEnvironment = new ApiEnvironment(environment);
  }

  @Test
  public void jersey() {
    apiEnvironment.jersey();
    verify(environment, times(1)).jersey();
  }

  @Test
  public void getHealthCheckExecutorService() {
    apiEnvironment.getHealthCheckExecutorService();
    verify(environment, times(1)).getHealthCheckExecutorService();
  }

  @Test
  public void admin() {
    apiEnvironment.admin();
    verify(environment, times(1)).admin();
  }

  @Test
  public void lifecycle() {
    apiEnvironment.lifecycle();
    verify(environment, times(1)).lifecycle();
  }

  @Test
  public void servlets() {
    apiEnvironment.servlets();
    verify(environment, times(1)).servlets();
  }

  @Test
  public void getObjectMapper() {
    apiEnvironment.getObjectMapper();
    verify(environment, times(1)).getObjectMapper();
  }

  @Test
  public void getName() {
    apiEnvironment.getName();
    verify(environment, times(1)).getName();
  }

  @Test
  public void getValidator() {
    apiEnvironment.getValidator();
    verify(environment, times(1)).getValidator();
  }

  @Test
  public void setValidator() {
    apiEnvironment.setValidator(validator());
    verify(environment, times(1)).setValidator(validator());
  }

  @Test
  public void metrics() {
    apiEnvironment.metrics();
    verify(environment, times(1)).metrics();
  }

  @Test
  public void healthChecks() {
    apiEnvironment.healthChecks();
    verify(environment, times(1)).healthChecks();
  }

  @Test
  public void getApplicationContext() {
    apiEnvironment.getApplicationContext();
    verify(environment, times(1)).getApplicationContext();
  }

  @Test
  public void getJerseyServletContainer() {
    apiEnvironment.getJerseyServletContainer();
    verify(environment, times(1)).getJerseyServletContainer();
  }

  @Test
  public void getAdminContext() {
    apiEnvironment.getAdminContext();
    verify(environment, times(1)).getAdminContext();
  }

  private Validator validator() {
    if (validator == null) {
      validator = new Validator() {

        @Override
        public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName,
            Object value, Class<?>... groups) {
          return null;
        }

        @Override
        public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName,
            Class<?>... groups) {
          return null;
        }

        @Override
        public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
          return null;
        }

        @Override
        public <T> T unwrap(Class<T> type) {
          return null;
        }

        @Override
        public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
          return null;
        }

        @Override
        public ExecutableValidator forExecutables() {
          return null;
        }
      };
    }
    return validator;
  }

}
