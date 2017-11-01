package gov.ca.cwds.data.persistence.ns;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import gov.ca.cwds.data.persistence.PersistentObject;

@Entity
@Table(name = "allegations")
public class Allegation implements PersistentObject {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "screening_id")
  private String screeningId;

  @Column(name = "perpetrator_id")
  private String perpetratorId;

  @Column(name = "victim_id")
  private String victimId;

  @Column(name = "created_at")
  private String createdAt;

  @Column(name = "updated_at")
  private String updatedAt;


  @Column(name = "allegation_types")
  @Type(type = "gov.ca.cwds.rest.util.StringArrayType")
  private String[] allegationTypes;


  @ManyToOne
  @JoinColumn(name = "screening_id", insertable = false, updatable = false)
  private gov.ca.cwds.data.persistence.ns.Screening screening;

  @Override
  public Serializable getPrimaryKey() {
    return this.getId();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the screeningId
   */
  public String getScreeningId() {
    return screeningId;
  }

  /**
   * @return the perpetratorId
   */
  public String getPerpetratorId() {
    return perpetratorId;
  }

  /**
   * @return the victimId
   */
  public String getVictimId() {
    return victimId;
  }

  /**
   * @return the createdAt
   */
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   * @return the updatedAt
   */
  public String getUpdatedAt() {
    return updatedAt;
  }

  /**
   * @return the allegationTypes
   */

  public String[] getAllegationTypes() {
    return allegationTypes;
  }

  /**
   * @param allegationTypes the allegationTypes to set
   */
  public void setAllegationTypes(String[] allegationTypes) {
    this.allegationTypes = allegationTypes;
  }


  /**
   * @return the screening
   */

  public Screening getScreening() {
    return screening;
  }



}
