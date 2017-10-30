package gov.ca.cwds.rest.api.domain.investigation;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"victim_last_name", "victim_first_name", "perpetrator_last_name",
    "perpetrator_first_name", "disposition_description", "allegation_description"})
public class HistoryOfInvolvementAllegation implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("victim_last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Victim's last name",
      example = "Smith")
  private String victimLastName;

  @JsonProperty("victim_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Victim's first name",
      example = "Cody")
  private String victimFirstName;

  @JsonProperty("perpetrator_last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Perpetrator's last name",
      example = "Berk")
  private String perpetratorLastName;

  @JsonProperty("perpetrator_first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Perpetrator's first name",
      example = "Jeremy")
  private String pereptratorFistName;

  @JsonProperty("disposition_description")
  @ApiModelProperty(required = false, readOnly = false,
      value = "Description of allegation disposition", example = "this probaly happened")
  private String dispositionDescription;

  @JsonProperty("allegation_description")
  @ApiModelProperty(required = false, readOnly = false, value = "Description of allegation",
      example = "Physical Abuse")
  private String allegationDescription;

  /**
   * 
   */
  public HistoryOfInvolvementAllegation() {
    super();
  }

  /**
   * @param victimLastName - victim's last name
   * @param victimFirstName - victim's first name
   * @param perpetratorLastName - perpetrator's last name
   * @param pereptratorFistName - perpetrator's first name
   * @param dispositionDescription - description of the disposition
   * @param allegationDescription - description of the allegation
   */
  public HistoryOfInvolvementAllegation(String victimLastName, String victimFirstName,
      String perpetratorLastName, String pereptratorFistName, String dispositionDescription,
      String allegationDescription) {
    super();
    this.victimLastName = victimLastName;
    this.victimFirstName = victimFirstName;
    this.perpetratorLastName = perpetratorLastName;
    this.pereptratorFistName = pereptratorFistName;
    this.dispositionDescription = dispositionDescription;
    this.allegationDescription = allegationDescription;
  }

  /**
   * @return - victims last name
   */
  public String getVictimLastName() {
    return victimLastName;
  }

  /**
   * @param victimLastName - victims last name
   */
  public void setVictimLastName(String victimLastName) {
    this.victimLastName = victimLastName;
  }

  /**
   * @return - victims first name
   */
  public String getVictimFirstName() {
    return victimFirstName;
  }

  /**
   * @param victimFirstName - victims first name
   */
  public void setVictimFirstName(String victimFirstName) {
    this.victimFirstName = victimFirstName;
  }

  /**
   * @return - perpetrator last name
   */
  public String getPerpetratorLastName() {
    return perpetratorLastName;
  }

  /**
   * @param perpetratorLastName - perpetrator last name
   */
  public void setPerpetratorLastName(String perpetratorLastName) {
    this.perpetratorLastName = perpetratorLastName;
  }

  /**
   * @return - perpetrator first name
   */
  public String getPereptratorFistName() {
    return pereptratorFistName;
  }

  /**
   * @param perpetratorFirstName - perpetrator last name
   */
  public void setPereptratorFistName(String perpetratorFirstName) {
    this.pereptratorFistName = perpetratorFirstName;
  }

  /**
   * @return - disposition description
   */
  public String getDispositionDescription() {
    return dispositionDescription;
  }

  /**
   * @param dispositionDescription - disposition description
   */
  public void setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
  }

  /**
   * @return - allegation description
   */
  public String getAllegationDescription() {
    return allegationDescription;
  }

  /**
   * @param allegationDescription - allegation description
   */
  public void setAllegationDescription(String allegationDescription) {
    this.allegationDescription = allegationDescription;
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
