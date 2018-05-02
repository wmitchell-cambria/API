package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation indicating that a zip code should be null, empty (blank) or 5 digits long.
 *
 * @author CWDS API Team
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = ZipCodeValidator.class)
public @interface ValidZipCode {

  /**
   * Return the violation message
   * 
   * @return The violation message
   */
  String message() default "Zip should be empty, zero, or 5 digits";

  /**
   * @return groups
   */
  Class<?>[] groups() default {};

  /**
   * @return payload
   */
  Class<? extends Payload>[] payload() default {};

}
