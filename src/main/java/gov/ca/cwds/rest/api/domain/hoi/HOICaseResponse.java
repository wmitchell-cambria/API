package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.RequestContextAwareResponse;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;

/**
 * A domain API {@link Request} for {@link HOICase}
 * 
 * @author CWDS API Team
 */
@ApiModel
@JsonSnakeCase
public class HOICaseResponse implements RequestContextAwareResponse {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonUnwrapped
  private List<HOICase> hoiCases = new ArrayList<>();

  /**
   * Disallow use of default constructor.
   */
  public HOICaseResponse() {
    // no -opt
  }

  /**
   * @param hoiCases - hoiCases
   */
  public HOICaseResponse(List<HOICase> hoiCases) {
    super();
    this.hoiCases = hoiCases;
  }

  /**
   * 
   * @return the array of {@link HOICase}
   */
  @JsonValue
  public List<HOICase> getHoiCases() {
    return hoiCases;
  }

  /**
   * Setter for array of {@link HOICase hoiCases}.
   * 
   * @param hoiCases - hoiCases
   */
  public void setHoiCases(List<HOICase> hoiCases) {
    this.hoiCases = hoiCases;
  }

  /**
   * Add the {@link HOICase} to the {@link List}
   * 
   * @param hoiCase - hoiCase
   */
  public void addHoiCase(HOICase hoiCase) {
    if (hoiCase != null) {
      hoiCases.add(hoiCase);
    }
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
