package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * Screening allegation.
 * 
 * @author CWDS API Team
 */
public class AllegationHOI extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("description")
  private String description;

  @JsonProperty("disposition")
  private SystemCodeDescriptor disposition;

  @JsonProperty("victim")
  private Victim victim;

  @JsonProperty("perpetrator")
  private Perpetrator perpetrator;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public AllegationHOI() {
    // No-argument constructor
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SystemCodeDescriptor getDisposition() {
    return disposition;
  }

  public void setDisposition(SystemCodeDescriptor disposition) {
    this.disposition = disposition;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public Victim getVictim() {
    return victim;
  }

  public void setVictim(Victim victim) {
    this.victim = victim;
  }

  public Perpetrator getPerpetrator() {
    return perpetrator;
  }

  public void setPerpetrator(Perpetrator perpetrator) {
    this.perpetrator = perpetrator;
  }
}
