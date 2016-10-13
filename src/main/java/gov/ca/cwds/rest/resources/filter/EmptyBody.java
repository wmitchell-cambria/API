package gov.ca.cwds.rest.resources.filter;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation indicating a resource method needs to have its body content emptied out.
 * 
 * @author CWDS API Team
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface EmptyBody {
}
