package gov.ca.cwds.rest.api.domain;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing an race
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Race extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("race")
  @ApiModelProperty(example = "White")
  @Size(max = 50)
  String raceType;

  @JsonProperty("subrace")
  @ApiModelProperty(example = "European")
  @Size(max = 50)
  String subRaceType;

  /**
   * Construct from persistence class
   * 
   * @param race persistence level race object
   */
  public Race(gov.ca.cwds.data.persistence.ns.Race race) {
    this.raceType = race.getRaceType();
    this.subRaceType = race.getSubRaceType();
  }

  /**
   * @param race - race
   * @param subRace - sub race
   */
  public Race(@JsonProperty("race") String race, @JsonProperty("sub_race") String subRace) {
    super();
    this.raceType = race;
    this.subRaceType = subRace;
  }

  /**
   * @return the race
   */
  public String getRaceType() {
    return raceType;
  }

  /**
   * @param race the race to set
   */
  public void setRaceType(String race) {
    this.raceType = race;
  }

  /**
   * @return the subrace
   */
  public String getSubRaceType() {
    return subRaceType;
  }

  /**
   * @param subrace the subrace to set
   */
  public void setSubRaceType(String subrace) {
    this.subRaceType = subrace;
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
