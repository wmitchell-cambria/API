package gov.ca.cwds.auth;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.authorizer.util.StaffPrivilegeUtil;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;

/**
 * Authorizer for screenings. See this: https://osi-cwds.atlassian.net/browse/INT-1061
 * 
 * @author Ferb API Team
 *
 */
public class ScreeningAuthorizer extends BaseAuthorizer<ScreeningEntity, String> {

  private static final String ACCESS_RESTRICTION_SENSITIVE = "sensitive";
  private static final String ACCESS_RESTRICTION_SEALED = "sealed";

  @Override
  protected boolean checkInstance(final ScreeningEntity screening) {
    if (screening == null) {
      return true;
    }

    String accessRestriction = screening.getAccessRestrictions();

    // If screening has no access restriction then anyone can access it
    if (StringUtils.isBlank(accessRestriction)) {
      return true;
    }

    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Set<StaffPrivilegeType> staffPrivilegeTypes =
        StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(perryAccount);

    // If staff person has no privileges then can not access this screening
    if (staffPrivilegeTypes.isEmpty()) {
      return false;
    }

    boolean authorized = false;

    if (ACCESS_RESTRICTION_SENSITIVE.equals(accessRestriction)) {
      // If the screening is marked as sensitive, then a user with sensitive permissions in any
      // county can access it
      authorized = staffPrivilegeTypes.contains(StaffPrivilegeType.COUNTY_SENSITIVE)
          || staffPrivilegeTypes.contains(StaffPrivilegeType.STATE_SENSITIVE);
    } else if (ACCESS_RESTRICTION_SEALED.equals(accessRestriction)) {
      // If the screening is marked as sealed, then a user with sealed permissions in any county can
      // access it
      authorized = staffPrivilegeTypes.contains(StaffPrivilegeType.COUNTY_SEALED)
          || staffPrivilegeTypes.contains(StaffPrivilegeType.STATE_SEALED);
    }

    return authorized;
  }
}
