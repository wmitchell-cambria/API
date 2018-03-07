package gov.ca.cwds.rest.api.domain.cms;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.rest.api.domain.ReportingDomain;

/**
 * Logical representation of a Referral
 * 
 * @author CWDS API Team
 */
public class PostedCmsReferral extends ReportingDomain {

  private static final long serialVersionUID = 1L;

  private Referral referral;
  private Set<Client> client;
  private Set<Allegation> allegation;
  private Set<CrossReport> crossReport;
  private Set<ReferralClient> referralClient;
  private Reporter reporter;

  /**
   * @param referral - PostedReferral
   * @param allegation = PostedAllegation
   * @param crossReport - CrossReport
   * @param referralClient - ReferralClient
   * @param reporter - PostedReporter
   * @param client - PostedClient
   */
  public PostedCmsReferral(PostedReferral referral, Set<PostedClient> client,
      Set<PostedAllegation> allegation, Set<CrossReport> crossReport,
      Set<ReferralClient> referralClient, PostedReporter reporter) {
    super();
    this.referral = referral;
    this.client = new LinkedHashSet<>();
    this.client.addAll(client);
    this.allegation = new LinkedHashSet<>();
    this.allegation.addAll(allegation);
    
    this.crossReport = crossReport;
    this.referralClient = referralClient;
    this.reporter = reporter;
  }

  /**
   * @return the referral
   */
  public Referral getReferral() {
    return referral;
  }

  /**
   * @return the client
   */
  public Set<Client> getClient() {
    return client;
  }

  /**
   * @return the allegation
   */
  public Set<Allegation> getAllegation() {
    return allegation;
  }

  /**
   * @return the crossReport
   */
  public Set<CrossReport> getCrossReport() {
    return crossReport;
  }

  /**
   * @return the referralClient
   */
  public Set<ReferralClient> getReferralClient() {
    return referralClient;
  }

  /**
   * @return the reporter
   */
  public Reporter getReporter() {
    return reporter;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
