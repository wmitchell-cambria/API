package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import gov.ca.cwds.fixture.DrmsDocumentTemplateEntityBuilder;

public class DrmsDocumentTemplateTest {
  private String thirdId = "1234567890ABC";
  private Short applicationContextType = 82;
  private String documentDOSFilePrefixName = "INALG_NS";
  private Short governmentEntityType = 99;
  private String cmsDocumentId = "9831181421090420*SMITHBO 00001";
  private Short languageType = 1253;
  private String titleName = "Screener Narrative";
  private String transactionType = "N";

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DrmsDocumentTemplateTest.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testContructor() {
	  DrmsDocumentTemplate domain = new DrmsDocumentTemplate(thirdId, applicationContextType,
			  documentDOSFilePrefixName, governmentEntityType, cmsDocumentId, languageType,
			  titleName, transactionType);
	  assertThat(domain.getThirdId(), is(equalTo(thirdId)));
	  assertThat(domain.getApplicationContextType(), is(equalTo(applicationContextType)));
	  assertThat(domain.getDocumentDOSFilePrefixName(), is(equalTo(documentDOSFilePrefixName)));
	  assertThat(domain.getGovermentEntityType(), is(equalTo(governmentEntityType)));
	  assertThat(domain.getCmsDocumentId(), is(equalTo(cmsDocumentId)));
	  assertThat(domain.getLanguageType(), is(equalTo(languageType)));
	  assertThat(domain.getTitleName(), is(equalTo(titleName)));
	  assertThat(domain.getTransactionType(), is(equalTo(transactionType)));
  }
  
  @Test
  public void testConstructorUsingPersistent() {
	  gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate persistent = new DrmsDocumentTemplateEntityBuilder().build();
	  DrmsDocumentTemplate domain = new DrmsDocumentTemplate(persistent);
	  assertThat(persistent.getThirdId(), is(equalTo(domain.getThirdId())));
	  assertThat(persistent.getApplicationContextType(), is(equalTo(domain.getApplicationContextType())));
	  assertThat(persistent.getDocumentDOSFilePrefixName(), is(equalTo(domain.getDocumentDOSFilePrefixName())));
	  assertThat(persistent.getGovermentEntityType(), is(equalTo(domain.getGovermentEntityType())));
	  assertThat(persistent.getCmsDocumentId(), is(equalTo(domain.getCmsDocumentId())));
	  assertThat(persistent.getLanguageType(), is(equalTo(domain.getLanguageType())));
	  assertThat(persistent.getTitleName(), is(equalTo(domain.getTitleName())));
	  assertThat(persistent.getTransactionType(), is(equalTo(domain.getTransactionType())));
  }
}
