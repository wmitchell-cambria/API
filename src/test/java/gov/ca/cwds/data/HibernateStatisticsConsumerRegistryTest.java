package gov.ca.cwds.data;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.provideHibernateStatistics;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.registerHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.unRegisterHibernateStatisticsConsumer;
import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.HibernateStatisticsConsumer;
import java.util.concurrent.atomic.AtomicReference;
import org.hibernate.stat.Statistics;
import org.junit.Test;

public class HibernateStatisticsConsumerRegistryTest {

  @Test
  public void testHibernateStatisticsConsumerRegistry() {
    AtomicReference<Statistics> actualProvidedStatisticsReference = new AtomicReference<>();
    HibernateStatisticsConsumer consumer = actualProvidedStatisticsReference::set;

    registerHibernateStatisticsConsumer(CMS_BUNDLE_TAG, consumer);
    // we will provode null for the unit test because where else we get Statistics instance?
    provideHibernateStatistics(CMS_BUNDLE_TAG, null);
    unRegisterHibernateStatisticsConsumer(CMS_BUNDLE_TAG);

    assertNull(actualProvidedStatisticsReference.get());
  }

}
