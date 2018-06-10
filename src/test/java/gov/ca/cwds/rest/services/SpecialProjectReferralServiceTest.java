package gov.ca.cwds.rest.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.rest.util.Doofenshmirtz;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.security.utils.PrincipalUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PerrySubject.class, PrincipalUtils.class})
public class SpecialProjectReferralServiceTest extends Doofenshmirtz<SpecialProject> {

  SpecialProjectReferralService target;

  @Override
  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    super.setup();

    // Fake logged-in user.
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn("0X5");

    final String[] roles = {"Sensitive Persons"};
    final Set<String> setRoles = new HashSet<>();
    setRoles.addAll(Arrays.asList(roles));
    final PerryAccount perryAccount = perryAccountWithPrivilegesInator(roles);
    mockStatic(PerrySubject.class);

    perryAccount.setCountyCode("1126"); // State of California
    perryAccount.setCountyCwsCode("99"); // ditto

    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);

    final List<SpecialProject> ssbSpecialProjects = new ArrayList<>();
    ssbSpecialProjects.add(new SpecialProject());
    when(specialProjectDao
        .findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(any(Short.class)))
            .thenReturn(ssbSpecialProjects);

    final SpecialProjectReferral createdSpr = new SpecialProjectReferral();
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class)))
        .thenReturn(createdSpr);

    target = new SpecialProjectReferralService();
    target.setNonCWSNumberDao(nonCWSNumberDao);
    target.setSafelySurrenderedBabiesDao(safelySurrenderedBabiesDao);
    target.setSpecialProjectDao(specialProjectDao);
    target.setSpecialProjectReferralDao(specialProjectReferralDao);
  }

  @Test
  public void type() throws Exception {
    assertThat(SpecialProjectReferralService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    SpecialProjectReferralService target = new SpecialProjectReferralService();
    assertThat(target, notNullValue());
  }

  @Test
  public void processSafelySurrenderedBabies_A$String$String$govcacwdsrestapidomainSafelySurrenderedBabies()
      throws Exception {
    String childClientId = null;
    String referralId = null;
    gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies ssb =
        mock(gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies.class);
    target.processSafelySurrenderedBabies(childClientId, referralId, ssb);
  }

}
