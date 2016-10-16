package gov.ca.cwds.rest.api;

import java.io.Serializable;

/**
 * {@link Response} wrapping a primary key.
 * 
 * @author CWDS API Team
 */
public class PrimaryKeyResponse implements Response {

  private Serializable primaryKey;

  public PrimaryKeyResponse(Serializable primaryKey) {
    super();
    this.primaryKey = primaryKey;
  }

  /**
   * @return the primaryKey
   */
  public Serializable getPrimaryKey() {
    return primaryKey;
  }
}
