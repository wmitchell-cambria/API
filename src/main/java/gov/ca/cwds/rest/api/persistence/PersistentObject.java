package gov.ca.cwds.rest.api.persistence;

import java.io.Serializable;

public interface PersistentObject {

  /**
   * @return the primaryKey
   */
  public Serializable getPrimaryKey();
}
