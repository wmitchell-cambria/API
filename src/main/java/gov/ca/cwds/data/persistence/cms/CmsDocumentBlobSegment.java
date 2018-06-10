package gov.ca.cwds.data.persistence.cms;

import java.util.Arrays;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnTransformer;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.TypedPersistentObject;

/**
 * {@link PersistentObject} represents a document blob record in TSBLOBT, the document segments
 * table.
 * 
 * <p>
 * Note that this entity class <strong>does not extend {@link CmsPersistentObject}</strong>, because
 * table TSBLOBT lacks the last update timestamp and last update user id fields.
 * </p>
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "TSBLOBT")
public class CmsDocumentBlobSegment implements TypedPersistentObject<VarargPrimaryKey>,
    Comparator<CmsDocumentBlobSegment>, Comparable<CmsDocumentBlobSegment> {

  private static final long serialVersionUID = -6101861394294752291L;

  @Id
  @Column(name = "DOC_HANDLE", length = 30)
  @NotNull
  @Size(min = 30, max = 30)
  @Pattern(regexp = "\\d{16}[*][A-Z0-9]+\\s*\\d{5}", message = "invalid DOC_HANDLE")
  private String docHandle;

  @Id
  @Column(name = "DOC_SEGSEQ", length = 4)
  @NotNull
  @Size(min = 4, max = 4)
  @Pattern(regexp = "\\d{4}")
  private String segmentSequence;

  @Column(name = "DOC_BLOB", length = 4000, insertable = true, updatable = true)
  @NotNull
  @Size(min = 1, max = 4000)
  @ColumnTransformer(read = "blob(DOC_BLOB)")
  private byte[] docBlob;

  /**
   * Default constructor
   * 
   * Required for Hibernate.
   */
  public CmsDocumentBlobSegment() {
    super();
  }

  /**
   * Convenience constructor.
   * 
   * @param docHandle document identifier
   * @param segmentSequence blob segment sequence
   * @param docBlob binary blob segment data
   */
  public CmsDocumentBlobSegment(String docHandle, String segmentSequence, byte[] docBlob) {
    super();
    this.docHandle = docHandle;
    this.segmentSequence = segmentSequence;
    this.docBlob = Arrays.copyOf(docBlob, docBlob.length); // SonarQube complaint
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public VarargPrimaryKey getPrimaryKey() {
    return new VarargPrimaryKey(this.getDocHandle(), this.getSegmentSequence());
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    int prime = 31;
    int result = 1;

    result = prime * result + ((docHandle == null) ? 0 : docHandle.hashCode());
    result = prime * result + ((segmentSequence == null) ? 0 : segmentSequence.hashCode());

    // 1) NOT part of unique key, 2) potentially large waste of processing to compute.
    // 3) if you got this far, well ... ;)
    return prime * result + ((docBlob == null) ? 0 : Arrays.hashCode(docBlob));
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof CmsDocumentBlobSegment)) {
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

    // 1) NOT part of unique key, 2) potentially large and waste of processing to compute.
    // 3) if you got this far, well ... ;)
    if (docBlob == null) {
      return other.docBlob == null;
    } else
      return Arrays.equals(docBlob, other.docBlob);

  }

  // ==================
  // ACCESSORS:
  // ==================

  /**
   * Blob segment sequence is a 4-digit, zero left-padded string, starting with "0001".
   * 
   * @return segment number
   */
  public String getSegmentSequence() {
    return segmentSequence;
  }

  /**
   * Sets the the segment's sequence number. See {@link #getSegmentSequence()} for details.
   * 
   * @param segmentSequence segment's sequence number
   * @see #getSegmentSequence()
   */
  public void setSegmentSequence(String segmentSequence) {
    this.segmentSequence = segmentSequence;
  }

  /**
   * Hex string of this segment's binary data.
   * 
   * @return hex string of segment data
   */
  public byte[] getDocBlob() {
    return docBlob;
  }

  /**
   * Sets the hexadecimal binary data for this segment, max size 4k.
   * 
   * @param docBlob hex of binary, compressed data for this segment
   */
  public void setDocBlob(byte[] docBlob) {
    this.docBlob = docBlob;
  }

  /**
   * Gets the document handle.
   * 
   * @return the document's id, doc_handle.
   */
  public String getDocHandle() {
    return docHandle;
  }

  /**
   * Sets the document's id, the document handle.
   * 
   * @param docHandle 30-character identifier.
   */
  public void setDocHandle(String docHandle) {
    this.docHandle = docHandle;
  }

  @Override
  public int compare(CmsDocumentBlobSegment o1, CmsDocumentBlobSegment o2) {
    final int first = o1.getDocHandle().compareTo(o2.getDocHandle());
    return first == 0 ? first
        : Integer.valueOf(o1.getSegmentSequence())
            .compareTo(Integer.valueOf(o2.getSegmentSequence()));
  }

  @Override
  public int compareTo(CmsDocumentBlobSegment o) {
    return compare(this, o);
  }

}
