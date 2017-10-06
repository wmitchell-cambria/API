package gov.ca.cwds.rest.api.domain.investigation;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ReportingDomain;

/**
 *
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class AllegationList extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  private Set<Allegation> allegations;

  /**
   * empty consturctor
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
   * @return - allegations of an investigation
   */
  public Set<Allegation> getAllegations() {
    return allegations;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((allegations == null) ? 0 : allegations.hashCode());
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

}
