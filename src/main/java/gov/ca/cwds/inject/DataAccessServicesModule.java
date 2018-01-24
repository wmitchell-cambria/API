package gov.ca.cwds.inject;

import com.google.inject.Injector;
import com.google.inject.Key;
import gov.ca.cwds.cms.data.access.inject.AbstractDataAccessServicesModule;
import org.hibernate.SessionFactory;

/**
 * CWDS API Team
 */
public class DataAccessServicesModule extends AbstractDataAccessServicesModule {

  @Override
  protected SessionFactory getDataAccessSercvicesSessionFactory(Injector injector) {
    return injector.getInstance(Key.get(SessionFactory.class, CmsSessionFactory.class));
  }
}
