package gov.ca.cwds.rest.api.domain.investigation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.fixture.investigation.AllegationEntityBuilder;
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
  private static final int PRIME = 31;

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

  @Override
  public int hashCode() {
    int result = 1;
    result = PRIME * result + ((allegations == null) ? 0 : allegations.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AllegationList other = (AllegationList) obj;
    if (allegations == null) {
      if (other.allegations != null)
        return false;
    } else if (!allegations.equals(other.allegations))
      return false;
    return true;
  }

  public static void main(String[] args) throws IOException {
    ObjectMapper mapper = ObjectMapperUtils.createObjectMapper();

    Set<Allegation> allegations = new HashSet<>();
    Allegation alg = new AllegationEntityBuilder().build();
    allegations.add(alg);

    AllegationList list = new AllegationList(allegations);

    String serialized = mapper.writeValueAsString(list);
    System.out.println(serialized);
  }

}
