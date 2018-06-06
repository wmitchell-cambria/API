package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * {@link Response} adding an id to the {@link SpecialProjectReferral}
 * 
 * @author CWDS API Team
 */
public class PostedSpecialProjectReferral extends SpecialProjectReferral {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;
  
  /** 
   * Constructor
   * 
   * @param specialProjectReferral - persisted special project referral
   * 
   */
  public PostedSpecialProjectReferral(gov.ca.cwds.data.persistence.cms.SpecialProjectReferral spr) {
    super(spr);
    if (StringUtils.isBlank(spr.getId())) {
      throw new ServiceException("SpecialProjectReferral Third ID cannot be blank");
    }
    this.id = spr.getId();
  }
  
  /**
   * @return the Third ID
   */
  public String getId() {
    return id;
  }
}
