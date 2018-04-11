package gov.ca.cwds.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import gov.ca.cwds.config.TestConfig;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.authenticate.AuthenticationUtils;
import gov.ca.cwds.rest.authenticate.UserGroup;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jackson.Jackson;

public class FunctionalTest implements ConfigurationSourceProvider {

  public static final String API_CONF_YML_PATH = "config/api.yml";
  public static final String FILE_PATH_KEY = "TEST_FILE_PATH";
  TestConfig testConfig;
  String url;
  String token;

  @Before
  public void init() throws FileNotFoundException {
    Yaml yaml = new Yaml();
    testConfig = loadTestConfiguratin(yaml);
    url = testConfig.getTestUrl().getBaseUrl();
    token = login();
  }

  private String login() {
    AuthenticationUtils authentication = new AuthenticationUtils(null);
    return authentication.getToken(UserGroup.SOCIAL_WORKER);
  }

  @Override
  public InputStream open(String path) throws IOException {
    final File file = new File(path);
    if (!file.exists()) {
      throw new FileNotFoundException("File " + file + " not found");
    }

    return new FileInputStream(file);
  }

  protected String getResourceUrlFor(String resource) {
    return url + resource;
  }

  private TestConfig loadTestConfiguratin(Yaml yaml) {
    String testConfFilePath = retrieveTestConfFilePath();
    TestConfig testConf = loadTestConfigurations(yaml, testConfFilePath);

    return testConf;
  }

  private TestConfig loadTestConfigurations(Yaml yaml, String testConfFilePath) {
    TestConfig testConf = null;
    try (InputStream ymlTestingSourceProvider =
        new SubstitutingSourceProvider(this, new EnvironmentVariableSubstitutor(false))
            .open(testConfFilePath);) {
      testConf = yaml.loadAs(ymlTestingSourceProvider, TestConfig.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return testConf;
  }

  private String retrieveTestConfFilePath() {
    String testConfFilePath = "";
    try (InputStream ymlAppSourceProvider =
        new SubstitutingSourceProvider(this, new EnvironmentVariableSubstitutor(false))
            .open(API_CONF_YML_PATH)) {
      ObjectMapper mapper = Jackson.newObjectMapper(new YAMLFactory());
      ApiConfiguration appConf = mapper.readValue(ymlAppSourceProvider, ApiConfiguration.class);

      String defaultLocation = appConf.getTestConfig().getConfigFile();
      testConfFilePath = getApiYmlPath(defaultLocation);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return testConfFilePath;
  }

  private String getApiYmlPath(String defaultPath) {
    String filepath = retreiveTestConfigFromEnvVar();
    return filepath.isEmpty() ? defaultPath : filepath;
  }

  private String retreiveTestConfigFromEnvVar() {
    String filePath = System.getenv().get(FILE_PATH_KEY);
    return filePath != null ? filePath : "";
  }
}
