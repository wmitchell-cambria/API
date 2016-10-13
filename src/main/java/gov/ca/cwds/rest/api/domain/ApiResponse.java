package gov.ca.cwds.rest.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.ca.cwds.rest.resources.serialize.ApiResponseSerializer;

@JsonSerialize(using = ApiResponseSerializer.class)
public class ApiResponse<T extends DomainObject> {
  private String id;
  private T object;

  public ApiResponse(String id, T domainObject) {
    super();
    this.id = id;
    this.object = domainObject;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the object
   */
  public T getObject() {
    return object;
  }

}
