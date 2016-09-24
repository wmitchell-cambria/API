package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation indicating that if the IF property is set the THEN property must also be set.
 * 
 * @author CWDS API Team
 */
@Target({ TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IfThenValidator.class)
public @interface IfThen {
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	boolean required() default true;

	String ifProperty();
	String thenProperty();
}
