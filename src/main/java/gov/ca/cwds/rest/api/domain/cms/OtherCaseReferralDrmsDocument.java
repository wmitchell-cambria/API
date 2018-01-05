package gov.ca.cwds.rest.api.domain.cms;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * {@link DomainObject} representing an drmsDocument
 *
 * @author Intake Team 4
 */
public class OtherCaseReferralDrmsDocument  extends ReportingDomain implements Request, Response, Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(min = 1, max = 10)
  @ApiModelProperty(
    required = true,
    value = "Mandatory Foreign key that IS_IDENTIFIED_AS a DRMS_DOCUMENT.",
    example = "F0jeDaf00F"
  )
  private String drmsDocumentId;

  @NotNull
  @Size(min = 1, max = 2)
  @ApiModelProperty(
    required = true,
    value =
        "COUNTY_SPECIFIC_CODE - A code, with values between '01' and '58' or '99' that indicates which "
            + "county has primary assignment responsibility for the CASE or REFERRAL that this row belongs to. "
            + "The value for each county is identical to the Logical ID value for the county in the "
            + "Government_Entity_Type code table.",
    example = "99"
  )
  private String countySpecificCode;

  @NotNull
  @ApiModelProperty(
    required = true,
    value =
        "TITLE_NAME - A descriptive name assigned to each external document by the user to identify "
            + "the type of DOCUMENT or report to be generated and/or attached to a REFERRAL (e.g. follow up "
            + "letters, summary notes, external reports received, etc). This is the name which will be "
            + "displayed to the user in the document select grids within each given user context. "
            + "(See 'application context type' in the entity type DOCUMENT_TEMPLATE for detail description and examples.)",
    example = "Request for Home Evaluation"
  )
  private String titleName;

  @Size(min = 1, max = 10)
  @ApiModelProperty(
    value = "Optional Foreign key that IS_ATTACHED_TO a REFERRAL.",
    example = "07Xs6mg0Dv"
  )
  private String referralId;

  @NotNull
  @ApiModelProperty(
    required = true,
    value =
        "DOCUMENT_EXTENSION_TYPE - The file extension for the document.  The selection choices will be "
            + "provided by the Document Extension Type code table, therefore this attribute will store the SysId "
            + "of the code value.",
    example = "6372"
  )
  private Short documentExtensionTypeId;

  @ApiModelProperty(
    value = "The size of the compressed document in kilobytes.",
    example = "100"
  )
  private Integer documentLengthKb;

  /**
   * @param drmsDocumentId - primary Key (The id of DrmsDocument)
   * @param countySpecificCode - countySpecificCode
   * @param titleName - titleName
   * @param referralId - referralId
   * @param documentExtensionTypeId - documentExtensionTypeId
   * @param documentLengthKb - documentLengthKb
   */
  public OtherCaseReferralDrmsDocument(@JsonProperty("drmsDocumentId") String drmsDocumentId,
                 @JsonProperty("countySpecificCode")String countySpecificCode,
                 @JsonProperty("titleName") String titleName,
                 @JsonProperty("referralId") String referralId,
                 @JsonProperty("documentExtensionTypeId") Short documentExtensionTypeId,
                 @JsonProperty("documentLengthKb") Integer documentLengthKb) {
    super();
    this.drmsDocumentId = drmsDocumentId;
    this.countySpecificCode = countySpecificCode;
    this.titleName = titleName;
    this.referralId = referralId;
    this.documentExtensionTypeId = documentExtensionTypeId;
    this.documentLengthKb = documentLengthKb;
  }



  /**
   * Construct from persistence layer CMS otherCaseReferralDrmsDocument.
   *
   * @param persistedOtherCaseReferralDrmsDocument persistence level otherCaseReferralDrmsDocument
   */
  public OtherCaseReferralDrmsDocument(gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument persistedOtherCaseReferralDrmsDocument) {
    super();
    this.drmsDocumentId = persistedOtherCaseReferralDrmsDocument.getDrmsDocumentId();
    this.countySpecificCode = persistedOtherCaseReferralDrmsDocument.getCountySpecificCode();
    this.titleName = persistedOtherCaseReferralDrmsDocument.getTitleName();
    this.referralId = persistedOtherCaseReferralDrmsDocument.getReferralId();
    this.documentExtensionTypeId = persistedOtherCaseReferralDrmsDocument.getDocumentExtensionTypeId();
    this.documentLengthKb = persistedOtherCaseReferralDrmsDocument.getDocumentLengthKb();
  }


  /**
   * @return the drmsDocumentId
   */
  public String getDrmsDocumentId() {
    return drmsDocumentId;
  }

  /**
   * @return the countySpecificCode
   */
  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  /**
   * @return the titleName
   */
  public String getTitleName() {
    return titleName;
  }

  /**
   * @return the referralId
   */
  public String getReferralId() {
    return referralId;
  }

  /**
   * @return the documentExtensionTypeId
   */
  public Short getDocumentExtensionTypeId() {
    return documentExtensionTypeId;
  }

  /**
   * @return the documentLengthKb
   */
  public Integer getDocumentLengthKb() {
    return documentLengthKb;
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
