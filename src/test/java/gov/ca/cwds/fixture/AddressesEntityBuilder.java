package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * @author Intake Team 4
 *
 */
@SuppressWarnings("javadoc")
public class AddressesEntityBuilder {
  String id = "23456";
  String legacySourceTable = "ADDRESS_T";
  String legacyId = "1234567ABC";
  String streetAddress = "742 Evergreen Terrace";
  String city = "Springfield";
  String state = "CA";
  String zip = "93838";
  String type = "school";

  public Addresses build() {
    return new Addresses(id,streetAddress, city, state, zip, type, legacyId, legacySourceTable);
  }

  public String getId() {
    return id;
  }

  public AddressesEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public AddressesEntityBuilder setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }

  public String getState() {
    return state;
  }

  public AddressesEntityBuilder setState(String state) {
    this.state = state;
    return this;
  }

  public String getType() {
    return type;
  }

  public AddressesEntityBuilder setType(String type) {
    this.type = type;
    return this;
  }

  public String getLegacyId() {
    return legacyId;
  }

  public AddressesEntityBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  public AddressesEntityBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public String getCity() {
    return city;
  }

  public AddressesEntityBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public String getZip() {
    return zip;
  }

  public AddressesEntityBuilder setZip(String zip) {
    this.zip = zip;
    return this;
  }

}
