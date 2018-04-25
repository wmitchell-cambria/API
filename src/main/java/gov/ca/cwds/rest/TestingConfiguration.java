package gov.ca.cwds.rest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TestingConfiguration.
 * 
 * @author CWDS API Team
 *
 */
public class TestingConfiguration {

  @NotNull
  @JsonProperty("path")
  private String configFile;

  /**
   * getConfigFile.
   * 
   * @return the configFile
   */
  public String getConfigFile() {
    return configFile;
  }

  /**
   * setConfigFile.
   * 
   * @param configFile - configFile
   */
  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }

}
