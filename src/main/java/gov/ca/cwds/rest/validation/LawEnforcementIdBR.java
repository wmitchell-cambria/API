package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Checks to see that the LawEnforcementId is required for Badge Number and
 * EmployerName should not be Exist.
 */

@Target({ TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = LawEnforcementIdBRValidator.class)
@Documented
public @interface LawEnforcementIdBR {

	String fieldName();

	String dependentFieldName();

	String message() default "{format}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		LawEnforcementIdBR[] value();
	}

}
