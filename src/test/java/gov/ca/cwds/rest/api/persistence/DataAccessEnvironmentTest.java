package gov.ca.cwds.rest.api.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;
import gov.ca.cwds.rest.api.persistence.ns.NsPersistentObject;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;

public class DataAccessEnvironmentTest {

  @Test
  public void dataAccessEnvironmentSuccessfullyReturnsDaoAfterRegistration() throws Exception {
    @SuppressWarnings("unchecked")
    CrudsDao<NsPersistentObject> crudsDao = mock(CrudsDao.class);
    DataAccessEnvironment.register(StaffPerson.class, crudsDao);
    assertThat(DataAccessEnvironment.get(StaffPerson.class), is(equalTo(crudsDao)));
  }
}
