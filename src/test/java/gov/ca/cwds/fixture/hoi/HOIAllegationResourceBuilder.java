package gov.ca.cwds.fixture.hoi;

import org.joda.time.DateTime;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIAllegation;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerpetrator;
import gov.ca.cwds.rest.api.domain.hoi.HOIVictim;

/**
 * 
 * @author CWDS API Team
 */
public class HOIAllegationResourceBuilder {

  private String id = "jhdgfkhaj";
  private SystemCodeDescriptor type;
  private HOIVictim victim;
  private HOIPerpetrator perpetrator;
  private LegacyDescriptor legacyDescriptor;
  private SystemCodeDescriptor disposition;


  public HOIAllegationResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }


  public HOIAllegationResourceBuilder setType(SystemCodeDescriptor type) {
    this.type = type;
    return this;
  }


  public HOIAllegationResourceBuilder setVictim(HOIVictim victim) {
    this.victim = victim;
    return this;
  }

  public HOIAllegationResourceBuilder setPerpetrator(HOIPerpetrator perpetrator) {
    this.perpetrator = perpetrator;
    return this;
  }

  public HOIAllegationResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public HOIAllegationResourceBuilder() {
    disposition = new SystemCodeDescriptor();
    disposition.setId((short) 45);
    disposition.setDescription("Substantiated");
    type = new SystemCodeDescriptor();
    type.setId((short) 2179);
    type.setDescription("physical abuse");
    victim = new HOIVictim();
    victim.setFirstName("Victim First Name");
    victim.setLastName("Victim Last Name");
    victim.setId("iiiiiii");
    victim.setLegacyDescriptor(new LegacyDescriptor("iiiiiii", "iiiiiii-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    victim.setLimitedAccessType(LimitedAccessType.SEALED);

    perpetrator = new HOIPerpetrator();
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
  public gov.ca.cwds.rest.api.domain.hoi.HOIAllegation createHOIAllegation() {
    return new HOIAllegation(id, type, disposition, victim, perpetrator, legacyDescriptor);
  }
}
