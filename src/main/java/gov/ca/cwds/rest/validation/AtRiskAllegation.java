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

import gov.ca.cwds.rest.business.rules.R06511AtRiskAllegation;

/**
 * 
 * Annotation indicating that a Set of allegations must contain one of following types if 'At risk,
 * Sibling abused' (5001) is present. 2178 - General Neglect 2179 - Physical Abuse 2180 - Severe
 * Neglect 2181 - Sexual Abuse
 * 
 * 
 * @author CWDS API Team
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = R06511AtRiskAllegation.class)
public @interface AtRiskAllegation {

  String message() default "Allegations must contain one of following types if 'At risk, Sibling abused' (5001) is present: [2178 - General Neglect], [2179 - Physical Abuse], [2180 - Severe Neglect], [2181 - Sexual Abuse]";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
