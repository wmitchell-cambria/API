package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;

public class InvestigationServiceTest {
  private InvestigationDao investigationDao;
  private StaffPersonDao staffPersonDao;
  private AddressDao addressDao;
  private LongTextDao longTextDao;

  InvestigationService target;

  private static final String DEFAULT_KEY = "Anb27uN00I";


  @Before
  public void setup() throws Exception {
    this.investigationDao = mock(InvestigationDao.class);
    this.staffPersonDao = mock(StaffPersonDao.class);
    this.addressDao = mock(AddressDao.class);
    this.longTextDao = mock(LongTextDao.class);

    target = new InvestigationService(investigationDao, staffPersonDao, addressDao, longTextDao);
  }

  @Test
  public void type() throws Exception {
    assertThat(ContactService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_Args__String() throws Exception {
    final String primaryKey = DEFAULT_KEY;
    Response actual = target.find(primaryKey);
    assertThat(actual, notNullValue());
  }


}
