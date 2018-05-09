package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * {@link DomainObject} representing an HOIScreening List response
 *
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HOIScreeningResponse extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonUnwrapped
  private Set<HOIScreening> screenings;

  public HOIScreeningResponse(Set<HOIScreening> screenings) {
    this.screenings = screenings;
  }

  @JsonValue
  public Set<HOIScreening> getScreenings() {
    return screenings;
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
