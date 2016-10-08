package gov.ca.cwds.rest.services.legacy;

import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * This business service provides transactional methods for manipulating
 * {@link StaffPerson}
 * 
 * @author CWDS API Team
 */
@Deprecated
public interface StaffPersonService extends CrudsService<gov.ca.cwds.rest.api.domain.legacy.StaffPerson, StaffPerson> {}
