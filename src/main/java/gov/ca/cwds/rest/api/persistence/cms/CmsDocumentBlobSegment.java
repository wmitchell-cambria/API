package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.cms.ReferralClient.PrimaryKey;

/**
 * {@link PersistentObject} represents a record in TSBLOBT.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "TSBLOBT")
public class CmsDocumentBlobSegment implements PersistentObject, Serializable {

  private static final long serialVersionUID = -6101861394294752291L;

  @Id
  @Column(name = "DOC_HANDLE")
  private String docHandle;

  @Id
  @Column(name = "DOC_SEGSEQ")
  private String segmentSequence;

  @ColumnTransformer(read = "blob(DOC_BLOB)")
  private String docBlob;

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocumentBlobSegment() {
    super();
  }

  public CmsDocumentBlobSegment(String docHandle, String segmentSequence, String docBlob) {
    super();
    this.docHandle = docHandle;
    this.segmentSequence = segmentSequence;
    this.docBlob = docBlob;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public Serializable getPrimaryKey() {
    return new PrimaryKey(this.getDocHandle(), this.getSegmentSequence());
  }

  public String getSegmentSequence() {
    return segmentSequence;
  }

  public void setSegmentSequence(String segmentSequence) {
    this.segmentSequence = segmentSequence;
  }

  public String getDocBlob() {
    return docBlob;
  }

  public void setDocBlob(String docBlob) {
    this.docBlob = docBlob;
  }

  public String getDocHandle() {
    return docHandle;
  }

  public void setDocHandle(String docHandle) {
    this.docHandle = docHandle;
  }

}
