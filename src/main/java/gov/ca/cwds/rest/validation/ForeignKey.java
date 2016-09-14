package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import gov.ca.cwds.rest.jdbi.legacy.StaffPersonDao;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Checks to see that the value is a valid Foreign Key.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ForeignKeyValidator.class)
// @Constraint(validatedBy = StaffPersonIdValidator.class)
public @interface ForeignKey {
  String message() default "must be a valid {format}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String format() default "Foreign Key";

  boolean required() default true;

  String daoImplementer();
}
