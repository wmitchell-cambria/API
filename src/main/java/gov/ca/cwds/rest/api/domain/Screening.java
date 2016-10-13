package gov.ca.cwds.rest.api.domain;

import gov.ca.cwds.rest.validation.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO - RDB add constraints
/**
 * {@link DomainObject} representing a screening
 * 
 * @author CWDS API Team
 */
public class Screening extends DomainObject {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("reference")
  private String reference;

  @JsonProperty("ended_at")
  private String ended_at;

  @JsonProperty("incident_county")
  private String incident_county;

  @Date
  @JsonProperty("incident_date")
  private String incident_date;

  @JsonProperty("location_type")
  private String location_type;

  @JsonProperty("method_of_referral")
  private String method_of_referral;

  @JsonProperty("name")
  private String name;

  @JsonProperty("response_time")
  private String response_time;

  @JsonProperty("screening_decision")
  private String screening_decision;

  @JsonProperty("started_at")
  private String started_at;

  @JsonProperty("narrative")
  private String narrative;

  /**
   * Constructor
   * 
   * @param id The id
   * @param reference The reference
   * @param ended_at The ended at
   * @param incident_county The incident county
   * @param incident_date The incident date
   * @param location_type The location type
   * @param method_of_referral The method of referral
   * @param name The name
   * @param response_time The response time
   * @param screening_decision The screening decision
   * @param started_at The started at
   * @param narrative The narrative
   */
  @JsonCreator
  public Screening(@JsonProperty("id") Long id, @JsonProperty("reference") String reference,
      @JsonProperty("ended_at") String ended_at,
      @JsonProperty("incident_county") String incident_county,
      @JsonProperty("incident_date") String incident_date,
      @JsonProperty("location_type") String location_type,
      @JsonProperty("method_of_referral") String method_of_referral,
      @JsonProperty("name") String name, @JsonProperty("response_time") String response_time,
      @JsonProperty("screening_decision") String screening_decision,
      @JsonProperty("started_at") String started_at, @JsonProperty("narrative") String narrative) {
    super();
    this.id = id;
    this.reference = reference;
    this.ended_at = ended_at;
    this.incident_county = incident_county;
    this.incident_date = incident_date;
    this.location_type = location_type;
    this.method_of_referral = method_of_referral;
    this.name = name;
    this.response_time = response_time;
    this.screening_decision = screening_decision;
    this.started_at = started_at;
    this.narrative = narrative;
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
   * @return the ended_at
   */
  public String getEnded_at() {
    return ended_at;
  }

  /**
   * @return the incident_county
   */
  public String getIncident_county() {
    return incident_county;
  }

  /**
   * @return the incident_date
   */
  public String getIncident_date() {
    return incident_date;
  }

  /**
   * @return the location_type
   */
  public String getLocation_type() {
    return location_type;
  }

  /**
   * @return the method_of_referral
   */
  public String getMethod_of_referral() {
    return method_of_referral;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the response_time
   */
  public String getResponse_time() {
    return response_time;
  }

  /**
   * @return the screening_decision
   */
  public String getScreening_decision() {
    return screening_decision;
  }

  /**
   * @return the started_at
   */
  public String getStarted_at() {
    return started_at;
  }

  /**
   * @return the narrative
   */
  public String getNarrative() {
    return narrative;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ended_at == null) ? 0 : ended_at.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((incident_county == null) ? 0 : incident_county.hashCode());
    result = prime * result + ((incident_date == null) ? 0 : incident_date.hashCode());
    result = prime * result + ((location_type == null) ? 0 : location_type.hashCode());
    result = prime * result + ((method_of_referral == null) ? 0 : method_of_referral.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((narrative == null) ? 0 : narrative.hashCode());
    result = prime * result + ((reference == null) ? 0 : reference.hashCode());
    result = prime * result + ((response_time == null) ? 0 : response_time.hashCode());
    result = prime * result + ((screening_decision == null) ? 0 : screening_decision.hashCode());
    result = prime * result + ((started_at == null) ? 0 : started_at.hashCode());
    return result;
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Screening other = (Screening) obj;
    if (ended_at == null) {
      if (other.ended_at != null) {
        return false;
      }
    } else if (!ended_at.equals(other.ended_at)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (incident_county == null) {
      if (other.incident_county != null) {
        return false;
      }
    } else if (!incident_county.equals(other.incident_county)) {
      return false;
    }
    if (incident_date == null) {
      if (other.incident_date != null) {
        return false;
      }
    } else if (!incident_date.equals(other.incident_date)) {
      return false;
    }
    if (location_type == null) {
      if (other.location_type != null) {
        return false;
      }
    } else if (!location_type.equals(other.location_type)) {
      return false;
    }
    if (method_of_referral == null) {
      if (other.method_of_referral != null) {
        return false;
      }
    } else if (!method_of_referral.equals(other.method_of_referral)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (narrative == null) {
      if (other.narrative != null) {
        return false;
      }
    } else if (!narrative.equals(other.narrative)) {
      return false;
    }
    if (reference == null) {
      if (other.reference != null) {
        return false;
      }
    } else if (!reference.equals(other.reference)) {
      return false;
    }
    if (response_time == null) {
      if (other.response_time != null) {
        return false;
      }
    } else if (!response_time.equals(other.response_time)) {
      return false;
    }
    if (screening_decision == null) {
      if (other.screening_decision != null) {
        return false;
      }
    } else if (!screening_decision.equals(other.screening_decision)) {
      return false;
    }
    if (started_at == null) {
      if (other.started_at != null) {
        return false;
      }
    } else if (!started_at.equals(other.started_at)) {
      return false;
    }
    return true;
  }


}
