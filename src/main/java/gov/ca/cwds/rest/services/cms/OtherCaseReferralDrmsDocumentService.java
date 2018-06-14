package gov.ca.cwds.rest.services.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.OtherCaseReferralDrmsDocumentDao;
import gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;

import static java.lang.Math.min;

/**
 * Business layer object to work on {@link OtherCaseReferralDrmsDocument}
 *
 * @author Intake Team 4
 */
public class OtherCaseReferralDrmsDocumentService
    implements TypedCrudsService<
        String, OtherCaseReferralDrmsDocument, OtherCaseReferralDrmsDocument> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(OtherCaseReferralDrmsDocumentService.class);

  public static final Short EXTENSION_TYPE_WORD_DOC = 6372;
  public static final Short EXTENSION_TYPE_WORD_DOCX = 6856;

  private OtherCaseReferralDrmsDocumentDao otherCaseReferralDrmsDocumentDao;

  /**
   * @param otherCaseReferralDrmsDocumentDao {@link Dao} handling {@link
   *     gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument} objects
   */
  @Inject
  public OtherCaseReferralDrmsDocumentService(
      OtherCaseReferralDrmsDocumentDao otherCaseReferralDrmsDocumentDao
      ) {
    this.otherCaseReferralDrmsDocumentDao = otherCaseReferralDrmsDocumentDao;
  }

  @Override
  public OtherCaseReferralDrmsDocument create(OtherCaseReferralDrmsDocument request) {

    try {
      gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument managed =
          new gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument(
              request,
              RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = otherCaseReferralDrmsDocumentDao.create(managed);
      return new OtherCaseReferralDrmsDocument(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("otherCaseReferralDrmsDocument already exists : {}", request);
      throw new ServiceException(e);
    }
  }

  @Override
  public OtherCaseReferralDrmsDocument find(String s) {
    throw new NotImplementedException("find not implemented");
  }

  @Override
  public OtherCaseReferralDrmsDocument delete(String s) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public OtherCaseReferralDrmsDocument update(String s, OtherCaseReferralDrmsDocument request) {
    throw new NotImplementedException("update not implemented");
  }
}
