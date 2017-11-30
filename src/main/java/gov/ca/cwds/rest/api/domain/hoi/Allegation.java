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
public class Allegation extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("description")
  private String description;

  @JsonProperty("disposition")
  private SystemCodeDescriptor disposition;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  @JsonProperty("victim")
  private Person victim;

  @JsonProperty("perpetrator")
  private Person perpetrator;

  /**
   * No-argument constructor
   */
  public Allegation() {
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

  public Person getVictim() {
    return victim;
  }

  public void setVictim(Person victim) {
    this.victim = victim;
  }

  public Person getPerpetrator() {
    return perpetrator;
  }

  public void setPerpetrator(Person perpetrator) {
    this.perpetrator = perpetrator;
  }
}
