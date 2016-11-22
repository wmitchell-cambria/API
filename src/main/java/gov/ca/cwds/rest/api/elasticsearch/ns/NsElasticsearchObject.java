package gov.ca.cwds.rest.api.elasticsearch.ns;

import gov.ca.cwds.rest.api.domain.DomainObject;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * Base class for objects in the elasticsearch layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class NsElasticsearchObject {

  private String updated_at;
  private String created_at;

  /**
   * Default constructor
   */
  protected NsElasticsearchObject() {

  }

  /**
   * Constructor
   * 
   * @param lastUpdatedId the id of the last person to update this object
   */
  protected NsElasticsearchObject(String lastUpdatedId) {
    this.updated_at = lastUpdatedId;
    this.created_at = DomainObject.cookTimestamp(new Date()).toString();
  }

  /**
   * @return the updated_at
   */
  public String getUpdated_at() {
    return updated_at;
  }

  /**
   * @return the created_at
   */
  public String getCreated_at() {
    return created_at;
  }

}
