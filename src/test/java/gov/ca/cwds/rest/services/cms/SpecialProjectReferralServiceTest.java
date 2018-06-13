package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import gov.ca.cwds.data.cms.SpecialProjectDao;
import gov.ca.cwds.data.cms.SpecialProjectReferralDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.SpecialProject;
import gov.ca.cwds.data.persistence.cms.SpecialProjectReferral;
import gov.ca.cwds.fixture.CsecBuilder;
import gov.ca.cwds.fixture.SpecialProjectEntityBuilder;
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
  public void shouldReturnPostedSpecialProjectReferralWhenSaveCsecSpecialProjectReferral() throws Exception {
    List<Csec> csecs = new ArrayList();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
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
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted.getClass(), is(PostedSpecialProjectReferral.class));    
  }
  
  @Test 
  public void shouldReturnNullWhenSpecialProjectDoesNotExistsOnSave() throws Exception {
    List<Csec> csecs = new ArrayList();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "0987654ABC";
    String incidentCounty = "34";
    Date endDate = new Date();
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .setEndDate(endDate)
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

    // specialProjectReferralDao.create should not be called
    PostedSpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    
 }
  
  @Test
  public void shouldReturnNullWhenSpecialProjectReferralAlreadyExist() throws Exception {
    List<Csec> csecs = new ArrayList<Csec>();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "9876543ABC";
    String specialProjectId = "0987654ABC";
    String incidentCounty = "34";
    MessageBuilder messageBuilder = new MessageBuilder();

    // mock the special project find dao
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .setId(specialProjectId)
        .build();
    List<SpecialProject> specialProjects = new ArrayList<SpecialProject>();
    specialProjects.add(specialProject);
    when(specialProjectDao.findSpecialProjectsByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);

    // mock the special project referral dao (findSpecialProjectByReferralIdAndSpecialProjectId)
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = 
        new SpecialProjectReferralResourceBuilder()
        .setSpecialProjectId(specialProjectId)
        .setReferralId(referralId)
        .build();
    SpecialProjectReferral sprEntity = new gov.ca.cwds.data.persistence.cms.SpecialProjectReferral("9876543ABC",
        sprDomain,
        "aab",
        new Date());
    List<SpecialProjectReferral> specialProjectReferrals = new ArrayList();
    specialProjectReferrals.add(sprEntity);    
    when(specialProjectReferralDao
        .findSpecialProjectReferralsByReferralIdAndSpecialProjectId(referralId, specialProjectId))
        .thenReturn(specialProjectReferrals);

    // specialProjectReferralDao.create should not be called
    PostedSpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    

  }
  
  @Test
  public void shouldReturnNullWhenInvalidGovernmentEntityType() throws Exception {
    List<Csec> csecs = new ArrayList<Csec>();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "0987654ABC";
    String incidentCounty = "ZZ";
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .build();
    List<SpecialProject> specialProjects = new ArrayList<SpecialProject>();
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
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    
    
  }
  
  @Test
  public void shouldRetrunNullWhenCSECDataNotProvided() throws Exception {
    List<Csec> csecs = new ArrayList();

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
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    
    
  }
  
  @Test
  public void testFind() {
    try {
      specialProjectReferralService.find("abc");
      fail("Expected exception was not thrown");
    } catch (Exception e) {
    }
  }

  @Test
  public void testDelete() {
    try {
      specialProjectReferralService.delete("abc");
      fail("Expected exception was not thrown");
    } catch (Exception e) {
    }
  }

  @Test
  public void testUpdate() {
    try {
      String primaryKey = "1234567ABC";
      gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
      
      specialProjectReferralService.update(primaryKey, sprDomain);
      fail("Expected exception was not thrown");
    } catch (Exception e) {
    }
  }
}
