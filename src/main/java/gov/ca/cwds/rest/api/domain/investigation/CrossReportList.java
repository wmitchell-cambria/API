package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * Container of {@link CrossReport} objects, represented as an unnamed array.
 *
 * Jackson best practice: annotation, {@code @JsonValue}, on getter {@link #getCrossReports()},
 * serializes this object as an unnamed array of CrossReport objects.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CrossReportList extends ReportingDomain implements Request, Response {
  private static final long serialVersionUID = 1L;

  private Set<CrossReport> crossReports;

  /**
   * Default constructor
   */
  public CrossReportList() {
    super();
  }

  /**
   * @param crossReports - array of CrossReport objects
   */
  public CrossReportList(Set<CrossReport> crossReports) {
    super();
    this.crossReports = crossReports;
  }

  /**
   * 
   * @return - cross reports of an investigation
   */
  public Set<CrossReport> getCrossReports() {
    return this.crossReports;
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
