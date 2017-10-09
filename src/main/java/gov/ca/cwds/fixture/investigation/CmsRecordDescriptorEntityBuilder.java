package gov.ca.cwds.fixture.investigation;

import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

@SuppressWarnings("javadoc")
public class CmsRecordDescriptorEntityBuilder {
  protected String id = "1234567ABC";
  protected String uiId = "111122223333444455";
  protected String tableName = "REFERL_T";
  protected String tableDescription = "REFERRAL";

  public CmsRecordDescriptor build() {
    return new CmsRecordDescriptor(id, uiId, tableName, tableDescription);
  }

  public CmsRecordDescriptorEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setUiId(String uiId) {
    this.uiId = uiId;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setTableName(String tableName) {
    this.tableName = tableName;
    return this;
  }

  public CmsRecordDescriptorEntityBuilder setTableDescription(String tableDescription) {
    this.tableDescription = tableDescription;
    return this;
  }

}
