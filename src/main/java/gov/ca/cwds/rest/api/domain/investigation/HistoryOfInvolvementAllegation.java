package gov.ca.cwds.rest.api.domain.investigation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"victim_last_name", "victim_first_name", "perpetrator_last_name",
    "perpetrator_first_name", "disposition_description", "allegation_description"})
public class HistoryOfInvolvementAllegation implements Serializable{
  private static final int PRIME = 31;

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

  public String getVictimLastName() {
    return victimLastName;
  }

  public void setVictimLastName(String victimLastName) {
    this.victimLastName = victimLastName;
  }

  public String getVictimFirstName() {
    return victimFirstName;
  }

  public void setVictimFirstName(String victimFirstName) {
    this.victimFirstName = victimFirstName;
  }

  public String getPerpetratorLastName() {
    return perpetratorLastName;
  }

  public void setPerpetratorLastName(String perpetratorLastName) {
    this.perpetratorLastName = perpetratorLastName;
  }

  public String getPereptratorFistName() {
    return pereptratorFistName;
  }

  public void setPereptratorFistName(String pereptratorFistName) {
    this.pereptratorFistName = pereptratorFistName;
  }

  public String getDispositionDescription() {
    return dispositionDescription;
  }

  public void setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
  }

  public String getAllegationDescription() {
    return allegationDescription;
  }

  public void setAllegationDescription(String allegationDescription) {
    this.allegationDescription = allegationDescription;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result =
        PRIME * result + ((allegationDescription == null) ? 0 : allegationDescription.hashCode());
    result =
        PRIME * result + ((dispositionDescription == null) ? 0 : dispositionDescription.hashCode());
    result = PRIME * result + ((pereptratorFistName == null) ? 0 : pereptratorFistName.hashCode());
    result = PRIME * result + ((perpetratorLastName == null) ? 0 : perpetratorLastName.hashCode());
    result = PRIME * result + ((victimFirstName == null) ? 0 : victimFirstName.hashCode());
    result = PRIME * result + ((victimLastName == null) ? 0 : victimLastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HistoryOfInvolvementAllegation that = (HistoryOfInvolvementAllegation) o;
    return Objects.equals(victimLastName, that.victimLastName) &&
        Objects.equals(victimFirstName, that.victimFirstName) &&
        Objects.equals(perpetratorLastName, that.perpetratorLastName) &&
        Objects.equals(pereptratorFistName, that.pereptratorFistName) &&
        Objects.equals(dispositionDescription, that.dispositionDescription) &&
        Objects.equals(allegationDescription, that.allegationDescription);
  }
}
