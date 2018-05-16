package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

public class ScreeningRelationship extends ReportingDomain implements Request {
  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Screening Relationship Id",
      example = "12345")
  private String id;

  @JsonProperty("client_id")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The Id for the the Primary client this relationship refers to. Generated on create",
      example = "ZXY123")
  private String clientId;

  @JsonProperty("relative_id")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The Id for the the primary client's relative", example = "ABC987")
  private String relativeId;

  @JsonProperty("relationship_type")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The relationship type code", example = "190")
  private int relationshipType;

  @JsonProperty("created_at")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The relationship type code. Genearted on create", example = "190")
  private Date createdAt;

  @JsonProperty("updated_at")
  @ApiModelProperty(required = true, readOnly = false,
      value = "The relationship type code. Genearted on create, and updated when modified",
      example = "190")
  private Date updatedAt;

  public ScreeningRelationship() { }

  public ScreeningRelationship(String id, String personId, String relationId, int relationshipType) {
    this(id, personId,relationId, relationshipType, new Date(), null);
  }
  public ScreeningRelationship(String id, String personId, String relationId, int relationshipType, Date created_at, Date updated_at) {
    this.id = id;
    this.clientId = personId;
    this.relativeId = relationId;
    this.relationshipType = relationshipType;
    this.createdAt = created_at;
    this.updatedAt = updated_at;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String person) {
    this.clientId = person;
  }

  public String getRelativeId() {
    return relativeId;
  }

  public void setRelativeId(String relativeId) {
    this.relativeId = relativeId;
  }

  public int getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(int relationshipType) {
    this.relationshipType = relationshipType;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\nScreeningRelationship{");
    builder.append("\n\tid:");
    builder.append(id);
    builder.append(",\n");
    builder.append("\tclientId:");
    builder.append(clientId);
    builder.append(",\n");
    builder.append("\trelativeId:");
    builder.append(relativeId);
    builder.append(",\n");
    builder.append("\trelationshipType:");
    builder.append(relationshipType);
    builder.append(",\n");
    builder.append("\tcreatedAt:");
    builder.append(createdAt);
    builder.append(",\n");
    builder.append("\tupdatedAt:");
    builder.append(updatedAt);
    builder.append(",\n");
    builder.append('}');
    return builder.toString();
  }
}
