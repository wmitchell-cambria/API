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

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((docHandle == null) ? 0 : docHandle.hashCode());
    result = prime * result + ((segmentSequence == null) ? 0 : segmentSequence.hashCode());
    result = prime * result + ((docBlob == null) ? 0 : docBlob.hashCode());

    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CmsDocumentBlobSegment other = (CmsDocumentBlobSegment) obj;

    if (docHandle == null) {
      if (other.docHandle != null) {
        return false;
      }
    } else if (!docHandle.equals(other.docHandle)) {
      return false;
    }
    if (segmentSequence == null) {
      if (other.segmentSequence != null) {
        return false;
      }
    } else if (!segmentSequence.equals(other.segmentSequence)) {
      return false;
    }
    if (docBlob == null) {
      if (other.docBlob != null) {
        return false;
      }
    } else if (!docBlob.equals(other.docBlob)) {
      return false;
    }

    return true;
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
