package gov.ca.cwds.data.persistence.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.persistence.junit.template.PersistentTestTemplate;
import gov.ca.cwds.fixture.DrmsDocumentTemplateEntityBuilder;
import gov.ca.cwds.fixture.DrmsDocumentTemplateResourceBuilder;
import org.junit.Test;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Intake Team 4
 *
 */
public class DrmsDocumentTemplateTest implements PersistentTestTemplate {

  private String id = "1234567ABC";

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /*
   * Constructor test
   */
  @Override
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DrmsDocumentTemplate.class.newInstance(), is(notNullValue()));
  }

  @Override
  @Test
  public void testPersistentConstructor() throws Exception {

    DrmsDocumentTemplate vp = new DrmsDocumentTemplateEntityBuilder().build();

    DrmsDocumentTemplate persistent =
        new DrmsDocumentTemplate(id, vp.getApplicationContextType(), vp.getDocumentDOSFilePrefixName(),
                vp.getGovermentEntityType(), vp.getCmsDocumentId(), vp.getInactive(), vp.getLanguageType(),
                vp.getLastUpdatedTime(), vp.getTitleName(), vp.getTransactionType());
    assertThat(persistent.getThirdId(), is(equalTo(id)));
    assertThat(persistent.getApplicationContextType(), is(equalTo(vp.getApplicationContextType())));
    assertThat(persistent.getDocumentDOSFilePrefixName(), is(equalTo(vp.getDocumentDOSFilePrefixName())));
    assertThat(persistent.getGovermentEntityType(), is(equalTo(vp.getGovermentEntityType())));
    assertThat(persistent.getCmsDocumentId(), is(equalTo(vp.getCmsDocumentId())));
    assertThat(persistent.getInactive(), is(equalTo(vp.getInactive())));
    assertThat(persistent.getLanguageType(), is(equalTo(vp.getLanguageType())));
    assertThat(persistent.getLastUpdatedTime(), is(equalTo(vp.getLastUpdatedTime())));
    assertThat(persistent.getTitleName(), is(equalTo(vp.getTitleName())));
    assertThat(persistent.getTransactionType(), is(equalTo(vp.getTransactionType())));
  }

  @Override
  @Test
  public void testConstructorUsingDomain() throws Exception {

    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate domain = new DrmsDocumentTemplateResourceBuilder().build();
    Date now = new Date();
    DrmsDocumentTemplate persistent = new DrmsDocumentTemplate(domain, now);

    assertThat(persistent.getThirdId(), is(equalTo(domain.getThirdId())));
    assertThat(persistent.getApplicationContextType(), is(equalTo(domain.getApplicationContextType())));
    assertThat(persistent.getDocumentDOSFilePrefixName(), is(equalTo(domain.getDocumentDOSFilePrefixName())));
    assertThat(persistent.getGovermentEntityType(), is(equalTo(domain.getGovermentEntityType())));
    assertThat(persistent.getCmsDocumentId(), is(equalTo(domain.getCmsDocumentId())));
    assertThat(persistent.getInactive(), is(equalTo("N")));
    assertThat(persistent.getLanguageType(), is(equalTo(domain.getLanguageType())));
    assertThat(persistent.getLastUpdatedTime(), is(equalTo(now)));
    assertThat(persistent.getTitleName(), is(equalTo(domain.getTitleName())));
    assertThat(persistent.getTransactionType(), is(equalTo(domain.getTransactionType())));
  }

}
