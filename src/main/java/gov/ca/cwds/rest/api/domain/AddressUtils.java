package gov.ca.cwds.rest.api.domain;

import org.apache.commons.lang3.StringUtils;

public class AddressUtils {

  /**
   * Default zip code value in legacy/cms
   */
  private static final String DEFAULT_ZIP = "0";

  /**
   * If given zip code is null, blank/empty, or consists only white spaces then return the default
   * zip code otherwise return the provided valued trimmed.
   * 
   * @param zip The zip code
   * @return If given zip code is null, blank/empty, or consists only white spaces then return the
   *         default zip code otherwise return the provided valued trimmed.
   */
  public static String defaultIfBlank(String zip) {
    return StringUtils.isBlank(zip) ? DEFAULT_ZIP : zip.trim();
  }

}
