package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.ClientRelationship;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ClientRelationshipEntityBuilder {

  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private Date endDate;
  private Date startDate;
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";

  private String lastUpdatedId = "0XA";

  public ClientRelationship build() {
    return new ClientRelationship(absentParentCode, clientRelationshipType, endDate,
        secondaryClientId, primaryClientId, id, sameHomeCode, startDate);
  }

  public ClientRelationshipEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ClientRelationshipEntityBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public String getSecondaryClientId() {
    return secondaryClientId;
  }

  public ClientRelationshipEntityBuilder setSecondaryClientId(String secondaryClientId) {
    this.secondaryClientId = secondaryClientId;
    return this;
  }

  public String getPrimaryClientId() {
    return primaryClientId;
  }

  public ClientRelationshipEntityBuilder setPrimaryClientId(String primaryClientId) {
    this.primaryClientId = primaryClientId;
    return this;
  }

  public String getSameHomeCode() {
    return sameHomeCode;
  }

  public ClientRelationshipEntityBuilder setSameHomeCode(String sameHomeCode) {
    this.sameHomeCode = sameHomeCode;
    return this;
  }

  public ClientRelationshipEntityBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public ClientRelationshipEntityBuilder setClientRelationshipType(Short clientRelationshipType) {
    this.clientRelationshipType = clientRelationshipType;
    return this;
  }

  public ClientRelationshipEntityBuilder setAbsentParentCode(String absentParentCode) {
    this.absentParentCode = absentParentCode;
    return this;
  }

  public ClientRelationshipEntityBuilder setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    return this;
  }

}

