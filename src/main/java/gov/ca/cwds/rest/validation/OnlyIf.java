package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation indicating that a property can only be set if the if property is set.
 * 
 * @author CWDS API Team
 */
@Target({ TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = IfThenValidator.class)
public @interface OnlyIf {
	String message() default "{property} can only be set if {ifProperty} is set";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String ifProperty();
	String property();
}
