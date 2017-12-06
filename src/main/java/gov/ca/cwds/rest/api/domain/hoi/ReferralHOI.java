package gov.ca.cwds.rest.api.domain.hoi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.AccessLimitation;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
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
public class ReferralHOI extends ApiObjectIdentity
    implements ApiTypedIdentifier<String>, Request, Response {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralHOI.class);

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
  private ReporterHOI reporter;

  @JsonProperty("assigned_social_worker")
  private SocialWorker assignedSocialWorker;

  @JsonProperty("access_limitation")
  private AccessLimitation accessLimitation;

  @JsonProperty("allegations")
  private List<AllegationHOI> allegations = new ArrayList<>();

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public ReferralHOI() {
    // No-argument constructor
  }

  /**
   * @param clientId
   * @param client
   * @param referral
   * @param staffPerson
   * @param reporter
   * @param allegations
   */
  public ReferralHOI(String clientId, Client client, Referral referral, StaffPerson staffPerson,
      Reporter reporter, Set<Allegation> allegations) {

    this.id = referral.getId();
    this.startDate = referral.getReceivedDate();
    this.endDate = referral.getClosureDate();
    this.county = new SystemCodeDescriptor(referral.getGovtEntityType(),
        SystemCodeCache.global().getSystemCodeShortDescription(referral.getGovtEntityType()));
    this.responseTime = new SystemCodeDescriptor(referral.getReferralResponseType(),
        SystemCodeCache.global().getSystemCodeShortDescription(referral.getReferralResponseType()));
    this.assignedSocialWorker =
        new SocialWorker(staffPerson.getId(), staffPerson.getFirstName(), staffPerson.getLastName(),
            new LegacyDescriptor(staffPerson.getId(), null,
                new DateTime(staffPerson.getLastUpdatedTime()), LegacyTable.STAFF_PERSON.getName(),
                null));
    this.accessLimitation = new AccessLimitation(LimitedAccessType.NONE,
        referral.getLimitedAccessDate(), referral.getLimitedAccessDesc(),
        new SystemCodeDescriptor(referral.getLimitedAccessGovtAgencyType(), SystemCodeCache.global()
            .getSystemCodeShortDescription(referral.getLimitedAccessGovtAgencyType())));
    for (Allegation allegation : allegations) {
      if (clientId.equals(allegation.getVictimClientId())
          || clientId.equals(allegation.getPerpetratorClientId())) {
        this.allegations.add(new AllegationHOI());
      }
    }
    this.legacyDescriptor = new LegacyDescriptor(referral.getId(), null,
        new DateTime(referral.getLastUpdatedTime()), LegacyTable.REFERRAL.getName(), null);
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
  public ReporterHOI getReporter() {
    return reporter;
  }

  /**
   * @param reporter - reporter
   */
  public void setReporter(ReporterHOI reporter) {
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
  public List<AllegationHOI> getAllegations() {
    return allegations;
  }

  /**
   * @param allegations - allegations
   */
  public void setAllegations(List<AllegationHOI> allegations) {
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
    ReferralHOI referral = new ReferralHOI();

    AccessLimitation accessLimitation = new AccessLimitation();
    accessLimitation.setLimitedAccessCode(LimitedAccessType.SEALED);
    accessLimitation.setLimitedAccessDate(new Date());
    accessLimitation.setLimitedAccessDescription("bla bla blah");
    SystemCodeDescriptor govtEntity = new SystemCodeDescriptor();
    govtEntity.setId((short) 1101);
    govtEntity.setDescription("Sacramento");
    accessLimitation.setLimitedAccessGovernmentEntity(govtEntity);
    referral.setAccessLimitation(accessLimitation);


    AllegationHOI allegation = new AllegationHOI();
    allegation.setDescription("Allegation description");

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

    List<AllegationHOI> allegations = new ArrayList<>();
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


    ReporterHOI reporter = new ReporterHOI();
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
