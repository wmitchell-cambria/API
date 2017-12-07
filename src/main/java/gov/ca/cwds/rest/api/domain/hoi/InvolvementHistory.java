package gov.ca.cwds.rest.api.domain.hoi;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import io.dropwizard.jackson.JsonSnakeCase;

/**
 * {@link DomainObject} representing a History Of Involvement
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.NON_EMPTY)
@JsonSnakeCase
@JsonPropertyOrder({"cases", "referrals", "screenings"})
public class InvolvementHistory extends ApiObjectIdentity
    implements ApiTypedIdentifier<String>, Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("cases")
  private Set<HOICase> cases = new HashSet<>();

  @JsonProperty("referrals")
  private Set<HOIReferral> referrals = new HashSet<>();

  @JsonProperty("screenings")
  private Set<HOIScreening> screenings = new HashSet<>();

  /**
   * Constructor
   */
  public InvolvementHistory() {
    super();
  }

  /**
   * Constructor
   * 
   * @param cases the cases
   * @param referrals the referrals
   * @param screenings the screenings
   */
  public InvolvementHistory(@JsonProperty("cases") Set<HOICase> cases,
      @JsonProperty("referrals") Set<HOIReferral> referrals,
      @JsonProperty("screenings") Set<HOIScreening> screenings) {
    super();
    this.cases = cases;
    this.referrals = referrals;
    this.screenings = screenings;
  }

  /**
   * @return the cases
   */
  public Set<HOICase> getCases() {
    return cases;
  }

  /**
   * @return the referrals
   */
  public Set<HOIReferral> getReferrals() {
    return referrals;
  }

  /**
   * @return the screenings
   */
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
  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setId(String id) {
    // TODO Auto-generated method stub

  }

}
