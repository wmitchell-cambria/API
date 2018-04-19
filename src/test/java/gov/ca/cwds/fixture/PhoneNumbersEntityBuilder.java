package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;

/**
 * @author Intake Team 4
 *
 */
@SuppressWarnings("javadoc")
public class PhoneNumbersEntityBuilder {
  String id = "123";
  String number = "6513218765";
  String type = "Cell";

  public PhoneNumbers build() {
    return new PhoneNumbers(id, number, type);
  }

  public String getId() {
    return id;
  }

  public PhoneNumbersEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getNumber() {
    return number;
  }

  public PhoneNumbersEntityBuilder setNumber(String number) {
    this.number = number;
    return this;
  }

  public String getType() {
    return type;
  }

  public PhoneNumbersEntityBuilder setType(String type) {
    this.type = type;
    return this;
  }


}
