package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * Referral for HOI.
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "startDate", "endDate", "county", "responseTime", "reporter",
    "assignedSocialWorker", "accessLimitation", "allegations", "legacyDescriptor"})
public class HOIReferral extends ApiObjectIdentity
    implements ApiTypedIdentifier<String>, Request, Response {

  private static final Logger LOGGER = LoggerFactory.getLogger(HOIReferral.class);

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-22")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-23")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("response_time")
  private SystemCodeDescriptor responseTime;

  @JsonProperty("reporter")
  private HOIReporter reporter;

  @JsonProperty("assigned_social_worker")
  private SocialWorker assignedSocialWorker;

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("allegations")
  private List<HOIAllegation> allegations = new ArrayList<>();

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public HOIReferral() {
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

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return FerbDateUtils.freshDate(startDate);
  }

  /**
   * @param startDate - startDate
   */
  public void setStartDate(Date startDate) {
    this.startDate = FerbDateUtils.freshDate(startDate);
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return FerbDateUtils.freshDate(endDate);
  }

  /**
   * @param endDate - endDate
   */
  public void setEndDate(Date endDate) {
    this.endDate = FerbDateUtils.freshDate(endDate);
  }

  /**
   * @return the county
   */
  public SystemCodeDescriptor getCounty() {
    return county;
  }

  /**
   * @param county - county
   */
  public void setCounty(SystemCodeDescriptor county) {
    this.county = county;
  }

  /**
   * @return the responseTime
   */
  public SystemCodeDescriptor getResponseTime() {
    return responseTime;
  }

  /**
   * @param responseTime - responseTime
   */
  public void setResponseTime(SystemCodeDescriptor responseTime) {
    this.responseTime = responseTime;
  }

  /**
   * @return the reporter
   */
  public HOIReporter getReporter() {
    return reporter;
  }

  /**
   * @param reporter - reporter
   */
  public void setReporter(HOIReporter reporter) {
    this.reporter = reporter;
  }

  /**
   * @return the assignedSocialWorker
   */
  public SocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  /**
   * @param assignedSocialWorker - assignedSocialWorker
   */
  public void setAssignedSocialWorker(SocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  /**
   * @return the allegations
   */
  public List<HOIAllegation> getAllegations() {
    return allegations;
  }

  /**
   * @param allegations - allegations
   */
  public void setAllegations(List<HOIAllegation> allegations) {
    this.allegations = allegations;
  }

  /**
   * @return the accessLimitation
   */
  public AccessLimitation getAccessLimitation() {
    return accessLimitation;
  }

  /**
   * @param accessLimitation - accessLimitation
   */
  public void setAccessLimitation(AccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
  }

  /**
   * @return the legacyDescriptor
   */
  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor - legacyDescriptor
   */
  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public static void main(String[] args) throws Exception {
    HOIReferral referral = new HOIReferral();

    AccessLimitation accessLimitation = new AccessLimitation();
    accessLimitation.setLimitedAccessCode(LimitedAccessType.SEALED);
    accessLimitation.setLimitedAccessDate(new Date());
    accessLimitation.setLimitedAccessDescription("bla bla blah");
    SystemCodeDescriptor govtEntity = new SystemCodeDescriptor();
    govtEntity.setId((short) 1101);
    govtEntity.setDescription("Sacramento");
    accessLimitation.setLimitedAccessGovernmentEntity(govtEntity);
    referral.setAccessLimitation(accessLimitation);


    HOIAllegation allegation = new HOIAllegation();
    SystemCodeDescriptor type = new SystemCodeDescriptor();
    type.setId((short) 2179);
    type.setDescription("Physical Abuse");

    SystemCodeDescriptor aleggationDisposition = new SystemCodeDescriptor();
    aleggationDisposition.setId((short) 45);
    aleggationDisposition.setDescription("Substantiated");
    allegation.setDisposition(aleggationDisposition);

    allegation.setId("jhdgfkhaj");
    allegation.setLegacyDescriptor(new LegacyDescriptor("jhdgfkhaj", "jhdgfkhaj-hohj-jkj",
        new DateTime(), LegacyTable.ALLEGATION.getName(), LegacyTable.ALLEGATION.getDescription()));

    Victim victim = new Victim();
    victim.setFirstName("Victim First Name");
    victim.setLastName("Victim Last Name");
    victim.setId("iiiiiii");
    victim.setLegacyDescriptor(new LegacyDescriptor("iiiiiii", "iiiiiii-hohj-jkj", new DateTime(),
        LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    victim.setLimitedAccessType(LimitedAccessType.SEALED);
    allegation.setVictim(victim);

    Perpetrator perpetrator = new Perpetrator();
    perpetrator.setFirstName("Perpetrator First Name");
    perpetrator.setLastName("Perpetrator Last Name");
    perpetrator.setId("pppppppp");
    perpetrator.setLegacyDescriptor(new LegacyDescriptor("pppppppp", "pppppppp-hohj-jkj",
        new DateTime(), LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));
    perpetrator.setLimitedAccessType(LimitedAccessType.NONE);
    allegation.setPerpetrator(perpetrator);

    List<HOIAllegation> allegations = new ArrayList<>();
    allegations.add(allegation);
    referral.setAllegations(allegations);

    SocialWorker socialWorker = new SocialWorker();
    socialWorker.setFirstName("Worker First Name");
    socialWorker.setLastName("Worker Last Name");
    socialWorker.setId("jhgguhgjh");
    socialWorker
        .setLegacyDescriptor(new LegacyDescriptor("jhgguhgjh", "jhgguhgjh-hohj-jkj", new DateTime(),
            LegacyTable.STAFF_PERSON.getName(), LegacyTable.STAFF_PERSON.getDescription()));
    referral.setAssignedSocialWorker(socialWorker);


    SystemCodeDescriptor county = new SystemCodeDescriptor();
    county.setId((short) 1101);
    county.setDescription("Sacramento");
    referral.setCounty(county);

    referral.setEndDate(new Date());
    referral.setId("jhvuify0X5");

    referral.setLegacyDescriptor(new LegacyDescriptor("jhvuify0X5", "jhgguhgjh-hohj-jkj",
        new DateTime(), LegacyTable.REFERRAL.getName(), LegacyTable.REFERRAL.getDescription()));


    HOIReporter reporter = new HOIReporter();
    reporter.setFirstName("Reporter First Name");
    reporter.setLastName("Reporter Last Name");
    reporter.setId("jhgjhgjh");
    reporter.setLegacyDescriptor(new LegacyDescriptor("jhgjhgjh", "jhgjhgjh-hohj-jkj",
        new DateTime(), LegacyTable.REPORTER.getName(), LegacyTable.REPORTER.getDescription()));
    referral.setReporter(reporter);

    SystemCodeDescriptor responseTime = new SystemCodeDescriptor();
    responseTime.setId((short) 1518);
    responseTime.setDescription("5 Day");
    referral.setResponseTime(responseTime);

    referral.setStartDate(new Date());

    String json = ObjectMapperUtils.createObjectMapper().writeValueAsString(referral);
    LOGGER.info(json);
  }


}
