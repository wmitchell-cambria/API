package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import gov.ca.cwds.data.cms.SpecialProjectDao;
import gov.ca.cwds.data.cms.SpecialProjectReferralDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.SpecialProject;
import gov.ca.cwds.data.persistence.cms.SpecialProjectReferral;
import gov.ca.cwds.fixture.CsecBuilder;
import gov.ca.cwds.fixture.SpecialProjectEntityBuilder;
import gov.ca.cwds.fixture.SpecialProjectReferralEntityBuilder;
import gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.cms.PostedSpecialProjectReferral;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.referentialintegrity.RISpecialProjectReferral;

public class SpecialProjectReferralServiceTest {

  private SpecialProjectReferralService  specialProjectReferralService;
  
  private SpecialProjectReferralDao specialProjectReferralDao;
  private SpecialProjectDao specialProjectDao;
  private RISpecialProjectReferral riSpecialProjectReferral;
  
  private Validator validator;

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() {
    new TestingRequestExecutionContext("0X5");
    RequestExecutionContext.instance();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    specialProjectReferralDao = mock(SpecialProjectReferralDao.class);
    
    specialProjectDao = mock(SpecialProjectDao.class);
    
    riSpecialProjectReferral = mock(RISpecialProjectReferral.class);
    
    specialProjectReferralService = new SpecialProjectReferralService(specialProjectReferralDao,
        specialProjectDao, riSpecialProjectReferral, validator);
    
    
  }
  
  @Test
  public void shouldReturnPostedSpecialProjectReferralObjectWhenCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    
    SpecialProjectReferral sprEntity = new gov.ca.cwds.data.persistence.cms.SpecialProjectReferral("9876543ABC",
        sprDomain,
        "aab",
        new Date());
    
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);
    PostedSpecialProjectReferral sprPosted = specialProjectReferralService.create(sprDomain);
    
    assertThat(sprPosted.getClass(), is(PostedSpecialProjectReferral.class));
  }

  @Test
  public void shouldThrowAssertionErrorCreate() throws Exception {
    thrown.expect(AssertionError.class);
    try {
      PostedSpecialProjectReferral sprPosted = specialProjectReferralService.create(null);
    } catch (AssertionError e) {
      assertEquals("Expected AssertionError", e.getMessage());
    }
  }
  
  @Test
  public void shouldReturnPostedSpecialProjectReferralWhenSave() throws Exception {
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    String referralId = "0987654ABC";
    String incidentCounty = "34";
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .build();
    List<SpecialProject> specialProjects = new ArrayList();
    specialProjects.add(specialProject);
    
    when(specialProjectDao.findSpecialProjectsByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral sprEntity = new gov.ca.cwds.data.persistence.cms.SpecialProjectReferral("9876543ABC",
        sprDomain,
        "aab",
        new Date());
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);

    PostedSpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csec, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted.getClass(), is(PostedSpecialProjectReferral.class));    
  }
  
  @Test 
  public void shouldReturnNullWhenSpecialProjectDoesNotExistsOnSave() throws Exception {
    
  }
  
  @Test
  public void testFind() {
    try {
      specialProjectReferralService.find("abc");
      fail("Expected exception");
    } catch (Exception e) {

    }
  }

}
