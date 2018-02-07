package gov.ca.cwds.rest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation indicating that a county should not be "State of California"
 * R - 02366
 *
 * @author CWDS API Team
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = CountyValidator.class)
public @interface ValidCounty {
  String message() default "County is not valid";

  Class<?>[]groups() default {};

  Class<? extends Payload>[]payload() default {};
}