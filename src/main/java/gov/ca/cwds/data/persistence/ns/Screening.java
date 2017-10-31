package gov.ca.cwds.data.persistence.ns;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import gov.ca.cwds.data.ns.NsPersistentObject;

/**
 * {@link NsPersistentObject} representing a Person.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "screenings")
public class Screening extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screenings_id_seq")
  @SequenceGenerator(name = "screenings_id_seq", sequenceName = "screenings_id_seq",
      allocationSize = 50)
  @Column(name = "id")
  private Long id;

  @Column(name = "reference")
  private String reference;

  @Column(name = "ended_at")
  @Type(type = "date")
  private Date endedAt;

  @Column(name = "incident_county")
  private String incidentCounty;

  @Column(name = "incident_date")
  @Type(type = "date")
  private Date incidentDate;

  @Column(name = "location_type")
  private String locationType;

  @Column(name = "communication_method")
  private String communicationMethod;

  @Column(name = "name")
  private String name;

  @Column(name = "response_time")
  private String responseTime;

  @Column(name = "screening_decision")
  private String screeningDecision;

  @Column(name = "started_at")
  @Type(type = "date")
  private Date startedAt;

  @Column(name = "report_narrative")
  private String narrative;

  @Column(name = "assignee")
  private String assignee;

  @Column(name = "additional_information")
  private String additionalInformation;

  @Column(name = "screening_decision_detail")
  private String screeningDecisionDetail;

  @Column(name = "safety_information")
  private String safetyInformation;

  @Column(name = "safety_alerts")
  private String[] safetyAlerts;

  @Column(name = "referral_id")
  private String referralId;

  @Column(name = "assignee_staff_id")
  private String assigneeStaffId;

  @Column(name = "access_restrictions")
  private String accessRestrictions;

  @Column(name = "restrictions_rationale")
  private String restrictionsRationale;

  @Column(name = "user_county_code")
  private String userCountyCode;

  @Column(name = "restrictions_date")
  private Date restrictionsDate;

  @Column(name = "indexable")
  private boolean indexable;


  /*
   * @OneToOne(cascade = CascadeType.ALL)
   * 
   * @JoinColumn(name = "contact_address_id") private Address contactAddress;
   * 
   * @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "screening") private
   * Set<Participant> participants = new HashSet<>(0);
   */

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Screening() {
    super();
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   */
  public Screening(String reference) {
    this.reference = reference;
  }

  /**
   * Constructor
   * 
   * @param reference The reference
   * @param endedAt The endedAt date
   * @param incidentCounty The incident county
   * @param incidentDate The incident date
   * @param locationType The location type
   * @param communicationMethod The communication method
   * @param name The name of the screening
   * @param responseTime The response time
   * @param screeningDecision The screening decision
   * @param startedAt The started at date
   * @param narrative The narrative
   * @param contactAddress The contact address
   * @param participants The list of participants
   */
  public Screening(String reference, Date endedAt, String incidentCounty, Date incidentDate,
      String locationType, String communicationMethod, String name, String responseTime,
      String screeningDecision, Date startedAt, String narrative, Address contactAddress,
      Set<Participant> participants) {
    super();
    this.reference = reference;
    this.endedAt = endedAt;
    this.incidentCounty = incidentCounty;
    this.incidentDate = incidentDate;
    this.locationType = locationType;
    this.communicationMethod = communicationMethod;
    this.name = name;
    this.responseTime = responseTime;
    this.screeningDecision = screeningDecision;
    this.startedAt = startedAt;
    this.narrative = narrative;

  }



  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Long getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @return the endedAt
   */
  public Date getEndedAt() {
    return endedAt;
  }

  /**
   * @return the incidentCounty
   */
  public String getIncidentCounty() {
    return incidentCounty;
  }

  /**
   * @return the incidentDate
   */
  public Date getIncidentDate() {
    return incidentDate;
  }

  /**
   * @return the locationType
   */
  public String getLocationType() {
    return locationType;
  }

  /**
   * @return the communicationMethod
   */
  public String getCommunicationMethod() {
    return communicationMethod;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the responseTime
   */
  public String getResponseTime() {
    return responseTime;
  }

  /**
   * @return the screeningDecision
   */
  public String getScreeningDecision() {
    return screeningDecision;
  }

  /**
   * @return the startedAt
   */
  public Date getStartedAt() {
    return startedAt;
  }

  /**
   * @return the narrative
   */
  public String getNarrative() {
    return narrative;
  }

  /**
   * @return the contactAddress
   */
  /*
   * public Address getContactAddress() { return contactAddress; }
   * 
   *//**
      * @return the participants
      *//*
         * public Set<Participant> getParticipants() { return participants; }
         */

}
