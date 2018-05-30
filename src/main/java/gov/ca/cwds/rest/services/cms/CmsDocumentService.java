package gov.ca.cwds.rest.services.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link CmsDocument}.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentService implements TypedCrudsService<String, CmsDocument, CmsDocument> {

  private static final class WorkDeleteBlobSegments implements Work {

    final String docId;
    final CmsDocumentService parent;

    WorkDeleteBlobSegments(String docId, CmsDocumentService parent) {
      this.docId = docId;
      this.parent = parent;
    }

    @Override
    public void execute(Connection con) throws SQLException {
      try (final PreparedStatement delStmt = con.prepareStatement(parent.blobsDelete())) {
        delStmt.setString(1, docId);
        delStmt.executeUpdate();
      }
    }

  }

  private static final class WorkInsertBlobSegments implements Work {

    final String docId;
    final CmsDocumentService parent;
    final List<CmsDocumentBlobSegment> blobs;

    WorkInsertBlobSegments(String docId, CmsDocumentService parent,
        List<CmsDocumentBlobSegment> blobs) {
      this.docId = docId;
      this.parent = parent;
      this.blobs = blobs;
    }

    @Override
    public void execute(Connection con) throws SQLException {
      try (final PreparedStatement delStmt = con.prepareStatement(parent.blobsDelete());
          final Statement insStmt = con.createStatement()) {
        delStmt.setString(1, docId);
        delStmt.executeUpdate();

        for (CmsDocumentBlobSegment blob : blobs) {
          insStmt.executeUpdate(parent.blobToInsert(blob));
        }
      }
    }

  }

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentService.class);
  private static final String PRIMARY_KEY = "primaryKey={}";

  private CmsDocumentDao dao;

  /**
   * Constructor.
   * 
   * @param dao The {@link Dao} handling {@link CmsDocumentDao} objects.
   */
  @Inject
  public CmsDocumentService(CmsDocumentDao dao) {
    this.dao = dao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public CmsDocument find(String primaryKey) {
    LOGGER.debug(PRIMARY_KEY, primaryKey);
    CmsDocument retval = null;
    String base64Doc;

    gov.ca.cwds.data.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      doc.setCompressionMethod(
          doc.getCompressionMethod() != null ? doc.getCompressionMethod().trim() : "");
      doc.setDocAuth(doc.getDocAuth() != null ? doc.getDocAuth().trim() : "");
      doc.setDocName(doc.getDocName() != null ? doc.getDocName().trim() : "");
      doc.setDocServ(doc.getDocServ() != null ? doc.getDocServ().trim() : "");

      base64Doc = dao.decompressDoc(doc);
      retval = new CmsDocument(doc);
      retval.setBase64Blob(base64Doc);
    } else {
      LOGGER.warn("EMPTY document!");
    }

    return retval;
  }

  /**
   * Create binary document. Force PKWare compression for new documents. LZW is for legacy documents
   * only.
   *
   * @param request domain document
   */
  @Override
  public CmsDocument create(CmsDocument request) {
    gov.ca.cwds.data.persistence.cms.CmsDocument doc =
        new gov.ca.cwds.data.persistence.cms.CmsDocument(request);
    CmsDocument retval = null;
    String base64Doc = request.getBase64Blob();
    if (StringUtils.isNotBlank(request.getDocAuth())) {
      doc.setDocAuth(request.getDocAuth().trim());
    }
    if (StringUtils.isNotBlank(request.getDocName())) {
      doc.setDocName(request.getDocName().trim());
    }
    if (StringUtils.isNotBlank(request.getDocServ())) {
      doc.setDocServ(request.getDocServ().trim());
    }

    doc.setCompressionMethod(CmsDocumentDao.COMPRESSION_TYPE_PK_FULL);
    final List<CmsDocumentBlobSegment> blobs = dao.compressDoc(doc, request.getBase64Blob().trim());
    doc.setBlobSegments(new HashSet<>(blobs));
    insertBlobs(doc, blobs);

    try {
      doc = dao.create(doc);
      retval = new CmsDocument(doc);
      retval.setBase64Blob(base64Doc);
      return retval;

    } catch (EntityExistsException e) {
      LOGGER.info("CmsDocument already exists : {}", request);
      throw new ServiceException("CmsDocument already exists : {}" + request, e);
    }
  }

  /**
   * Update binary document. Force PKWare compression
   * ({@link CmsDocumentDao#COMPRESSION_TYPE_PK_FULL}) for new documents.
   * {@link CmsDocumentDao#COMPRESSION_TYPE_LZW_FULL} is for legacy documents only.
   * 
   * @param primaryKey primary key
   * @param request domain document
   */
  @Override
  public CmsDocument update(String primaryKey, CmsDocument request) {
    LOGGER.debug(PRIMARY_KEY, primaryKey);
    CmsDocument retval = null;

    gov.ca.cwds.data.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      if (StringUtils.isNotBlank(request.getDocAuth())) {
        doc.setDocAuth(request.getDocAuth().trim());
      }
      if (StringUtils.isNotBlank(request.getDocName())) {
        doc.setDocName(request.getDocName().trim());
      }
      if (StringUtils.isNotBlank(request.getDocServ())) {
        doc.setDocServ(request.getDocServ().trim());
      }
      if (StringUtils.isNotBlank(request.getCompressionMethod())) {
        doc.setCompressionMethod(request.getCompressionMethod().trim());
      }

      doc.setCompressionMethod(CmsDocumentDao.COMPRESSION_TYPE_PK_FULL);
      final List<CmsDocumentBlobSegment> blobs =
          dao.compressDoc(doc, request.getBase64Blob().trim());
      doc.getBlobSegments().clear();
      insertBlobs(doc, blobs);

      gov.ca.cwds.data.persistence.cms.CmsDocument managed =
          new gov.ca.cwds.data.persistence.cms.CmsDocument(doc);
      managed.setBlobSegments(new HashSet<>(blobs));

      try {
        dao.update(managed);
      } catch (Exception e) {
        LOGGER.error("FAILED TO UPDATE DOCUMENT! {}", e.getMessage(), e);
        throw new ServiceException("FAILED TO UPDATE DOCUMENT! {" + request + "}", e);
      }

      retval = new CmsDocument(managed);
      String base64Doc = dao.decompressDoc(managed);
      retval.setBase64Blob(base64Doc);
    } else {
      LOGGER.warn("EMPTY document! {}", primaryKey);
    }

    return retval;
  }

  protected String blobToInsert(CmsDocumentBlobSegment blob) {
    return new StringBuilder().append("INSERT INTO ").append(getCurrentSchema())
        .append(".TSBLOBT(DOC_HANDLE, DOC_SEGSEQ, DOC_BLOB) VALUES").append("('")
        .append(blob.getDocHandle()).append("','").append(blob.getSegmentSequence()).append("',x'")
        .append(DatatypeConverter.printHexBinary(blob.getDocBlob())).append("')").toString();
  }

  protected String blobsDelete() {
    return new StringBuilder().append("DELETE FROM ").append(getCurrentSchema())
        .append(".TSBLOBT WHERE DOC_HANDLE = ?").toString();
  }

  protected String getCurrentSchema() {
    return ((SessionFactoryImplementor) dao.getSessionFactory()).getSettings()
        .getDefaultSchemaName();
  }

  @SuppressFBWarnings("SQL_INJECTION_JDBC") // No SQL injection here
  private void insertBlobsJdbc(final Connection con,
      gov.ca.cwds.data.persistence.cms.CmsDocument doc, List<CmsDocumentBlobSegment> blobs)
      throws SQLException {
    try (final PreparedStatement delStmt = con.prepareStatement(blobsDelete());
        final Statement insStmt = con.createStatement()) {
      delStmt.setString(1, doc.getId());
      delStmt.executeUpdate();

      for (CmsDocumentBlobSegment blob : blobs) {
        insStmt.executeUpdate(blobToInsert(blob));
      }

      con.commit(); // WARNING: deadlock without this.
    } catch (SQLException e) {
      con.rollback();
      throw e;
    }
  }

  protected void deleteBlobs(String docId) {
    this.dao.grabSession().doWork(new WorkDeleteBlobSegments(docId, this));
  }

  protected void insertBlobs(gov.ca.cwds.data.persistence.cms.CmsDocument doc,
      List<CmsDocumentBlobSegment> blobs) {
    this.dao.grabSession().doWork(new WorkInsertBlobSegments(doc.getId(), this, blobs));
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public CmsDocument delete(String primaryKey) {
    LOGGER.debug(PRIMARY_KEY, primaryKey);
    CmsDocument retval = null;

    try {
      gov.ca.cwds.data.persistence.cms.CmsDocument managed = dao.delete(primaryKey);
      deleteBlobs(primaryKey);
      if (managed != null) {
        retval = new CmsDocument(managed);
        retval.setBase64Blob(dao.decompressDoc(managed));
      }
    } catch (Exception e) {
      LOGGER.error("FAILED TO DELETE DOCUMENT MAIN: {}", e.getMessage(), e);
      throw new ServiceException("FAILED TO DELETE DOCUMENT MAIN: {" + primaryKey + "}", e);
    }

    return retval;
  }
}
