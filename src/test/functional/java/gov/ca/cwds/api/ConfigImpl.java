package gov.ca.cwds.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import gov.ca.cwds.authenticate.config.ConfigReader;
import gov.ca.cwds.authenticate.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jackson.Jackson;

/**
 * This class will overwrite the yaml file in api-core and read the API yaml file by implements
 * {@link ConfigReader}.
 * 
 * @author CWDS API Team
 *
 */
public class ConfigImpl implements ConfigReader {

  public static final String API_CONF_YML_PATH = "config/api.yml";
  public static final String FILE_PATH_KEY = "TEST_FILE_PATH";

  @Override
  public InputStream open(String path) throws IOException {
    final File file = new File(path);
    if (!file.exists()) {
      throw new FileNotFoundException("File " + file + " not found");
    }

    return new FileInputStream(file);
  }

  @Override
  public CwdsAuthenticationClientConfig readConfig() {
    return loadTestConfiguratin();
  }

  private CwdsAuthenticationClientConfig loadTestConfiguratin() {
    Yaml yaml = getYaml();
    String testConfFilePath = retrieveTestConfFilePath();
    CwdsAuthenticationClientConfig testConf = loadTestConfigurations(yaml, testConfFilePath);

    return testConf;
  }

  private CwdsAuthenticationClientConfig loadTestConfigurations(Yaml yaml,
      String testConfFilePath) {
    CwdsAuthenticationClientConfig testConf = null;
    try (InputStream ymlTestingSourceProvider =
        new SubstitutingSourceProvider(this, new EnvironmentVariableSubstitutor(false))
            .open(testConfFilePath);) {
      testConf = yaml.loadAs(ymlTestingSourceProvider, CwdsAuthenticationClientConfig.class);
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
