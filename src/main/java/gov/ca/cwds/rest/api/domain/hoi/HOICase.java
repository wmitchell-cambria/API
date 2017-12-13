package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * Case for HOI.
 * 
 * @author CWDS API Team
 */
public class HOICase extends ApiObjectIdentity implements ApiTypedIdentifier<String>, Request {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date startDate;

  @JsonProperty("end_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-09-30")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("service_component")
  private SystemCodeDescriptor serviceComponent;

  @JsonProperty("focus_child")
  private HOIVictim focusChild;

  @JsonProperty("assigned_social_worker")
  private HOISocialWorker assignedSocialWorker;

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("parents")
  private List<HOIRelatedPerson> parents = new ArrayList<>();

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public HOICase() {
    // No-argument constructor
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public Date getStartDate() {
    return FerbDateUtils.freshDate(startDate);
  }

  public void setStartDate(Date startDate) {
    this.startDate = FerbDateUtils.freshDate(startDate);
  }

  public Date getEndDate() {
    return FerbDateUtils.freshDate(endDate);
  }

  public void setEndDate(Date endDate) {
    this.endDate = FerbDateUtils.freshDate(endDate);
  }

  public SystemCodeDescriptor getCounty() {
    return county;
  }

  public void setCounty(SystemCodeDescriptor county) {
    this.county = county;
  }

  public SystemCodeDescriptor getServiceComponent() {
    return serviceComponent;
  }

  public void setServiceComponent(SystemCodeDescriptor serviceComponent) {
    this.serviceComponent = serviceComponent;
  }

  public HOIVictim getFocusChild() {
    return focusChild;
  }

  public void setFocusChild(HOIVictim focusChild) {
    this.focusChild = focusChild;
  }

  public HOISocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(HOISocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  public List<HOIRelatedPerson> getParents() {
    return parents;
  }

  public void setParents(List<HOIRelatedPerson> parents) {
    this.parents = parents;
  }

  public AccessLimitation getAccessLimitation() {
    return accessLimitation;
  }

  public void setAccessLimitation(AccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public static void main(String[] args) throws Exception {
    HOICase kase = new HOICase();

    AccessLimitation accessLimitation = new AccessLimitation();
    accessLimitation.setLimitedAccessCode(LimitedAccessType.SEALED);
    accessLimitation.setLimitedAccessDate(new Date());
    accessLimitation.setLimitedAccessDescription("bla bla blah");
    SystemCodeDescriptor govtEntity = new SystemCodeDescriptor();
    govtEntity.setId((short) 1101);
    govtEntity.setDescription("Sacramento");
    accessLimitation.setLimitedAccessGovernmentEntity(govtEntity);
    kase.setAccessLimitation(accessLimitation);

    HOISocialWorker socialWorker = new HOISocialWorker();
    socialWorker.setFirstName("Worker First Name");
    socialWorker.setLastName("Worker Last Name");
    socialWorker.setId("jhgguhgjh");
    socialWorker
        .setLegacyDescriptor(new LegacyDescriptor("jhgguhgjh", "jhgguhgjh-hohj-jkj", new DateTime(),
            LegacyTable.STAFF_PERSON.getName(), LegacyTable.STAFF_PERSON.getDescription()));
    kase.setAssignedSocialWorker(socialWorker);

    SystemCodeDescriptor county = new SystemCodeDescriptor();
    county.setId((short) 1101);
    county.setDescription("Sacramento");
    kase.setCounty(county);

    kase.setEndDate(new Date());
    kase.setStartDate(new Date());

    HOIVictim victim = new HOIVictim();
    victim.setFirstName("Victim First Name");
    victim.setLastName("Victim Last Name");
    victim.setId("iiiiiii");
    victim.setLegacyDescriptor(new LegacyDescriptor("iiiiiii", "iiiiiii-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    victim.setLimitedAccessType(LimitedAccessType.SEALED);
    kase.setFocusChild(victim);

    kase.setId("ccccccccc");
    kase.setLegacyDescriptor(new LegacyDescriptor("ccccccccc", "ccccccccc-hohj-jkj", new DateTime(),
        LegacyTable.CASE.getName(), LegacyTable.CASE.getDescription()));

    SystemCodeDescriptor serviceComponent = new SystemCodeDescriptor();
    serviceComponent.setId((short) 1695);
    serviceComponent.setDescription("Permanent Placement");
    kase.setServiceComponent(serviceComponent);

    HOIRelatedPerson father = new HOIRelatedPerson();
    father.setFirstName("Father First Name");
    father.setLastName("Father Last Name");
    father.setId("fafafafafa");
    father.setLegacyDescriptor(new LegacyDescriptor("fafafafafa", "fafafafafa-hohj-jkj",
        new DateTime(), LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    father.setLimitedAccessType(LimitedAccessType.NONE);
    SystemCodeDescriptor fatherRelationship = new SystemCodeDescriptor();
    fatherRelationship.setId((short) 214);
    fatherRelationship.setDescription("Father/Son (Step)");
    father.setRelationship(fatherRelationship);

    HOIRelatedPerson mother = new HOIRelatedPerson();
    mother.setFirstName("Mother First Name");
    mother.setLastName("Mother Last Name");
    mother.setId("mmmmmmmmmm");
    mother.setLegacyDescriptor(new LegacyDescriptor("mmmmmmmmmm", "mmmmmmmmmm-hohj-jkj",
        new DateTime(), LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    mother.setLimitedAccessType(LimitedAccessType.NONE);
    SystemCodeDescriptor motherRelationship = new SystemCodeDescriptor();
    motherRelationship.setId((short) 250);
    motherRelationship.setDescription("Mother/Son (Adoptive)");
    mother.setRelationship(motherRelationship);

    List<HOIRelatedPerson> parents = new ArrayList<>();
    parents.add(father);
    parents.add(mother);
    kase.setParents(parents);

    String json = ObjectMapperUtils.createObjectMapper().writeValueAsString(kase);
    System.out.println(json);
  }

}

