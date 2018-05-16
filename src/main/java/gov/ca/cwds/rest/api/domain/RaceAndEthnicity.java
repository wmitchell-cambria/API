package gov.ca.cwds.rest.api.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.validation.IfCollectionContainsShortThen;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a RaceAndEthnicity.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"raceCode", "unableToDetermineCode", "hispanicCode", "hispanicOriginCode",
    "hispanicUnableToDetermineCode"})
@IfCollectionContainsShortThen.List({
    @IfCollectionContainsShortThen(ifProperty = "raceCode", thenProperty = "unableToDetermineCode",
        ifValue = RaceAndEthnicity.UNABLE_TO_DETERMINE,
        message = "Unable to determine code must be set if race codes include 6351"),
    @IfCollectionContainsShortThen(ifProperty = "hispanicCode",
        thenProperty = "hispanicUnableToDetermineCode",
        ifValue = RaceAndEthnicity.UNABLE_TO_DETERMINE,
        message = "Hispanic unable to determine code must be set if hispanic codes include 6351")})
public class RaceAndEthnicity extends ReportingDomain implements Request, Response {

  static final short UNABLE_TO_DETERMINE = 6351;

  private static final long serialVersionUID = 1L;

  private static final String EXAMPLE_RACE_CODES = "['839', '840']";

  @Valid
  @JsonProperty("race_codes")
  @ApiModelProperty(required = false, readOnly = false, value = "primary races",
      dataType = "java.util.List", example = EXAMPLE_RACE_CODES,
      allowableValues = EXAMPLE_RACE_CODES)
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.ETHNICITY, ignoreable = true)
  private List<Short> raceCode;

  @JsonProperty("unable_to_determine_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  @OneOf(value = {"A", "I", "K", ""}, ignoreCase = false, ignoreWhitespace = true)
  private String unableToDetermineCode;

  @Valid
  @JsonProperty("hispanic_codes")
  @ApiModelProperty(required = false, readOnly = false, value = "other (secondary) races",
      dataType = "java.util.List", example = "['3164']")
  @ValidSystemCodeId(required = false, category = SystemCodeCategoryId.ETHNICITY, ignoreable = true)
  private List<Short> hispanicCode;

  @NotNull
  @OneOf(value = {"D", "N", "U", "X", "Y", "Z", ""}, ignoreCase = false, ignoreWhitespace = true)
  @JsonProperty("hispanic_origin_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "X")
  private String hispanicOriginCode;

  @OneOf(value = {"A", "I", "K", ""}, ignoreCase = false, ignoreWhitespace = true)
  @JsonProperty("hispanic_unable_to_determine_code")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "A")
  private String hispanicUnableToDetermineCode;

  /**
   * empty constructor
   */
  public RaceAndEthnicity() {
    super();
  }

  /**
   * @param raceCode The race code
   * @param unableToDetermineCode The unable to determine code
   * @param hispanicCode The hispanic code
   * @param hispanicOriginCode The hispanic origin code
   * @param hispanicUnableToDetermineCode The hispanic unable to determine code
   */
  @JsonCreator
  public RaceAndEthnicity(@JsonProperty("race_codes") List<Short> raceCode,
      @JsonProperty("unable_to_determine_code") String unableToDetermineCode,
      @JsonProperty("hispanic_codes") List<Short> hispanicCode,
      @JsonProperty("hispanic_orgin_code") String hispanicOriginCode,
      @JsonProperty("hispanic_unable_to_determine_code") String hispanicUnableToDetermineCode) {
    super();
    this.raceCode = raceCode;
    this.unableToDetermineCode = unableToDetermineCode;
    this.hispanicCode = hispanicCode;
    this.hispanicOriginCode = hispanicOriginCode;
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
  }

  /**
   * 
   * @param client - client object
   * @param raceCode -list of raceCode
   * @param hispanicCode - list of hispanic Code
   */
  public RaceAndEthnicity(Client client, List<Short> raceCode, List<Short> hispanicCode) {
    this.raceCode = raceCode;
    this.hispanicCode = hispanicCode;
    hispanicOriginCode = client.getHispanicOriginCode();
    hispanicUnableToDetermineCode = client.getEthUnableToDetReasonCode();
    unableToDetermineCode = client.getHispUnableToDetReasonCode();
  }

  /**
   * @return the raceCode
   */
  public List<Short> getRaceCode() {
    return raceCode;
  }

  /**
   * @return the unableToDetermineCode
   */
  public String getUnableToDetermineCode() {
    return unableToDetermineCode;
  }

  /**
   * @return the hispanicCode
   */
  public List<Short> getHispanicCode() {
    return hispanicCode;
  }

  /**
   * @return the hispanicOriginCode
   */
  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  /**
   * @return the hispanicUnableToDetermineCode
   */
  public String getHispanicUnableToDetermineCode() {
    return hispanicUnableToDetermineCode;
  }

  /**
   * @param raceCode - raceCode
   */
  public void setRaceCode(List<Short> raceCode) {
    this.raceCode = raceCode;
  }

  /**
   * @param unableToDetermineCode - unableToDetermineCode
   */
  public void setUnableToDetermineCode(String unableToDetermineCode) {
    this.unableToDetermineCode = unableToDetermineCode;
  }

  /**
   * @param hispanicCode - hispanicCode
   */
  public void setHispanicCode(List<Short> hispanicCode) {
    this.hispanicCode = hispanicCode;
  }

  /**
   * @param hispanicOriginCode - hispanicOriginCode
   */
  public void setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
  }

  /**
   * @param hispanicUnableToDetermineCode - hispanicUnableToDetermineCode
   */
  public void setHispanicUnableToDetermineCode(String hispanicUnableToDetermineCode) {
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
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
