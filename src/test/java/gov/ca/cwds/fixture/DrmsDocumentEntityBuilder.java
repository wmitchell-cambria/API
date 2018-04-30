package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.DrmsDocument;

public class DrmsDocumentEntityBuilder {
  private String id = "2";
  private Date creationTimeStamp = new Date();
  private String drmsDocumentTemplateId = "DUMMY";
  private String fingerprintStaffPerson = "q1p";
  private String staffPersonId = "q1p";
  private String handleName = "DUMMY";

  /**
   *
   * @param id key
   * @return The Builder
   */
  public DrmsDocumentEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   *
   * @param creationTimeStamp record creation moment
   * @return The Builder
   */
  public DrmsDocumentEntityBuilder setCreationTimeStamp(Date creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  /**
   *
   * @param drmsDocumentTemplateId primary key
   * @return The Builder
   */
  public DrmsDocumentEntityBuilder setDrmsDocumentTemplateId(
      String drmsDocumentTemplateId) {
    this.drmsDocumentTemplateId = drmsDocumentTemplateId;
    return this;
  }

  /**
   *
   * @param fingerprintStaffPerson primary key
   * @return The Builder
   */
  public DrmsDocumentEntityBuilder setFingerprintStaffPerson(
      String fingerprintStaffPerson) {
    this.fingerprintStaffPerson = fingerprintStaffPerson;
    return this;
  }

  /**
   *
   * @param staffPersonId key
   * @return The Builder
   */
  public DrmsDocumentEntityBuilder setStaffPersonId(String staffPersonId) {
    this.staffPersonId = staffPersonId;
    return this;
  }

  /**
   *
   * @param handleName doc me!
   * @return The Builder
   */
  public DrmsDocumentEntityBuilder setHandleName(String handleName) {
    this.handleName = handleName;
    return this;
  }

  /**
   *
   * @return the built DrmsDocument
   */
  public DrmsDocument build() {
    return new DrmsDocument(id, creationTimeStamp, drmsDocumentTemplateId, fingerprintStaffPerson,
        staffPersonId, handleName);
  }
}
