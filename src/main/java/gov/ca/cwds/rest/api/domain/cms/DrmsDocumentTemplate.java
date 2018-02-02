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
 * @author CWDS API Team
 */
public class DrmsDocumentTemplate extends ReportingDomain implements Request, Response, Serializable {

  private static final long serialVersionUID = 1L;

  @Size(min = 1, max = 10)
  @ApiModelProperty(
    value =
        "THIRD_ID - This is a system generated unique number that supplements user supplied data in "
            + "the primary key. The Third ID attribute will be used as part of the new Primary Key in combination "
            + "with user supplied data. It will also be used alone as a separate key for direct access. This "
            + "Third ID is composed of a base 62 Creation Timestamp and the STAFF_PERSON ID (a sequential 3 digit "
            + "base 62 number generated by the system).",
    example = "F0jeDaf00F"
  )
  private String thirdId;

  @NotNull
  @ApiModelProperty(
    required = true,
    value =
        "APPLICATION_CONTEXT_TYPE - A system generated number assigned to each  pre-defined group of "
            + "documents/reports which will be made available for a given user context within the application "
            + "(e.g., Client note book, Hearing note book, Case note book, etc.).  For example, when a user "
            + "opens the Hearing note book, the only group of documents he/she may have access to are, Petition, "
            + "Report to Court, Notice of Hearing, ... but not the Case Plan Document, Agency Foster Parent "
            + "Agreement,  etc.).",
    example = "82"
  )
  private Short applicationContextType;

  @NotNull
  @Size(min = 1, max = 8)
  @ApiModelProperty(
    required = true,
    value =
        "DOCUMENT_DOS_FILE_PREFIX_NAME - A pre-defined five character prefix DOS file name used to uniquely "
            + "identify each type of document or report associated with a specific DOCUMENT_TEMPLATE "
            + "(e.g. cspld = Case Plan Document, petnd = Petition Document, etc). This is the first five characters "
            + "of the file name the user will see in the MS. Word directory. For example, each Petition Template "
            + "may be used to generate 5, 10, 20, PETITION_DOCUMENTs. Each PETITION_DOCUMENT will have a DOS file "
            + "name in the MS. Word directory of 'petnd001.doc', 'petnd002.doc', 'petnd003.doc', 'petnd999.doc', etc). "
            + "The last three digits numeric sequence number will be system generated for each CASE to which a DOCUMENT "
            + "is being associated with. When a user wants to work on any one of those PETITION_DOCUMENTs, this "
            + "'petnd999' file name will be used to uniquely identify the PETITION_DOCUMENT file to DOS. This name "
            + "will be defined when a template is created and saved in MS. Word.",
    example = "inalgdes"
  )
  private String documentDOSFilePrefixName;

  @NotNull
  @ApiModelProperty(
    required = true,
    value =
        "GOVERNMENT_ENTITY_TYPE - This attribute is used to distinguish between 'System Templates' and "
            + "'User Entered Templates', also known as 'County Specific Templates'. If the Government "
            + "Entity Type = zero, then the template is a system template (i.e. templates for documents and "
            + "reports that are populated by the application and are delivered with the system). If the Government "
            + "Entity Type is not equal to zero, then the template is a user entered template. User entered templates "
            + "can be created by the counties as well as by CDSS.  The attribute selection will be provided by two "
            + "code tables: Government Entity Type and Template Category Type.  If the template is created by a "
            + "county, the Government Entity Type code table provides the list of counties and the attribute will "
            + "be the system generated number which represents the county of creation (e.g. Napa, Sacramento, "
            + "Fresno, etc) If the template is created by CDSS, the Template Category Type code table provides "
            + "template category and the attribute will be the system generated number which represents the category "
            + "(e.g. CDSS or CDSS Adoptions.)",
    example = "1101"
  )
  private Short govermentEntityType;

  @NotNull
  @Size(min = 1, max = 30)
  @ApiModelProperty(
          required = true,
          value = "A 30 characters unique name assigned by the DRMS for each DOCUMENT TEMPLATE to be used by the " +
                  "application to access the document from DRMS.    This template handle name will be generated each " +
                  "time the template is created or modified.",
          example = "6515111113080420*RAMESHA 00051"
  )
  private String cmsDocumentId;

  @NotNull
  @ApiModelProperty(
    required = true,
    value =
        "LANGUAGE_TYPE - The system generated number which identifies the type of language in which the "
            + "template's text is written (e.g., Spanish, English, etc.).",
    example = "1253"
  )
  private Short languageType;

  @Size(min = 1, max = 40)
  @ApiModelProperty(
          required = true,
          value = "TITLE_NAME - A descriptive name assigned to each template  associated with each type of DOCUMENT or " +
                  "report to be generated (e.g., Notice Of Hearing, Subpoena, Petition, Report to Court, etc.).    " +
                  "This is the name which will be displayed to the user in the document select grids within each given " +
                  "user context.  (See 'application context type' for detail description and examples.)",
          example = "Screener Narrative"
  )
  private String titleName;

  @NotNull
  @Size(min = 1, max = 1)
  @ApiModelProperty(
          required = true,
          value = "TRANSACTION_TYPE - Used to define whether a Document uses a transaction and what type of Document " +
                  "transaction it is (e.g., LRT, SRT).",
          example = "N"
  )
  private String transactionType;

  /**
   * @param thirdId template identifier
   * @param applicationContextType applicationContextType
   * @param documentDOSFilePrefixName  documentDOSFilePrefixName
   * @param govermentEntityType  govermentEntityType
   * @param cmsDocumentId  cmsDocumentId
   * @param languageType  languageType
   * @param titleName  titleName
   * @param transactionType  transactionType
   *
   */
  public DrmsDocumentTemplate(@JsonProperty("thirdId")String thirdId,
              @JsonProperty("applicationContextType")Short applicationContextType,
              @JsonProperty("documentDOSFilePrefixName")String documentDOSFilePrefixName,
              @JsonProperty("govermentEntityType")Short govermentEntityType,
              @JsonProperty("cmsDocumentId")String cmsDocumentId,
              @JsonProperty("languageType")Short languageType,
              @JsonProperty("titleName")String titleName,
              @JsonProperty("transactionType")String transactionType) {
    this.thirdId = thirdId;
    this.applicationContextType = applicationContextType;
    this.documentDOSFilePrefixName = documentDOSFilePrefixName;
    this.govermentEntityType = govermentEntityType;
    this.cmsDocumentId = cmsDocumentId;
    this.languageType = languageType;
    this.titleName = titleName;
    this.transactionType = transactionType;
  }

  /**
   * Construct from persistence layer CMS DrmsDocumentTemplate
   *
   * @param drmsDocumentTemplate The domain object to construct this object from
   */
  public DrmsDocumentTemplate(gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate drmsDocumentTemplate) {
    this.thirdId = drmsDocumentTemplate.getThirdId();
    this.applicationContextType = drmsDocumentTemplate.getApplicationContextType();
    this.documentDOSFilePrefixName = drmsDocumentTemplate.getDocumentDOSFilePrefixName();
    this.govermentEntityType = drmsDocumentTemplate.getGovermentEntityType();
    this.cmsDocumentId = drmsDocumentTemplate.getCmsDocumentId();
    this.languageType = drmsDocumentTemplate.getLanguageType();
    this.titleName = drmsDocumentTemplate.getTitleName();
    this.transactionType = drmsDocumentTemplate.getTransactionType();
  }


  /**
   * @return the
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the thirdId
   */
  public Short getApplicationContextType() {
    return applicationContextType;
  }

  /**
   * @return the documentDOSFilePrefixName
   */
  public String getDocumentDOSFilePrefixName() {
    return documentDOSFilePrefixName;
  }

  /**
   * @return the govermentEntityType
   */
  public Short getGovermentEntityType() {
    return govermentEntityType;
  }

  /**
   * @return the cmsDocumentId
   */
  public String getCmsDocumentId() {
    return cmsDocumentId;
  }

  /**
   * @return the languageType
   */
  public Short getLanguageType() {
    return languageType;
  }

  /**
   * @return the titleName
   */
  public String getTitleName() {
    return titleName;
  }

  /**
   * @return the transactionType
   */
  public String getTransactionType() {
    return transactionType;
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