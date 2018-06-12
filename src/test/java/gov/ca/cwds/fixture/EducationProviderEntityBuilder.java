package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.EducationProvider;

public class EducationProviderEntityBuilder {

  private String id = "fk12345678";
  private String streetNumber = "streetNumber";
  private String streetName = "streetName";
  private Integer zipNumber = 99999;
  private Short zipSuffixNumber = 0;
  private String cityName = "Sacramento";
  private Short stateCode = 1828;

  public EducationProvider build() {

    EducationProvider educationProvider = new EducationProvider();
    educationProvider.setId(id);
    educationProvider.setStreetNumber(streetNumber);
    educationProvider.setStreetName(streetName);
    educationProvider.setZipNumber(zipNumber);
    educationProvider.setZipSuffixNumber(zipSuffixNumber);
    educationProvider.setCityName(cityName);
    educationProvider.setStateCodeType(stateCode);
    return educationProvider;
  }

  public EducationProviderEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public EducationProviderEntityBuilder setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  public EducationProviderEntityBuilder setStreetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  public EducationProviderEntityBuilder setZipNumber(Integer zipNumber) {
    this.zipNumber = zipNumber;
    return this;
  }

  public EducationProviderEntityBuilder setZipSuffixNumber(Short zipSuffixNumber) {
    this.zipSuffixNumber = zipSuffixNumber;
    return this;
  }

  public EducationProviderEntityBuilder setStateCode(Short stateCode) {
    this.stateCode = stateCode;
    return this;
  }


}
