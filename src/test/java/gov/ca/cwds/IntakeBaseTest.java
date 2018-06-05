package gov.ca.cwds;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.rest.IntegratedResourceTestSuiteIT;
import gov.ca.cwds.test.support.DatabaseHelper;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import liquibase.exception.LiquibaseException;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.test.support.BaseApiTest;
import gov.ca.cwds.test.support.BaseDropwizardApplication;

/**
 * CWDS API Team
 */
public abstract class IntakeBaseTest extends BaseApiTest<ApiConfiguration> {

  @ClassRule
  public static final BaseDropwizardApplication<ApiConfiguration> application =
          IntegratedResourceTestSuiteIT.application;

  protected BaseDropwizardApplication<ApiConfiguration> getApplication() {
    return application;
  }

  protected ObjectMapper objectMapper = Jackson.newObjectMapper();

  @BeforeClass
  public static void beforeClass() throws LiquibaseException {
    ApiConfiguration configuration = application.getConfiguration();

    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getNsDataSourceFactory().getUrl(),
        configuration.getNsDataSourceFactory().getUser(),
        configuration.getNsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_intake_ns_database_master.xml");

    new gov.ca.cwds.test.support.DatabaseHelper(configuration.getCmsDataSourceFactory().getUrl(),
        configuration.getCmsDataSourceFactory().getUser(),
        configuration.getCmsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_cwsint_database_master.xml");

    new DatabaseHelper(configuration.getRsDataSourceFactory().getUrl(),
        configuration.getRsDataSourceFactory().getUser(),
        configuration.getRsDataSourceFactory().getPassword())
        .runScript("liquibase/api/api_cwsrs_database_master.xml");
  }

  protected String doGetCall(String pathInfo) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected int doAuthorizedGetCallStatus(String tokenFilePath, String pathInfo) throws IOException {
    WebTarget target = clientTestRule.withSecurityToken(tokenFilePath).target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).get();
    return response.getStatus();
  }

  protected String doPostCall(String pathInfo, String request) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

  protected String doPutCall(String pathInfo, String request) throws IOException {
    WebTarget target = clientTestRule.target(pathInfo);
    Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(request,
        MediaType.APPLICATION_JSON_TYPE));
    return IOUtils.toString((InputStream) response.getEntity(), StandardCharsets.UTF_8);
  }

}
