package gov.ca.cwds.rest.services.cms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentService.class);

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
    LOGGER.debug("primaryKey={}", primaryKey);
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
   * Update binary document.
   * 
   * @param primaryKey primary key
   * @param request domain document
   */
  @Override
  public CmsDocument update(String primaryKey, CmsDocument request) {
    LOGGER.debug("primaryKey={}", primaryKey);

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

      final List<CmsDocumentBlobSegment> blobs =
          dao.compressPK(doc, request.getBase64Blob().trim());
      doc.getBlobSegments().clear();
      // doc.setBlobSegments(new LinkedHashSet<>());

      insertBlobs(doc, blobs);

      gov.ca.cwds.data.persistence.cms.CmsDocument managed =
          new gov.ca.cwds.data.persistence.cms.CmsDocument(doc);

      try {
        dao.update(managed);
        // dao.getSessionFactory().getCurrentSession().
      } catch (Exception e) {
        LOGGER.error("FAILED TO SAVE DOCUMENT MAIN: {}", e.getMessage(), e);
      }

      request.setCompressionMethod(managed.getCompressionMethod());
      request.setDocAuth(managed.getDocAuth());
      request.setDocName(managed.getDocName());
      request.setDocLength(managed.getDocLength());
      request.setSegmentCount(managed.getSegmentCount());
    } else {
      LOGGER.warn("EMPTY document! {}", primaryKey);
    }

    return request;
  }

  protected String blobToInsert(CmsDocumentBlobSegment blob) {
    return new StringBuilder().append("INSERT INTO ").append(getCurrentSchema())
        .append(".TSBLOBT(DOC_HANDLE, DOC_SEGSEQ, DOC_BLOB) VALUES").append("('")
        .append(blob.getDocHandle()).append("','").append(blob.getSegmentSequence()).append("',x'")
        .append(blob.getDocBlob()).append("')").toString();
  }

  protected String getCurrentSchema() {
    return ((SessionFactoryImplementor) dao.getSessionFactory()).getSettings()
        .getDefaultSchemaName();
  }

  protected void insertBlobs(gov.ca.cwds.data.persistence.cms.CmsDocument doc,
      List<CmsDocumentBlobSegment> blobs) {
    // dao.getSessionFactory().getCurrentSession().clear();
    try (final Connection con = getConnection()) {
      try (
          final PreparedStatement delStmt = con.prepareStatement(
              "DELETE FROM " + getCurrentSchema() + ".TSBLOBT WHERE DOC_HANDLE = ?");
          final Statement stmt = con.createStatement()) {

        delStmt.setString(1, doc.getId());
        delStmt.executeUpdate();

        for (CmsDocumentBlobSegment blob : blobs) {
          stmt.executeUpdate(blobToInsert(blob));
        }

        con.commit();
      } catch (Exception e) {
        con.rollback();
        throw e;
      }
    } catch (Exception e) {
      throw new ServiceException("FAILED TO INSERT DOCUMENT SEGMENTS", e);
    }
  }

  /**
   * Synchronize grabbing connections from the connection pool to prevent deadlocks in C3P0.
   * 
   * @return a connection
   * @throws SQLException on database error
   */
  protected synchronized Connection getConnection() throws SQLException {
    return dao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry()
        .getService(ConnectionProvider.class).getConnection();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public CmsDocument delete(String primaryKey) {
    throw new NotImplementedException("DELETE NOT IMPLEMENTED!");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public CmsDocument create(CmsDocument request) {
    throw new NotImplementedException("CREATE NOT IMPLEMENTED!");
  }

}
