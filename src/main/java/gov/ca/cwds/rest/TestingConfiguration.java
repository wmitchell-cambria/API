package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class TestingConfiguration {

  @NotNull
  @JsonProperty("path")
  private String configFile;

  public String getConfigFile() {
    return configFile;
  }

  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }

}
