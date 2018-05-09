package gov.ca.cwds.authenticate.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.config.User;
import io.dropwizard.configuration.ConfigurationSourceProvider;

/**
 * This interface is used to implement the {@link ConfigImpl}.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public interface YmlLoader extends ConfigurationSourceProvider {

  /**
   * readConfig.
   * 
   * @return the config values
   */
  public CwdsAuthenticationClientConfig readConfig();

  /**
   * read the yaml values in the List.
   * 
   * @return the yaml list vales
   */
  default Yaml getYaml() {
    Constructor clientConfig = new Constructor(CwdsAuthenticationClientConfig.class);
    TypeDescription configDesc = new TypeDescription(CwdsAuthenticationClientConfig.class);
    configDesc.putListPropertyType("defaultUsers", User.class);
    clientConfig.addTypeDescription(configDesc);
    return new Yaml(clientConfig);
  }
}
