package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

public class AddressIntakeApiResourceBuilder {

  String id = "23456";
  String legacySourceTable = "ADDRESS_T";
  String legacyId = "1234567ABC";
  String streetAddress = "742 Evergreen Terrace";
  String city = "Springfield";
  String state = "CA";
  String zip = "93838";
  String type = "school";
  LegacyDescriptor legacyDescriptor;

  public AddressIntakeApi build() {
    return new AddressIntakeApi(legacySourceTable, legacyId, streetAddress, city, state, zip, type,
        legacyDescriptor);

  }

  public AddressIntakeApiResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public AddressIntakeApiResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public AddressIntakeApiResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public AddressIntakeApiResourceBuilder setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }

  public AddressIntakeApiResourceBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public AddressIntakeApiResourceBuilder setState(String state) {
    this.state = state;
    return this;
  }

  public AddressIntakeApiResourceBuilder setZip(String zip) {
    this.zip = zip;
    return this;
  }

  public AddressIntakeApiResourceBuilder setType(String type) {
    this.type = type;
    return this;
  }

  public AddressIntakeApiResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }
}