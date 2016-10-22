package gov.ca.cwds.rest.api.persistence.ns;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * {@link NsPersistentObject} representing a Person
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "hotline_contact")
public class Screening extends NsPersistentObject {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hotline_contact_id")
  @SequenceGenerator(name = "seq_hotline_contact_id", sequenceName = "seq_hotline_contact_id",
      allocationSize = 50)
  @Column(name = "hotline_contact_id")
  private Long id;

  @Column(name = "hotline_contact_reference")
  private String reference;

  @Column(name = "screening_end_datetime")
  @Type(type = "date")
  private Date endedAt;

  @Column(name = "hotline_contact_county")
  private String incidentCounty;

  @Column(name = "incident_datetime")
  @Type(type = "date")
  private Date incidentDate;

  @Column(name = "location_type")
  private String locationType;

  @Column(name = "hotline_communication_method")
  private String communicationMethod;

  @Column(name = "hotline_contact_name")
  private String name;

  @Column(name = "screening_result")
  private String screeningDecision;

  @Column(name = "screening_start_datetime")
  @Type(type = "date")
  private Date startedAt;

  @Column(name = "screening_report_narrative")
  private String narrative;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contact_address_id")
  private Address contactAddress;

  @Column(name = "hotline_contact_participant_array")
  private String participantIds;

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
   * @param screeningDecision The screening decision
   * @param startedAt The started at date
   * @param narrative The narrative
   * @param contactAddress The contact address
   * @param participantIds The list of participant ids
   */
  public Screening(String reference, Date endedAt, String incidentCounty, Date incidentDate,
      String locationType, String communicationMethod, String name, String screeningDecision,
      Date startedAt, String narrative, Address contactAddress, String participantIds) {
    super();
    this.reference = reference;
    this.endedAt = endedAt;
    this.incidentCounty = incidentCounty;
    this.incidentDate = incidentDate;
    this.locationType = locationType;
    this.communicationMethod = communicationMethod;
    this.name = name;
    this.screeningDecision = screeningDecision;
    this.startedAt = startedAt;
    this.narrative = narrative;
    this.contactAddress = contactAddress;
    this.participantIds = participantIds;
  }



  /**
   * Constructor
   * 
   * @param id The id
   * @param screeningRequest The screenRequest
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Screening(Long id, gov.ca.cwds.rest.api.domain.ScreeningRequest screeningRequest,
      Long lastUpdatedId) {
    super(lastUpdatedId);

    this.id = id;
    this.reference = screeningRequest.getReference();
    this.endedAt = DomainObject.uncookDateString(screeningRequest.getEnded_at());
    this.incidentCounty = screeningRequest.getIncident_county();
    this.incidentDate = DomainObject.uncookDateString(screeningRequest.getIncident_date());
    this.locationType = screeningRequest.getLocation_type();
    this.communicationMethod = screeningRequest.getCommunication_method();
    this.name = screeningRequest.getName();
    this.screeningDecision = screeningRequest.getScreening_decision();
    this.startedAt = DomainObject.uncookDateString(screeningRequest.getStarted_at());
    this.narrative = screeningRequest.getNarrative();
    if (screeningRequest.getAddress() != null) {
      this.contactAddress = new Address(screeningRequest.getAddress(), null);
    }
    this.participantIds =
        screeningRequest.getParticipant_ids().toString().replace("[", "").replace("]", "");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
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
   * @return the participantIds
   */
  public String getParticipantIds() {
    return participantIds;
  }

  /**
   * @return the contactAddress
   */
  public Address getContactAddress() {
    return contactAddress;
  }



}
