package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountyValidator
        implements ConstraintValidator<ValidCounty, Short> {

  private static final Short STATE_OF_CALIFORNIA_SYSTEM_CODE = new Short("1126");

  @Override
  public void initialize(ValidCounty constraintAnnotation) {
    //nothing to initialize
  }

  /**
   *
   * R - 02366 County drop douwns
   *
   * @param value Government Entity Type id
   * @param context ConstraintValidatorContext
   * @return valudation result
   */
  @Override
  public boolean isValid(Short value, ConstraintValidatorContext context) {
    return !value.equals(STATE_OF_CALIFORNIA_SYSTEM_CODE);
  }
}
