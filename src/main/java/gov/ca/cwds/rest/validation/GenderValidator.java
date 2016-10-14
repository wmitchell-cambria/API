package gov.ca.cwds.rest.validation;

import java.util.ArrayList;

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
  private static final Logger LOGGER = LoggerFactory.getLogger(GenderValidator.class);

  private String values;
  private ArrayList<String> validValue = new ArrayList<String>();
  private boolean required;

  @Override
  public void initialize(Gender constraintAnnotation) {
    this.values = constraintAnnotation.values();
    this.required = constraintAnnotation.required();
    validValue.add("M");
    validValue.add("F");
    validValue.add("O");
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (required || !Strings.isNullOrEmpty(value)) {
      if (validValue.indexOf(value) == -1) {
        LOGGER.info("unable to validate gender value", values);
        return false;
      } else {
        return true;
      }
    }
    return true;
  }
}
