package gov.ca.cwds.inject;

import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAspect;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAwareProxyFactory;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.CachingIntakeCodeService;
import gov.ca.cwds.rest.services.IntakeLovService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningRelationshipService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
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
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import gov.ca.cwds.rest.services.cms.OtherCaseReferralDrmsDocumentService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import gov.ca.cwds.rest.services.cms.TickleService;
import gov.ca.cwds.rest.services.contact.DeliveredService;
import gov.ca.cwds.rest.services.hoi.HOICaseService;
import gov.ca.cwds.rest.services.hoi.HOIReferralService;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;
import gov.ca.cwds.rest.services.investigation.contact.DeliveredToIndividualService;
import gov.ca.cwds.rest.services.submit.ScreeningSubmitService;
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
   * AOP method interception for DropWizard annotation {@link UnitOfWork}.
   * 
   * <p>
   * Note that {@link UnitOfWork} gets a <strong>new connection</strong> every time an annotated
   * method is encountered. By definition, this annotation is not re-entrant and does not
   * participate in existing transactions on the same request.
   * </p>
   * 
   * @author CWDS API Team
   */
  public static class UnitOfWorkInterceptor implements org.aopalliance.intercept.MethodInterceptor {

    UnitOfWorkAwareProxyFactory proxyFactory;

    @Inject
    @CmsHibernateBundle
    HibernateBundle<ApiConfiguration> cmsHibernateBundle;

    @Inject
    @NsHibernateBundle
    HibernateBundle<ApiConfiguration> nsHibernateBundle;

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
      proxyFactory =
          UnitOfWorkModule.getUnitOfWorkProxyFactory(cmsHibernateBundle, nsHibernateBundle);
      final UnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        aspect.beforeStart(mi.getMethod().getAnnotation(UnitOfWork.class));
        final Object result = mi.proceed();
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
   * AOP method interception for Ferb annotation {@link XAUnitOfWork}. Automatically manages
   * Hibernate sessions and XA transactions.
   * 
   * <p>
   * NEXT: switch *all* data sources to XA and change all resources to use {@link XAUnitOfWork}
   * instead of {@link UnitOfWork}.
   * </p>
   * 
   * @author CWDS API Team
   * @see XAUnitOfWorkAspect
   */
  public static class XAUnitOfWorkInterceptor
      implements org.aopalliance.intercept.MethodInterceptor {

    XAUnitOfWorkAwareProxyFactory proxyFactory;

    @Inject
    @XaCmsHibernateBundle
    FerbHibernateBundle xaCmsHibernateBundle;

    @Inject
    @XaNsHibernateBundle
    FerbHibernateBundle xaNsHibernateBundle;

    @Inject
    @CwsRsHibernateBundle
    FerbHibernateBundle xaRsHibernateBundle;

    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
      proxyFactory = UnitOfWorkModule.getXAUnitOfWorkProxyFactory(xaCmsHibernateBundle,
          xaNsHibernateBundle, xaRsHibernateBundle);
      final XAUnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        LOGGER.debug("Before XA annotation");
        aspect.beforeStart(mi.getMethod().getAnnotation(XAUnitOfWork.class));
        final Object result = mi.proceed();
        aspect.afterEnd();
        LOGGER.debug("After XA annotation");
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
    bind(AllegationService.class);
    bind(AssignmentService.class);
    bind(AuthorizationService.class);
    bind(ClientCollateralService.class);
    bind(ClientRelationshipService.class);
    bind(CmsDocReferralClientService.class);
    bind(CmsDocumentService.class);
    bind(CmsNSReferralService.class);
    bind(CmsReferralService.class);
    bind(ContactService.class);
    bind(CrossReportService.class);
    bind(CsecHistoryService.class);
    bind(DeliveredService.class);
    bind(DeliveredToIndividualService.class);
    bind(DrmsDocumentService.class);
    bind(DrmsDocumentTemplateService.class);
    bind(GovernmentOrganizationCrossReportService.class);
    bind(HOICaseService.class);
    bind(HOIReferralService.class);
    bind(InvolvementHistoryService.class);
    bind(LegacyKeyService.class);
    bind(OtherCaseReferralDrmsDocumentService.class);
    bind(PersonService.class);
    bind(ReferralClientService.class);
    bind(ReferralService.class);
    bind(ReporterService.class);
    bind(ScreeningRelationshipService.class);
    bind(ScreeningService.class);
    bind(ScreeningSubmitService.class);
    bind(ScreeningToReferral.class);
    bind(StaffPersonIdRetriever.class);
    bind(StaffPersonService.class);
    bind(TickleService.class);

    // NEXT: Schedule for removal??
    // Enable AOP for DropWizard @UnitOfWork.
    final UnitOfWorkInterceptor interceptor = new UnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
    requestInjection(interceptor);

    // Enable AOP for Ferb @XAUnitOfWork.
    final XAUnitOfWorkInterceptor xaInterceptor = new XAUnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(XAUnitOfWork.class), xaInterceptor);
    requestInjection(xaInterceptor);

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
   * @param intakeLovDao - intakeLovDao
   * @return the IntakeCode
   */
  @Provides
  public IntakeLovService provideIntakeLovService(IntakeLovDao intakeLovDao) {
    LOGGER.debug("provide intakeCode service");
    final long secondsToRefreshCache = 15L * 24 * 60 * 60; // 15 days -- NEXT: soft-code me
    return new CachingIntakeCodeService(intakeLovDao, secondsToRefreshCache);
  }

  /**
   * @param intakeLovService - intakeLovService
   * @return IntakeCodeCache
   */
  @Provides
  public IntakeCodeCache provideIntakeLovCodeCache(IntakeLovService intakeLovService) {
    LOGGER.debug("provide intakeCode cache");
    IntakeCodeCache intakeCodeCache = (IntakeCodeCache) intakeLovService;
    intakeCodeCache.register();
    return intakeCodeCache;
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
