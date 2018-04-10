package gov.ca.cwds.rest.api.domain;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.validation.Date;
import io.dropwizard.jackson.JsonSnakeCase;
import io.dropwizard.validation.OneOf;
import io.swagger.annotations.ApiModelProperty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.format.DateTimeFormat;

/**
 * {@link DomainObject} representing a Participant.
 *
 * @author CWDS API Team
 */
@JsonSnakeCase
@JsonPropertyOrder({"id", "legacySourceTable", "legacyId", "firstName", "lastName", "gender", "ssn",
    "dateOfBirth", "roles", "addresses", "races", "ethnicity"})
public class ParticipantIntakeApi extends ReportingDomain implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @ApiModelProperty(required = true, readOnly = false, value = "Participant Id", example = "12345")
  private String id;

  @JsonProperty("first_name")
  @ApiModelProperty(required = false, readOnly = false, value = "First Name", example = "John")
  @Size(max = 64)
  private String firstName;

  @JsonProperty("middle_name")
  @Size(min = 0, max = 64)
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "middle name")
  private String middleName;

  @JsonProperty("last_name")
  @ApiModelProperty(required = false, readOnly = false, value = "Last name", example = "Smith")
  @Size(max = 64)
  private String lastName;

  @JsonProperty("name_suffix")
  @ApiModelProperty(required = false, readOnly = false, value = "name suffix", example = "Jr.")
  private String nameSuffix;

  @JsonProperty("gender")
  @OneOf(value = {"male", "female", "unknown"})
  @ApiModelProperty(required = false, readOnly = false, value = "Gender Code", example = "male",
      allowableValues = "male, female, unknown")
  private String gender;

  @JsonProperty("ssn")
  @ApiModelProperty(required = false, readOnly = false, value = "", example = "123456789")
  // This regualr expression(regexp) validates the ssn should be only numeric and length 9
  @Pattern(regexp = "^(|[0-9]{9})$")
  private String ssn;

  @JsonProperty("date_of_birth")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @Type(type = "date")
  @ApiModelProperty(required = false, readOnly = false, value = "Date of Birth",
      example = "2001-09-11")
  @Date
  private String dateOfBirth;

  @JsonProperty("approximate_age")
  @ApiModelProperty(required = false, readOnly = false, value = "Approximate Age",
      example = "25")
  private String approximateAge;

  @JsonProperty("approximate_age_units")
  @OneOf(value = {"days", "weeks", "months", "years"})
  @ApiModelProperty(required = false, readOnly = false, value = "Approximate Age Units", example = "years",
      allowableValues = "days, weeks, months, years")
  private String approximateAgeUnits;

  @JsonProperty("languages")
  @ApiModelProperty(required = false, readOnly = false, dataType = "java.util.List",
      value = "", example = "American Sign Language", notes = "The Participant's Languages")
  private Set<String> languages;

  @JsonProperty("legacy_id")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Client Id",
      example = "ABC1234567")
  @Size(max = CMS_ID_LEN)
  private String legacyId;

  @JsonProperty("legacy_source_table")
  @ApiModelProperty(required = true, readOnly = false, value = "Legacy Source Table",
      example = "CLIENT_T")
  private String legacySourceTable;

  @ApiModelProperty(required = true, readOnly = false)
  @JsonProperty("legacy_descriptor")
  @Valid
  private LegacyDescriptor legacyDescriptor;


  /*
  * Workafoung for fields containing raw json
  * races
  * ethnicity
  *
  */
  @ApiModelProperty(required = true, readOnly = false, value = "Races",
     example = "['White', 'Black or African American']")
  private String races;
  @JsonRawValue
  public String getRaces(){
    return races;
  }
  public void setRaces(final String races){
    this.races = races;
  }
  @JsonProperty(value = "races")
  public void setRacesRaw(JsonNode jsonNode){
    setRaces(jsonNode.toString());
  }

  /*
   * Workafoung for fields containing raw json
   * races
   * ethnicity
   *
   */
  @ApiModelProperty(required = true, readOnly = false, value = "Ethnicity",
      dataType = "List[gov.ca.cwds.rest.api.domain.PhoneNumber]", example = "{\"hispanic_latino_origin\":\"Yes\",\"ethnicity_detail\":[\"Hispanic\"]}")
  private String ethnicity;
  @JsonRawValue
  public String getEthnicity(){
    return ethnicity;
  }
  public void setEthnicity(final String ethnicity){
    this.ethnicity = ethnicity;
  }
  @JsonProperty(value = "ethnicity")
  public void setEthnicityRaw(JsonNode jsonNode){
    setEthnicity(jsonNode.toString());
  }

  @JsonProperty("screening_id")
  @ApiModelProperty(required = false, readOnly = false, value = "Screening Id", example = "12345")
  private long screeningId;

  @Valid
  @JsonProperty("roles")
  @ApiModelProperty(required = true, readOnly = false, value = "Role of participant",
      dataType = "java.util.List", example = "['Victim', 'Mandated Reporter']")
  private Set<String> roles;

  @Valid
  @ApiModelProperty(dataType = "List[gov.ca.cwds.rest.api.domain.AddressIntakeApi]")
  @JsonProperty("addresses")
  private Set<AddressIntakeApi> addresses;

  @Valid
  @ApiModelProperty(dataType = "List[gov.ca.cwds.rest.api.domain.PhoneNumber]")
  @JsonProperty("phone_numbers")
  private Set<PhoneNumber> phoneNumbers;

  @JsonProperty("sealed")
  @ApiModelProperty(required = false, readOnly = false, value = "sealed", example = "true")
  private boolean sealed;

  @JsonProperty("sensitive")
  @ApiModelProperty(required = false, readOnly = false, value = "sensitive", example = "true")
  private boolean sensitive;

  /**
   * empty constructor
   */
  public ParticipantIntakeApi() {
    super();
  }

  /**
   * Constructor
   *
   * @param id The id of the Participant
   * @param legacySourceTable - legacy source table name
   * @param clientId - the legacy clientId
   * @param legacyDescriptor legacy descriptor
   * @param screeningId The screening Id
   * @param firstName The first Name
   * @param middleName The middle Name
   * @param lastName The last Name
   * @param nameSuffix The participants suffix Name
   * @param gender The gender
   * @param dateOfBirth The date Of Birth
   * @param ssn The social security number
   * @param roles The roles of the participant
   * @param addresses The addresses of the participant
   * @param races The race And Ethnicity
   * @param Ethnicity The race And Ethnicity
   * @throws ServiceException throw any exception
   */
  @JsonCreator
  public ParticipantIntakeApi(@JsonProperty("id") String id,
      @JsonProperty("legacy_source_table") String legacySourceTable,
      @JsonProperty("legacy_client_id") String clientId,
      @JsonProperty("legacy_descriptor") LegacyDescriptor legacyDescriptor,
      @JsonProperty("first_name") String firstName, @JsonProperty("middle_name") String middleName,
      @JsonProperty("last_name") String lastName, @JsonProperty("name_suffix") String nameSuffix,
      @JsonProperty("gender") String gender, @JsonProperty("ssn") String ssn,
      @JsonProperty("date_of_birth") String dateOfBirth,
      @JsonProperty("languages") Set<String> languages,
      @JsonProperty("screening_id") long screeningId,
      @JsonProperty("roles") Set<String> roles,
      @JsonProperty("addresses") Set<AddressIntakeApi> addresses
      ) {
    super();
    this.id = id;
    this.legacySourceTable = legacySourceTable;
    this.legacyId = clientId;
    this.legacyDescriptor = legacyDescriptor;
    this.screeningId = screeningId;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.ssn = ssn;
    this.roles = roles;
    this.addresses = addresses;

  }

  public ParticipantIntakeApi(ParticipantEntity participantEntity){
    super();
    this.id = participantEntity.getId();
    this.firstName = participantEntity.getFirstName();
    this.middleName = participantEntity.getMiddleName();
    this.lastName = participantEntity.getLastName();
    this.nameSuffix = participantEntity.getNameSuffix();
    this.gender = participantEntity.getGender();
    this.ssn = participantEntity.getSsn();
    if(participantEntity.getDateOfBirth() != null) {
      this.dateOfBirth = new SimpleDateFormat(DATE_FORMAT)
          .format(participantEntity.getDateOfBirth());
    }
    this.approximateAge = participantEntity.getApproximateAge();
    this.approximateAgeUnits = participantEntity.getApproximateAgeUnits();
    this.languages =  Arrays.stream(participantEntity.getLanguages().split(",")).collect(Collectors.toCollection(HashSet::new));
    this.legacyId = participantEntity.getLegacyId();
    this.legacySourceTable = participantEntity.getLegacySourceTable();
    this.legacyDescriptor = new LegacyDescriptor();
    this.races = participantEntity.getRaces();
    this.ethnicity = participantEntity.getEthnicity();
    this.screeningId = Long.valueOf(participantEntity.getScreening().getId());
    this.roles = new HashSet<>(Arrays.asList(participantEntity.getRoles().split(",")));

//    this.addresses
//    this.phoneNumbers

    this.sealed = Boolean.parseBoolean(participantEntity.getSealed());
    this.sensitive = Boolean.parseBoolean(participantEntity.getSensitive());


  }


  /**
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the legacy source table name
   */
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  /**
   * @param legacySourceTable - the legacy source table name
   */
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  /**
   * @return the legacy clientId
   */
  public String getLegacyId() {
    return legacyId;
  }

  /**
   * @param clientId - the legacy Id
   */
  public void setLegacyId(String clientId) {
    this.legacyId = clientId;
  }

  /**
   * @return the legacyDescriptor
   */
  public LegacyDescriptor getLegacyDescriptor() {
    if (legacyDescriptor == null) {
      legacyDescriptor = new LegacyDescriptor();
    }
    return legacyDescriptor;
  }

  /**
   * @param legacyDescriptor The legacy Descriptor
   */
  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {

    return middleName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the name suffix
   */
  public String getNameSuffix() {
    return nameSuffix;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @return the screeningId
   */
  public long getScreeningId() {
    return screeningId;
  }

  /**
   * @return the roles
   */
  public Set<String> getRoles() {
    return this.roles;
  }

  /**
   * @return the dateOfBirth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @return the ssn
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * @return the languages
   */
  public Set<String> getLanguages() {
    if (languages == null) {
      languages = new HashSet<>();
    }
    return languages;
  }

  /**
   * @return addresses
   */
  public Set<AddressIntakeApi> getAddresses() {
    if (addresses == null) {
      addresses = new HashSet<>();
    }
    return addresses;
  }

  /**
   * @param addresses - domain addresses
   */
  public void setAddresses(Set<AddressIntakeApi> addresses) {
    this.addresses = addresses;
  }

  /**
   * adds a set of addresses to a current set.
   *
   * @param addresses - domain addresses
   */
  public void addAddresses(Set<AddressIntakeApi> addresses) {
    if (addresses == null) {
      return;
    }
    getAddresses().addAll(addresses);
  }

  /**
   * @return phone numbers
   */
  public Set<PhoneNumber> getPhoneNumbers() {
    if (phoneNumbers == null) {
      phoneNumbers = new HashSet<>();
    }
    return phoneNumbers;
  }

  /**
   * @param phoneNumbers - domain addresses
   */
  public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  /**
   * adds a set of phone numbers to current s set.
   *
   * @param phoneNumbers - domain addresses
   */
  public void addPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    if (phoneNumbers == null) {
      return;
    }
    getPhoneNumbers().addAll(phoneNumbers);
  }



  /**
   * @return the raceAndEthnicity
   */

  public String getApproximateAge() {
    return approximateAge;
  }

  public String getApproximateAgeUnits() {
    return approximateAgeUnits;
  }

  public boolean isSealed() {
    return sealed;
  }

  public boolean isSensitive() {
    return sensitive;
  }

  /**
   * {@inheritDoc}
   *
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   *
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
