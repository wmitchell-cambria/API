package gov.ca.cwds.fixture.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIRelatedPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.hoi.HOIVictim;

/**
 * 
 * @author CWDS API Team
 */
public class HOICaseResourceBuilder {

  private String id = "jhvuify0X5";
  private Date startDate = new Date();
  private Date endDate = new Date();
  private SystemCodeDescriptor county;
  private SystemCodeDescriptor serviceComponent;
  private HOIVictim focusChild;
  private HOISocialWorker assignedSocialWorker;
  private AccessLimitation accessLimitation;
  private List<HOIRelatedPerson> parents;
  private LegacyDescriptor legacyDescriptor;

  public HOICaseResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public HOICaseResourceBuilder setStartDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  public HOICaseResourceBuilder setEndDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  public HOICaseResourceBuilder setCounty(SystemCodeDescriptor county) {
    this.county = county;
    return this;
  }

  public HOICaseResourceBuilder setResponseTime(SystemCodeDescriptor serviceComponent) {
    this.serviceComponent = serviceComponent;
    return this;
  }

  public HOICaseResourceBuilder setReporter(HOIVictim focusChild) {
    this.focusChild = focusChild;
    return this;
  }

  public HOICaseResourceBuilder setAssignedSocialWorker(HOISocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
    return this;
  }

  public HOICaseResourceBuilder setAccessLimitation(AccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
    return this;
  }

  public HOICaseResourceBuilder setAllegations(List<HOIRelatedPerson> parents) {
    this.parents = parents;
    return this;
  }

  public HOICaseResourceBuilder setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
    return this;
  }


  public HOICaseResourceBuilder() {
    accessLimitation = validAccessLimitation();
    county = validCounty();
    serviceComponent = validServiceComponent();
    focusChild = new HOIVictimResourceBuilder().createHOIVictim();
    assignedSocialWorker = new HOISocialWorkerResourceBuilder().createHOISocialWorker();
    parents = validParents();
  }

  private List<HOIRelatedPerson> validParents() {
    HOIRelatedPerson parent = new HOIRelatedPersonResourceBuilder().createHOIRelatedPerson();
    List<HOIRelatedPerson> validParents = new ArrayList<>();
    validParents.add(parent);
    return validParents;
  }

  private SystemCodeDescriptor validServiceComponent() {
    SystemCodeDescriptor serviceComponent = new SystemCodeDescriptor();
    serviceComponent.setId((short) 1518);
    serviceComponent.setDescription("5 Day");
    return serviceComponent;
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
   * @return the HOICase
   */
  public HOICase createHOICase() {
    HOICase hoicase = new HOICase();
    hoicase.setId(id);
    hoicase.setStartDate(startDate);
    hoicase.setEndDate(endDate);
    hoicase.setCounty(county);
    hoicase.setServiceComponent(serviceComponent);
    hoicase.setAssignedSocialWorker(assignedSocialWorker);
    hoicase.setAccessLimitation(accessLimitation);
    hoicase.setParents(parents);
    hoicase.setLegacyDescriptor(legacyDescriptor);

    return hoicase;
  }
}
