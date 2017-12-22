package gov.ca.cwds.inject;

import java.util.Properties;

import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.AddressValidationService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.CachingSystemCodeService;
import gov.ca.cwds.rest.services.cms.ClientCollateralService;
import gov.ca.cwds.rest.services.cms.ClientRelationshipService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CmsNSReferralService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import gov.ca.cwds.rest.services.cms.TickleService;
import gov.ca.cwds.rest.services.contact.DeliveredService;
import gov.ca.cwds.rest.services.es.IndexQueryService;
import gov.ca.cwds.rest.services.hoi.HOICaseService;
import gov.ca.cwds.rest.services.hoi.HOIReferralService;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;
import gov.ca.cwds.rest.services.investigation.contact.DeliveredToIndividualService;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * (aka, DI) by Google Guice.
 * 
 * @author CWDS API Team
 */
public class ServicesModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServicesModule.class);

  /**
   * @author CWDS API Team
   */
  public static class UnitOfWorkInterceptor implements org.aopalliance.intercept.MethodInterceptor {

    @Inject
    @CmsHibernateBundle
    HibernateBundle<ApiConfiguration> cmsHibernateBundle;

    UnitOfWorkAwareProxyFactory proxyFactory;

    @Inject
    @NsHibernateBundle
    HibernateBundle<ApiConfiguration> nsHibernateBundle;

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {

      proxyFactory =
          UnitOfWorkModule.getUnitOfWorkProxyFactory(cmsHibernateBundle, nsHibernateBundle);
      UnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        aspect.beforeStart(mi.getMethod().getAnnotation(UnitOfWork.class));
        Object result = mi.proceed();
        aspect.afterEnd();
        return result;
      } catch (Exception e) {
        aspect.onError();
        throw e;
      } finally {
        aspect.onFinish();
      }
    }

  }

  /**
   * Default, no-op constructor.
   */
  public ServicesModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    bind(gov.ca.cwds.rest.services.cms.AddressService.class);
    bind(gov.ca.cwds.rest.services.StaffPersonService.class);

    bind(AddressService.class);
    bind(AddressValidationService.class);
    bind(AllegationService.class);
    bind(AssignmentService.class);
    bind(ClientCollateralService.class);
    bind(ClientRelationshipService.class);
    bind(CmsDocReferralClientService.class);
    bind(CmsDocumentService.class);
    bind(CmsNSReferralService.class);
    bind(CmsReferralService.class);
    bind(ContactService.class);
    bind(CrossReportService.class);
    bind(DeliveredService.class);
    bind(DeliveredToIndividualService.class);
    bind(DrmsDocumentService.class);
    bind(GovernmentOrganizationCrossReportService.class);
    bind(IndexQueryService.class);
    bind(LegacyKeyService.class);
    bind(PersonService.class);
    bind(ReferralClientService.class);
    bind(ReferralService.class);
    bind(ReporterService.class);
    bind(ScreeningService.class);
    bind(ScreeningToReferral.class);
    bind(StaffPersonIdRetriever.class);
    bind(StaffPersonService.class);
    bind(TickleService.class);
    bind(HOIReferralService.class);
    bind(InvolvementHistoryService.class);
    bind(HOICaseService.class);

    final UnitOfWorkInterceptor interceptor = new UnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
    requestInjection(interceptor);

    final Properties p = new Properties();
    p.setProperty("something", "Some String");
    Names.bindProperties(binder(), p);

    // @Singleton does not work with DropWizard Guice.
    bind(GovernmentOrganizationService.class).toProvider(GovtOrgSvcProvider.class);
  }

  /**
   * @param governmentOrganizationDao - governmentOrganizationDao
   * @param lawEnforcementDao - lawEnforcementDao
   * @return the cross report agencies
   */
  public GovernmentOrganizationService provideGovernmentOrganizationService(
      GovernmentOrganizationDao governmentOrganizationDao, LawEnforcementDao lawEnforcementDao) {
    return new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
  }

  @Provides
  Validator provideValidator() {
    return Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Provides
  MessageBuilder provideMessageBuilder() {
    return new MessageBuilder();
  }

  /**
   * @param systemCodeDao - systemCodeDao
   * @param systemMetaDao - systemMetaDao
   * @return the systemCodes
   */
  @Provides
  public SystemCodeService provideSystemCodeService(SystemCodeDao systemCodeDao,
      SystemMetaDao systemMetaDao) {
    LOGGER.debug("provide syscode service");
    final long secondsToRefreshCache = 15L * 24 * 60 * 60; // 15 days
    return new CachingSystemCodeService(systemCodeDao, systemMetaDao, secondsToRefreshCache, false);
  }

  /**
   * @param systemCodeService - systemCodeService
   * @return the SystemCodeCache
   */
  @Provides
  public SystemCodeCache provideSystemCodeCache(SystemCodeService systemCodeService) {
    LOGGER.debug("provide syscode cache");
    SystemCodeCache systemCodeCache = (SystemCodeCache) systemCodeService;
    systemCodeCache.register();
    return systemCodeCache;
  }

  /**
   * @param systemCodeCache - systemCodeCache
   * @return the CmsSystemCodeSerializer
   */
  @Provides
  public CmsSystemCodeSerializer provideCmsSystemCodeSerializer(SystemCodeCache systemCodeCache) {
    LOGGER.debug("provide syscode serializer");
    return new CmsSystemCodeSerializer(systemCodeCache);
  }
}
