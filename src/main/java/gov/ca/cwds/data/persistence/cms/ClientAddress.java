package gov.ca.cwds.data.persistence.cms;


import static gov.ca.cwds.rest.util.FerbDateUtils.freshDate;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * {@link PersistentObject} representing a Client Address.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ClientAddress.findAll",
    query = "FROM ClientAddress")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ClientAddress.findAllUpdatedAfter",
    query = "FROM ClientAddress WHERE lastUpdatedTime > :after")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.ClientAddress.findByAddressAndClient",
    query = "FROM ClientAddress WHERE fkAddress = :addressId and fkClient = :clientId")
@Entity
@Table(name = "CL_ADDRT")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAddress extends BaseClientAddress {

  private static final long serialVersionUID = 1L;

  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKADDRS_T", nullable = false, insertable = false, updatable = false)
  private Address addresses;

  /**
   * referential integrity check.
   * <p>
   * Doesn't actually load the data. Just checks the existence of the parent address, client and
   * referral records.
   * </p>
   */
  @HashCodeExclude
  @EqualsExclude
  @ToStringExclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
  private Client client;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public ClientAddress() {
    super();
  }

  /**
   * @param id The identifier
   * @param addressType The addressType
   * @param bkInmtId The bkInmtId
   * @param effEndDt The effEndDt
   * @param effStartDt The effStartDt
   * @param fkAddress The fkAddress
   * @param fkClient The fkClient
   * @param homelessInd The homelessInd
   * @param fkReferral The fkReferral
   * @param addresses The addresses
   */
  public ClientAddress(String id, Short addressType, String bkInmtId, Date effEndDt,
      Date effStartDt, String fkAddress, String fkClient, String homelessInd, String fkReferral,
      Address addresses) {
    super();
    this.id = id;
    this.addressType = addressType;
    this.bkInmtId = bkInmtId;
    this.effEndDt = freshDate(effEndDt);
    this.effStartDt = freshDate(effStartDt);
    this.fkAddress = fkAddress;
    this.fkClient = fkClient;
    this.homelessInd = homelessInd;
    this.fkReferral = fkReferral;
    this.addresses = addresses;
  }

  /**
   * A convenience helper Constructor that includes lastUpdatedId
   * 
   * @param id The identifier
   * @param addressType The addressType
   * @param bkInmtId The bkInmtId
   * @param effEndDt The effEndDt
   * @param effStartDt The effStartDt
   * @param fkAddress The fkAddress
   * @param fkClient The fkClient
   * @param homelessInd The homelessInd
   * @param fkReferral The fkReferral
   * @param addresses The addresses
   * @param lastUpdateId - staff Id
   */
  public ClientAddress(String id, Short addressType, String bkInmtId, Date effEndDt,
      Date effStartDt, String fkAddress, String fkClient, String homelessInd, String fkReferral,
      Address addresses, String lastUpdateId) {
    super(lastUpdateId);
    this.id = id;
    this.addressType = addressType;
    this.bkInmtId = bkInmtId;
    this.effEndDt = freshDate(effEndDt);
    this.effStartDt = freshDate(effStartDt);
    this.fkAddress = fkAddress;
    this.fkClient = fkClient;
    this.homelessInd = homelessInd;
    this.fkReferral = fkReferral;
    this.addresses = addresses;
  }

  /**
   * @param id - ClientAddress Id
   * @param clientAddress - domain ClientAddress object
   * @param lastUpdateId - the Id of the last person update this object
   * @param lastUpdatedTime - The time when this object is last updated
   */
  public ClientAddress(String id, gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress,
      String lastUpdateId, Date lastUpdatedTime) {
    super(lastUpdateId, lastUpdatedTime);
    try {
      this.id = id;
      this.addressType = clientAddress.getAddressType();
      this.bkInmtId = clientAddress.getBookingOrInmateId();
      this.effEndDt = DomainChef.uncookDateString(clientAddress.getEffectiveEndDate());
      this.effStartDt = DomainChef.uncookDateString(clientAddress.getEffectiveStartDate());
      this.fkAddress = clientAddress.getAddressId();
      this.fkClient = clientAddress.getClientId();
      this.homelessInd = clientAddress.getHomelessInd();
      this.fkReferral = clientAddress.getReferralId();
    } catch (ApiException e) {
      throw new PersistenceException(e);
    }
  }

  /**
   * @return the address
   */
  public Address getAddresses() {
    return addresses;
  }

  public Client getClient() {
    return client;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
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
