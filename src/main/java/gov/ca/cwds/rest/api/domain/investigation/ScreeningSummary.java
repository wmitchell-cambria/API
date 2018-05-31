package gov.ca.cwds.rest.api.domain.investigation;

import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a Screening Summary
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
@JsonSnakeCase
@JsonPropertyOrder({"id", "name", "decision", "decision_detail", "safety_alerts",
    "safety_information", "additional_information", "started_at", "allegations"})
public class ScreeningSummary extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "1")
  private String id;

  @JsonProperty("name")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "Henderson Screening")
  private String name;

  @JsonProperty("decision")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "promote_to_referral")
  private String decision;

  @JsonProperty("decision_detail")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "immediate")
  private String decisionDetail;

  @JsonProperty("safety_alerts")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "[Dangerous Animal on Premises, Firearms in Home, Severe Mental Health Status]")
  private Set<String> safetyAlerts;

  @JsonProperty("safety_information")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "The animal at residence is a lion")
  private String safetyInformation;

  @JsonProperty("additional_information")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "There was excessive evidence of abuse")
  private String additionalInformation;

  @JsonProperty("started_at")
  @ApiModelProperty(required = false, readOnly = false, value = "",
      example = "2017-09-01T16:48:05.457Z")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainObject.TIMESTAMP_ISO8601_FORMAT)
  private Date startedAt;

  @JsonProperty("allegations")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "")
  private Set<SimpleAllegation> allegations;

  /**
   * Default
   */
  public ScreeningSummary() {
    super();
  }

  /**
   * Constructor
   * 
   * @param id the id
   * @param name name
   * @param decision decision
   * @param decisionDetail the decision detail
   * @param safetyAlerts the safety alerts
   * @param safetyInformation the safety information
   * @param additionalInformation the additional information
   * @param startedAt started at
   * @param allegations the allegations
   */
  public ScreeningSummary(@JsonProperty("id") String id, @JsonProperty("name") String name,
      @JsonProperty("decision") String decision,
      @JsonProperty("decision_detail") String decisionDetail,
      @JsonProperty("safety_alerts") Set<String> safetyAlerts,
      @JsonProperty("safety_information") String safetyInformation,
      @JsonProperty("additional_information") String additionalInformation,
      @JsonProperty("started_at") Date startedAt,
      @JsonProperty("allegations") Set<SimpleAllegation> allegations) {
    super();
    this.id = id;
    this.name = name;
    this.decision = decision;
    this.decisionDetail = decisionDetail;
    this.safetyAlerts = safetyAlerts;
    this.safetyInformation = safetyInformation;
    this.additionalInformation = additionalInformation;
    this.startedAt = freshDate(startedAt);
    this.allegations = allegations;
  }

  /**
   * Constructor - assigning Screening summary values
   * 
   * @param screeningEntity - ScreeningEntity object
   * @param allegations - list of allegations
   */
  public ScreeningSummary(ScreeningEntity screeningEntity, Set<SimpleAllegation> allegations) {
    safetyAlerts = new HashSet<>();
    this.name = screeningEntity.getName();
    Collections.addAll(this.safetyAlerts, screeningEntity.getSafetyAlerts());
    this.decision = screeningEntity.getScreeningDecision();
    this.additionalInformation = screeningEntity.getAdditionalInformation();
    this.decisionDetail = screeningEntity.getScreeningDecisionDetail();
    this.id = screeningEntity.getId();
    this.safetyInformation = screeningEntity.getSafetyInformation();
    this.startedAt = screeningEntity.getStartedAt() == null ?
        null : java.sql.Timestamp.valueOf(screeningEntity.getStartedAt());
    this.allegations = allegations;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the decision
   */
  public String getDecision() {
    return decision;
  }

  /**
   * @return the decisionDetail
   */
  public String getDecisionDetail() {
    return decisionDetail;
  }

  /**
   * @return the safetyAlerts
   */
  public Set<String> getSafetyAlerts() {
    return safetyAlerts != null ? safetyAlerts : new HashSet<>();
  }

  /**
   * @return the safetyInformation
   */
  public String getSafetyInformation() {
    return safetyInformation;
  }

  /**
   * @return the additionalInformation
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }

  /**
   * @return the startedAt
   */
  public Date getStartedAt() {
    return freshDate(startedAt);
  }

  /**
   * @return the allegations
   */
  public Set<SimpleAllegation> getAllegations() {
    return this.allegations != null ? this.allegations : new HashSet<>();
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
