package gov.ca.cwds.data.cms.xa;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class XaCmsGovernmentOrganizationCrossReportDaoImplTest extends Doofenshmirtz<Address> {

  XaCmsGovernmentOrganizationCrossReportDaoImpl target;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new XaCmsGovernmentOrganizationCrossReportDaoImpl(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(XaCmsGovernmentOrganizationCrossReportDaoImpl.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

}
