package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * test if value is a valid date
 * 
 * @author CWDS API Team
 *
 */
public class GenderValidator implements ConstraintValidator<Gender, String> {
  private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

  private String values;
  private boolean required;

  @Override
  public void initialize(Gender constraintAnnotation) {
    this.values = constraintAnnotation.values();
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (required || !Strings.isNullOrEmpty(value)) {
      if (value.equals("M"))
        return true;
      if (value.equals("F"))
        return true;
      if (value.equals("O"))
        return true;
      else {
        LOGGER.info("unable to validate gender value", values);
        return false;
      }
    }
    return true;
  }
}
