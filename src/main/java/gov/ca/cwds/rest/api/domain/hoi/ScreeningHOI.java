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
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.util.FerbDateUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * Screening for HOI.
 * 
 * @author CWDS API Team
 */
public class ScreeningHOI extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "ABC Screening")
  private String name;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("start_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-08-23")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty("end_date")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "2017-09-23")
  private Date endDate;

  @JsonProperty("county")
  private SystemCodeDescriptor county;

  @JsonProperty("decision")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "promote to referral")
  private String decision;

  @JsonProperty("decision_detail")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "drug counseling")
  private String decisionDetail;

  @JsonProperty("reporter")
  private ReporterHOI reporter;

  @JsonProperty("assigned_social_worker")
  private SocialWorker assignedSocialWorker;

  @JsonProperty("all_people")
  private List<PersonHOI> allPeople = new ArrayList<>();

  /**
   * No-argument constructor
   */
  public ScreeningHOI() {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDecisionDetail() {
    return decisionDetail;
  }

  public void setDecisionDetail(String decisionDetail) {
    this.decisionDetail = decisionDetail;
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

  public ReporterHOI getReporter() {
    return reporter;
  }

  public void setReporter(ReporterHOI reporter) {
    this.reporter = reporter;
  }

  public SocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(SocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }


  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }

  public List<PersonHOI> getAllPeople() {
    return allPeople;
  }

  public void setAllPeople(List<PersonHOI> allPeople) {
    this.allPeople = allPeople;
  }

  public static void main(String[] args) throws Exception {
    ScreeningHOI screening = new ScreeningHOI();

    SocialWorker socialWorker = new SocialWorker();
    socialWorker.setFirstName("Worker First Name");
    socialWorker.setLastName("Worker Last Name");
    socialWorker.setId("jhgguhgjh");
    socialWorker
        .setLegacyDescriptor(new LegacyDescriptor("jhgguhgjh", "jhgguhgjh-hohj-jkj", new DateTime(),
            LegacyTable.STAFF_PERSON.getName(), LegacyTable.STAFF_PERSON.getDescription()));
    screening.setAssignedSocialWorker(socialWorker);


    SystemCodeDescriptor county = new SystemCodeDescriptor();
    county.setId((short) 1101);
    county.setDescription("Sacramento");
    screening.setCounty(county);

    screening.setEndDate(new Date());
    screening.setId("1234");
    screening.setName("ABC Screening");

    ReporterHOI reporter = new ReporterHOI();
    reporter.setFirstName("Reporter First Name");
    reporter.setLastName("Reporter Last Name");
    reporter.setId("jhgjhgjh");
    reporter.setLegacyDescriptor(new LegacyDescriptor("jhgjhgjh", "jhgjhgjh-hohj-jkj",
        new DateTime(), LegacyTable.REPORTER.getName(), LegacyTable.REPORTER.getDescription()));
    screening.setReporter(reporter);

    screening.setStartDate(new Date());

    screening.setDecision("promote to referral");
    screening.setDecisionDetail("drug counseling");

    PersonHOI person1 = new PersonHOI();
    person1.setFirstName("John");
    person1.setLastName("S");
    person1.setId("bbbbbbbbb");
    person1.setLegacyDescriptor(new LegacyDescriptor("bbbbbbbbb", "bbbbbbbbb-hohj-jkj",
        new DateTime(), LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));


    PersonHOI person2 = new PersonHOI();
    person2.setFirstName("Jane");
    person2.setLastName("S");
    person2.setId("aaaaaaa");
    person2.setLegacyDescriptor(new LegacyDescriptor("aaaaaaaaa", "aaaaaaaaa-hohj-jkj",
        new DateTime(), LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription()));

    List<PersonHOI> people = new ArrayList<>();
    people.add(person1);
    people.add(person2);
    screening.setAllPeople(people);
    String json = ObjectMapperUtils.createObjectMapper().writeValueAsString(screening);
    System.out.println(json);
  }
}
