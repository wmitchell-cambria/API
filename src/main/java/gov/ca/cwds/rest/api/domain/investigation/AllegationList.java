package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * Container of {@link Allegation} objects, represented as an unnamed array.
 *
 * Jackson best practice: annotation, {@code @JsonValue}, on getter {@link #getAllegations()},
 * serializes this object as an unnamed array of Allegation objects.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AllegationList extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  private Set<Allegation> allegations;

  /**
   * Default constructor
   */
  public AllegationList() {
    super();
  }

  /**
   * @param allegations - allegations of an investigation
   */
  public AllegationList(Set<Allegation> allegations) {
    super();
    this.allegations = allegations;
  }

  /**
   * Jackson best practice: annotation, {@code @JsonValue}, on this getter tells Jackson to
   * serialize this object as an unnamed array of Allegation objects.
   * 
   * @return - allegations of an investigation
   */
  @JsonValue
  public Set<Allegation> getAllegations() {
    return allegations;
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
