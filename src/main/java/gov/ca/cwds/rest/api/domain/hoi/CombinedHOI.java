package gov.ca.cwds.rest.api.domain.hoi;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"cases", "referrals", "screenings"})
public class CombinedHOI extends ApiObjectIdentity implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("cases")
  private Set<CaseHOI> cases = new HashSet<>();

  @JsonProperty("referrals")
  private Set<ReferralHOI> referrals = new HashSet<>();

  @JsonProperty("screenings")
  private Set<ScreeningHOI> screenings = new HashSet<>();

  /**
   * Constructor
   */
  public CombinedHOI() {
    super();
  }

  /**
   * Constructor
   * 
   * @param cases the cases
   * @param referrals the referrals
   * @param screenings the screenings
   */
  public CombinedHOI(@JsonProperty("cases") Set<CaseHOI> cases,
      @JsonProperty("referrals") Set<ReferralHOI> referrals,
      @JsonProperty("screenings") Set<ScreeningHOI> screenings) {
    super();
    this.cases = cases;
    this.referrals = referrals;
    this.screenings = screenings;
  }

  /**
   * @return the cases
   */
  public Set<CaseHOI> getCases() {
    return cases;
  }

  /**
   * @return the referrals
   */
  public Set<ReferralHOI> getReferrals() {
    return referrals;
  }

  /**
   * @return the screenings
   */
  public Set<ScreeningHOI> getScreenings() {
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

}
