package gov.ca.cwds.rest.services.investigation;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.investigation.InvestigationDao;
import gov.ca.cwds.rest.services.cms.LongTextService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;

public class InvestigationServiceTest {
  InvestigationDao investigationDao;
  StaffPersonDao staffPersonDao;
  AddressDao addressDao;
  LongTextService longTextService;
  ClientDao clientDao;

  InvestigationService target;

  private static final String DEFAULT_KEY = "Anb27uN00I";


  @Before
  public void setup() throws Exception {
    this.investigationDao = mock(InvestigationDao.class);
    this.staffPersonDao = mock(StaffPersonDao.class);
    this.addressDao = mock(AddressDao.class);
    this.longTextService = mock(LongTextService.class);
    this.clientDao = mock(ClientDao.class);

    target = new InvestigationService(investigationDao, staffPersonDao, addressDao, longTextService,
        clientDao);
  }

  @Test
  public void type() throws Exception {
    assertThat(ContactService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }



}
