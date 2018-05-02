package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.AddressUtils;

/**
 * Validates that a zip code is either null, empty (blank), or 5 digits long.
 * 
 * @author CWDS API Team
 */
public class ZipCodeValidator implements ConstraintValidator<ValidZipCode, String> {

  private static final short ZIP_CODE_LENGHT = 5;

  @Override
  public void initialize(ValidZipCode constraintAnnotation) {
    // nothing to initialize
  }

  /**
   * Validates that provided zip code is either null, empty (blank), or 5 digits long.
   *
   * @param value Zip code
   * @param context ConstraintValidatorContext
   * @return validation result
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.isBlank(value) || AddressUtils.DEFAULT_ZIP.equals(value.trim())
        || ZIP_CODE_LENGHT == value.trim().length();
  }
}
