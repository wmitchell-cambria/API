package gov.ca.cwds.rest.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks to see that the value is in a valid Zip Code format, five digits or zero.
 * 
 * @author CWDS API Team
 */
public class ZipCodeValidator implements ConstraintValidator<ZipCode, Integer> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ZipCodeValidator.class);

  private boolean required;

  @Override
  public void initialize(ZipCode constraintAnnotation) {
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    boolean success = false;
    if (required || !(value == null)) {
      try{
        String stringValue = Integer.toString(value);
        String string = "[0-9]{5}|0";
        Pattern pattern = Pattern.compile(string);
        Matcher matcher = pattern.matcher(stringValue);
        if (matcher.matches()) {
          success = true;
        }
      }catch (Exception e){
        LOGGER.info("Unable to validate Zip Code with value {}", value);
        return false;
      }
    }
    return success;
  }
}
