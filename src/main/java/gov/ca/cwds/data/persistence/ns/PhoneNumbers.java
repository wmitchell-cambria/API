package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.Identifiable;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.ns.papertrail.HasPaperTrail;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * {@link PersistentObject} representing an PhoneNumbers
 * 
 * @author Intake Team 4
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "phone_numbers")
public class PhoneNumbers implements PersistentObject, HasPaperTrail, Identifiable<String> {

  @Id
  @GenericGenerator(
      name = "phone_numbers_id",
      strategy = "gov.ca.cwds.data.persistence.ns.utils.StringSequenceIdGenerator",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "phone_numbers_id_seq")
      }
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_numbers_id")
//  @SequenceGenerator(name = "phone_numbers_id", sequenceName = "phone_numbers_id_seq",
//      allocationSize = 50)
  @Column(name = "id")
  private String id;

  @Column(name = "number")
  private String number;

  @Column(name = "type")
  private String type;

  public PhoneNumbers() {
  }

  /**
   * @param number - the phone number
   * @param type - the phone number type
   */
  public PhoneNumbers(String number, String type) {
    this.number = number;
    this.type = type;
  }


  /**
   * Constructor
   *
   * @param phoneNumber The domain object to construct this object from
   */
  public PhoneNumbers(gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber) {
    this.number = phoneNumber.getPhoneNumber();
    this.type = phoneNumber.getPhoneType();
  }


  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the phone number
   */
  public String getNumber() {
    return number;
  }

  /**
   * @return the phone number type
   */
  public String getType() {
    return type;
  }

}
