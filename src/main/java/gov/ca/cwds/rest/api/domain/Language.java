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
 * {@link DomainObject} representing an language
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
public class Language extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("language")
  @ApiModelProperty(example = "English")
  @Size(max = 50)
  String theLanguage;

  /**
   * Construct from persistence class
   * 
   * @param language persistence level language object
   */
  public Language(gov.ca.cwds.data.persistence.ns.Language language) {
    this.theLanguage = language.getLanguageCodeId();
  }

  /**
   * @param language - language
   */
  public Language(@JsonProperty("language") String language) {
    super();
    this.theLanguage = language;
  }

  /**
   * @return the language
   */
  public String getTheLanguage() {
    return theLanguage;
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
