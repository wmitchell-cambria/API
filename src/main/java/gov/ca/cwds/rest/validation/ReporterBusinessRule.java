package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Checks for if LawEnforcemnt is present employerName should not exist and Street Number, Street Name and City name
 */

@Target({ TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = ReporterBRValidator.class)
@Documented
public @interface ReporterBusinessRule {

	String fieldName();

	String dependentFieldName();

	String message() default "{gov.ca.cwds.rest.validation.ReporterBusinessRule.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ReporterBusinessRule[] value();
	}

}
