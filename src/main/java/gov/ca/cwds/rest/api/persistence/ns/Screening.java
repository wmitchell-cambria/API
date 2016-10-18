package gov.ca.cwds.rest.api.persistence.ns;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * {@link PersistentObject} representing a Person
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "hotline_contact")
public class Screening extends PersistentObject {

  @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

  // @Column(name = "response_type")
  // private String response_time;

  @Column(name = "screening_result")
  private String screeningDecision;

  @Column(name = "screening_start_datetime")
  @Type(type = "date")
  private Date startedAt;

  @Column(name = "screening_report_narrative")
  private String narrative;

  @Column(name = "contact_address_id")
  private Long addressId;

  private static int count = 8;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public Screening() {
    super();
  }



  public Screening(Long id, String reference, Date endedAt, String incidentCounty,
      Date incidentDate, String locationType, String communicationMethod, String name,
      String screeningDecision, Date startedAt, String narrative, Long addressId) {
    super();
    this.id = id;
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
    this.addressId = addressId;
  }


  /**
   * Constructor
   * 
   * @param staffPerson The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public Screening(gov.ca.cwds.rest.api.domain.Screening screening, Long lastUpdatedId) {
    super(lastUpdatedId);

    try {
      this.id = (long) count++;
      this.reference = screening.getReference();
      this.endedAt = DomainObject.uncookDateString(screening.getEnded_at());
      this.incidentCounty = screening.getIncident_county();
      this.incidentDate = DomainObject.uncookDateString(screening.getIncident_date());
      this.locationType = screening.getLocation_type();
      this.communicationMethod = screening.getCommunication_method();
      this.name = screening.getName();
      this.screeningDecision = screening.getScreening_decision();
      this.startedAt = DomainObject.uncookDateString(screening.getStarted_at());
      this.narrative = screening.getNarrative();
      // this.addressId = screening.;

    } catch (Exception e) {
      System.out.println("exception in screening constr");
    }
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
   * @return the addressId
   */
  public Long getAddressId() {
    return addressId;
  }

  /**
   * @param addressId the addressId to set
   */
  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

}
