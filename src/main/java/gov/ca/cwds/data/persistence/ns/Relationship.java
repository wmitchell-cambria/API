package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "relationships")
public class Relationship implements PersistentObject {
  @Id
  @GenericGenerator(name = "relationships_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator", parameters = {
      @org.hibernate.annotations.Parameter(name = "sequence_name", value = "relationships_id_seq")})
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relationships_id")

  @Column(name = "id")
  private String id;

  @Column(name = "client")
  private String clientId;

  @Column(name = "relative")
  private String relativeId;

  @Column(name = "relation")
  private int relationshipType;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  public Relationship() { }

  public Relationship(String id, String clientId, String relativeId, int relationshipType, Date created_at, Date updated_at ) {
    this.id = id;
    this.clientId = clientId;
    this.relativeId = relativeId;
    this.relationshipType = relationshipType;
    this.createdAt = created_at;
    this.updatedAt = updated_at;
  }
  @Override
  public Serializable getPrimaryKey() {
    return null;
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

  public void setClientId(String clientId) {
    this.clientId = clientId;
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
}
