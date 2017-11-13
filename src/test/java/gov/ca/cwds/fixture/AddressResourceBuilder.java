package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * 
 * @author CWDS API Team
 */
public class AddressResourceBuilder {

  String legacySourceTable = "";
  String legacyId = "";
  String streetAddress = "123 Main";
  String city = "Sacramento";
  Integer state = 1828; // "CA";
  String zip = "95757";
  Integer type = 32;
  LegacyDescriptor legacyDescriptor;

  /**
   * @param legacySourceTable - legacySourceTable
   * @return the legacySourceTable
   */
  public AddressResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  /**
   * @param legacyId - legacyId
   * @return the legacyId
   */
  public AddressResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  /**
   * @param streetAddress - streetAddress
   * @return the streetAddress
   */
  public AddressResourceBuilder setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }

  /**
   * @param city - city
   * @return the city
   */
  public AddressResourceBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  /**
   * @param state - state
   * @return the state
   */
  public AddressResourceBuilder setState(Integer state) {
    this.state = state;
    return this;
  }

  /**
   * @param zip - zip
   * @return the zip
   */
  public AddressResourceBuilder setZip(String zip) {
    this.zip = zip;
    return this;
  }

  /**
   * @param type - type
   * @return the type
   */
  public AddressResourceBuilder setType(Integer type) {
    this.type = type;
    return this;
  }

  /**
   * @param legacyDescriptor common legacy descriptor
   * @return the legacyDescriptor common legacy descriptor
   */
  public AddressResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }

  /**
   * @return the Address
   */
  public gov.ca.cwds.rest.api.domain.Address createAddress() {
    return new gov.ca.cwds.rest.api.domain.Address(legacySourceTable, legacyId, streetAddress, city,
        state, zip, type, legacyDescriptor);
  }
}
