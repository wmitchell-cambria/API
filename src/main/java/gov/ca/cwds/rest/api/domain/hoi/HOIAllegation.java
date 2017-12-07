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
public class HOIAllegation extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("type")
  private SystemCodeDescriptor type;

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
  public HOIAllegation() {
    // No-argument constructor
  }

  /**
   * @param id - id
   * @param type - type
   * @param disposition - disposition
   * @param victim - victim
   * @param perpetrator - perpetrator
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIAllegation(String id, SystemCodeDescriptor type, SystemCodeDescriptor disposition,
      Victim victim, Perpetrator perpetrator, LegacyDescriptor legacyDescriptor) {
    super();
    this.id = id;
    this.type = type;
    this.disposition = disposition;
    this.victim = victim;
    this.perpetrator = perpetrator;
    this.legacyDescriptor = legacyDescriptor;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public SystemCodeDescriptor getType() {
    return type;
  }

  public void setType(SystemCodeDescriptor type) {
    this.type = type;
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
