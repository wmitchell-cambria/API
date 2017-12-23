package gov.ca.cwds.rest.services.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.cms.OtherCaseReferralDrmsDocumentDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate;
import gov.ca.cwds.rest.api.domain.cms.OtherCaseReferralDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.PostedDrmsDocument;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.util.DocUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;
import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
  private DrmsDocumentService drmsDocumentService;
  private DrmsDocumentTemplateService drmsDocumentTemplateService;
  private CmsDocumentService cmsDocumentService;

  /**
   * @param otherCaseReferralDrmsDocumentDao {@link Dao} handling {@link
   *     gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument} objects
   */
  @Inject
  public OtherCaseReferralDrmsDocumentService(
      OtherCaseReferralDrmsDocumentDao otherCaseReferralDrmsDocumentDao,
      DrmsDocumentService drmsDocumentService,
      DrmsDocumentTemplateService drmsDocumentTemplateService,
      CmsDocumentService cmsDocumentService) {
    super();
    this.otherCaseReferralDrmsDocumentDao = otherCaseReferralDrmsDocumentDao;
    this.drmsDocumentService = drmsDocumentService;
    this.drmsDocumentTemplateService = drmsDocumentTemplateService;
    this.cmsDocumentService = cmsDocumentService;
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
    throw new NotImplementedException("find not implement");
  }

  @Override
  public OtherCaseReferralDrmsDocument delete(String s) {
    throw new NotImplementedException("delete not implement");
  }

  @Override
  public OtherCaseReferralDrmsDocument update(String s, OtherCaseReferralDrmsDocument request) {
    throw new NotImplementedException("update not implement");
  }

  public void createDefaultSreenerNarrativeForNewReferral(
      ScreeningToReferral screeningToReferral,
      String referralId,
      Referral referral,
      MessageBuilder messageBuilder) {

    DrmsDocumentTemplate drmsTemplate =
        drmsDocumentTemplateService.findScreenerNarrativeTemplate(referral.getGovtEntityType());

    if (drmsTemplate != null) {
      CmsDocument cmsTemplate = cmsDocumentService.find(drmsTemplate.getCmsDocumentId());

      //Make Word doc from Template with new DOC_HANDLE etc.
      //TODO


      Date now = new Date();
      String docAuth = RequestExecutionContext.instance().getUserId();
      String docId = CmsKeyIdGenerator.generate(RequestExecutionContext.instance().getStaffId(), now);

      // TODO Generate handle the proper way. 0015441304100220*RAMESHA 00006
      String docHandle =
          CmsKeyIdGenerator.getUIIdentifierFromKey(docId).replace("-","")
              .concat("*")
              .concat(StringUtils.rightPad(docAuth.substring(0, 7), 8))
              .concat(StringUtils.leftPad(String.valueOf((int) (Math.random() * 99999)), 5, "0"));
      Short segments = 1;
      Long docLength = 1L;

      // TODO ???  The server name through which the document was added.
      String docServ = "AUTOCRTD";

      String docDate = new SimpleDateFormat(DomainObject.DATE_FORMAT).format(now);
      String docTime = new SimpleDateFormat(DomainObject.TIME_FORMAT).format(now);

      //TODO Not sure about numberring alghorithm. Will use random from "000" to "999" for now.
      String nameNumber = StringUtils.leftPad(String.valueOf((int)(Math.random() * 999)), 3, "0");
      String docName = drmsTemplate.getDocumentDOSFilePrefixName().substring(0,5).concat(nameNumber).concat(".DOC");


      String base64Blob = DatatypeConverter.printBase64Binary(
              screenerNarrativeFromTemplate(
                      screeningToReferral,
                      DatatypeConverter.parseBase64Binary(cmsTemplate.getBase64Blob())));

      CmsDocument cmsDocument = new CmsDocument(docHandle, segments, docLength,
              docAuth,
              docServ,
              docDate, docTime, docName,
              cmsTemplate.getCompressionMethod(),
              base64Blob );
      cmsDocument = cmsDocumentService.create(cmsDocument);

      DrmsDocument document = new DrmsDocument(
              new Date(),
              drmsTemplate.getThirdId(),
              null,
              RequestExecutionContext.instance().getStaffId(),
              cmsDocument.getId());

      PostedDrmsDocument posted = drmsDocumentService.create(document);
      OtherCaseReferralDrmsDocument otherCaseReferralDrmsDocument = new OtherCaseReferralDrmsDocument(
              posted.getId(),
              referral.getCountySpecificCode(),
              drmsTemplate.getTitleName(),
              referralId,
              EXTENSION_TYPE_WORD_DOCX,
              Math.round(cmsDocument.getDocLength() / 1024L));   //The size of the compressed document in kilobytes.

      create(otherCaseReferralDrmsDocument);

    }
  }

  private byte[] screenerNarrativeFromTemplate(ScreeningToReferral screeningToReferral, byte[] template){
    Map<String, String> keyValuePairs = new HashMap<>();
    // Get child name from allegations
    String childName = "Dummy Name";
    for(Allegation allegation: screeningToReferral.getAllegations()){
    }

    keyValuePairs.put("ChildName",childName);
    keyValuePairs.put("ReferralNumber",CmsKeyIdGenerator.getUIIdentifierFromKey(screeningToReferral.getReferralId()));
    keyValuePairs.put("bkBody",screeningToReferral.getReportNarrative());

    return DocUtils.createFromTemplateUseBookmarks(template, keyValuePairs);
  }

}
