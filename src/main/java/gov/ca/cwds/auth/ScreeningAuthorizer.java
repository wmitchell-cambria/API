package gov.ca.cwds.auth;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.authorizer.util.StaffPrivilegeUtil;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;

public class ScreeningAuthorizer extends BaseAuthorizer<ScreeningEntity, String> {

  private static final String ACCESS_RESTRICTION_SENSITIVE = "sensitive";
  private static final String ACCESS_RESTRICTION_SEALED = "sealed";

  @Inject
  private ScreeningDao screeningDao;

  @Override
  protected boolean checkId(final String screeningId) {
    final ScreeningEntity screeningEntity = screeningDao.find(screeningId);
    return screeningEntity == null || checkInstance(screeningEntity);
  }

  @Override
  protected boolean checkInstance(final ScreeningEntity screening) {
    String accessRestriction = screening.getAccessRestrictions();
    if (StringUtils.isBlank(accessRestriction)) {
      return true;
    }

    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Set<StaffPrivilegeType> staffPrivilegeTypes =
        StaffPrivilegeUtil.toStaffPersonPrivilegeTypes(perryAccount);

    boolean authorized = false;
    if (ACCESS_RESTRICTION_SENSITIVE.equals(accessRestriction)) {
      authorized = staffPrivilegeTypes.contains(StaffPrivilegeType.COUNTY_SENSITIVE)
          || staffPrivilegeTypes.contains(StaffPrivilegeType.STATE_SENSITIVE);
    } else if (ACCESS_RESTRICTION_SEALED.equals(accessRestriction)) {
      authorized = staffPrivilegeTypes.contains(StaffPrivilegeType.COUNTY_SEALED)
          || staffPrivilegeTypes.contains(StaffPrivilegeType.STATE_SEALED);
    }

    return authorized;
  }
}
