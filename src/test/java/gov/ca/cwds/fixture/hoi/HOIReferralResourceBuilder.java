package gov.ca.cwds.fixture.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIAllegation;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;

/**
 * 
 * @author CWDS API Team
 */
public class HOIReferralResourceBuilder {

  private String id = "jhvuify0X5";
  private Date startDate = new Date();
  private Date endDate = new Date();
  private SystemCodeDescriptor county;
  private SystemCodeDescriptor responseTime;
  private HOIReporter reporter;
  private HOISocialWorker assignedSocialWorker;
  private AccessLimitation accessLimitation;
  private List<HOIAllegation> allegations;
  private LegacyDescriptor legacyDescriptor;

  public HOIReferralResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public HOIReferralResourceBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public HOIReferralResourceBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public HOIReferralResourceBuilder setCounty(SystemCodeDescriptor county) {
    this.county = county;
    return this;
  }

  public HOIReferralResourceBuilder setResponseTime(SystemCodeDescriptor responseTime) {
    this.responseTime = responseTime;
    return this;
  }

  public HOIReferralResourceBuilder setReporter(HOIReporter reporter) {
    this.reporter = reporter;
    return this;
  }

  public HOIReferralResourceBuilder setAssignedSocialWorker(HOISocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
    return this;
  }

  public HOIReferralResourceBuilder setAccessLimitation(AccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
    return this;
  }

  public HOIReferralResourceBuilder setAllegations(List<HOIAllegation> allegations) {
    this.allegations = allegations;
    return this;
  }

  public HOIReferralResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public HOIReferralResourceBuilder() {
    accessLimitation = validAccessLimitation();
    county = validCounty();
    responseTime = validResponseTime();
    reporter = new HOIReporterResourceBuilder().createHOIReporter();
    assignedSocialWorker = new HOISocialWorkerResourceBuilder().createHOISocialWorker();
    allegations = validAllegations();
  }

  private List<HOIAllegation> validAllegations() {
    HOIAllegation allegation = new HOIAllegationResourceBuilder().createHOIAllegation();
    List<HOIAllegation> validAllegations = new ArrayList<>();
    validAllegations.add(allegation);
    return validAllegations;
  }

  private SystemCodeDescriptor validResponseTime() {
    SystemCodeDescriptor responseTime = new SystemCodeDescriptor();
    responseTime.setId((short) 1518);
    responseTime.setDescription("5 Day");
    return responseTime;
  }

  private SystemCodeDescriptor validCounty() {
    SystemCodeDescriptor validCounty = new SystemCodeDescriptor();
    validCounty.setId((short) 1101);
    validCounty.setDescription("Sacramento");
    return validCounty;
  }

  private AccessLimitation validAccessLimitation() {
    AccessLimitation validAccessLimitation = new AccessLimitation();
    validAccessLimitation.setLimitedAccessCode(LimitedAccessType.SEALED);
    validAccessLimitation.setLimitedAccessDate(new Date());
    validAccessLimitation.setLimitedAccessDescription("bla bla blah");
    SystemCodeDescriptor govtEntity = new SystemCodeDescriptor();
    govtEntity.setId((short) 1101);
    govtEntity.setDescription("Sacramento");
    validAccessLimitation.setLimitedAccessGovernmentEntity(govtEntity);
    return validAccessLimitation;
  }

  /**
   * @return the HOIReferral
   */
  public HOIReferral createHOIReferral() {
    HOIReferral referral = new HOIReferral();
    referral.setId(id);
    referral.setStartDate(startDate);
    referral.setEndDate(endDate);
    referral.setCounty(county);
    referral.setResponseTime(responseTime);
    referral.setAssignedSocialWorker(assignedSocialWorker);
    referral.setAccessLimitation(accessLimitation);
    referral.setAllegations(allegations);
    referral.setLegacyDescriptor(legacyDescriptor);

    return referral;
  }
}
