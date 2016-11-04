package gov.ca.cwds.rest.api.domain.legacy;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.core.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} represents a CMS Document.
 * 
 * @author CWDS API Team
 */
@ApiModel
@InjectLinks({@InjectLink(value = "/{resource}/{id}", rel = "self", style = Style.ABSOLUTE,
    bindings = {@Binding(name = "id", value = "${instance.id}"),
        @Binding(name = "resource", value = Api.RESOURCE_CMS_DOCUMENT)})})
public class CmsDocument extends DomainObject implements Request, Response, Serializable {

  private static final long serialVersionUID = -9133158600820834189L;

  @NotEmpty
  @Size(min = 30, max = 30)
  @ApiModelProperty(required = true, readOnly = false, value = "Value overwritten on POST",
      example = "0131351421120020*JONESMF 00004")
  private String id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "docDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String docDate;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1000")
  private Long docLength;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private Short segmentCount;

  @NotEmpty
  @Size(min = 1, max = 8)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "RAMESHA")
  private String docAuth;

  @NotEmpty
  @Size(min = 8, max = 8)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "D7706001")
  private String docServ;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "CWSCMP01")
  private String compressionMethod;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "1234")
  private String docName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
  @JsonProperty(value = "docTime")
  @gov.ca.cwds.rest.validation.Date(format = TIME_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "HH:mm:ss", example = "19:59:07")
  private String docTime;

  @NotNull
  private String base64Blob;

  @JsonCreator
  public CmsDocument(@JsonProperty("id") String id,
      @JsonProperty("segmentCount") Short segmentCount, @JsonProperty("docLength") Long docLength,
      @JsonProperty("docAuth") String docAuth, @JsonProperty("docServ") String docServ,
      @JsonProperty("docDate") String docDate, @JsonProperty("docTime") String docTime,
      @JsonProperty("docName") String docName,
      @JsonProperty("compressionMethod") String compressionMethod,
      @JsonProperty("base64Blob") String base64Blob) {
    super();
    this.id = id;

    this.segmentCount = segmentCount;
    this.docLength = docLength;
    this.docAuth = docAuth;
    this.docServ = docServ;
    this.docDate = docDate;
    this.docTime = docTime;
    this.docName = docName;
    this.compressionMethod = compressionMethod;
    this.base64Blob = base64Blob;
  }

  public CmsDocument(gov.ca.cwds.rest.api.persistence.cms.CmsDocument doc) {
    this.id = doc.getId();

    this.segmentCount = doc.getSegmentCount();
    this.docLength = doc.getDocLength();
    this.docAuth = doc.getDocAuth();
    this.docServ = doc.getDocServ();
    this.docDate = DomainObject.cookDate(doc.getDocDate());
    this.docTime = DomainObject.cookTime(doc.getDocTime());
    this.docName = doc.getDocName();
    this.compressionMethod = doc.getCompressionMethod();

    // StringBuilder buf =
    // new StringBuilder(doc.getDocLength() != null ? doc.getDocLength().intValue() : 1024);
    // for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
    // buf.append(seg.getDocBlob());
    // }
    //
    // this.base64Blob = buf.toString();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
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

    result = prime * result + ((segmentCount == null) ? 0 : segmentCount.hashCode());
    result = prime * result + ((docLength == null) ? 0 : docLength.hashCode());
    result = prime * result + ((docAuth == null) ? 0 : docAuth.hashCode());
    result = prime * result + ((docServ == null) ? 0 : docServ.hashCode());
    result = prime * result + ((docDate == null) ? 0 : docDate.hashCode());
    result = prime * result + ((docTime == null) ? 0 : docTime.hashCode());
    result = prime * result + ((docName == null) ? 0 : docName.hashCode());
    result = prime * result + ((compressionMethod == null) ? 0 : compressionMethod.hashCode());
    result = prime * result + ((base64Blob == null) ? 0 : base64Blob.hashCode());

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
    CmsDocument other = (CmsDocument) obj;

    if (segmentCount == null) {
      if (other.segmentCount != null) {
        return false;
      }
    } else if (!segmentCount.equals(other.segmentCount)) {
      return false;
    }
    if (docLength == null) {
      if (other.docLength != null) {
        return false;
      }
    } else if (!docLength.equals(other.docLength)) {
      return false;
    }
    if (docAuth == null) {
      if (other.docAuth != null) {
        return false;
      }
    } else if (!docAuth.equals(other.docAuth)) {
      return false;
    }
    if (docServ == null) {
      if (other.docServ != null) {
        return false;
      }
    } else if (!docServ.equals(other.docServ)) {
      return false;
    }
    if (docDate == null) {
      if (other.docDate != null) {
        return false;
      }
    } else if (!docDate.equals(other.docDate)) {
      return false;
    }
    if (docTime == null) {
      if (other.docTime != null) {
        return false;
      }
    } else if (!docTime.equals(other.docTime)) {
      return false;
    }
    if (docName == null) {
      if (other.docName != null) {
        return false;
      }
    } else if (!docName.equals(other.docName)) {
      return false;
    }
    if (compressionMethod == null) {
      if (other.compressionMethod != null) {
        return false;
      }
    } else if (!compressionMethod.equals(other.compressionMethod)) {
      return false;
    }

    if (base64Blob == null) {
      if (other.base64Blob != null) {
        return false;
      }
    } else if (!base64Blob.equals(other.base64Blob)) {
      return false;
    }

    return true;
  }

  public String getDocDate() {
    return docDate;
  }

  public void setDocDate(String docDate) {
    this.docDate = docDate;
  }

  public Long getDocLength() {
    return docLength;
  }

  public void setDocLength(Long docLength) {
    this.docLength = docLength;
  }

  public Short getSegmentCount() {
    return segmentCount;
  }

  public void setSegmentCount(Short segmentCount) {
    this.segmentCount = segmentCount;
  }

  public String getDocAuth() {
    return docAuth;
  }

  public void setDocAuth(String docAuth) {
    this.docAuth = docAuth;
  }

  public String getDocServ() {
    return docServ;
  }

  public void setDocServ(String docServ) {
    this.docServ = docServ;
  }

  public String getCompressionMethod() {
    return compressionMethod;
  }

  public void setCompressionMethod(String compressionMethod) {
    this.compressionMethod = compressionMethod;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public String getDocTime() {
    return docTime;
  }

  public void setDocTime(String docTime) {
    this.docTime = docTime;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBase64Blob() {
    return base64Blob;
  }

  public void setBase64Blob(String base64Blob) {
    this.base64Blob = base64Blob;
  }


}
