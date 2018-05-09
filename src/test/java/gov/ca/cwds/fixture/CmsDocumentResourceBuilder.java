package gov.ca.cwds.fixture;

import gov.ca.cwds.rest.api.domain.cms.CmsDocument;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class CmsDocumentResourceBuilder {

  String id = "0131351421120020*JONESMF 00004";
  String docDate = "1998-04-21";
  Long docLength = 1L;
  Short segmentCount = (short) 1234;
  String docAuth = "RAMESHA";
  String docServ = "D7706001";
  String compressionMethod = "CWSCMP01";
  String docName = "1234";
  String docTime = "19:59:07";
  String base64Blob = "0001181427010020*WILSOKA 00001";

  public CmsDocument build() {
    return new CmsDocument(id, segmentCount, docLength, docAuth, docServ, docDate, docTime, docName,
        compressionMethod, base64Blob);
  }

  public String getId() {
    return id;
  }

  public CmsDocumentResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public String getDocDate() {
    return docDate;
  }

  public CmsDocumentResourceBuilder setDocDate(String docDate) {
    this.docDate = docDate;
    return this;
  }

  public Long getDocLength() {
    return docLength;
  }

  public CmsDocumentResourceBuilder setDocLength(Long docLength) {
    this.docLength = docLength;
    return this;
  }

  public Short getSegmentCount() {
    return segmentCount;
  }

  public CmsDocumentResourceBuilder setSegmentCount(Short segmentCount) {
    this.segmentCount = segmentCount;
    return this;
  }

  public String getDocAuth() {
    return docAuth;
  }

  public CmsDocumentResourceBuilder setDocAuth(String docAuth) {
    this.docAuth = docAuth;
    return this;
  }

  public String getDocServ() {
    return docServ;
  }

  public CmsDocumentResourceBuilder setDocServ(String docServ) {
    this.docServ = docServ;
    return this;
  }

  public String getCompressionMethod() {
    return compressionMethod;
  }

  public CmsDocumentResourceBuilder setCompressionMethod(String compressionMethod) {
    this.compressionMethod = compressionMethod;
    return this;
  }

  public String getDocName() {
    return docName;
  }

  public CmsDocumentResourceBuilder setDocName(String docName) {
    this.docName = docName;
    return this;
  }

  public String getDocTime() {
    return docTime;
  }

  public CmsDocumentResourceBuilder setDocTime(String docTime) {
    this.docTime = docTime;
    return this;
  }

  public String getBase64Blob() {
    return base64Blob;
  }

  public CmsDocumentResourceBuilder setBase64Blob(String base64Blob) {
    this.base64Blob = base64Blob;
    return this;
  }

}
