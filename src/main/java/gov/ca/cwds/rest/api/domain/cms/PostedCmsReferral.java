package gov.ca.cwds.rest.api.domain.cms;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Logical representation of a Referral
 * 
 * @author CWDS API Team
 */
public class PostedCmsReferral extends DomainObject implements Response {
  private Referral referral;
  private Allegation allegation;
  private CrossReport crossReport;
  private ReferralClient referralClient;
  private Reporter reporter;

  public PostedCmsReferral(PostedReferral referral, PostedAllegation allegation,
      CrossReport crossReport, ReferralClient referralClient, PostedReporter reporter) {
    super();
    this.referral = referral;
    this.allegation = allegation;
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
   * @return the allegation
   */
  public Allegation getAllegation() {
    return allegation;
  }

  /**
   * @return the crossReport
   */
  public CrossReport getCrossReport() {
    return crossReport;
  }

  /**
   * @return the referralClient
   */
  public ReferralClient getReferralClient() {
    return referralClient;
  }

  /**
   * @return the reporter
   */
  public Reporter getReporter() {
    return reporter;
  }
}
