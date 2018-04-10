package gov.ca.cwds.data.persistence.ns;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * {@link PersistentObject} representing paper trail.
 *
 * @author Intake Team 4
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "varsions")
public class PaperTrail implements PersistentObject {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "varsions_id")
  @SequenceGenerator(name = "varsions_id", sequenceName = "varsions_id_seq")
  private Long id;

  @Column(name = "item_type")
  private String itemType;

  @Column(name = "item_id")
  private Integer itemId;

  @Column(name = "event")
  private String event;

  @Column(name = "whodunnit")
  private String whoDunnIt;

//  @Type(type = "json")
  @Column(name = "object")
  private String object;

//  @Type(type = "json")
  @Column(name = "object_change")
  private String objectChange;

  @Type(type = "timestamp")
  @Column(name = "created_at")
  private Date createdAt;

  public PaperTrail(String itemType, String itemId, String event) {
    this(itemType, Integer.valueOf(itemId), event);
  }

  public PaperTrail(String itemType, Integer itemId, String event) {
    this.itemType = itemType;
    this.itemId = itemId;
    this.event = event;
    this.whoDunnIt = RequestExecutionContext.instance().getStaffId();
    this.createdAt = new Date();
  }

  @Override
  public String getPrimaryKey() {
    return id.toString();
  }

  public Long getId() {
    return id;
  }

  public String getItemType() {
    return itemType;
  }

  public Integer getItemId() {
    return itemId;
  }

  public String getEvent() {
    return event;
  }

  public String getWhoDunnIt() {
    return whoDunnIt;
  }

  public String getObject() {
    return object;
  }

  public String getObjectChange() {
    return objectChange;
  }

  public Date getCreatedAt() {
    return createdAt;
  }
}
