package gov.ca.cwds.rest.api.persistence.ns;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.DomainException;
import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a StaffPerson
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "\"STFPERST\"")
public class StaffPersonNS extends PersistentObject {
  @Id
  @Column(name = "\"IDENTIFIER\"")
  private String id;

  @Column(name = "\"TWITTER_NM\"")
  private String twitterName;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public StaffPersonNS() {
    super();
  }

  /**
   * Constructor
   * 
   * @param staffPerson The domain object to construct this object from
   * @param lastUpdatedId the id of the last person to update this object
   */
  public StaffPersonNS(gov.ca.cwds.rest.api.domain.legacy.StaffPerson staffPerson,
      String lastUpdatedId) {
    super(lastUpdatedId);

    try {
      this.id = staffPerson.getId();
      this.twitterName = staffPerson.getTwitterName();
    } catch (DomainException e) {
      throw new PersistenceException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return StringUtils.trimToEmpty(id);
  }

  /**
   * @return the twitterName
   */
  public String getTwitterName() {
    return StringUtils.trimToEmpty(twitterName);
  }

}
