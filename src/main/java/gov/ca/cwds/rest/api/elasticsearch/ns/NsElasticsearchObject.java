package gov.ca.cwds.rest.api.elasticsearch.ns;

import gov.ca.cwds.rest.api.domain.DomainObject;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * Base class of objects in the elasticsearch layer.
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
   * @param updatedAt The time of last update of this object
   */
  protected NsElasticsearchObject(String updatedAt) {
    this.updated_at = updatedAt;
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
