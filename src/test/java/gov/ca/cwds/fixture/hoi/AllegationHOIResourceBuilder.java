package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.AllegationHOI;
import gov.ca.cwds.rest.api.domain.hoi.Perpetrator;
import gov.ca.cwds.rest.api.domain.hoi.Victim;

/**
 * 
 * @author CWDS API Team
 */
public class AllegationHOIResourceBuilder {

  private String id = "jhdgfkhaj";
  private String description = "Allegation description";
  private Victim victim;
  private Perpetrator perpetrator;
  private LegacyDescriptor legacyDescriptor;
  private SystemCodeDescriptor disposition;


  public AllegationHOIResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public AllegationHOIResourceBuilder setDescription(String description) {
    this.description = description;
    return this;
  }


  public AllegationHOIResourceBuilder setVictim(Victim victim) {
    this.victim = victim;
    return this;
  }

  public AllegationHOIResourceBuilder setPerpetrator(Perpetrator perpetrator) {
    this.perpetrator = perpetrator;
    return this;
  }

  public AllegationHOIResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public AllegationHOIResourceBuilder() {
    disposition = new SystemCodeDescriptor();
    disposition.setId((short) 45);
    disposition.setDescription("Substantiated");
    victim = new Victim();
    victim.setFirstName("Victim First Name");
    victim.setLastName("Victim Last Name");
    victim.setId("iiiiiii");
    victim.setLegacyDescriptor(new LegacyDescriptor("iiiiiii", "iiiiiii-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    victim.setLimitedAccessType(LimitedAccessType.SEALED);

    perpetrator = new Perpetrator();
    perpetrator.setFirstName("Perpetrator First Name");
    perpetrator.setLastName("Perpetrator Last Name");
    perpetrator.setId("pppppppp");
    perpetrator.setLegacyDescriptor(new LegacyDescriptor("pppppppp", "pppppppp-hohj-jkj",
        new DateTime(), LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    perpetrator.setLimitedAccessType(LimitedAccessType.NONE);

    legacyDescriptor = new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj", new DateTime(),
        LegacyTable.ALLEGATION.getName(), LegacyTable.ALLEGATION.getDescription());
  }


  /**
   * @return the AllegationHOI
   */
  public gov.ca.cwds.rest.api.domain.hoi.AllegationHOI createAllegationHOI() {
    return new AllegationHOI(id, description, disposition, victim, perpetrator, legacyDescriptor);
  }
}
