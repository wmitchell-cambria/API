package gov.ca.cwds.rest.services.legacy;

import gov.ca.cwds.rest.api.persistence.legacy.Allegation;
import gov.ca.cwds.rest.services.CrudsService;


/**
 * This business service provides transactional methods for manipulating
 * {@link Allegation}
 * 
 * @author CWDS API Team
 */
@Deprecated
public interface AllegationService extends CrudsService<gov.ca.cwds.rest.api.domain.legacy.Allegation, Allegation> {}
