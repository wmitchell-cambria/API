package gov.ca.cwds.rest.services.cms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.PostedScreening;
import gov.ca.cwds.rest.api.domain.ScreeningResponse;
import gov.ca.cwds.rest.api.domain.legacy.CmsDocument;
import gov.ca.cwds.rest.api.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.CmsDocumentDao;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.util.jni.LZWEncoder;

/**
 * Business layer object to work on {@link CmsDocument}.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentService.class);

  private CmsDocumentDao dao;

  /**
   * Constructor.
   * 
   * @param dao The {@link Dao} handling {@link CmsDocumentDao} objects.
   */
  public CmsDocumentService(CmsDocumentDao dao) {
    this.dao = dao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public CmsDocument find(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    CmsDocument retval = null;

    LOGGER.info("primaryKey=" + primaryKey);
    gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      if (doc.getCompressionMethod().endsWith("01")) {
        if (!LZWEncoder.isClassloaded()) {
          LOGGER.warn("LZW compression not enabled!");
        } else {
          LZWEncoder lzw = new LZWEncoder();
          try {
            File src = File.createTempFile("src", ".lzw");
            src.deleteOnExit();

            File tgt = File.createTempFile("tgt", ".doc");
            tgt.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(src);
            for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
              final byte[] bytes = DatatypeConverter.parseHexBinary(seg.getDocBlob().trim());
              fos.write(bytes, 0, bytes.length);
            }
            fos.flush();
            fos.close();

            lzw.fileCopyUncompress(src.getAbsolutePath(), tgt.getAbsolutePath());
          } catch (Exception e) {
            LOGGER.error("ERROR DECOMPRESSING LZW! " + e.getMessage());
            throw new RuntimeException(e);
          }
        }
      } else if (doc.getCompressionMethod().endsWith("02")) {
        LOGGER.warn("PK compression not enabled!");
      } else {
        LOGGER.warn("UNSUPPORTED compression method " + doc.getCompressionMethod());
      }
      retval = new CmsDocument(doc);
    } else {
      LOGGER.warn("EMPTY document!");
    }

    return retval;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    throw new NotImplementedException("Delete is not implemented");
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
  public PostedScreening create(Request request) {
    // assert (request instanceof ScreeningReference);
    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ScreeningResponse update(Serializable primaryKey, Request request) {
    assert (primaryKey instanceof Long);
    // assert (request instanceof ScreeningRequest);
    throw new NotImplementedException("Update is not implemented");
  }

}
