package gov.ca.cwds.rest.api.domain.legacy;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
public class CmsDocReferralClient extends DomainObject implements Request, Response, Serializable {

  private static final long serialVersionUID = -9133158600820834189L;

  public static final class CmsDocReferralClientDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    public CmsDocReferralClientDetail() {}

    @JsonCreator
    public CmsDocReferralClientDetail(@JsonProperty("referlId") String referlId,
        @JsonProperty("clientId") String clientId,
        @JsonProperty("commonFirstName") String commonFirstName,
        @JsonProperty("commonMiddleName") String commonMiddleName,
        @JsonProperty("commonLastName") String commonLastName,
        @JsonProperty("birthDate") String birthDate, @JsonProperty("otherName") String otherName,
        @JsonProperty("nameType") String nameType, @JsonProperty("address") String address,
        @JsonProperty("addressType") String addressType) {
      this.referlId = referlId;
      this.clientId = clientId;
      this.commonFirstName = commonFirstName;
      this.commonMiddleName = commonMiddleName;
      this.commonLastName = commonLastName;
      this.birthDate = birthDate;
      this.otherName = otherName;
      this.nameType = nameType;
      this.address = address;
      this.addressType = addressType;
    }

    @NotEmpty
    private String referlId;

    @NotEmpty
    private String clientId;

    private String commonFirstName;
    private String commonMiddleName;
    private String commonLastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @JsonProperty(value = "birthDate")
    @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
    @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
        example = "2000-01-01")
    private String birthDate;

    private String otherName;

    private String nameType;

    private String address;

    private String addressType;

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((address == null) ? 0 : address.hashCode());
      result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
      result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
      result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
      result = prime * result + ((commonFirstName == null) ? 0 : commonFirstName.hashCode());
      result = prime * result + ((commonLastName == null) ? 0 : commonLastName.hashCode());
      result = prime * result + ((commonMiddleName == null) ? 0 : commonMiddleName.hashCode());
      result = prime * result + ((nameType == null) ? 0 : nameType.hashCode());
      result = prime * result + ((otherName == null) ? 0 : otherName.hashCode());
      result = prime * result + ((referlId == null) ? 0 : referlId.hashCode());
      return result;
    }

    /*
     * (non-Javadoc)
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
      CmsDocReferralClientDetail other = (CmsDocReferralClientDetail) obj;
      if (address == null) {
        if (other.address != null) {
          return false;
        }
      } else if (!address.equals(other.address)) {
        return false;
      }
      if (addressType == null) {
        if (other.addressType != null) {
          return false;
        }
      } else if (!addressType.equals(other.addressType)) {
        return false;
      }
      if (birthDate == null) {
        if (other.birthDate != null) {
          return false;
        }
      } else if (!birthDate.equals(other.birthDate)) {
        return false;
      }
      if (clientId == null) {
        if (other.clientId != null) {
          return false;
        }
      } else if (!clientId.equals(other.clientId)) {
        return false;
      }
      if (commonFirstName == null) {
        if (other.commonFirstName != null) {
          return false;
        }
      } else if (!commonFirstName.equals(other.commonFirstName)) {
        return false;
      }
      if (commonLastName == null) {
        if (other.commonLastName != null) {
          return false;
        }
      } else if (!commonLastName.equals(other.commonLastName)) {
        return false;
      }
      if (commonMiddleName == null) {
        if (other.commonMiddleName != null) {
          return false;
        }
      } else if (!commonMiddleName.equals(other.commonMiddleName)) {
        return false;
      }
      if (nameType == null) {
        if (other.nameType != null) {
          return false;
        }
      } else if (!nameType.equals(other.nameType)) {
        return false;
      }
      if (otherName == null) {
        if (other.otherName != null) {
          return false;
        }
      } else if (!otherName.equals(other.otherName)) {
        return false;
      }
      if (referlId == null) {
        if (other.referlId != null) {
          return false;
        }
      } else if (!referlId.equals(other.referlId)) {
        return false;
      }
      return true;
    }

    public String getReferlId() {
      return referlId;
    }

    public void setReferlId(String referlId) {
      this.referlId = referlId;
    }

    public String getClientId() {
      return clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = clientId;
    }

    public String getCommonFirstName() {
      return commonFirstName;
    }

    public void setCommonFirstName(String commonFirstName) {
      this.commonFirstName = commonFirstName;
    }

    public String getCommonMiddleName() {
      return commonMiddleName;
    }

    public void setCommonMiddleName(String commonMiddleName) {
      this.commonMiddleName = commonMiddleName;
    }

    public String getCommonLastName() {
      return commonLastName;
    }

    public void setCommonLastName(String commonLastName) {
      this.commonLastName = commonLastName;
    }

    /**
     * @return the otherName
     */
    public String getOtherName() {
      return otherName;
    }

    /**
     * @param otherName the otherName to set
     */
    public void setOtherName(String otherName) {
      this.otherName = otherName;
    }

    /**
     * @return the nameType
     */
    public String getNameType() {
      return nameType;
    }

    /**
     * @param nameType the nameType to set
     */
    public void setNameType(String nameType) {
      this.nameType = nameType;
    }

    /**
     * @return the address
     */
    public String getAddress() {
      return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
      this.address = address;
    }

    /**
     * @return the addressType
     */
    public String getAddressType() {
      return addressType;
    }

    /**
     * @param addressType the addressType to set
     */
    public void setAddressType(String addressType) {
      this.addressType = addressType;
    }


  }

  @NotEmpty
  @Size(min = 30, max = 30)
  @ApiModelProperty(required = true, readOnly = false, value = "30 char document handle",
      example = "0131351421120020*JONESMF 00004")
  private String id;

  @NotNull
  @ApiModelProperty(required = true, readOnly = false, example = "MJS000.DOC")
  private String docName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  @JsonProperty(value = "docAddedDate")
  @gov.ca.cwds.rest.validation.Date(format = DATE_FORMAT, required = false)
  @ApiModelProperty(required = false, readOnly = false, value = "yyyy-MM-dd",
      example = "2000-01-01")
  private String docAddedDate;

  private Set<CmsDocReferralClientDetail> details = new LinkedHashSet<CmsDocReferralClientDetail>();

  @JsonCreator
  public CmsDocReferralClient(@JsonProperty("docHandle") String docHandle,
      @JsonProperty("docName") String docName, @JsonProperty("docAddedDate") String docAddedDate,
      @JsonProperty("details") Set<CmsDocReferralClientDetail> details) {
    super();

    this.id = docHandle;
    this.docName = docName;
    this.docAddedDate = docAddedDate;
    this.details = details;
  }

  public CmsDocReferralClient(
      List<gov.ca.cwds.rest.api.persistence.cms.CmsDocReferralClient> docs) {
    super();

    for (gov.ca.cwds.rest.api.persistence.cms.CmsDocReferralClient entry : docs) {
      CmsDocReferralClientDetail detail = new CmsDocReferralClientDetail();
      detail.setReferlId(entry.getReferlId());
      detail.setClientId(entry.getClientId());
      detail.setCommonFirstName(entry.getCommonFirstName());
      detail.setCommonLastName(entry.getCommonLastName());
      detail.setCommonMiddleName(entry.getCommonMiddleName());

      detail.setAddress(entry.getAddress());
      detail.setAddressType(entry.getAddressType());
      detail.setNameType(entry.getNameType());
      detail.setOtherName(entry.getOtherName());
      details.add(detail);

      this.setDocName(entry.getDocName());
      this.setId(entry.getDocHandle());
      this.setDocAddedDate(DomainObject.cookDate(entry.getDocAddedDate()));
    }
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

    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((docName == null) ? 0 : docName.hashCode());
    result = prime * result + ((docAddedDate == null) ? 0 : docAddedDate.hashCode());
    result = prime * result + ((details == null) ? 0 : details.hashCode());

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
    CmsDocReferralClient other = (CmsDocReferralClient) obj;

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (docName == null) {
      if (other.docName != null) {
        return false;
      }
    } else if (!docName.equals(other.docName)) {
      return false;
    }
    if (docAddedDate == null) {
      if (other.docAddedDate != null) {
        return false;
      }
    } else if (!docAddedDate.equals(other.docAddedDate)) {
      return false;
    }
    if (details == null) {
      if (other.details != null) {
        return false;
      }
    } else if (!details.equals(other.details)) {
      return false;
    }

    return true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDocName() {
    return docName;
  }

  public void setDocName(String docName) {
    this.docName = docName;
  }

  public String getDocAddedDate() {
    return docAddedDate;
  }

  public void setDocAddedDate(String docAddedDate) {
    this.docAddedDate = docAddedDate;
  }

  public Set<CmsDocReferralClientDetail> getDetails() {
    return details;
  }

  public void setDetails(Set<CmsDocReferralClientDetail> details) {
    this.details = details;
  }


}
