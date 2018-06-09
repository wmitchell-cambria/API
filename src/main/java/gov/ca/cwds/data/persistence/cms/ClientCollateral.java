package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.validation.ValidSystemCodeId;

/**
 * {@link PersistentObject} representing a Client Collateral person.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "CLN_COLT",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"FKCLIENT_T", "FKCOLTRL_T"})})
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCollateral extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Column(name = "ACTIVE_IND")
  private String activeIndicator;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @ValidSystemCodeId(required = true,
      category = SystemCodeCategoryId.CLIENT_COLLATERAL_RELATIONSHIP)
  @Type(type = "short")
  @Column(name = "COL_RELC")
  private Short collateralClientReporterRelationshipType;

  @Column(name = "COMNT_DSC")
  @ColumnTransformer(read = "trim(COMNT_DSC)")
  private String commentDescription;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String thirdId;

  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String clientId;

  @Column(name = "FKCOLTRL_T", length = CMS_ID_LEN)
  private String collateralIndividualId;

  /**
   * #147241489: referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent client record.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
  private Client client;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCOLTRL_T", nullable = false, updatable = false, insertable = false)
  private CollateralIndividual collateralIndividual;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientCollateral() {
    super();
  }

  /**
   * Constructor
   * 
   * @param activeIndicator = Indicates if relationship to CollateralIndividual is active (Y)
   * @param collateralClientReporterRelationshipType = Collateral Client Rptr Relationship Type in
   *        System Codes
   * @param commentDescription - description
   * @param clientId - foreign key Client table
   * @param collateralIndividualId - foreign key to CollateralIndividual table
   * @param thirdId - Unique key
   */
  public ClientCollateral(String activeIndicator, Short collateralClientReporterRelationshipType,
      String commentDescription, String clientId, String collateralIndividualId, String thirdId) {
    super();
    this.activeIndicator = activeIndicator;
    this.collateralClientReporterRelationshipType = collateralClientReporterRelationshipType;
    this.commentDescription = commentDescription;
    this.clientId = clientId;
    this.collateralIndividualId = collateralIndividualId;
    this.thirdId = thirdId;
  }

  /**
   * Constructor.
   * 
   * @param thirdId unique key
   * @param clientCollateral the domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time when this object is last updated
   */
  public ClientCollateral(String thirdId,
      gov.ca.cwds.rest.api.domain.cms.ClientCollateral clientCollateral, String lastUpdatedId,
      Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
    this.activeIndicator = clientCollateral.getActiveIndicator();
    this.collateralClientReporterRelationshipType =
        clientCollateral.getCollateralClientReporterRelationshipType();
    this.commentDescription = clientCollateral.getCommentDescription();
    this.clientId = clientCollateral.getClientId();
    this.collateralIndividualId = clientCollateral.getCollateralIndividualId();
    this.thirdId = thirdId;
  }

  @SuppressWarnings("javadoc")
  public String getActiveIndicator() {
    return activeIndicator;
  }

  @SuppressWarnings("javadoc")
  public Short getCollateralClientReporterRelationshipType() {
    return collateralClientReporterRelationshipType;
  }

  /**
   * @return commentDescription
   */
  public String getCommentDescription() {
    return commentDescription;
  }

  @SuppressWarnings("javadoc")
  public String getClientId() {
    return clientId;
  }

  @SuppressWarnings("javadoc")
  public String getCollateralIndividualId() {
    return collateralIndividualId;
  }

  @SuppressWarnings("javadoc")
  public String getThirdId() {
    return thirdId;
  }

  public Client getClient() {
    return client;
  }

  public CollateralIndividual getCollateralIndividual() {
    return collateralIndividual;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryKey() {
    return getThirdId();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
