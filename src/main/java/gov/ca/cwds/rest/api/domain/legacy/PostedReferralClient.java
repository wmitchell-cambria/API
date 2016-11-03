package gov.ca.cwds.rest.api.domain.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;

/**
 * {@link Response} adding an id to the {@link ReferralClient}
 * 
 * @author CWDS API Team
 */
public class PostedReferralClient extends ReferralClient {
  @JsonProperty("id")
  private String referralId;

  @JsonProperty("id")
  private String clientId;

  /**
   * Constructor
   * 
   * @param referralClient The persisted referralClient
   */
  public PostedReferralClient(gov.ca.cwds.rest.api.persistence.cms.ReferralClient referralClient) {

    super(referralClient);
    assert (referralClient.getReferralId() != null);
    assert (referralClient.getClientId() != null);

    this.referralId = referralClient.getReferralId();
    this.clientId = referralClient.getClientId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return referralId + clientId;
  }


}
