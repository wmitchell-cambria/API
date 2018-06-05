package gov.ca.cwds.rest.services.hoi;

import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import java.util.Collection;
import java.util.Map;

class HOIReferralsData {

  private Collection<ReferralClient> referralClients;
  private Collection<String> referralIds;

  // key: ReferralId, value: Referral
  private Map<String, Referral> referrals;

  HOIReferralsData() {
    // no-op
  }

  Collection<ReferralClient> getReferralClients() {
    return referralClients;
  }

  void setReferralClients(Collection<ReferralClient> referralClients) {
    this.referralClients = referralClients;
  }

  Collection<String> getReferralIds() {
    return referralIds;
  }

  void setReferralIds(Collection<String> referralIds) {
    this.referralIds = referralIds;
  }

  Map<String, Referral> getReferrals() {
    return referrals;
  }

  void setReferrals(Map<String, Referral> referrals) {
    this.referrals = referrals;
  }
}
