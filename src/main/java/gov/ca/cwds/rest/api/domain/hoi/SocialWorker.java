package gov.ca.cwds.rest.api.domain.hoi;

import gov.ca.cwds.rest.api.domain.LegacyDescriptor;

/**
 * Social worker person.
 * 
 * @author CWDS API Team
 */
public class SocialWorker extends HOIPerson {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public SocialWorker() {
    super();
  }

  /**
   * 
   * @param id - id
   * @param firstName - firstName
   * @param lastName - lastName
   * @param legacyDescriptor - legacyDescriptor
   */
  public SocialWorker(String id, String firstName, String lastName,
      LegacyDescriptor legacyDescriptor) {
    super(id, firstName, lastName, legacyDescriptor);
  }

}

