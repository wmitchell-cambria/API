package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import io.dropwizard.jackson.JsonSnakeCase;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonSnakeCase
public class HOIScreeningRequest implements Request {

  @JsonProperty("client_ids")
  private Set<String> clientIds;

  public HOIScreeningRequest() {
    // default
  }

  public Set<String> getClientIds() {
    return clientIds;
  }

  public void setClientIds(Set<String> clientIds) {
    this.clientIds = clientIds;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
